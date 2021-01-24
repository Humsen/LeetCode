package leetcode.easy;

import leetcode.common.ListNode;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-01-24 22:04:12
 */
public class ReverseList {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode previous = head;
        ListNode current = head.next;
        head.next = null;
        while (current != null) {
            ListNode next = current.next;

            current.next = previous;
            previous = current;
            current = next;
        }

        return previous;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);

        node1.next = node2;
        node2.next = node3;

        ListNode head = new ReverseList().reverseList(node1);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }

    }
}
