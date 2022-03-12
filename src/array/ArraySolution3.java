package array;

import indefinite.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class ArraySolution3 {

    public static void main(String[] args) {
        ArraySolution3 arraySolution3 = new ArraySolution3();

    }

    // Leetcode problem: 106
    /*
     * Build tree from postorder and inorder traversal
     * */
    int index;

    public TreeNode buildTree(int[] inorder, int[] postorder, int left, int right, Map<Integer, Integer> map) {
        if (left > right)
            return null;

        TreeNode root = new TreeNode(postorder[index]);
        int inorderIndex = map.get(postorder[index]);
        index--;
        root.right = buildTree(inorder, postorder, inorderIndex + 1, right, map);
        root.left = buildTree(inorder, postorder, left, inorderIndex - 1, map);

        return root;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        index = postorder.length - 1;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return buildTree(inorder, postorder, 0, postorder.length - 1, map);
    }
}
