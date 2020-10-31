package List.one;

/**
   给定两个有序链表的头指针head1和head2，打印两个链表的公共部分。
 */

public class CommonList {

    /**

     因为是有序链表，所以从两个链表的头开始进行如下判断：
     ● 如果head1的值小于head2，则head1往下移动。
     ● 如果head2的值小于head1，则head2往下移动。
     ● 如果head1的值与head2的值相等，则打印这个值，然后head1与head2都往下移动。
     ● head1或head2有任何一个移动到null，则整个过程停止。
     */
    public void printCommonPart(Node head1, Node head2){
        System.out.println("Common Part: ");
        while (head1 != null && head2 != null){
            if (head1.value < head2.value){
                head1 = head1.next;
            }else if(head1.value > head2.value){
                head2 = head2.next;
            }else {
                System.out.println(head1.value + " ");
                head1 = head1.next;
                head2 = head2.next;
            }
        }
        System.out.println();
    }
}
