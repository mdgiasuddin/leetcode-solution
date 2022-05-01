package indefinite;

import java.util.*;

public class ThirdSolution {
//    int maxPath = 0;
//
//    public int diameterOfBinaryTree(TreeNode root) {
//        diameterOfBinaryTreeAux(root);
//        return maxPath;
//    }
//
//    private int diameterOfBinaryTreeAux(TreeNode root) {
//        if (root == null) return 0;
//        int left = diameterOfBinaryTreeAux(root.left), right = diameterOfBinaryTreeAux(root.right);
//        maxPath = Math.max(maxPath, left + right);
//        return Math.max(left, right) + 1;
//    }

    int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        diameterOfBinaryTreeAux(root);
        return maxDiameter;
    }

    public int diameterOfBinaryTreeAux(TreeNode root) {
        if (root == null)
            return 0;

        int leftDiameter = diameterOfBinaryTreeAux(root.left);
        int rightDiameter = diameterOfBinaryTreeAux(root.right);

        maxDiameter = Math.max(maxDiameter, leftDiameter + rightDiameter);
        return 1 + Math.max(leftDiameter, rightDiameter);
    }

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null)
            return null;
        if (root.val == val)
            return root;
        if (val < root.val)
            return searchBST(root.left, val);
        return searchBST(root.right, val);
    }

    int minDiff = Integer.MAX_VALUE;
    TreeNode prev = null;

    public int minDiffInBST(TreeNode root) {
        inorder(root);
        return minDiff;
    }

    void inorder(TreeNode node) {
        if (node == null)
            return;

        inorder(node.left);
        if (prev != null) {
            minDiff = Math.min(minDiff, node.val - prev.val);
        }
        prev = node;
        inorder(node.right);
    }

    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null)
            return null;
        if (root1 == null)
            return root2;
        if (root2 == null)
            return root1;

        TreeNode root = new TreeNode(root1.val + root2.val);
        root.left = mergeTrees(root1.left, root2.left);
        root.right = mergeTrees(root1.right, root2.right);

        return root;
    }


    int kthMinimum = Integer.MAX_VALUE, kSoFar = 0;

    public int kthSmallest(TreeNode root, int k) {
        kthSmallestAux(root, k);
        return kthMinimum;
    }

    public void kthSmallestAux(TreeNode root, int k) {
        if (root == null)
            return;

        kthSmallestAux(root.left, k);
        kSoFar++;
        if (kSoFar == k)
            kthMinimum = root.val;
        kthSmallestAux(root.right, k);
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

    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if (depth == 1) {
            TreeNode newRoot = new TreeNode(val);
            newRoot.left = root;
            return newRoot;
        }

        return addOneRow(root, val, depth, 2);
    }

    public TreeNode addOneRow(TreeNode root, int val, int depth, int parentOfLevel) {
        if (root == null && parentOfLevel == depth + 1)
            return new TreeNode(val);
        if (root == null)
            return null;

        if (parentOfLevel < depth) {
            root.left = addOneRow(root.left, val, depth, parentOfLevel + 1);
            root.right = addOneRow(root.right, val, depth, parentOfLevel + 1);
        } else if (parentOfLevel == depth) {

            TreeNode left = root.left, right = root.right;
            TreeNode newLeft = new TreeNode(val), newRight = new TreeNode(val);

            root.left = newLeft;
            newLeft.left = left;
            root.right = newRight;
            newRight.right = right;
        }

        return root;
    }

    public int deepestLeavesSum(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);
        int sum = 0;

        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            if (temp == null) {
                if (queue.isEmpty())
                    break;
                sum = 0;
                queue.add(null);
            } else {
                sum += temp.val;
                if (temp.left != null)
                    queue.add(temp.left);
                if (temp.right != null)
                    queue.add(temp.right);
            }
        }

        return sum;
    }

    public boolean isEvenOddTree(TreeNode root) {
        int maxMin = Integer.MIN_VALUE;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);
        boolean evenLevel = true;

        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();

            if (temp == null) {
                if (queue.isEmpty())
                    break;
                maxMin = evenLevel ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                evenLevel = !evenLevel;
                queue.add(null);
            } else {
                if ((evenLevel && (temp.val <= maxMin || temp.val % 2 == 0))
                        || (!evenLevel && (temp.val >= maxMin || temp.val % 2 == 1)))
                    return false;

                maxMin = temp.val;

                if (temp.left != null)
                    queue.add(temp.left);
                if (temp.right != null)
                    queue.add(temp.right);
            }
        }

        return true;
    }

//    public List<Integer> rightSideView(TreeNode root) {
//        List<Integer> result = new ArrayList<>();
//
//        if (root == null)
//            return result;
//
//        Queue<TreeNode> queue = new LinkedList<>();
//        queue.add(root);
//        queue.add(null);
//
//        while (!queue.isEmpty()) {
//            TreeNode temp = queue.poll();
//            if (queue.peek() == null) {
//                queue.poll();
//                result.add(temp.val);
//                if (temp.left != null)
//                    queue.add(temp.left);
//                if (temp.right != null)
//                    queue.add(temp.right);
//                if (queue.isEmpty())
//                    break;
//                queue.add(null);
//            } else {
//                if (temp.left != null)
//                    queue.add(temp.left);
//                if (temp.right != null)
//                    queue.add(temp.right);
//            }
//
//        }
//        return result;
//    }


//    int maxDepth = 0;
//
//    public List<Integer> rightSideView(TreeNode root) {
//
//        List<Integer> result = new ArrayList<Integer>();
//        rightView(root, result, 0);
//        return result;
//
//    }
//
//    public void rightView(TreeNode root, List<Integer> result, int currDepth) {
//
//        if (root == null) return;
//
//        currDepth++;
//        if (currDepth > maxDepth) result.add(root.val);
//        maxDepth = Math.max(maxDepth, currDepth);
//
//        rightView(root.right, result, currDepth);
//        rightView(root.left, result, currDepth);
//
//    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        rightSideView(root, result, 0);
        return result;

    }

    public void rightSideView(TreeNode root, List<Integer> result, int level) {
        if (root == null)
            return;

        if (level == result.size()) {
            result.add(root.val);
        } else {
            result.set(level, root.val);
        }
        rightSideView(root.left, result, level + 1);
        rightSideView(root.right, result, level + 1);
    }

    public void getLeafNodes(TreeNode root, List<Integer> list) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            list.add(root.val);
        }
        getLeafNodes(root.left, list);
        getLeafNodes(root.right, list);
    }

    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        getLeafNodes(root1, list1);
        getLeafNodes(root2, list2);

        if (list1.size() != list2.size())
            return false;

        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i) != list2.get(i))
                return false;
        }
        return true;
    }

    public boolean isCompleteTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);

        int requiredLength = 1, currentLength = 0;
        boolean leftReachable = true;
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();

            if (temp == null) {
                if (!queue.isEmpty()) {
                    if (currentLength < requiredLength)
                        return false;
                    queue.add(null);
                    requiredLength *= 2;
                    currentLength = 0;
                } else
                    break;
            } else {
                currentLength++;
                if ((!leftReachable && (temp.left != null || temp.right != null))
                        || (temp.left == null && temp.right != null))
                    return false;
                if (temp.left != null)
                    queue.add(temp.left);
                if (temp.right != null)
                    queue.add(temp.right);
                if (temp.left == null || temp.right == null)
                    leftReachable = false;
            }
        }
        return true;
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
        int n = h + 1, m = (1 << h) - 1;
        result = new ArrayList<>();
        tmp = new ArrayList<>();
        for (int i = 0; i < m; i++) tmp.add("");
        find(root, 0, (m - 1) / 2);
        return result;
    }

    int evenSum = 0;

    public int getChildNodeSum(TreeNode root) {
        if (root == null)
            return 0;
        return (root.left == null ? 0 : root.left.val) + (root.right == null ? 0 : root.right.val);

    }

    void sumEvenGrandparentAux(TreeNode root) {
        if (root == null)
            return;
        if (root.val % 2 == 0) {
            evenSum += getChildNodeSum(root.left);
            evenSum += getChildNodeSum(root.right);
        }
        sumEvenGrandparentAux(root.left);
        sumEvenGrandparentAux(root.right);
    }

    public int sumEvenGrandparent(TreeNode root) {
        sumEvenGrandparentAux(root);
        return evenSum;
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

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);

        if (val < root.val)
            root.left = insertIntoBST(root.left, val);
        else
            root.right = insertIntoBST(root.right, val);

        return root;
    }

    boolean found = false;

    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        findTarget(root, k, set);

        return found;
    }

    private void findTarget(TreeNode root, int k, Set<Integer> set) {
        if (root == null)
            return;

        if (set.contains(k - root.val)) {
            found = true;
            return;
        } else {
            set.add(root.val);
        }
        findTarget(root.left, k, set);
        findTarget(root.right, k, set);
    }
}
