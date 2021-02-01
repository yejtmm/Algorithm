package OtherIssues.one;

/**
 给定一个等概率随机产生1～5的随机函数rand1To5如下：

 public int rand1To5(){
    return (int) (Math.random() * 5) + 1;
 }
 除此之外，不能使用任何额外的随机机制，请用rand1To5实现等概率随机产生1～7的随机函数rand1To7。

 补充问题：给定一个以p概率产生0，以1-p概率产生1的随机函数rand01p如下：

 public int rand01p(){
        double p = 0.83;
        return Math.random() < p ? 0 : 1;
 }

 除此之外，不能使用任何额外的随机机制，请用rand01p实现等概率随机产生1～6的随机函数rand1To6。


 进阶问题：给定一个等概率随机产生1～m的随机函数rand1ToM如下：

 public int rand1ToM(int m){
        return (int) (Math.random() * m) + 1;
 }

 除此之外，不能使用任何额外的随机机制。有两个输入参数，分别为m和n，
 请用rand1ToM（m）实现等概率随机产生1～n的随机函数rand1ToN。

 */
public class From5RandomTo7Random {

    /**
     原问题：
     1.rand1To5（）等概率随机产生 1，2，3，4，5。
     2.rand1To5（）-1等概率随机产生 0，1，2，3，4。
     3.（rand1To5（）-1）*5等概率随机产生 0，5，10，15，20。
     4.（rand1To5（）-1）*5+（rand1To5（）-1）等概率随机产生0，1，2，3，…，
        23，24。注意，这两个rand1To5（）是指独立的两次调用，请不要简化。这是“插空儿”的过程。
     5.如果步骤4产生的结果大于20，则重复进行步骤4，直到产生的结果在0～20之间。
       同时可以轻易知道出现21～24的概率会平均分配到0～20上。这是“筛”过程。
     6.步骤5会等概率随机产生0～20，所以步骤5的结果再进行%7操作，就会等概率地随机产生0～6。
     7.步骤6的结果再加1，就会等概率地随机产生1～7。
     */

    public int rand1To5(){
        return (int) (Math.random() * 5) + 1;
    }

    public int rand1To7(){
        int num = 0;
        do{
            num = (rand1To5() - 1) * 5 + rand1To5() - 1;
        }while (num > 20);

        return num % 7 + 1;
    }

    /**
     补充问题:
     虽然rand01p方法以p的概率产生0，以1-p的概率产生1，但是rand01p产生01和10的概率却都是p（1-p）,
     可以利用这一点来实现等概率随机产生0和1的函数。
     */

    public int rand01p(){
        //可随意改变p
        double p = 0.83;
        return Math.random() < p ? 0 : 1;
    }

    public int rand01(){
        int num;
        do {
             num = rand01p();
        }while (num == rand01p());
        return num;
    }

    /**
     有了等概率随机产生0和1的函数后，再按照如下步骤生成等概率随机产生1～6的函数：
     1.rand01（）方法可以等概率随机产生0和1。
     2.rand01（）*2等概率随机产生0和2。
     3.rand01（）*2+rand01（）等概率随机产生 0，1，2，3。
     注意，这两个 rand01（）是指独立的两次调用，请不要化简。这是“插空儿”过程。

     步骤3已经实现了等概率随机产生0～3的函数，具体请参看如下代码中的rand0To3方法：
     */
    public int rand0To3(){
        return rand01() * 2 + rand01();
    }

    /**
     4.rand0To3（）*4+rand0To3（）等概率随机产生0，1，2，…，14，15。
       注意，这两个rand0To3（）是指独立的两次调用，请不要简化。这还是“插空儿”过程。
     5.如果步骤4产生的结果大于11，则重复进行步骤4，直到产生的结果在0～11之间。
       那么可以知道出现12～15的概率会平均分配到0～11上。这是“筛”过程。
     6.因为步骤5的结果是等概率随机产生0～11，所以用第5步的结果再进行%6操作，就会等概率随机产生0～5。
     7.第6步的结果再加1，就会等概率随机产生1～6。

     */

    public int rand1To6(){
        int num = 0;
        do{
            num = rand0To3() * 4 + rand0To3();
        }while (num > 11);
        return num % 6 + 1;
    }

    /**
     进阶问题:
     只要给定某一个区间上的等概率随机函数，就可以实现任意区间上的随机函数。所以，如果m≥n，直接进入如上所述的“筛”过程；
     如果m＜n，先进入如上所述的“插空儿”过程，直到产生比n的范围还大的随机范围后，再进入“筛”过程。
     具体地说，是调用k次rand1ToM（m），生成有k位的m进制数，并且产生的范围要大于或等于n。
     比如随机5到随机7的问题，首先生成0～24范围的数，其实就是 0～（52-1）范围的数。
     在把范围扩到大于或等于 n 的级别之后，如果真实生成的数大于或等于 n，就忽略，也就是“筛”过程。
     只留下小于或等于 n 的数，那么在 0～n-1上就可以做到均匀分布。
     */

    public int rand1ToM(int m){
        return (int) (Math.random() * m) + 1;
    }

    public int rand1ToN(int n, int m){
        int [] nMSys = getMSysNum(n - 1, m);
        int [] randNum = getRanMSysNumLessN(nMSys, m);
        return getNumFromMSysNum(randNum, m) + 1;
    }

    //把value转成 m进制数
    public int [] getMSysNum(int value, int m){
        int [] res = new int[32];
        int index = res.length - 1;
        while (value != 0){
            res[index--] = value % m;
            value = value / m;
        }
        return res;
    }

    //等概率随机产生一个 0 ~ nMsys范围的数，只不过是用m进制数表达的
    public int [] getRanMSysNumLessN(int [] nMSys, int m){
        int [] res = new int[nMSys.length];
        int start = 0;
        while (nMSys[start] == 0){
            start++;
        }
        int index = start;
        boolean lastEqual = true;
        while (index != nMSys.length){
            res[index] = rand1ToM(m) - 1;
            if(lastEqual){
                if(res[index] > nMSys[index]){
                    index = start;
                    lastEqual = true;
                    continue;
                }else{
                    lastEqual = res[index] == nMSys[index];
                }
            }
            index++;
        }
        return res;
    }

    //把 m进制数转换成十进制数
    public int getNumFromMSysNum(int [] mSysNum,  int m){
        int res = 0;
        for (int i = 0; i != mSysNum.length; i++){
            res = res * m + mSysNum[i];
        }
        return res;
    }

}













