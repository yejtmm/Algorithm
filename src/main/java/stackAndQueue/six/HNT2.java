package stackAndQueue.six;

import java.util.Stack;

/**
 汉诺塔问题比较经典，这里修改一下游戏规则：现在限制不能从最左侧的塔直接移动到最右侧，
 也不能从最右侧直接移动到最左侧，而是必须经过中间。
 求当塔有N层的时候，打印最优移动过程和最优移动总步数。

 ● 方法二：非递归的方法，用栈来模拟汉诺塔的三个塔。
 */
public class HNT2 {

    /**
     修改后的汉诺塔问题不能让任何塔从“左”直接移动到“右”，也不能从“右”直接移动到“左”，而是要经过中间过程。
     也就是说，实际动作只有 4 个：“左”到“中”、“中”到“左”、“中”到“右”、“右”到“中”。

     现在我们把左、中、右三个地点抽象成栈，依次记为LS、MS和RS。最初所有的塔都在LS上。
     那么如上4个动作就可以看作是：
     某一个栈（from）把栈顶元素弹出，然后压入到另一个栈里（to），作为这一个栈（to）的栈顶。

     如果是7层塔，在最初时所有的塔都在LS上，LS从栈顶到栈底就依次是1～7，如果现在发生了“左”到“中”的动作，
     这个动作对应的操作是LS栈将栈顶元素1弹出，然后1压入到MS栈中，成为MS的栈顶。其他操作同理。

     一个动作能发生的先决条件是不违反小压大的原则。

     from栈弹出的元素num如果想压入到to栈中，那么num的值必须小于当前to栈的栈顶。
     还有一个原则不是很明显，但也是非常重要的，叫相邻不可逆原则，解释如下：

     1.我们把4个动作依次定义为：L-＞M、M-＞L、M-＞R和R-＞M。
     2.很明显，L-＞M和M-＞L过程互为逆过程，M-＞R和R-＞M互为逆过程。
     3.在修改后的汉诺塔游戏中，如果想走出最少步数，那么任何两个相邻的动作都不是互为逆过程的。

     举个例子：如果上一步的动作是 L-＞M，那么这一步绝不可能是M-＞L，
     直观地解释为：你在上一步把一个栈顶数从“左”移动到“中”，这一步为什么又要移回去呢？
     这必然不是取得最小步数的走法。同理，M-＞R动作和R-＞M动作也不可能相邻发生。

     有了小压大和相邻不可逆原则后，可以推导出两个十分有用的结论--非递归的方法核心结论：
     1.游戏的第一个动作一定是L-＞M，这是显而易见的。
     2.在走出最少步数过程中的任何时刻，4个动作中只有一个动作不违反小压大和相邻不可逆原则，
       另外三个动作一定都会违反。

     对于结论2，现在进行简单的证明。
     因为游戏的第一个动作已经确定是L-＞M，则以后的每一步都会有前一步的动作。
     假设前一步的动作是L-＞M：
         1.根据小压大原则，L-＞M的动作不会重复发生。
         2.根据相邻不可逆原则，M-＞L的动作也不该发生。
         3.根据小压大原则，M-＞R和R-＞M只会有一个达标。
     假设前一步的动作是M-＞L：
         1.根据小压大原则，R-＞M的动作不会重复发生。
         2.根据相邻不可逆原则，M-＞R的动作也不该发生。
         3.根据小压大原则，L-＞M和M-＞L只会有一个达标。

     综上所述，每一步只会有一个动作达标。那么只要每走一步都根据这两个原则考查所有的动作就可以，
     哪个动作达标就走哪个动作，反正每次都只有一个动作满足要求，按顺序走下来即可。
     */

    public int hanoiProblem(int num, String left, String mid, String right){
        Stack<Integer> ls = new Stack<>();
        Stack<Integer> ms = new Stack<>();
        Stack<Integer> rs = new Stack<>();
        ls.push(Integer.MAX_VALUE);
        ms.push(Integer.MAX_VALUE);
        rs.push(Integer.MAX_VALUE);
        for (int i = num; i > 0; --i){
            ls.push(i);
        }
        Action [] record = {Action.No};
        int step = 0;
        while (rs.size() != num + 1){
            step += fStackTotStack(record, Action.MToL, Action.LToM, ls, ms, left, mid);

            step += fStackTotStack(record, Action.LToM, Action.MToL, ms, ls, mid, left);

            step += fStackTotStack(record, Action.RToM, Action.MToR, ms, rs, mid, right);

            step += fStackTotStack(record, Action.MToR, Action.RToM, rs, ms, right, mid);
        }
        return step;
    }

    public static int fStackTotStack(Action [] record, Action preNoAct, Action nowAct,
                                     Stack<Integer> fStack, Stack<Integer> tStack, String from, String to){

        if(record[0] != preNoAct && fStack.peek() < tStack.peek()){
            tStack.push(fStack.pop());
            System.out.println("Move " + tStack.peek() + " from " + from + " to " + to);
            record[0] = nowAct;
            return 1;
        }
        return 0;
    }

}




