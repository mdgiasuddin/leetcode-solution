package tree;

public class TreeTest2 {
    public static void main(String[] args) {
        TreeTest treeTest = new TreeTest();
        TreeTest2 treeTest2 = new TreeTest2();

        int[] sequence = {1, 3, 7, 8};
        TreeNode root = treeTest.buildSimpleTree();

        treeTest2.validSequenceRootToLeaf(root, sequence);
    }

    // Leetcode challenge
    /*
     * Problem source: Tech Dose
     * */
    public void validSequenceRootToLeaf(TreeNode root, int[] sequence) {
        System.out.println(validSequenceRootToLeaf(root, 0, sequence));
    }

    private boolean validSequenceRootToLeaf(TreeNode node, int idx, int[] sequence) {
        if (node == null || node.val != sequence[idx] || (idx == sequence.length - 1 &&
                (node.left != null || node.right != null))) {
            return false;
        }

        if (idx == sequence.length - 1) {
            return true;
        }

        return validSequenceRootToLeaf(node.left, idx + 1, sequence) ||
                validSequenceRootToLeaf(node.right, idx + 1, sequence);
    }
}
