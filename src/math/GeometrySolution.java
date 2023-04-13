package math;

import java.util.*;

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

    // Leetcode problem: 850
    /*
     * Rectangle Area II.
     * Explanation: https://www.youtube.com/watch?v=TIojOkG1E4U&list=PLEvw47Ps6OBA3ysUO1XOho_6bFsqQ9NQ8&index=19
     * */
    public int rectangleArea(int[][] rectangles) {
        Set<Integer> xSet = new TreeSet<>();
        Set<Integer> ySet = new TreeSet<>();

        for (int[] rectangle : rectangles) {
            xSet.add(rectangle[0]);
            xSet.add(rectangle[2]);
            ySet.add(rectangle[1]);
            ySet.add(rectangle[3]);
        }

        List<Integer> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();
        Map<Integer, Integer> xIndex = new HashMap<>();
        Map<Integer, Integer> yIndex = new HashMap<>();

        int index = 0;
        for (int x : xSet) {
            xList.add(x);
            xIndex.put(x, index++);
        }
        index = 0;
        for (int y : ySet) {
            yList.add(y);
            yIndex.put(y, index++);
        }

        boolean[][] taken = new boolean[xSet.size()][ySet.size()];

        long area = 0;
        long mod = 1000000007;
        for (int[] rectangle : rectangles) {
            for (int xIdx = xIndex.get(rectangle[0]); xIdx < xIndex.get(rectangle[2]); xIdx++) {
                for (int yIdx = yIndex.get(rectangle[1]); yIdx < yIndex.get(rectangle[3]); yIdx++) {
                    if (!taken[xIdx][yIdx]) {
                        taken[xIdx][yIdx] = true;
                        long width = (long) xList.get(xIdx + 1) - xList.get(xIdx);
                        long height = (long) yList.get(yIdx + 1) - yList.get(yIdx);

                        area = (area + (width * height) % mod) % mod;
                    }
                }
            }
        }

        return (int) area;
    }
}
