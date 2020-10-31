package Tree.one;

import java.util.Stack;

/**
 用递归和非递归方式，分别按照二叉树先序、中序和后序打印所有的节点。
 我们约定：
       先序遍历顺序为根、左、右；
       中序遍历顺序为左、根、右；
       后序遍历顺序为左、右、根。
 */
public class BinaryTreeTraversal {

    //先序遍历
    public void preOrderRecur(Node head){
        if(head == null){
            return;
        }
        System.out.println(head.value + " ");
        preOrderRecur(head.left);
        preOrderRecur(head.right);
    }

    //中序遍历
    public void inOrderRecur(Node head){
        if(head == null){
            return;
        }
        inOrderRecur(head.left);
        System.out.println(head.value + " ");
        inOrderRecur(head.right);
    }

    //后序遍历
    public void posOrderRecur(Node head){
        if(head == null){
            return;
        }
        posOrderRecur(head.left);
        posOrderRecur(head.right);
        System.out.println(head.value + " ");
    }

    /**

     用递归方法解决的问题都能用非递归的方法实现。这是因为递归方法无非就是利用函数栈来保存信息，如果用自己申请的数据结构来代替函数栈，也可以实现相同的功能。
     用非递归的方式实现二叉树的先序遍历，具体过程如下：
     1.申请一个新的栈，记为stack。然后将头节点head压入stack中。
     2.从stack中弹出栈顶节点，记为cur，然后打印cur节点的值，再将节点cur的右孩子节点（不为空的话）先压入stack中，最后将cur的左孩子节点（不为空的话）压入stack中。
     3.不断重复步骤2，直到stack为空，全部过程结束。

                       1
                   2       3
                4    5   6    7

     节点 1先入栈，然后弹出并打印。
     接下来先把节点 3压入 stack，再把节点 2压入，stack从栈顶到栈底依次为2，3。
     节点2弹出并打印，把节点5压入stack，再把节点4压入，stack从栈顶到栈底为4，5，3。
     节点4弹出并打印，节点4没有孩子节点压入stack，stack从栈顶到栈底依次为5，3。
     节点5弹出并打印，节点5没有孩子节点压入stack，stack从栈顶到栈底依次为3。
     节点3弹出并打印，把节点7压入stack，再把节点6压入，stack从栈顶到栈底为6，7。
     节点6弹出并打印，节点6没有孩子节点压入stack，stack目前从栈顶到栈底为7。
     节点7弹出并打印，节点7没有孩子节点压入stack，stack已经为空，过程停止。
     */

    public void preOrderUnRecur(Node head){
        System.out.println("pre-order: ");
        if(head != null){
            Stack<Node> stack = new Stack<>();
            stack.add(head);
            while (!stack.isEmpty()){
                head = stack.pop();
                System.out.println(head.value + " ");
                if(head.right != null){
                    stack.push(head.right);
                }
                if(head.left != null){
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }


    /**
     用非递归的方式实现二叉树的中序遍历，具体过程如下：
     1.申请一个新的栈，记为stack。初始时，令变量cur=head。
     2.先把cur节点压入栈中，对以cur节点为头节点的整棵子树来说，依次把左边界压入栈中，即不停地令cur=cur.left，然后重复步骤2。
     3.不断重复步骤2，直到发现cur为空，此时从stack中弹出一个节点，记为node。打印node的值，并且让cur=node.right，然后继续重复步骤2。
     4.当stack为空且cur为空时，整个过程停止。还是用图3-1的例子来说明整个过程。初始时cur为节点1，将节点1压入stack，令cur=cur.left，即cur变为节点2。
       (步骤1+步骤2）cur为节点2，将节点2压入stack，令cur=cur.left，即cur变为节点4。
      (步骤2）cur为节点4，将节点4压入stack，令cur=cur.left，即cur变为null，此时stack从栈顶到栈底为4，2，1。
      (步骤2）cur为null，从stack弹出节点4（node）并打印，令cur=node.right，即cur为null，此时stack从栈顶到栈底为2，1。
     （步骤3）cur为null，从stack弹出节点2（node）并打印，令cur=node.right，即cur变为节点5，此时stack从栈顶到栈底为1。
     （步骤3）cur为节点5，将节点5压入stack，令cur=cur.left，即cur变为null，此时stack从栈顶到栈底为5，1。
     （步骤2）cur为null，从stack弹出节点5（node）并打印，令cur=node.right，即cur仍为null，此时stack从栈顶到栈底为1。
     （步骤3）cur为null，从stack弹出节点1（node）并打印，令cur=node.right，即cur变为节点3，此时stack为空。
     （步骤3）cur为节点3，将节点3压入stack，令cur=cur.left，即cur变为节点6，此时stack从栈顶到栈底为3。
     （步骤2）cur为节点6，将节点6压入stack，令cur=cur.left，即cur变为null，此时stack从栈顶到栈底为6，3。
     （步骤2）cur为null，从stack弹出节点6（node）并打印，令cur=node.right，即cur仍为null，此时stack从栈顶到栈底为3。
     （步骤3）cur为null，从stack弹出节点3（node）并打印，令cur=node.right，即cur变为节点7，此时stack为空。
     （步骤3）cur为节点7，将节点7压入stack，令cur=cur.left，即cur变为null，此时stack从栈顶到栈底为7。
     （步骤2）cur为null，stack也为空，整个过程停止。
     （步骤4）通过与例子结合的方式我们发现，步骤1到步骤4就是依次先打印左子树，然后打印每棵子树的头节点，最后打印右子树。
     */

    public void inOrderUnRecur(Node head){
        System.out.println("in-order: ");
        if(head != null){
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || head != null){
                if(head != null){
                    stack.push(head);
                    head = head.left;
                }else {
                    head = stack.pop();
                    System.out.println(head.value + " ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }


    /**
     先介绍用两个栈实现后序遍历的过程，具体过程如下：
     1.申请一个栈，记为s1，然后将头节点head压入s1中。
     2.从s1中弹出的节点记为cur，然后依次将cur的左孩子节点和右孩子节点压入s1中。
     3.在整个过程中，每一个从s1中弹出的节点都放进s2中。
     4.不断重复步骤2和步骤3，直到s1为空，过程停止。
     5.从s2中依次弹出节点并打印，打印的顺序就是后序遍历的顺序。还是用图3-1的例子来说明整个过程。节点1放入s1中。
       从s1中弹出节点1，节点1放入s2，然后将节点2和节点3依次放入s1，此时s1从栈顶到栈底为3，2；s2从栈顶到栈底为1。
       从s1中弹出节点3，节点3放入s2，然后将节点6和节点7依次放入s1，此时s1从栈顶到栈底为7，6，2；
       s2从栈顶到栈底为3，1。从s1中弹出节点7，节点7放入s2，节点7无孩子节点，此时s1从栈顶到栈底为6，2；s2从栈顶到栈底为7，3，1。
       从s1中弹出节点6，节点6放入s2，节点6无孩子节点，此时s1从栈顶到栈底为2；s2从栈顶到栈底为6，7，3，1。
       从s1中弹出节点2，节点2放入s2，然后将节点4和节点5依次放入s1，此时s1从栈顶到栈底为5，4；
       s2从栈顶到栈底为2，6，7，3，1。从s1中弹出节点5，节点5放入s2，节点5无孩子节点，此时s1从栈顶到栈底为4；
       s2从栈顶到栈底为5，2，6，7，3，1。从s1中弹出节点4，节点4放入s2，节点4无孩子节点，此时s1为空；
       s2从栈顶到栈底为4，5，2，6，7，3，1。
       过程结束，此时只要依次弹出s2中的节点并打印即可，顺序为4，5，2，6，7，3，1。

     通过如上过程我们知道，每棵子树的头节点都最先从s1中弹出，然后把该节点的孩子节点按照先左再右的顺序压入s1，
     那么从s1弹出的顺序就是先右再左，所以从s1中弹出的顺序就是中、右、左。然后，s2重新收集的过程就是把s1的弹出顺序逆序，
     所以s2从栈顶到栈底的顺序就变成了左、右、中
     */

    public void posOrderUnRecur1(Node head){
        System.out.println("pos-order: ");
        if(head != null){
            Stack<Node> s1 = new Stack<>();
            Stack<Node> s2 = new Stack<>();
            s1.push(head);
            while (!s1.isEmpty()){
                head = s1.pop();
                s2.push(head);
                if(head.left != null){
                    s1.push(head.left);
                }
                if(head.right != null){
                    s1.push(head.right);
                }
            }
            while (!s2.isEmpty()){
                System.out.println(s1.pop().value + " ");
            }
        }
        System.out.println();
    }

    /**
     只用一个栈实现后序遍历的过程，具体过程如下。
     1.申请一个栈，记为stack，将头节点压入stack，同时设置两个变量h和c。
       在整个流程中，h代表最近一次弹出并打印的节点，c代表stack的栈顶节点，初始时h为头节点，c为null。
     2.每次令c等于当前stack的栈顶节点，但是不从stack中弹出，此时分以下三种情况。
       ① 如果c的左孩子节点不为null，并且h不等于c的左孩子节点，也不等于c的右孩子节点，则把c的左孩子节点压入stack中。
         具体解释一下这么做的原因，首先h的意义是最近一次弹出并打印的节点，所以，如果h等于c的左孩子节点或者右孩子节点，说明c的左子树与右子树已经打印完毕，
         此时不应该再将c的左孩子节点放入stack中。否则，说明左子树还没处理过，那么此时将c的左孩子节点压入stack中。
      ② 如果条件①不成立，并且 c的右孩子节点不为null，h不等于c的右孩子节点，则把c的右孩子节点压入stack中。
         含义是如果h等于c的右孩子节点，说明c的右子树已经打印完毕，此时不应该再将c的右孩子节点放入stack中。否则，说明右子树还没处理过，
         此时将c的右孩子节点压入stack中。
      ③ 如果条件①和条件②都不成立，说明c的左子树和右子树都已经打印完毕，那么从stack中弹出c并打印，然后令h=c。
     3.一直重复步骤2，直到stack为空，过程停止。

     例子来说明整个过程.
     节点1压入stack，初始时h为节点1，c为null，stack从栈顶到栈底为1。
     令c等于stack的栈顶节点--节点1，此时步骤2的条件①命中，将节点2压入stack，h为节点1，stack从栈顶到栈底为2，1。
     令c等于stack的栈顶节点--节点2，此时步骤2的条件①命中，将节点4压入stack，h为节点1，stack从栈顶到栈底为4，2，1。
     令c等于stack的栈顶节点--节点4，此时步骤2的条件③命中，将节点4从stack中弹出并打印，h变为节点4，stack从栈顶到栈底为2，1。
     令c等于stack的栈顶节点--节点2，此时步骤2的条件②命中，将节点5压入stack，h为节点4，stack从栈顶到栈底为5，2，1。
     令c等于stack的栈顶节点--节点5，此时步骤2的条件③命中，将节点5从stack中弹出并打印，h变为节点5，stack从栈顶到栈底为2，1。
     令c等于stack的栈顶节点--节点2，此时步骤2的条件③命中，将节点2从stack中弹出并打印，h变为节点2，stack从栈顶到栈底为1。
     令c等于stack的栈顶节点--节点1，此时步骤2的条件②命中，将节点3压入stack，h为节点2，stack从栈顶到栈底为3，1。
     令c等于stack的栈顶节点--节点3，此时步骤2的条件①命中，将节点6压入stack，h为节点2，stack从栈顶到栈底为6，3，1。
     令c等于stack的栈顶节点--节点6，此时步骤2的条件③命中，将节点6从stack中弹出并打印，h变为节点6，stack从栈顶到栈底为3，1。
     令c等于stack的栈顶节点--节点3，此时步骤2的条件②命中，将节点7压入stack，h为节点6，stack从栈顶到栈底为7，3，1。
     令c等于stack的栈顶节点--节点7，此时步骤2的条件③命中，将节点7从stack中弹出并打印，h变为节点7，stack从栈顶到栈底为3，1。
     令c等于stack的栈顶节点--节点3，此时步骤2的条件③命中，将节点3从stack中弹出并打印，h变为节点3，stack从栈顶到栈底为1。
     令c等于stack的栈顶节点--节点1，此时步骤2的条件③命中，将节点1从stack中弹出并打印，h变为节点1，stack为空。
     过程结束。
     */
    public void posOrderUnRecur2(Node h){
        System.out.println("pos-order: ");
        if(h != null){
            Stack<Node> stack = new Stack<>();
            stack.push(h);
            Node c = null;
            while (!stack.isEmpty()){
                c = stack.peek();
                if(c.left != null && h != c.left && h != c.right){
                    stack.push(c.left);
                }else if(c.right != null && h != c.right){
                    stack.push(c.right);
                }else{
                    System.out.println(stack.pop().value + " ");
                    h = c;
                }
            }
        }
        System.out.println();
    }
}