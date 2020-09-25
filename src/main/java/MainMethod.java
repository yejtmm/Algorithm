import stackAndQueue.eight.SingleStack;

/**
 * main
 */
public class MainMethod {
    public static void main(String[] args) {
//        Stack<Integer> stack = new Stack<>();
//        stack.push(1);
//        stack.push(2);
//        stack.push(3);
//        stack.push(4);
//        stack.push(5);
//       reverseStack.reverse(stack);
//       stack.stream().forEach(a -> {
//           System.out.println(a);
//       });
        SingleStack singleStack = new SingleStack();
        int[][] ints = singleStack.rightWay(new int[]{3, 4, 1, 5, 6, 2, 7});
        int[][] ints2 = singleStack.getNearLessNoRepeat(new int[]{3, 4, 1, 5, 6, 2, 7});
        int[][] ints3 = singleStack.getNearLess(new int[]{3, 4, 1, 5, 6, 2, 7});

    }
}
