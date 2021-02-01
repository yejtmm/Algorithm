package ArraysAndMatrices.one;

/**
 转圈打印矩阵

 给定一个整型矩阵matrix，请按照转圈的方式打印它。

        1     2     3     4
        5     6     7     8
        9     10    11    12
        13    14    15    16
 打印结果为：1，2，3，4，8，12，16，15，14，13，9，5，6，7，11，10

 额外空间复杂度为O（1）。
 */
public class CirclePrintMatrix {

    /**
     矩阵分圈处理:

     在矩阵中用左上角的坐标（tR，tC）和右下角的坐标（dR，dC）就可以表示一个子矩阵，比如，题目中的矩阵，
     当（tR，tC）=（0，0）、（dR，dC）=（3，3）时，表示的子矩阵就是整个矩阵，那么这个子矩阵最外层的部分如下：

                1     2     3    4
                5                8
                9                12
                13    14    15   16

     如果能把这个子矩阵的外层转圈打印出来，那么在（tR，tC）=（0，0）、（dR，dC）=（3，3）时，
     打印的结果为：1，2，3，4，8，12，16，15，14，13，9，5。接下来令tR和tC加1，即（tR，tC）=（1，1），
     令dR和dC减1，即（dR，dC）=（2，2），此时表示的子矩阵如下：

                6     7
                10    11

     再把这个子矩阵转圈打印出来，结果为：6，7，11，10。把tR和tC加1，即（tR，tC）=（2，2），令dR和dC减1，
     即（dR，dC）=（1，1）。如果发现左上角坐标跑到了右下角坐标的右方或下方，整个过程就停止。
     已经打印的所有结果连起来就是我们要求的打印结果。
     printEdge方法是转圈打印一个子矩阵的外层。
     */

    public void spiralOrderPrint(int [] [] matrix){
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC){
            printEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    public void printEdge(int [] [] m, int tR, int tC, int dR, int dC){
        if (tR == dR){
            for (int i = tC; i <= dC; i++){
                System.out.print(m[tR][i] + " ");
            }
        }else if(tC == dC){
            for (int i = tR; i <= dR; i++){
                System.out.println(m[i][tC] + " ");
            }
        }else {
            int curC = tC;
            int curR = tR;
            while (curC != dC){
                System.out.println(m[tR][curC] + " ");
                curC++;
            }
            while (curR != dR){
                System.out.println(m[curR][dC] + " ");
                curR++;
            }
            while (curC != tC){
                System.out.println(m[dR][curC] + " ");
                curC--;
            }
            while (curR != tR){
                System.out.println(m[curR][tC] + " ");
                curR--;
            }
        }
    }








}
