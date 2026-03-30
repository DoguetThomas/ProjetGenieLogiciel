package services;

import services.StravaRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StravaCsvReader {

    public static List<StravaRecord> readCsv(String filePath) {

        /*liste de ligne CSV*/
        List<StravaRecord> records = new ArrayList<>();
        /*une ligne*/
        String line = "";
        /*Séparateur*/
        String cvsSplitBy = ",";

        /*formatteur pour les moments exacts*/
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        /*lecture d'une ligne à partir d'un buffer*/
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            //on ignore la premiere ligne qui contient les noms
            String headerLine = br.readLine();
            if (headerLine == null) return records;

            //boucle sur les lignes
            while ((line = br.readLine()) != null) {
                //on conserve les colones vides avec le -1
                String[] data = line.split(cvsSplitBy, -1);

                //création du moment de la ligne
                StravaRecord record = new StravaRecord();

                //utilisation de parseDoubleOrNull pour gérer les cases vides (sera un double ou null)
                record.airPower = parseDoubleOrNull(data[0]);
                record.formPower = parseDoubleOrNull(data[1]);
                record.groundTime = parseDoubleOrNull(data[2]);
                record.legSpringStiffness = parseDoubleOrNull(data[3]);
                record.power = parseDoubleOrNull(data[4]);
                record.verticalOscillation = parseDoubleOrNull(data[5]);
                record.cadence = parseDoubleOrNull(data[6]);
                record.datafile = data[7];
                record.distance = parseDoubleOrNull(data[8]);
                record.enhancedAltitude = parseDoubleOrNull(data[9]);
                record.enhancedSpeed = parseDoubleOrNull(data[10]);
                record.heartRate = parseDoubleOrNull(data[11]);
                record.positionLat = parseDoubleOrNull(data[12]);
                record.positionLong = parseDoubleOrNull(data[13]);

                record.timestamp = LocalDateTime.parse(data[14], formatter);

                records.add(record);
            }
        }
        catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        return records;
    }

    //création de parseDoubleOrNull pour gérer les cas (condition sur répétées)
    private static Double parseDoubleOrNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
