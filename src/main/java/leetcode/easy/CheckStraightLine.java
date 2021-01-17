package leetcode.easy;

import java.util.Arrays;

/**
 * 1232. 缀点成线
 *
 * @author 何明胜 husen@hemingsheng.cn
 * @see <a href="https://leetcode-cn.com/problems/check-if-it-is-a-straight-line/">https://leetcode-cn.com/problems/check-if-it-is-a-straight-line/</a>
 * @since 2021-01-17 17:43:29
 */
public class CheckStraightLine {

    public boolean checkStraightLine(int[][] coordinates) {
        if (coordinates.length <= 2) {
            return true;
        }
        // 纵坐标都是0，在一条直线
        if (Arrays.stream(coordinates).map(ints -> ints[0]).allMatch(integer -> coordinates[0][0] == integer)) {
            return true;
        }

        int x1 = coordinates[0][0];
        int y1 = coordinates[0][1];
        int x2 = coordinates[1][0];
        int y2 = coordinates[1][1];
        // 得到系数k和b y=kx+b
        for (int i = 2; i < coordinates.length; i++) {
            if ((y2 - y1) * coordinates[i][0] != coordinates[i][1] * (x2 - x1) - (y1 * (x2 - x1) - (y2 - y1) * x1)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // int[][] coordinates = new int[][]{{1, 1}, {2, 2}, {3, 4}, {4, 5}, {5, 6}, {7, 7}};
        // int[][] coordinates = new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}};
        int[][] coordinates = new int[][]{{2, 1}, {4, 2}, {6, 3}};
        // int[][] coordinates = new int[][]{{2, 4}, {2, 5}, {2, 8}};
        // int[][] coordinates = new int[][]{{0, 0}, {0, 5}, {5, 5}, {5, 0}};
        boolean checkStraightLine = new CheckStraightLine().checkStraightLine(coordinates);
        System.out.println(checkStraightLine);
    }
}
