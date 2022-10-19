package tree;

import java.util.Map;

public class TreeTest {

    public static String inorderTree(TreeNode node, Map<String, Integer> map) {
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
