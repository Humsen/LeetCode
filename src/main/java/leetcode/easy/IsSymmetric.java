package leetcode.easy;

import leetcode.common.TreeNode;

import java.util.LinkedList;

/**
 * 101. 对称二叉树
 *
 * @author 何明胜 husen@hemingsheng.cn
 * @see <a href="https://leetcode-cn.com/problems/symmetric-tree/">https://leetcode-cn.com/problems/symmetric-tree/</a>
 * @since 2020-05-31 15:01
 */
public class IsSymmetric {
    /**
     * 采用广度优先比遍历
     * 每次放入当前行，并将队列前后的元素对比，直到当前行为空
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        LinkedList<TreeNode> queueOdd = new LinkedList<>();
        LinkedList<TreeNode> queueEven = new LinkedList<>();
        queueOdd.add(root.left);
        queueOdd.add(root.right);
        boolean odd = true;
        while (!queueOdd.isEmpty() || !queueEven.isEmpty()) {
            if (odd = !odd) {
                while (!queueEven.isEmpty()) {
                    if (isNotSymmetric(queueEven.getFirst(), queueEven.getLast())) {
                        return false;
                    }
                    addQueue(queueOdd, queueEven.removeFirst(), queueEven.removeLast());
                }
            } else {
                while (!queueOdd.isEmpty()) {
                    if (isNotSymmetric(queueOdd.getFirst(), queueOdd.getLast())) {
                        return false;
                    }
                    addQueue(queueEven, queueOdd.removeFirst(), queueOdd.removeLast());
                }
            }
        }

        return true;
    }

    private boolean isNotSymmetric(TreeNode start, TreeNode end) {
        if (start == null || end == null) {
            return start != end;
        }

        return start.val != end.val;
    }

    private void addQueue(LinkedList<TreeNode> queue, TreeNode start, TreeNode end) {
        if (start == null || end == null) {
            return;
        }
        if (!(start.right == null && end.left == null)) {
            queue.addFirst(start.right);
            queue.addLast(end.left);
        }
        if (!(start.left == null && end.right == null)) {
            queue.addFirst(start.left);
            queue.addLast(end.right);
        }
    }

    public static void main(String[] args) {
        IsSymmetric isSymmetric = new IsSymmetric();

        System.out.println(isSymmetric.isSymmetric(TreeNode.buildTree(1, 2, 2, 3, 4, 4, 3)));
        System.out.println(isSymmetric.isSymmetric(TreeNode.buildTree(1, 2, 2, null, 3, null, 3)));
        System.out.println(isSymmetric.isSymmetric(TreeNode.buildTree(1)));
    }
}
