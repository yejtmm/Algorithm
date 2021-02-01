package BitOperation.one;

/**

 不用额外变量交换两个整数的值

 如何不用任何额外变量交换两个整数的值？

 */

public class ExchangeNumber {

    public void exchange(int a, int b){

        a = a ^ b;

        b = a ^ b;

        a = a ^ b;

        System.out.println("a = " + a + "; b = " + b);
    }

    /**
     ● 假设a异或b的结果记为c，c就是a整数位信息和b整数位信息的所有不同信息。
     比如，a=4=100，b=3=011，a^b=c=000。● a异或c的结果就是b。
     比如a=4=100，c=111，a^c=011=3=b。● b异或c的结果就是a。
     比如b=3=011，c=111，b^c=100=4=a。
     所以，在执行上面三行代码之前，假设有a信息和b信息。
     执行完第一行代码之后，a变成了c，b还是b；
     执行完第二行代码之后，a仍然是c，b变成了a；
     执行完第三行代码之后，a变成了b，b仍然是a。过程结束。
     */

}
