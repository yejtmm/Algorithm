package stackAndQueue.two;

import java.util.Stack;

/**
 编写一个类，用两个栈实现队列，支持队列的基本操作（add、poll、peek）。
 */
public class TwoStacksMakeQueue {
    /**
     栈的特点是先进后出，而队列的特点是先进先出。我们用两个栈正好能把顺序反过来实现类似队列的操作。

     具体实现时是一个栈作为压入栈，在压入数据时只往这个栈中压入，记为stackPush；
     另一个栈只作为弹出栈，在弹出数据时只从这个栈弹出，记为stackPop。

     因为数据压入栈的时候，顺序是先进后出的。
     那么只要把stackPush的数据再压入stackPop中，顺序就变回来了。
     例如，将1～5依次压入stackPush，那么从stackPush的栈顶到栈底为5～1，此时依次再将5～1倒入stackPop，
     那么从stackPop的栈顶到栈底就变成了1～5。再从stackPop弹出时，顺序就像队列一样，
     */

    /**
     1.如果stackPush要往stackPop中压入数据，那么必须一次性把stackPush中的数据全部压入。
     2.如果stackPop不为空，stackPush绝对不能向stackPop中压入数据。违反了以上两点都会发生错误。

     违反1的情况举例：1～5依次压入stackPush，stackPush的栈顶到栈底为5～1，从stackPush压入stackPop时，
     只将5和4压入了stackPop，stackPush还剩下1、2、3没有压入。此时如果用户想进行弹出操作，那么4将最先弹出，
     与预想的队列顺序就不一致。

     违反2的情况举例：1～5依次压入stackPush，stackPush将所有的数据压入stackPop，
     此时从stackPop的栈顶到栈底就变成了1～5。此时又有6～10依次压入stackPush，stackPop不为空，
     stackPush不能向其中压入数据。如果违反2压入了stackPop，从stackPop的栈顶到栈底就变成了6～10、1～5。
     那么此时如果用户想进行弹出操作，6将最先弹出，与预想的队列顺序就不一致。

     */

    private Stack<Integer> stackPush;
    private Stack<Integer> stackPop;

    public TwoStacksMakeQueue(){
        stackPush = new Stack<Integer>();
        stackPop = new Stack<Integer>();
    }

    private void pushToPop(){
        if(stackPop.empty()){
            while (!stackPush.empty()){
                stackPop.push(stackPush.pop());
            }
        }
    }

    /**
     * 入队
     * @param pushInt
     */
    public void add(int pushInt){
        stackPush.push(pushInt);
        pushToPop();
    }

    /**
     * 出队
     * @return
     */
    public int poll(){
        if(stackPush.empty() && stackPop.empty()){
            throw new RuntimeException("队列是空的");
        }
        pushToPop();
        return stackPop.pop();
    }

    public int peek(){
        if(stackPop.empty() && stackPush.empty()){
            throw new RuntimeException("队列是空的");
        }
        pushToPop();
        return stackPop.peek();
    }
}






