package RecursionAndDynamicProgramming.three;

/**
 换钱的最好货币数

 给定数组 arr，arr 中所有的值都为正数且不重复。每个值代表一种面值的货币，每种面值的货币可以使用任意张，再给定一个整数aim，代表要找的钱数，求组成aim的最少货币数。

 例如：
        arr[5, 2, 3], aim=20
 4张5元可以组成20元，其他的找钱方案都要使用更多张的货币，所以返回4。
        arr[5, 2, 3], aim=0
 不用任何货币就可以组成0元，返回0。
        arr[3,5], aim=2
 根本无法组成2元，钱不能找开的情况下默认返回-1。
 */

public class ExchangeMoney {

    /**
     暴力尝试
     */
    public int minCoins1(int [] arr, int aim){
        if(arr == null || arr.length == 0 || aim < 0){
            return -1;
        }
        return process(arr, 0, aim);
    }

    public int process(int [] arr, int i, int rest){
        // base case;
        // 已经没有面值能够考虑了
        // 如果此时剩余的钱为0,返回0张
        // 如果此时剩余的钱不是0,返回-1
        if(i == arr.length){
            return rest == 0 ? 0 : -1;
        }
        // 最少张数，初始时为 -1, 因为还没有找到有效解
         int res = -1;
        // 依次尝试使用当前面值（arr[i]）0张、1张、k张，但不能超过rest
        for (int k = 0; k * arr[i] <= rest; k++){
            // 使用了k张arr[i],剩下的钱为rest - k * arr[i]
            // 交给剩下的面值去搞定(arr[i+1..N-1])
            int next = process(arr, i + 1, rest - k * arr[i]);
            if(next != -1){
                res = res == -1 ? next + k : Math.min(res, next + k);
            }
        }
        return res;
    }

    /**
     优化之后
     尝试过程是无后效性的。上面的尝试其实明显是无后效性的，但是为了方便理解，我们还是举个例子，arr={5，2，3，1}，aim=100，那么process（arr，0，100）的返回值就是最终答案。
     如果使用2张5元，0张2元，那么后续的过程是process（arr，2，90）；但如果使用0张5元，5张2元，那么后续的过程还是process（arr，2，90）。
     这个状态的返回值肯定是一样的，说明一个状态最终的返回值与怎么达到这个状态的过程无关。

     1）可变参数i和rest一旦确定，返回值就确定了。
     2）如果可变参数i和rest组合的所有情况组成一张二维表，这张表一定可以装下所有的返回值。i的含义是arr中的位置，又因为process中允许i来到arr的终止位置，
       所以i的范围是[0，N]。rest代表剩余的钱数，剩余的钱不可能大于aim，所以rest的范围是[0，aim]。
       所以这张二维表是一个N行aim列的表，记为dp[][]。
     3）最终状态是process（arr，0，aim），也就是dp[0][aim]的值，位于dp表0行最后一列。
     4）填写初始的位置，根据process（arr，i，rest）函数的base case：
        if( i == arr.length){
                return rest == 0 ? 0 : -1;
        }
        i=arr.length，就是dp表中最后一行dp[N][…]，这最后一行只有dp[N][0]是0，其他位置都是-1。
     5）base case之外的情况都是普遍位置，在process （arr，i，rest）函数中如下：


     // 最少张数， 初始时为 -1, 因为还没找到有效解
     int res = -1;
     // 依次尝试使用当前面值（arr[i]）0张、1张、k张，但不能超过rest
     for (int k = 0; k * arr[i] <= rest; k++){
            // 使用了k张arr[i],剩下的钱为rest - k * arr[i]
            // 交给剩下的面值去搞定(arr[i+1..N-1])
        int next = process(arr, i + 1, rest - k * arr[i]);
        if(next != -1){
            res = res == -1 ? next + k : Math.min(res, next + k);
        }
     }
     return res;

     process （arr，i，rest）的返回值就是dp[i][rest]，这个位置依赖哪些位置呢？

      i行    ......                           ...   *dp[i][rest-arr[i]]   ...      dp[i][rest]
     i+1行   ......   dp[i+1][rest-2*arr[i]]  ...  dp[i+1][rest-1*arr[i]] ...  dp[i+1][rest-0*arr[i]]

     表中右上角的位置就是dp[i][rest]，根据process （arr，i，rest）函数可知，dp[i][rest]的值就是以下这些值中最小的一个：
     dp[i+1][rest-0*arr[i]]+0、dp[i+1][rest-1*arr[i]]+1、dp[i+1][rest-2*arr[i]]+2、…dp[i+1][rest-k*arr[i]]+k、……直到越界。
     在表中已经标出了这些位置。也就是说，要想得到dp[i][rest]的值，必须枚举i+1行的这些值。

     但其实这个枚举过程是可以优化的。请看表中用星号标出的位置，即dp[i][rest-arr[i]]这个位置。如果在求 dp[i][rest]之前，dp[i][rest-arr[i]]已经求过了。
     那么我们看看 dp[i][rest-arr[i]]是怎么求出来的，同样，根据 process （arr，i，rest）函数可知，dp[i][rest-arr[i]]的值就是以下这些值中最小的一个：
     dp[i+1][rest-arr[i]]+0、dp[i][rest-2*arr[i]]+1、…dp[i+1][rest-k*arr[i]]+k-1、……直到越界。
     请对比一下 dp[i][rest]和 dp[i][rest-arr[i]]各自依赖的位置就可以得到，dp[i][rest]=min{dp[i][rest-arr[i]]+1，dp[i+1][rest]}。
     也就是说，求 dp[i][rest]只依赖下面的一个位置（dp[i+1][rest]）和左边的一个位置（dp[i][rest-arr[i]]+1）即可。

     现在dp表中最后一排的值已经有了，既然剩下的位置都只依赖下面和左边的位置，那么只要从左往右求出倒数第二排、从左往右求出倒数第三排……从左往右求出第一排即可。

     6）最后返回dp[0][aim]位置的值就是答案。
     */

    public int minCoins2(int [] arr, int aim){
        if(arr == null || arr.length == 0 || aim < 0){
            return -1;
        }
        int N = arr.length;
        int [] [] dp = new int[N + 1][aim + 1];
        // 设置最后一排的值， 除dp[N][0]为 0 外，其他都是-1
        for (int col = 1; col <= aim; col++){
            dp[N][col] = -1;
        }
        //从底往上计算每一行
        for (int i = N - 1; i >= 0; i--){
            //每一行都是从左往右
             for (int rest = 0; rest <= aim; rest++){
                 //初始时先设置 dp[i][rest]的值无效
                 dp[i][rest] = -1;
                 if(dp[i + 1][rest] != -1){
                     dp[i][rest] = dp[i + 1][rest];
                 }
                 if(rest - arr[i] >= 0 && dp[i][rest - arr[i]] != -1){
                     if(dp[i][rest] == -1){
                         dp[i][rest] = dp[i][rest - arr[i]] + 1;
                     }else {
                         dp[i][rest] = Math.min(dp[i][rest], dp[i][rest - arr[i]] + 1);
                     }
                 }
             }

        }
        return dp[0][aim];
    }

    /**
     minCoins2 方法就是填一张 N×aim 的表，而且因为省掉了枚举过程，所以每个位置的值都在O（1）的时间内得到，该方法时间复杂度为O（N×aim）。
     原问题还可以在动态规划基础上做空间压缩。空间压缩的原理请读者参考本书“矩阵的最小路径和”问题，
     */

}
