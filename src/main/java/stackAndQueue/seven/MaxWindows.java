package stackAndQueue.seven;

import java.util.LinkedList;

/**
  有一个整型数组arr和一个大小为w的窗口从数组的最左边滑到最右边，窗口每次向
  数组为[4，3，5，4，3，3，6，7]，窗口大小为3时：
  [4，3，5]，4，3，3，6，7      窗口中最大值： 5
  4，[3，5，4]，3，3，6，7      窗口中最大值： 5
  4，3，[5，4，3]，3，6，7      窗口中最大值： 5
  4，3，5，[4，3，3]，6，7      窗口中最大值： 4
  4，3，5，4，[3，3，6]，7      窗口中最大值： 6
  4，3，5，4，3，[3，6，7]      窗口中最大值： 7

  如果数组长度为n，窗口大小为w，则一共产生n-w+1个窗口的最大值。请实现一个函数。
  ● 输入：整型数组arr，窗口大小为w。
  ● 输出：一个长度为n-w+1的数组res，res[i]表示每一种窗口状态下的最大值。
    以本题为例，结果应该返回{5，5，5，4，6，7}。
 */
public class MaxWindows {

    /**
     假设数组长度为 N，窗口大小为 w
     本题的关键在于利用双端队列来实现窗口最大值的更新。首先生成双端队列qmax，qmax中存放数组arr中的下标。
     假设遍历到arr[i]，qmax的放入规则为：
     1.如果qmax为空，直接把下标i放进qmax，放入过程结束。
     2.如果qmax不为空，取出当前qmax队尾存放的下标，假设为j。
     1）如果arr[j]＞arr[i]，直接把下标i放进qmax的队尾，放入过程结束。
     2）如果arr[j]＜=arr[i]，把j从qmax中弹出，重复qmax的放入规则。

     也就是说，如果qmax是空的，就直接放入当前的位置。如果qmax不是空的，qmax队尾的位置所代表的值如果不比当前的值大，
     将一直弹出队尾的位置，直到 qmax队尾的位置所代表的值比当前的值大，当前的位置才放入qmax的队尾。

     假设遍历到arr[i]，qmax的弹出规则为：
     如果qmax队头的下标等于i-w，说明当前qmax队头的下标已过期，弹出当前对头的下标即可。
     根据如上的放入和弹出规则，qmax 便成了一个维护窗口为 w 的子数组的最大值更新的结构。下面举例说明题目给出的例子。

     1.开始时qmax为空，qmax={}。
     2.遍历到arr[0]==4，将下标0放入qmax，qmax={0}。
     3.遍历到arr[1]==3，当前qmax的队尾下标为0，又有arr[0]＞arr[1]，所以将下标1放入qmax的尾部，qmax={0，1}。
     4.遍历到arr[2]==5，当前qmax的队尾下标为1，又有arr[1]＜=arr[2]，所以将下标1从qmax的尾部弹出，qmax变为{0}。
       当前qmax的队尾下标为0，又有arr[0]＜=arr[2]，所以将下标0从qmax尾部弹出，qmax变为{}。
       将下标2放入qmax，qmax={2}。此时已经遍历到下标2的位置，窗口arr[0..2]出现，当前qmax队头的下标为2，
       所以窗口arr[0..2]的最大值为arr[2]（即5）。
     5.遍历到 arr[3]==4，当前 qmax的队尾下标为 2，又有 arr[2]＞arr[3]，所以将下标3放入qmax尾部，qmax={2，3}。
       窗口arr[1..3]出现，当前qmax队头的下标为2，这个下标还没有过期，所以窗口arr[1..3]的最大值为arr[2]（即5）。
     6.遍历到 arr[4]==3，当前 qmax的队尾下标为 3，又有 arr[3]＞arr[4]，所以将下标4放入qmax尾部，qmax={2，3，4}。
       窗口arr[2..4]出现，当前qmax队头的下标为2，这个下标还没有过期，所以窗口arr[2..4]的最大值为arr[2]（即5）。
     7.遍历到arr[5]==3，当前qmax的队尾下标为4，又有arr[4]＜=arr[5]，所以将下标4从qmax的尾部弹出，qmax变为{2，3}。
       当前qmax的队尾下标为3，又有arr[3]＞arr[5]，所以将下标5放入qmax尾部，qmax={2，3，5}。窗口arr[3..5]出现，
       当前qmax队头的下标为2，这个下标已经过期，所以从qmax的头部弹出，qmax变为{3，5}。当前qmax队头的下标为3，
       这个下标没有过期，所以窗口arr[3..5]的最大值为arr[3]（即4）。
     8.遍历到arr[6]==6，当前qmax的队尾下标为5，又有arr[5]＜=arr[6]，所以将下标5从qmax的尾部弹出，qmax变为{3}。
       当前qmax的队尾下标为3，又有arr[3]＜=arr[6]，所以将下标3从qmax的尾部弹出，qmax变为{}。将下标6放入qmax，qmax={6}。
       窗口arr[4..6]出现，当前qmax队头的下标为6，这个下标没有过期，所以窗口arr[4..6]的最大值为arr[6]（即6）。
     9.遍历到 arr[7]==7，当前 qmax的队尾下标为 6，又有 arr[6]＜=arr[7]，所以将下标6从qmax的尾部弹出，qmax变为{}。
       将下标7放入qmax，qmax={7}。窗口arr[5..7]出现，当前qmax队头的下标为7，这个下标没有过期，所以窗口arr[5..7]的最大值为arr[7]（即7）。
     10.依次出现的窗口最大值为[5，5，5，4，6，7]，在遍历过程中收集起来，最后返回即可。

     上述过程中，每个下标值最多进qmax一次，出qmax一次。
     所以遍历的过程中进出双端队列的操作是时间复杂度为 O（N），整体的时间复杂度也为 O（N）。
     */

    public int [] getMaxWindow(int [] arr, int w){
        if(arr == null || w < 1 || arr.length < w){
            return null;
        }
        LinkedList<Integer> qMax = new LinkedList<>();
        int [] res = new int[arr.length - w + 1];
        int index = 0;
        for (int i = 0; i < arr.length; ++i){
            while (!qMax.isEmpty() && arr[qMax.peekLast()] <= arr[i]){
                qMax.pollLast();
            }
            qMax.addLast(i);
            if(qMax.peekFirst() == i - w){
                qMax.pollFirst();
            }
            if(i >= w - 1){
                res[index++] = arr[qMax.peekFirst()];
            }
        }
        return res;
    }

}
