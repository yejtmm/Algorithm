package stackAndQueue.one;

import java.util.Stack;

/**
  实现一个特殊的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素的操作。
  1.pop、push、getMin操作的时间复杂度都是O（1）。
  2.设计的栈类型可以使用现成的栈结构。

  在设计时，我们使用两个栈，一个栈用来保存当前栈中的元素，其功能和一个正常的栈没有区别，这个栈记为stackData；
  另一个栈用于保存每一步的最小值，这个栈记为stackMin。具体的实现方式有两种。
 */
public class StackGetDataMin1 {

    /**
    第一种设计方案:
     (1）压入数据规则
     压入数据规则假设当前数据为newNum，先将其压入stackData。然后判断stackMin是否为空：
     ● 如果为空，则newNum也压入stackMin。
     ● 如果不为空，则比较newNum和stackMin的栈顶元素中哪一个更小：
     ● 如果newNum更小或两者相等，则newNum也压入stackMin；
     ● 如果stackMin中栈顶元素小，则stackMin不压入任何内容。
     (2）弹出数据规则
     先在 stackData 中弹出栈顶元素，记为 value。然后比较当前 stackMin 的栈顶元素和 value哪一个更小。
     通过上文提到的压入规则可知，stackMin 中存在的元素是从栈底到栈顶逐渐变小的，
     stackMin栈顶的元素既是stackMin栈的最小值，也是当前stackData栈的最小值。
     所以不会出现value比stackMin的栈顶元素更小的情况，value只可能大于或等于stackMin的栈顶元素。
     当value等于stackMin的栈顶元素时，stackMin弹出栈顶元素；当value大于stackMin的栈顶元素时，
     stackMin不弹出栈顶元素，返回value。很明显可以看出，压入与弹出规则是对应的。
     (3）查询当前栈中的最小值操作
     stackMin始终记录着stackData中的最小值。所以，stackMin的栈顶元素始终是当前stackData中的最小值。
     */

    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public StackGetDataMin1(){
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
        int value = this.stackData.pop();
        if(value == this.getMin()){
            this.stackMin.pop();
        }
        return value;
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
