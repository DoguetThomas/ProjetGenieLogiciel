package model;

    /**
     * Représente les statistiques d'un kilomètre (un split)
     */
    public class Split {
        private final int km;
        private final int durationSeconds;
        private final int avgHeartRate;

        public Split(int km, int durationSeconds, int avgHeartRate) {
            this.km = km;
            this.durationSeconds = durationSeconds;
            this.avgHeartRate = avgHeartRate;
        }

        // Getters
        public int getKm() {
            return km;
        }

        public int getDurationSeconds() {
            return durationSeconds;
        }

        public int getAvgHeartRate() {
            return avgHeartRate;
        }


        @Override
        public String toString() {
            return "Km " + km + " : " + durationSeconds + " sec | FC Moy: " + avgHeartRate + " bpm";
        }
    }

