import model.StravaRecord;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class StravaRecordTest {

    @Test
    void testFullConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.of(2026, 4, 10, 10, 0);

        StravaRecord record = new StravaRecord(
                10.0, 20.0, 300.0, 40.0, 250.0, 50.0, 180.0,
                "activity.csv", 10500.0, 150.0, 12.5, 145.0,
                48.11, -1.67, now
        );

        assertEquals(10.0, record.getAirPower());
        assertEquals(20.0, record.getFormPower());
        assertEquals(300.0, record.getGroundTime());
        assertEquals(40.0, record.getLegSpringStiffness());
        assertEquals(250.0, record.getPower());
        assertEquals(50.0, record.getVerticalOscillation());
        assertEquals(180.0, record.getCadence());
        assertEquals("activity.csv", record.getDatafile());
        assertEquals(10500.0, record.getDistance());
        assertEquals(150.0, record.getEnhancedAltitude());
        assertEquals(12.5, record.getEnhancedSpeed());
        assertEquals(145.0, record.getHeartRate());
        assertEquals(48.11, record.getPositionLat());
        assertEquals(-1.67, record.getPositionLong());
        assertEquals(now, record.getTimestamp());
    }

    @Test
    void testSettersAndDefaultConstructor() {
        StravaRecord record = new StravaRecord();
        LocalDateTime now = LocalDateTime.now();

        record.setAirPower(1.0);
        record.setFormPower(2.0);
        record.setGroundTime(3.0);
        record.setLegSpringStiffness(4.0);
        record.setPower(5.0);
        record.setVerticalOscillation(6.0);
        record.setCadence(7.0);
        record.setDatafile("test.csv");
        record.setDistance(8.0);
        record.setEnhancedAltitude(9.0);
        record.setEnhancedSpeed(10.0);
        record.setHeartRate(11.0);
        record.setPositionLat(12.0);
        record.setPositionLong(13.0);
        record.setTimestamp(now);

        assertEquals(1.0, record.getAirPower());
        assertEquals("test.csv", record.getDatafile());
        assertEquals(now, record.getTimestamp());
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.of(2026, 4, 10, 10, 0);
        StravaRecord record = new StravaRecord();
        record.setDatafile("run.csv");
        record.setDistance(5000.0);
        record.setTimestamp(now);

        String result = record.toString();

        assertTrue(result.contains("datafile='run.csv'"));
        assertTrue(result.contains("distance=5000.0"));
        assertTrue(result.contains("timestamp=" + now));
        assertTrue(result.startsWith("StravaRecord{"));
    }
}