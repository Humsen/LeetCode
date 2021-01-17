package leetcode.common;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 树的数据结构
 *
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2020-05-31 14:45
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    /**
     * 根据广度遍历的结果构造二叉树
     *
     * @param nodes 广度优先遍历的节点值数组
     * @return 根节点
     */
    public static TreeNode buildTree(Integer... nodes) {
        if (nodes == null || nodes.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(nodes[0]);
        if (nodes.length == 1) {
            return root;
        }

        Queue<TreeNode> queue = new ArrayDeque<>(nodes.length);
        queue.offer(root);
        int index = 1;
        while (!queue.isEmpty()) {
            TreeNode tempRoot = queue.poll();
            tempRoot.left = nodes[index] == null ? null : new TreeNode(nodes[index]);
            if (++index > nodes.length - 1) {
                break;
            }
            tempRoot.right = nodes[index] == null ? null : new TreeNode(nodes[index]);
            if (++index > nodes.length - 1) {
                break;
            }

            if (tempRoot.left != null) {
                queue.add(tempRoot.left);
            }
            if (tempRoot.right != null) {
                queue.add(tempRoot.right);
            }
        }

        return root;
    }
}
