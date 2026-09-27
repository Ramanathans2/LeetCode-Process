class Solution {
    public ListNode reverseList(ListNode tail, ListNode head, int k) {
        ListNode original = head;
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = head;

        while (k != 0) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            k--;
        }

        head.next = next;
        head = prev;

        if (tail != null) {
            tail.next = head;
        }

        return original;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        int n = 0;
        ListNode curr = head;

        while (curr != null) {
            curr = curr.next;
            n++;
        }

        if (k == 1 || n == 1) {
            return head;
        }

        int count = 1;
        ListNode ans = head;

        while (count != k) {
            ans = ans.next;
            count++;
        }

        ListNode tail = reverseList(null, head, k);
        n = n - k;

        while (n >= k) {
            tail = reverseList(tail, tail.next, k);
            n = n - k;
        }

        return ans;
    }
}