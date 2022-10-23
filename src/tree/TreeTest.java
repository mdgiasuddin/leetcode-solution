package tree;

import java.util.*;

public class TreeTest {

    public static void main(String[] args) {
        TreeTest treeTest = new TreeTest();

        treeTest.validateTreeAsSumTree();
    }

    public TreeNode buildUpTree() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        TreeNode node1 = new TreeNode(4);
        node1.right = new TreeNode(8);

        TreeNode node2 = new TreeNode(5);
        node2.left = new TreeNode(9);
        node2.left.left = new TreeNode(13);
        node2.left.right = new TreeNode(14);

        root.left.left = node1;
        root.left.right = node2;

        TreeNode node3 = new TreeNode(6);
        node3.right = new TreeNode(10);
        node3.right.left = new TreeNode(15);
        node3.right.right = new TreeNode(16);

        TreeNode node4 = new TreeNode(7);
        node4.left = new TreeNode(11);
        node4.right = new TreeNode(12);
        node4.right.left = new TreeNode(17);

        root.right.left = node3;
        root.right.right = node4;

        return root;
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
        TreeNode root = buildUpTree();

        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> result = new ArrayList<>();

        burningTree(root, queue, result, new ArrayList<>(), 6);

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

    private void traverseLeftBoundary(TreeNode node, List<Integer> list) {
        if (node == null)
            return;

        if (node.left != null) {
            list.add(node.val);
            traverseLeftBoundary(node.left, list);
        } else if (node.right != null) {
            list.add(node.val);
            traverseLeftBoundary(node.right, list);
        }
    }

    private void traverseRightBoundary(TreeNode node, List<Integer> list) {
        if (node == null)
            return;

        if (node.right != null) {
            traverseRightBoundary(node.right, list);
            list.add(node.val);
        } else if (node.left != null) {
            traverseRightBoundary(node.left, list);
            list.add(node.val);
        }
    }

    private void traverseLeafNodes(TreeNode node, List<Integer> list) {
        if (node == null)
            return;

        if (node.left == null && node.right == null) {
            list.add(node.val);
            return;
        }

        traverseLeafNodes(node.left, list);
        traverseLeafNodes(node.right, list);
    }

    /*
     * Traverse tree anti clock-wise boundary.
     * root to left boundary, leaf nodes then right boundary from bottom to top.
     * Amazon interview question.
     * Source: https://www.geeksforgeeks.org/boundary-traversal-of-binary-tree/
     * */
    public void boundaryTraversal() {
        TreeNode root = buildUpTree();

        List<Integer> result = new ArrayList<>();
        result.add(root.val);

        traverseLeftBoundary(root.left, result);
        traverseLeafNodes(root, result);
        traverseRightBoundary(root.right, result);

    }

    /*
     * Path from root to a target node.
     * Amazon interview question.
     * Code source: https://www.geeksforgeeks.org/print-path-root-given-node-binary-tree/
     * */
    public void pathRootToNode() {
        TreeNode root = buildUpTree();
        List<String> result = new ArrayList<>();

        if (pathRootToNode(root, 20, result)) {
            System.out.println("Path: " + String.join("->", result));
        } else {
            System.out.println("No valid path!");
        }
    }

    public boolean pathRootToNode(TreeNode node, int target, List<String> result) {
        if (node == null) {
            return false;
        }

        result.add(String.valueOf(node.val));
        if (node.val == target) {
            return true;
        }

        if (pathRootToNode(node.left, target, result) ||
                pathRootToNode(node.right, target, result)) {
            return true;
        }

        result.remove(result.size() - 1);
        return false;
    }

    /*
     * Amazon interview question.
     * Code source: https://www.geeksforgeeks.org/check-if-a-given-binary-tree-is-sumtree/
     * */
    public void validateTreeAsSumTree() {
        TreeNode root = new TreeNode(40);
        root.left = new TreeNode(10);
        root.right = new TreeNode(10);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(6);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(7);

        System.out.println(validateTreeAsSumTree(root));

    }

    private boolean validateTreeAsSumTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        if (validateTreeAsSumTree(root.left) && validateTreeAsSumTree(root.right)) {
            int left;
            if (root.left == null)
                left = 0;
            else if (root.left.left == null && root.left.right == null)
                left = root.left.val;
            else
                left = 2 * root.left.val;

            int right;
            if (root.right == null)
                right = 0;
            else if (root.right.left == null && root.right.right == null)
                right = root.right.val;
            else
                right = 2 * root.right.val;

            return root.val == left + right;
        }

        return false;
    }
}
