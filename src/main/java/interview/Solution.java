package interview;

/**
 * @author hemingsheng@bixin.cn
 * @since 2021-02-27 21:19:14
 */
public class Solution {

    /**
     * 定义链表，
     * 输入0-100，再输出
     * 定义A-Z，再输出
     */
    public static void main(String[] args) {
        ListNode<Integer> head = new ListNode<>(0);
        ListNode<Integer> tmp = head;
        for (int i = 1; i < 101; i++) {
            tmp.next = new ListNode<>(i);
            tmp = tmp.next;
        }
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }

        System.out.println();
        ListNode<Character> headChar = new ListNode<>('A');
        ListNode<Character> tmpChar = headChar;
        for (int i = 66; i < 91; i++) {
            tmpChar.next = new ListNode<>((char) i);
            tmpChar = tmpChar.next;
        }
        while (headChar != null) {
            System.out.print(headChar.value + " ");
            headChar = headChar.next;
        }
    }

    public static class ListNode<T> {
        public T value;

        public ListNode<T> next;

        public ListNode(T value) {
            this.value = value;
        }
    }
}
