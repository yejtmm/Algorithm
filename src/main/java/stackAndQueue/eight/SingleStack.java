package stackAndQueue.eight;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 给定一个不含有重复值的数组arr，找到每一个i位置左边和右边离i位置最近且值比arr[i]小的位置。
 返回所有位置相应的信息。
 arr = {3, 4, 1, 5, 6, 2, 7}
 返回二维数组作为结果
 {
    {-1, 2}, {0, 2}, {-1, -1}, {2, 5}, {3, 5}, {2, -1}, {5, -1}
 }
 -1表示不存在。
 上面的结果表示在arr中，0位置左边和右边离0位置最近且值比arr[0]小的位置是-1和2；
 1位置左边和右边离1位置最近且值比arr[1]小的位置是0和2；
 2位置左边和右边离2位置最近且值比arr[2]小的位置是-1和-1……

 进阶问题：给定一个可能含有重复值的数组arr，找到每一个i位置左边和右边离i位置最近且值比arr[i]小的位置。
 返回所有位置相应的信息。

 如果arr长度为N，实现原问题和进阶问题的解法，时间复杂度都达到O（N）。

 */
public class SingleStack {

    /**
     * 时间复杂度为O（N2）的解
     * @param arr
     * @return
     */
    @Deprecated
    public int [] [] rightWay(int [] arr){
        int [] [] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++){
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0){
                if(arr[cur] < arr[i]){
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length){
                if(arr[cur] < arr[i]){
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    /**
     时间复杂度做到O（N) 需要用到 "单调栈结构"
     */
    /**
     原问题：准备一个栈，记为 stack＜Integer＞，栈中放的元素是数组的位置，开始时 stack 为空。
     如果找到每一个i位置左边和右边离i位置最近且值比arr[i]小的位置，
     那么需要让stack从栈顶到栈底的位置所代表的值是严格递减的；
     如果找到每一个i位置左边和右边离i位置最近且值比 arr[i]大的位置，
     那么需要让 stack从栈顶到栈底的位置所代表的值是严格递增的。
     本题需要解决的是前者，但是对于后者，原理完全是一样的。

     单调栈的使用和求解流程，初始时arr={3，4，1，5，6，2，7}，stack从栈顶到栈底为：{}；

     遍历到arr[0]==3，发现stack为空，就直接放入0位置。stack从栈顶到栈底为：{0位置（值是3）}；

     遍历到 arr[1]==4，发现直接放入 1位置，不会破坏 stack从栈顶到栈底的位置所代表的值是严格递减的，那么直接放入。
     stack从栈顶到栈底依次为：{1位置（值是4）、0位置（值是3）}；

     遍历到arr[2]==1，发现直接放入2位置（值是1），会破坏stack从栈顶到栈底的位置所代表的值是严格递减的，
     所以从stack开始弹出位置。如果x位置被弹出，在栈中位于x位置下面的位置，就是x位置左边离x位置最近且值比arr[x]小的位置；
     当前遍历到的位置就是x位置右边离x位置最近且值比arr[x]小的位置。
     从stack弹出位置1，在栈中位于1位置下面的是位置0，当前遍历到的是位置2，所以ans[1]={0，2}。
     弹出1位置之后，发现放入2位置（值是1）还会破坏stack从栈顶到栈底的位置所代表的值是严格递减的，所以继续弹出位置0。
     在栈中位于位置0下面已经没有位置了，说明在位置0左边不存在比arr[0]小的值，当前遍历到的是位置2，所以ans[0]={-1，2}。
     stack已经为空，所以放入2位置（值是1），stack从栈顶到栈底为：{2位置（值是1）}；

     遍历到 arr[3]==5，发现直接放入 3位置，不会破坏 stack从栈顶到栈底的位置所代表的值是严格递减的，那么直接放入。
     stack从栈顶到栈底依次为：{3位置（值是5）、2位置（值是1）}；

     遍历到 arr[4]==6，发现直接放入 4位置，不会破坏 stack从栈顶到栈底的位置所代表的值是严格递减的，那么直接放入。
     stack从栈顶到栈底依次为：{4位置（值是6）、3位置（值是5）、2位置（值是1）}；

     遍历到 arr[5]==2，发现直接放入 5位置，会破坏 stack从栈顶到栈底的位置所代表的值是严格递减的，所以开始弹出位置。
     弹出位置4，栈中它的下面是位置3，当前是位置5，ans[4]={3，5}。弹出位置3，栈中它的下面是位置2，当前是位置5，ans[3]={2，5}。
     然后放入5位置就不会破坏stack的单调性了。stack从栈顶到栈底依次为：{5位置（值是2）、2位置（值是1）}；

     遍历到 arr[6]==7，发现直接放入 6位置，不会破坏 stack从栈顶到栈底的位置所代表的值是严格递减的，那么直接放入。
     stack从栈顶到栈底依次为：{6位置（值是7）、5位置（值是2）、2位置（值是1）}。

     遍历阶段结束后，清算栈中剩下的位置。

     弹出6位置，栈中它的下面是位置5，6位置是清算阶段弹出的，所以ans[6]={5，-1}；

     弹出5位置，栈中它的下面是位置2，5位置是清算阶段弹出的，所以ans[5]={2，-1}；

     弹出2位置，栈中它的下面没有位置了，2位置是清算阶段弹出的，所以ans[2]={-1，-1}。

     至此，已经全部生成了每个位置的信息

     */
    public int [] [] getNearLessNoRepeat(int [] arr){
        int [] [] res = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++){
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]){
                int popIndex = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
                res[popIndex][0] = leftLessIndex;
                res[popIndex][1] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()){
            int popIndex = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
            res[popIndex][0] = leftLessIndex;
            res[popIndex][1] = -1;
        }
        return res;
    }

    /**
     进阶问题，可能含有重复值的数组如何使用单调栈。其实整个过程和原问题的解法差不多。
     初始时arr={3，1，3，4，3，5，3，2，2}，stack从栈顶到栈底为：{}；
     遍历到arr[0]==3，发现stack为空，就直接放入0位置。stack从栈顶到栈底为：{0位置（值是3）}；
     遍历到arr[1]==1，从栈中弹出位置0，并且得到ans[0]={-1，1}。位置1进栈，stack从栈顶到栈底为：{1位置（值是1）}；
     遍历到arr[2]==3，发现位置2可以直接放入。stack从栈顶到栈底依次为：{2位置（值是3）、1位置（值是1）}；
     遍历到arr[3]==4，发现位置3可以直接放入。stack从栈顶到栈底依次为：{3位置（值是4）、2位置（值是3）、1位置（值是1）}；
     遍历到 arr[4]==3，从栈中弹出位置 3，并且得到 ans[3]={2，4}。此时发现栈顶是位置 2，值是3，当前遍历到位置4，值也是3，
       所以两个位置压在一起。stack从栈顶到栈底依次为：{[2位置，4位置]（值是3）、1位置（值是1）}；
     遍历到arr[5]==5，发现位置5可以直接放入。stack从栈顶到栈底依次为：{5位置（值是5）、[2位置，4位置]（值是3）、1位置（值是1）}；
     遍历到arr[6]==3，从栈中弹出位置5，在栈中位置5的下面是[2位置，4位置]，选最晚加入的4位置，当前遍历到位置6，所以得到ans[5]={4，6}。
       位置6进栈，发现又是和栈顶位置代表的值相等的情况，所以继续压在一起，stack从栈顶到栈底依次为：
       {[2位置，4位置，6位置]（值是3）、1位置（值是1）}；
     遍历到arr[7]==2，从栈中弹出[2位置，4位置，6位置]，在栈中这些位置下面的是1位置，当前是7位置，所以得到
       ans[2]={1，7}、ans[4]={1，7}、ans[6]={1，7}。位置7进栈，stack从栈顶到栈底依次为：
       {7位置（值是2）、1位置（值是1）}；
     遍历到arr[8]==2，发现位置8可以直接进栈，并且又是相等的情况，stack从栈顶到栈底依次为：
       {[7位置，8位置]（值是2）、1位置（值是1）}。
     遍历完成后，开始清算阶段：弹出[7位置，8位置]，生成ans[7]={1，-1}、ans[8]={1，-1}；弹出1位置，生成ans[1]={-1，-1}。

     */
    public int [] [] getNearLess(int [] arr){
        int [] [] res = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++){
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]){
                List<Integer> popIs = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer popI : popIs){
                    res[popI][0] = leftLessIndex;
                    res[popI][1] = i;
                }
            }
            if(!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]){
                stack.peek().add(Integer.valueOf(i));
            }else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }
        while (!stack.isEmpty()){
            List<Integer> popIs = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer popI : popIs){
                res[popI][0] = leftLessIndex;
                res[popI][1] = -1;
            }
        }
        return res;
    }


}
