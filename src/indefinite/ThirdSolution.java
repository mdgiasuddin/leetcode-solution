package indefinite;

import tree.TreeNode;

import java.util.*;

public class ThirdSolution {

    public static void main(String[] args) {
        ThirdSolution thirdSolution = new ThirdSolution();
        int[] arr = new int[10];
        thirdSolution.containsNearbyAlmostDuplicate(arr, 10, 5);
    }

    TreeNode res = new TreeNode(0), temp = res;

    public TreeNode increasingBST(TreeNode root) {

        if (root != null) {
            increasingBST(root.left);

            temp.right = new TreeNode(root.val);
            temp = temp.right;

            increasingBST(root.right);
        }
        return res.right;
    }

    List<String> tmp;
    List<List<String>> result;
    int h;

    int height(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    void find(TreeNode root, int i, int j) {
        if (root == null) return;
        if (result.size() == i)
            result.add(new ArrayList<>(tmp));
        result.get(i).set(j, Integer.toString(root.val));
        find(root.left, i + 1, j - (1 << (h - i - 2)));
        find(root.right, i + 1, j + (1 << h - i - 2));
        return;
    }

    public List<List<String>> printTree(TreeNode root) {
        this.h = height(root);
        int m = (1 << h) - 1;
        result = new ArrayList<>();
        tmp = new ArrayList<>();
        for (int i = 0; i < m; i++) tmp.add("");
        find(root, 0, (m - 1) / 2);
        return result;
    }

    int sum = 0;

    public int distributeCoins(TreeNode root) {
        calc(root);
        return sum;
    }

    int calc(TreeNode root) {
        if (root == null)
            return 0;

        int l = calc(root.left);
        int r = calc(root.right);

        sum += Math.abs(l) + Math.abs(r);
        return l + r + root.val - 1;
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        Map<Integer, List<Integer>> map = new TreeMap<>();
        verticalTraversal(root, 0, map);

        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            Collections.sort(entry.getValue());
            result.add(entry.getValue());
        }
        return result;
    }

    void verticalTraversal(TreeNode root, int verticalLevel, Map<Integer, List<Integer>> map) {
        if (root == null)
            return;

        if (map.containsKey(verticalLevel)) {
            List<Integer> verticalList = map.get(verticalLevel);
            verticalList.add(root.val);
        } else {
            List<Integer> verticalList = new ArrayList<>();
            verticalList.add(root.val);
            map.put(verticalLevel, verticalList);
        }
        verticalTraversal(root.left, verticalLevel - 1, map);
        verticalTraversal(root.right, verticalLevel + 1, map);
    }

    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        recurse(root, res, 0);
        return res;
    }

    void recurse(TreeNode root, List<Integer> res, int level) {
        if (root == null)
            return;

        if (level == res.size()) {
            res.add(root.val);
        } else {
            res.set(level, Math.max(res.get(level), root.val));
        }
        recurse(root.left, res, level + 1);
        recurse(root.right, res, level + 1);
    }

    // Leetcode problem: 220
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (k == 0 || t < 0)
            return false;

        TreeSet<Long> set = new TreeSet<>();

        for (int i = 0; i < nums.length; i++) {
            Long floor = set.floor((long) nums[i] + t);
            Long ceiling = set.ceiling((long) nums[i] - t);

            if (floor != null && floor >= nums[i] || ceiling != null && ceiling <= nums[i])
                return true;

            if (i >= k)
                set.remove((long) nums[i - k]);

            set.add((long) nums[i]);

        }

        return false;
    }

}
