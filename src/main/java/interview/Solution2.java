package interview;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 笔试
 *
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2020-06-03 18:25
 */
public class Solution2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nextLine = scanner.nextLine();
        String[] s = nextLine.trim().split(" ");
        Integer[] integers = Arrays.stream(s).map(Integer::valueOf).toArray(Integer[]::new);

        Integer[] integers1 = Arrays.stream(s).map(Integer::parseInt).toArray(Integer[]::new);
    }

}
