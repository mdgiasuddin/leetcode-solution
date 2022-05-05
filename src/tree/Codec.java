package tree;

import java.util.ArrayList;
import java.util.List;

public class Codec {

    // Leetcode problem: 297
    // Serialize is simple. Just preorder traversal.
    public String serialize(TreeNode root) {
        List<String> preorder = new ArrayList<>();

        serialize(root, preorder);

        return String.join(",", preorder);
    }

    public void serialize(TreeNode root, List<String> preorder) {
        if (root == null) {
            preorder.add("NULL");
            return;
        }

        preorder.add(String.valueOf(root.val));
        serialize(root.left, preorder);
        serialize(root.right, preorder);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] preorder = data.split(",");

        // Need a global variable to track the current index.
        int[] index = new int[1];

        return deserialize(preorder, index);
    }

    public TreeNode deserialize(String[] preorder, int[] index) {
        if (preorder[index[0]].equals("NULL")) {
            index[0]++;
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(preorder[index[0]]));
        index[0]++;
        node.left = deserialize(preorder, index);

        // Do not need to increment the index again. Every traversal the index will be increased automatically.
        node.right = deserialize(preorder, index);

        return node;
    }
}
