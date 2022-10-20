package tree;

import java.util.*;

public class TreeTest {

    public static void main(String[] args) {
        TreeTest treeTest = new TreeTest();

        treeTest.burningTreeTest();
    }

    /*
     * Burn all the nodes starting from one node.
     * Amazon interview question.
     * Code source: https://www.youtube.com/watch?v=dj0q8D_hPdo
     * */
    private boolean burningTree(TreeNode node, Queue<TreeNode> queue, List<List<Integer>> result, List<Integer> current, int startNode) {
        if (node == null)
            return false;

        if (node.val == startNode) {
            current.add(node.val);
            result.add(new ArrayList<>(current));
            current.clear();

            if (node.left != null)
                queue.add(node.left);

            if (node.right != null)
                queue.add(node.right);

            return true;
        }

        boolean leftBurn = burningTree(node.left, queue, result, current, startNode);
        if (leftBurn) {
            int qSize = queue.size();
            while (qSize-- > 0) {
                TreeNode tmp = queue.poll();
                current.add(tmp.val);

                if (tmp.left != null)
                    queue.add(tmp.left);
                if (tmp.right != null)
                    queue.add(tmp.right);
            }

            current.add(node.val);
            result.add(new ArrayList<>(current));
            current.clear();

            if (node.right != null)
                queue.add(node.right);

            return true;
        }

        boolean rightBurn = burningTree(node.right, queue, result, current, startNode);
        if (rightBurn) {
            int qSize = queue.size();
            while (qSize-- > 0) {
                TreeNode tmp = queue.poll();
                current.add(tmp.val);

                if (tmp.left != null)
                    queue.add(tmp.left);
                if (tmp.right != null)
                    queue.add(tmp.right);
            }

            current.add(node.val);
            result.add(new ArrayList<>(current));
            current.clear();

            if (node.left != null)
                queue.add(node.left);

            return true;
        }

        return false;
    }

    public void burningTreeTest() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);

        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> result = new ArrayList<>();

        burningTree(root, queue, result, new ArrayList<>(), 1);

        while (!queue.isEmpty()) {
            List<Integer> row = new ArrayList<>();
            int qSize = queue.size();
            while (qSize-- > 0) {
                TreeNode tmp = queue.poll();
                row.add(tmp.val);

                if (tmp.left != null)
                    queue.add(tmp.left);
                if (tmp.right != null)
                    queue.add(tmp.right);
            }

            result.add(row);
        }

        System.out.println(result);
    }

    public String inorderTree(TreeNode node, Map<String, Integer> map) {
        if (node == null)
            return "";

        String str = "(";
        str += inorderTree(node.left, map);
        str += node.val;
        str += inorderTree(node.right, map);
        str += ")";

        int count = map.getOrDefault(str, 0);
        map.put(str, count + 1);
        return str;
    }

}
