package controllers;

import com.garmin.fit.Decode;
import com.garmin.fit.MesgBroadcaster;
import com.garmin.fit.RecordMesg;
import com.garmin.fit.RecordMesgListener;
import com.garmin.fit.SessionMesg;
import com.garmin.fit.SessionMesgListener;
import dto.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.garmin.fit.*;

import java.io.*;
import java.util.*;

/**
 * Utility used by StravaSyncController to convert FIT binary data to normalized
 * sync DTOs.
 *
 * https://developer.garmin.com/fit/cookbook/decoding-activity-files/
 */
public final class FitImportUtil {

    private static final double SEMICIRCLE_TO_DEGREE = 180.0 / 2147483648.0;

    private FitImportUtil() {
    }

    public static FitActivityDto toSyncRequest(byte[] fitBytes) throws IOException {
        return toSyncRequest(fitBytes, null);
    }

    public static FitActivityDto toSyncRequest(byte[] fitBytes, String providedName) throws IOException {

        Decode decode = new Decode();
        MesgBroadcaster broadcaster = new MesgBroadcaster(decode);

        ActivityAccumulator accumulator = new ActivityAccumulator();

        broadcaster.addListener((RecordMesgListener) accumulator::onRecord);
        broadcaster.addListener((LapMesgListener) accumulator::onLap);
        broadcaster.addListener((SessionMesgListener) accumulator::onSession);

        try (InputStream is = new ByteArrayInputStream(fitBytes)) {
            if (!decode.checkFileIntegrity((BufferedInputStream) new BufferedInputStream(is))) {
                throw new IOException("FIT file integrity check failed.");
            }
        }

        try (InputStream is = new ByteArrayInputStream(fitBytes)) {
            broadcaster.run(is);
        }

        return accumulator.build(providedName);
    }

    /**
     * Internal accumulator to gather all FIT data before mapping to DTO.
     */
    private static class ActivityAccumulator {

        private String sportType;

        private double totalDistance = 0.0;
        private long totalTime = 0;

        private int avgHr = 0;
        private int maxHr = 0;

        private double avgSpeed = 0.0;
        private double avgPower = 0.0;

        private final List<GeoDto> routePoints = new ArrayList<>();
        private final List<SplitDto> splits = new ArrayList<>();

        // Raw metric storage
        private final Map<String, List<TimedValueDto>> metrics = new HashMap<>();

        /*
         * =========================
         * FIT MESSAGE HANDLERS
         * =========================
         */

        void onRecord(RecordMesg record) {
            long timestamp = record.getTimestamp() != null
                    ? record.getTimestamp().getTimestamp()
                    : 0;

            Double lat = toDegrees(record.getPositionLat());
            Double lon = toDegrees(record.getPositionLong());

            Float altitude = record.getAltitude();
            Float speed = record.getSpeed(); // m/s
            Short hr = record.getHeartRate();
            Integer power = record.getPower();
            Short cadence = record.getCadence();

            if (lat != null && lon != null) {
                routePoints.add(new GeoDto(lat, lon));
            }

            addMetric("altitude", timestamp, altitude);
            addMetric("speed", timestamp, speed != null ? speed * 3.6 : null);
            addMetric("heart-rate", timestamp, hr);
            addMetric("power", timestamp, power);
            addMetric("cadence", timestamp, cadence);

            if (speed != null) {
                addMetric("pace", timestamp, speed > 0 ? (1000.0 / (speed * 60)) : null);
            }
        }

        void onLap(LapMesg lap) {
            if (lap.getTotalDistance() != null && lap.getTotalElapsedTime() != null) {
                splits.add(new SplitDto(
                        splits.size() + 1,
                        lap.getTotalElapsedTime().intValue(),
                        lap.getAvgHeartRate()));
            }
        }

        void onSession(SessionMesg session) {
            if (session.getSport() != null) {
                sportType = session.getSport().name();
            }
            // optional refinement
            if (session.getSubSport() != null) {
                sportType += "_" + session.getSubSport().name();
            }
            if (session.getTotalDistance() != null) {
                totalDistance = session.getTotalDistance() / 1000.0;
            }
            if (session.getTotalElapsedTime() != null) {
                totalTime = session.getTotalElapsedTime().longValue();
            }
            if (session.getAvgHeartRate() != null) {
                avgHr = session.getAvgHeartRate();
            }
            if (session.getMaxHeartRate() != null) {
                maxHr = session.getMaxHeartRate();
            }
            if (session.getAvgSpeed() != null) {
                avgSpeed = session.getAvgSpeed() * 3.6;
            }
            if (session.getAvgPower() != null) {
                avgPower = session.getAvgPower();
            }
        }

        /*
         * =========================
         * METRICS BUILDERS
         * =========================
         */
        private void addMetric(String key, long timestamp, Number value) {
            if (value == null)
                return;

            metrics.computeIfAbsent(key, k -> new ArrayList<>())
                    .add(new TimedValueDto(
                            String.valueOf(timestamp), // or ISO format if preferred
                            value));
        }

        /*
         * =========================
         * FINAL DTO BUILD
         * =========================
         */

        FitActivityDto build(String providedId) {
            String id = (providedId != null && !providedId.trim().isEmpty()) ? providedId
                    : String.valueOf(System.currentTimeMillis());
            return new FitActivityDto(
                    id,
                    sportType,
                    totalDistance,
                    totalTime,
                    avgHr,
                    maxHr,
                    avgSpeed,
                    avgPower,
                    routePoints,
                    splits,
                    metrics);
        }

        /*
         * =========================
         * HELPERS
         * =========================
         */

        private Double toDegrees(Integer semicircles) {
            if (semicircles == null)
                return null;
            return semicircles * SEMICIRCLE_TO_DEGREE;
        }
    }
}