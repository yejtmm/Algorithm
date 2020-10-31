package stackAndQueue.ten;

import java.util.LinkedList;

/**
   给定数组arr和整数num，共返回有多少个子数组满足如下情况：
   max(arr[i..j])-min(arr[i..j] <= num
   max(arr[i..j])表示子数组arr[i..j]中最大值，min(arr[i..j])表示子数组arr[i..j]中最小值

    如果数组长度为 N ，时间复杂度需为 O(N)
 */
public class MaxSubMin {

    /**
     普通的解法，找到arr的所有子数组，一共有O（N2）个，然后对每一个子数组做遍历找到其中的最小值和最大值，这个过程的时间复杂度为O（N），
     然后看看这个子数组是否满足条件。统计所有满足的子数组数量即可。普通解法容易实现，但是时间复杂度为O（N3），不再详述。
     最优解可以做到时间复杂度为O（N），额外空间复杂度为O（N），用到的双端队列结构与解决“生成窗口最大值数组”问题中的双端队列结构含义基本一致。

     生成两个双端队列 qmax和 qmin。当子数组为 arr[i..j]时，qmax维护了窗口子数组arr[i..j]的最大值更新的结构，qmin 维护了窗口子数组 arr[i..j]的最小值更新的结构。
     当子数组 arr[i..j]向右扩一个位置变成arr[i..j+1]时，qmax和qmin结构更新代价的平均时间复杂度为O（1），并且可以在 O（1）的时间内得到 arr[i..j+1]的最大值和最小值。
     当子数组 arr[i..j]向左缩一个位置变成arr[i+1..j]时，qmax和qmin结构更新代价的平均时间复杂度为O（1），并且在O（1）的时间内得到arr[i+1..j]的最大值和最小值。

     通过分析题目满足的条件，可以得到如下两个结论。
     ● 如果子数组arr[i..j]满足条件，即max（arr[i..j]）-min（arr[i..j]）＜=num，那么arr[i..j]中的每一个子数组，即arr[k..l]（i≤k≤l≤j）都满足条件。
       我们以子数组arr[i..j-1]为例说明，arr[i..j-1]最大值只可能小于或等于 arr[i..j]的最大值，arr[i..j-1]最小值只可能大于或等于 arr[i..j]的最小值，
       所以arr[i..j-1]必然满足条件。同理，arr[i..j]中的每一个子数组都满足条件。
     ● 如果子数组arr[i..j]不满足条件，那么所有包含arr[i..j]的子数组，即arr[k..l]（k≤i≤j≤l）都不满足条件。证明过程同第一个结论。

     根据双端队列qmax和qmin的结构性质，以及如上两个结论，设计整个过程如下：

     1.生成两个双端队列qmax和qmin，含义如上文所说。生成两个整型变量i和j，表示子数组的范围，即arr[i..j]。生成整型变量res，表示所有满足条件的子数组数量。
     2.令j不断向右移动（j++），表示arr[i..j]一直向右扩大，并不断更新qmax和qmin结构，保证qmax和qmin始终维持动态窗口最大值和最小值的更新结构。
       一旦出现arr[i..j]不满足条件的情况，j向右扩的过程停止，此时arr[i..j-1]、arr[i..j-2]、arr[i..j-3]...arr[i..i]一定都是满足条件的。
       也就是说，所有必须以arr[i]作为第一个元素的子数组，满足条件的数量为j-i个。于是令res+=j-i。
     3.当进行完步骤 2，令 i向右移动一个位置，并对 qmax和 qmin做出相应的更新，qmax和qmin从原来的arr[i..j]窗口变成arr[i+1..j]窗口的最大值和最小值的更新结构。
       然后重复步骤2，也就是求所有必须以arr[i+1]作为第一个元素的子数组中，满足条件的数量有多少个。
     4.根据步骤2和步骤3，依次求出：必须以arr[0]开头的子数组，满足条件的数量有多少个；必须以arr[1]开头的子数组，满足条件的数量有多少个；
       必须以arr[2]开头的子数组，满足条件的数量有多少个，全部累加起来就是答案。

     上述过程中，所有的下标值最多进qmax和qmin一次，出qmax和qmin一次。i和j的值也不断增加，并且从来不减小。所以整个过程的时间复杂度为O（N）。

     */

    public static int getNum(int[] arr, int num){
        int res = 0;
        if(arr != null && arr.length > 0 && num >= 0){
            LinkedList<Integer> qmin = new LinkedList<>();
            LinkedList<Integer> qmax = new LinkedList<>();
            int i = 0;
            int j = 0;
            while (i < arr.length){
                while (j < arr.length){
                    if(qmin.isEmpty() || qmin.peekLast() != j){
                        while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]){
                            qmin.pollLast();
                        }
                        qmin.addLast(j);
                        while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[j]){
                            qmax.pollLast();
                        }
                        qmax.addLast(j);
                    }
                    if(arr[qmax.getFirst()] - arr[qmin.getFirst()] > num){
                        break;
                    }
                    j++;
                }

                res += j -i;
                if(qmin.peekFirst() == i){
                    qmin.pollFirst();
                }
                if(qmax.peekFirst() == i){
                    qmax.pollFirst();
                }
                i++;
            }
        }
        return res;
    }

}
