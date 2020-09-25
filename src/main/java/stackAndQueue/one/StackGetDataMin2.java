package stackAndQueue.one;

import java.util.Stack;

/**
 实现一个特殊的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素的操作。
 1.pop、push、getMin操作的时间复杂度都是O（1）。
 2.设计的栈类型可以使用现成的栈结构。

 在设计时，我们使用两个栈，一个栈用来保存当前栈中的元素，其功能和一个正常的栈没有区别，这个栈记为stackData；
 另一个栈用于保存每一步的最小值，这个栈记为stackMin。具体的实现方式有两种。
 */

public class StackGetDataMin2 {

    /**
     第二种设计方案:
     (1）压入数据规则
     假设当前数据为newNum，先将其压入stackData。然后判断stackMin是否为空。
       如果为空，则newNum也压入stackMin；
       如果不为空，则比较newNum和stackMin的栈顶元素中哪一个更小。
       如果newNum更小或两者相等，则newNum也压入stackMin；
       如果 stackMin 中栈顶元素小，则把stackMin的栈顶元素重复压入stackMin，即在栈顶元素上再压入一个栈顶元素。
     (2）弹出数据规则
     在stackData中弹出数据，弹出的数据记为value；弹出stackMin中的栈顶，返回value。
     很明显可以看出，压入与弹出规则是对应的。
     (3）查询当前栈中的最小值操作
     stackMin始终记录着stackData中的最小值。所以，stackMin的栈顶元素始终是当前stackData中的最小值。
     */

    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public StackGetDataMin2(){
        this.stackData = new Stack<Integer>();
        this.stackMin = new Stack<Integer>();
    }

    /**
     * 压栈
     * @param newNum
     */
    public void push(int newNum){
        if(this.stackMin.isEmpty() || newNum <= this.getMin()){
            this.stackMin.push(newNum);
        }else {
            int newMin = this.stackMin.peek();
            this.stackMin.push(newMin);
        }
        this.stackData.push(newNum);
    }

    /**
     * 出栈操作
     * @return
     */
    public int pop(){
        if(this.stackData.isEmpty()){
            throw new RuntimeException("栈是空的");
        }
        this.stackMin.pop();
        return this.stackData.pop();
    }

    /**
     * 获取最小值
     * @return
     */
    public int getMin(){
        if(this.stackMin.isEmpty()){
            throw new RuntimeException("栈是空的");
        }
        return this.stackMin.peek();
    }

}

/**
 方案一和方案二其实都是用 stackMin 栈保存着 stackData 每一步的最小值。
 共同点是所有操作的时间复杂度都为O（1）、空间复杂度都为O（n）。
 区别是：
 方案一中 stackMin压入时稍省空间，但是弹出操作稍费时间；
 方案二中 stackMin压入时稍费空间，但是弹出操作稍省时间。
 */
