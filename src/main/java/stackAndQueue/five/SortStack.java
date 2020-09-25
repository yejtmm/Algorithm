package stackAndQueue.five;

import java.util.Stack;

/**
 一个栈中元素的类型为整型，现在想将该栈从顶到底按从大到小的顺序排序，只许申请一个栈。
 除此之外，可以申请新的变量，但不能申请额外的数据结构。如何完成排序？
 */

public class SortStack {

    /**
     将要排序的栈记为stack，申请的辅助栈记为help。在stack上执行pop操作，弹出的元素记为cur。
     ● 如果cur小于或等于help的栈顶元素，则将cur直接压入help；
     ● 如果cur大于help的栈顶元素，则将help的元素逐一弹出，逐一压入stack，
       直到cur小于或等于help的栈顶元素，再将cur压入help。
     一直执行以上操作，直到stack中的全部元素都压入到help。
     最后将help中的所有元素逐一压入stack，即完成排序。
     */

    public static void sortStackByStack(Stack<Integer> stack){
        Stack<Integer> help = new Stack<>();
        while (!stack.isEmpty()){
            int cur = stack.pop();
            while (!help.isEmpty() && help.peek() < cur){
                stack.push(help.pop());
            }
            help.push(cur);
        }
        while (!help.isEmpty()){
            stack.push(help.pop());
        }
    }

}
