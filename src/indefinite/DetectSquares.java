package indefinite;

import java.util.*;

public class DetectSquares {
    List<List<Integer>> points;
    Map<List<Integer>, Integer> count;

    public DetectSquares() {
        points = new ArrayList<>();
        count = new HashMap<>();
    }

    public void add(int[] point) {
        List<Integer> p = Arrays.asList(point[0], point[1]);
        int c = count.getOrDefault(p, 0);

        count.put(p, c + 1);
        points.add(p);
    }

    public int count(int[] point) {
        int res = 0;
        for (List<Integer> p : points) {
            if (Math.abs(p.get(0) - point[0]) != Math.abs(p.get(1) - point[1]) || (p.get(0) == point[0] && p.get(1) == point[1]))
                continue;

            res += count.getOrDefault(Arrays.asList(point[0], p.get(1)), 0) * count.getOrDefault(Arrays.asList(p.get(0), point[1]), 0);
        }

        return res;
    }
}
