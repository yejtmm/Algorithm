package stackAndQueue.three;

import java.util.Stack;

/**
 一个栈依次压入1、2、3、4、5，那么从栈顶到栈底分别为5、4、3、2、1。
 将这个栈转置后，从栈顶到栈底为 1、2、3、4、5，也就是实现栈中元素的逆序，
 但是只能用递归函数来实现，不能用其他数据结构。
 */
public class ReverseStack {

    /**
     递归函数一：将栈stack的栈底元素返回并移除。
     递归函数二：逆序一个栈，
     */

    public static int getAndRemoveLastElement(Stack<Integer> stack){
        int result = stack.pop();
        if(stack.isEmpty()){
            return result;
        }else {
            int last = getAndRemoveLastElement(stack);
            stack.push(result);
            return last;
        }
    }

    public static void reverse(Stack<Integer> stack){
        if(!stack.isEmpty()){
            int i = getAndRemoveLastElement(stack);
            reverse(stack);
            stack.push(i);
        }
    }
}
