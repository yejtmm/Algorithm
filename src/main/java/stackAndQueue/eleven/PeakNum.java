package stackAndQueue.eleven;

import java.util.Stack;

/**
   一个不含有负数的数组可以代表一圈环形山，每个位置的值代表山的高度。
   比如，{3，1，2，4，5}、{4，5，3，1，2}或{1，2，4，5，3}都代表同样结构的环形山。
   3-＞1-＞2-＞4-＞5-＞3方向叫作next方向（逆时针），3-＞5-＞4-＞2-＞1-＞3方向叫作last方向（顺时针）

   山峰A和山峰B能够相互看见的条件为：
   1.如果A和B是同一座山，认为不能相互看见。
   2.如果A和B是不同的山，并且在环中相邻，认为可以相互看见。
    相邻的山峰对有（1，2）（2，4）（4，5）（3，5）（1，3）。
   3.如果A和B是不同的山，并且在环中不相邻，假设两座山高度的最小值为min。
     如果A通过next方向到B的途中没有高度比min大的山峰，或者A通过last方向到B的途中没有高度比min大的山峰，认为A和B可以相互看见。
     高度为3的山和高度为4的山，两座山的高度最小值为3。3从last方向走向4，中途会遇见5，所以last方向走不通；
     3从next方向走向4，中途会遇见1和2，但是都不大于两座山高度的最小值3，所以next方向可以走通。有一个能走通就认为可以相互看见。
     再如，高度为2的山和高度为5的山，两个方向上都走不通，所以不能相互看见。
     所有在环中不相邻，并且能看见的山峰对有（2，3）（3，4）。
     给定一个不含有负数且没有重复值的数组arr，请返回有多少对山峰能够相互看见。

 进阶问题：给定一个不含有负数但可能含有重复值的数组arr，返回有多少对山峰能够相互看见。

 如果arr长度为N，没有重复值的情况下时间复杂度达到O（1），可能有重复值的情况下时间复杂度请达到O（N）。

 */
public class PeakNum {

    /**

     原问题：时间复杂度 O（1）的解。如果数组中所有的数字都不一样，可见山峰对的数量可以由简单公式得到。
     环形结构中只有1座山峰时，可见山峰对的数量为0；环形结构中只有2座山峰时，可见山峰对的数量为1。这都是显而易见的。
     环形结构中有i座山峰时（i＞2），可见山峰对的数量为2×i-3。

     下面给出证明。我们只用高度小的山峰去找高度大的山峰，而永远不用高度大的山峰去找高度小的山峰。
     比如题目描述中的例子，从 2 出发按照“小找大”原则，会找到（2，3）和（2，4），但是不去尝试 2能不能看到 1，因为这是“大找小”，而不是“小找大”。
     （1，2）这一对可见山峰不会错过，因为从1出发按照“小找大”原则找的时候会找到这一对。从每一个位置出发，都按照“小找大”原则找到山峰对的数量，
     就是总的可见山峰对数量。如果有 i 座山峰并且高度都不一样，必然在环中存在唯一的最大值和唯一的次大值（第二大的值）

     x节点表示除最高值和次高值之外的任何一座山峰，因为x既不是最大值，也不是次大值，所以x在last方向上必存在第一个高度比它大的节点，假设这个节点是y，
     y有可能就是最大值节点，但是一定存在。
     x在next方向上必存在第一个高度比它大的节点，假设这个节点是z，z有可能就是次大值节点，但是一定存在。
     因为y是x在last方向上第一个高度比它大的节点，所以x在last方向上没到达y之前遇到的所有山峰高度都小于x，不符合“小找大”方式。
     同理，x在next方向上没到达z之前遇到的所有山峰高度都小于x，不符合“小找大”方式。同时根据可见山峰对的定义，y从last方向到达z这一段的每一个节点x都看不见。
     所以从x出发能找到且只能找到（x，y）和（x，z）这2对。
     如果环中有i个节点，除了最大值和次大值之外，还剩i-2个节点，这i-2个节点都根据“小找大”的方式，每一个都能找到2对，
     所以一共有（i-2）×2对，还有1对，就是次大值能够看见最大值这对。所以一共是2×i-3对。


     进阶问题：时间复杂度O（N）的解。
     “单调栈结构”的解法。还是按照“小找大”的方式来求解可见山峰对个数. 假设环形山 如下

                                 4   2
                              4       4
                           5          5
                         3          3
                          2      4
                             5

     首先遍历一次环形山结构，找到最大值的位置，如果最大值不止一个，找哪一个最大值都行。比如图1-10中5是最大值且不止一个，找到哪个都行，我们选择最下方的5。
     准备一个栈，记为stack＜Record＞，stack中放入的是如下数据结构：

     public class Record {

         public int value;
         public int times;

         public Record(int value){
             this.value = value;
             this.times = 1;
         }
     }

     接下来从最大值开始沿着next方向准备再遍历一遍环形山。stack中先放入（5，1），表示5这个高度，收集1个。
     以后放入记录时，都保证第一维的数字从顶到底是依次增大的。
     目前stack从顶到底为：（5，1）。沿next方向来到4，生成记录（4，1），表示4这个数，收集1个。发现如果这个记录加入stack，第一维的数字从顶到底是依次增大的，所以放入（4，1）。
     目前stack从顶到底依次为：（4，1）、（5，1）。沿next方向来到3，生成记录（3，1），表示3这个数，收集1个。
     发现如果这个记录加入stack，第一维的数字从顶到底是依次增大的，所以放入（3，1）。目前stack从顶到底依次为：（3，1）、（4，1）、（5，1）。

     沿next方向来到5，生成记录（5，1）。发现如果这个记录加入stack，第一维的数字从顶到底就不是依次增大的。所以stack开始弹出记录，首先弹出（3，1），
     当前来到的数字是5，当前弹出的数字是3，原来在栈中的时候当前弹出数字的下面是4，说明当前弹出的3在next方向上遇到第
     一个大于它的数就是当前来到的数字 5，在 last 方向上遇到第一个大于它的数就是此时的栈顶4。
     进一步说明从当前弹出的3出发，通过“小找大”的方式，可以找到2个可见山峰对，就是（3，4）和（3，5）。
     stack继续弹出记录（4，1），当前来到的数字是5，当前弹出的数字是4，原来在栈中的时候，当前弹出数字下面的数字是 5，说明从当前弹出的 4 出发，通过“小找大”的方式，又找到2个可见山峰对。
     stack从顶到底只剩下（5，1）这个记录，当前生成的新记录是（5，1），把两个记录合并。目前stack从顶到底为：（5，2），发现的山峰对数量为：4。

     沿next方向来到4，生成记录（4，1）。发现如果这个记录加入stack，第一维的数字从顶到底是依次增大的，所以放入（4，1）。目前stack从顶到底依次为：（4，1）、（5，2），发现的山峰对数量：4。
     沿next方向来到2，生成记录（2，1）。发现如果这个记录加入stack，第一维的数字从顶到底是依次增大的，所以放入（2，1）。目前stack从顶到底依次为：（2，1）、（4，1）、（5，2），发现的山峰对数量：4。
     沿next方向来到4，生成记录（4，1）。发现如果这个记录加入stack，第一维的数字从顶到底就不是依次增大的了。所以stack开始弹出记录，首先弹出（2，1），与上面的解释同理，可以发现2个山峰对。
     此时stack顶部记录为（4，1），把两个记录合并。目前stack从顶到底依次为：（4，2）、（5，2），发现的山峰对数量：6。

     沿next方向来到4，生成记录（4，1）。此时stack顶部记录为（4，2），把两个记录合并。
     目前stack从顶到底依次为：（4，3）、（5，2），发现的山峰对数量：6。沿next方向来到5。生成记录（5，1），发现如果这个记录加入stack，第一维的数字从顶到底就不是依次增大的。
     所以stack弹出（4，3），这条记录表示当前收集到的这3个4有可能相邻；
     或者即便是不相邻，中间夹的数字也一定小于4（比如之前遇到的2），并且所夹的数字一定已经用“小找大”的方式算过山峰对了（看看之前遇到的2在弹出的时候）

                                            5                  5
                                             last            next
                                              4...4....4....4

     虚线表示可能夹住某些数字，但都是比4小的，而且都是算过山峰对的数字，不需要去关心。那么这3个4一共产生多少对可见山峰呢？
     首先，每一个4都可以看到last方向上的5和next方向上的5；其次，这3个4内部，每两个4都可以相互看见。
     所以产生2×3+C（2，3）=9对山峰。

     总结一下。如果在遍历阶段，某个记录（X，K）从stack中弹出了，产生可见山峰对的数量为：1）如果K==1，产生2对。2）如果K＞1，产生2×K+C（2，K）对。
     stack 在弹出（4，3）之后，当前顶部记录为（5，2），当前生成的记录是（5，1），合并在一起。目前stack从顶到底为：（5，3），发现的山峰对数量：15。
     沿next方向来到3，生成记录（3，1）。发现如果这个记录加入stack，第一维的数字从顶到底是依次增大的，所以放入（3，1）。
     目前stack从顶到底依次为：（3，1）、（5，3），发现的山峰对数量：15。沿next方向来到2，生成记录（2，1）。
     发现如果这个记录加入stack，第一维的数字从顶到底是依次增大的，所以放入（2，1）。
     目前stack从顶到底依次为：（2，1）、（3，1）、（5，3），发现的山峰对数量：15。
     遍历完毕，在遍历过程中发现了15对山峰。进行最后一个阶段：单独清算栈中记录的阶段。这个阶段又分成3个小阶段。
     第1个小阶段：弹出的记录不是栈中最后一个记录，也不是倒数第二个记录。第2个小阶段：弹出的记录是栈中倒数第二个记录。
     第3个小阶段：弹出的记录是栈中最后一个记录。

     比如上面的例子，在最后单独清算栈中记录的阶段，就是 3 个小阶段都有，弹出（2，1）时是第1个小阶段，弹出（3，1）时是第2个小阶段，弹出（5，3）时是第3个小阶段。
     图1-12是没有第1小阶段，但是有2、3小阶段的例子。
     假设从最下方5开始，沿next方向遍历，遍历完成后，stack从顶到底依次为：（4，2）、（5，1），然后进入清算阶段会发现没有第1小阶段。
     图1-13是没有第1、2小阶段，但是有第3小阶段的例子。

                               图 1-12               4
                                                         4
                                                     5

                               图1-13                4
                                                 5       4
                                                     5

     假设从最下方5开始，沿next方向遍历，遍历完成后，stack从顶到底为：（5，2），然后进入清算阶段会发现没有第1、2小阶段。
     任何环形结构都不可能出现没有第3小阶段的情况，因为我们总是从环形结构的最大值开始遍历的，它既然是整个环形结构的最大值，
     所以不会在遍历阶段的过程中被其他高度的山释放，一定会等到清算阶段时才会从栈中弹出。在最后的清算阶段，假设从栈中弹出的记录为（X，K），那么产生山峰对的逻辑如下。
     1）如果发现当前记录位于第1小阶段，产生山峰对为：如果K==1，产生2对；如果K＞1，产生2×K+C（2，K）对。
        这是因为（X，K）这个记录弹出之后，剩下的记录大于或等于2条，而整个图形是环，说明这K个X在last方向和next方向一定都能找到大于它们高度的山。
        举个例子，比如清算阶段时，stack 从顶到底依次为：（2，1）、（3，1）、（5，3）。在（2，1）弹出的时候，栈中还剩（3，1）、（5，3），说明这个2在last方向上能遇到3，
        在next方向上会遇到5（因为是环）。产生2对可见山峰。

     再举个例子，比如清算阶段时，stack从顶到底依次为：（1，7）、（2，6）、（3，10）、（5，7）。在（1，7）弹出的时候，
     栈中还剩（2，6）、（3，10）、（5，7）。说明这7个1在last方向上能遇到2，在next方向上会遇到5（因为是环）。
     每个1都能看见last方向上的2和next方向上的5，所以对外一共产生7×2个可见山峰对。7个1内部产生C（2，7）个可见山峰对。

     2）如果发现当前记录位于第2小阶段，也就是当前记录为栈中倒数第二条记录。那么需要查看栈中的最后一条记录，假设最后一条记录为（Y，M）。
       如果M==1，产生1×K+C（2，K）对；如果M＞1，产生2×K+C（2，K）对。举个例子，比如清算阶段时，stack从顶到底依次为：（4，7）、（5，1）。
       在（4，7）弹出的时候，栈中还剩（5，1），说明这7个4在last方向上能遇到5，在next方向上也会遇到5，但是遇到的是同一个5（因为是环）。
       每个4都能看见last方向上的5和next方向上的5，但因为是同一个5，所以对外一共产生1×7个可见山峰对，7个4内部产生C（2，7）个可见山峰对。

      再举个例子，比如清算阶段时，stack从顶到底依次为：（4，7）、（5，3）。
      在（4，7）弹出的时候，栈中还剩（5，3），说明这7个4在last方向上能遇到5，在next方向上也会遇到5，而且遇到的是不同的5（因为最后一条记录收集到的5不止1个）。
      每个4都能看见last方向上的5和next方向上的5，但因为是不同的5，所以对外一共产生2×7个可见山峰对，7个4内部产生C（2，7）个可见山峰对。

     3）如果发现当前记录位于第3小阶段，也就是当前记录为栈中最后一条记录。这个X一定是环中的最大值。根据“小找大”的方式，对外不会产生山峰对，只是K个X内部产生山峰对。
        如果K==1，产生0对；如果K＞1，产生C（2，K）对。

     根据单调栈的性质，全部过程的时间复杂度为O（N）
     */

    public int getVisibleNum(int [] arr){
        if(arr == null || arr.length < 2){
            return 0;
        }
        int size = arr.length;
        int maxIndex = 0;
        // 现在环中找到其中一个最大值的位置，随便哪一个
        for (int i = 0; i < size; i++){
            maxIndex = arr[maxIndex] < arr[i] ? i : maxIndex;
        }
        Stack<Record> stack = new Stack<>();
        //先把（最大值，1）记录放入stack中
        stack.push(new Record(arr[maxIndex]));
        //从最大值位置的下一个位置开始沿next方向遍历
        int index = nextIndex(maxIndex, size);
        //用"小找大"的方式统计所有可见山峰对
        int res = 0;
        //遍历阶段开始，当index再次回到maxIndex的时候，说明转了一圈，遍历阶段就结束
        while (index != maxIndex){
            //当前数字arr[index]要进栈，判断会不会破坏第一维的数字从顶到底依次变大
            //如果破坏了，就依次弹出栈顶记录， 并记录山峰对数量
            while (stack.peek().value < arr[index]){
                int k = stack.pop().times;
                //弹出记录为（x,k）,如果k == 1，产生 2 对
                //如果 K > 1,产生 2 * K + C(2,k)对
                res += getInternalSum(k) + 2 * k;
            }
            //当前数字 arr[index]要进入栈了，如果和当前栈顶数字一样就合并
            //不一样就把记录（arr[index], 1）放入栈中
            if(stack.peek().value == arr[index]){
                stack.peek().times++;
            }else {
                stack.push(new Record(arr[index]));
            }
            index = nextIndex(index, size);
        }
        //清算阶段开始
        //清算阶段的第 1 小阶段
        while (stack.size() > 2){
            int times = stack.pop().times;
            res += getInternalSum(times) + 2 * times;
        }
        // 清算阶段的第 2 小阶段
        if(stack.size() == 2){
            int times = stack.pop().times;
            res += getInternalSum(times) + (stack.peek().times == 1 ? times : 2 * times);
        }
        //清算第 3 小阶段
        res += getInternalSum(stack.pop().times);
        return res;
    }

    // 如果 k == 1, 返回 0; 如果 k > 1,返回 C(2,k)
    public int getInternalSum(int k){
        return k == 1 ? 0 : (k * (k - 1) / 2);
    }

    //环形数组中当前位置为 i ,数组长度为size,返回 i 的下一个位置
    public int nextIndex(int i, int size){
        return i < (size - 1) ? (i + 1) : 0;
    }


}
