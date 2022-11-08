package geometry;

public class GeometrySolution {

    // Leetcode problem: 1232
    /*
     * Check If It Is a Straight Line.
     * Calculate slope & check if all of them are equal.
     * */
    public boolean checkStraightLine(int[][] coordinates) {
        if (coordinates.length == 2) {
            return true;
        }

        int[] coord1 = coordinates[0];
        int[] coord2 = coordinates[1];

        for (int i = 2; i < coordinates.length; i++) {
            int[] coord3 = coordinates[i];

            if ((coord1[1] - coord2[1]) * (coord2[0] - coord3[0]) !=
                    (coord1[0] - coord2[0]) * (coord2[1] - coord3[1])) {
                return false;
            }

            coord1 = coord2;
            coord2 = coord3;
        }

        return true;
    }
}
