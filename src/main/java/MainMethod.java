import RecursionAndDynamicProgramming.two.MatrixPathSum;

/**
 * main
 */
public class MainMethod {
    public static void main(String[] args) {
        MatrixPathSum matrixPathSum = new MatrixPathSum();
        int [] [] m = {{1,3,5,9,0}, {8,1,3,4,0},{5,0,6,1,0},{8,8,4,0,0}};
        int sum2 = matrixPathSum.minPathSum2(m);
        System.out.println(sum2);

    }
}
