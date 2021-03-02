package interview;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author hemingsheng@bixin.cn
 * @since 2021-02-27 21:24:32
 */
public class Main {

    /**
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
     * 注意：答案中不可以包含重复的三元组。
     * 示例：
     * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
     * <p>
     * 满足要求的三元组集合为：
     * [
     * [-1, 0, 1],
     * [-1, -1, 2]
     * ]
     */
    public static void main(String[] args) {
        Integer[] nums = new Integer[]{-1, 0, 1, 2, -1, 4};

        solution(nums).forEach(integers -> System.out.println(Arrays.toString(integers)));
    }

    public static List<Integer[]> solution(Integer[] nums) {
        if (nums.length <= 2) {
            return Collections.singletonList(nums);
        }

        List<Integer[]> result = new ArrayList<>(nums.length - 2);
        List<String> resultTmp = new ArrayList<>(nums.length - 2);
        List<Integer> integers = Arrays.asList(nums);
        // Collections.sort(integers);
        for (int i = 0; i < integers.size() - 2; i++) {
            for (int j = 1; j < integers.size() - 1; j++) {
                for (int k = 2; k < integers.size(); k++) {
                    int first = integers.get(i);
                    int second = integers.get(j);
                    int third = integers.get(k);

                    if (first + second + third != 0) {
                        continue;
                    }

                    List<Integer> integers1 = Arrays.asList(first, second, third);
                    Collections.sort(integers1);
                    if (resultTmp.contains(integers1.get(0) + integers1.get(1) + integers.get(2) + "")) {
                        continue;
                    }
                    resultTmp.add(integers1.get(0) + integers1.get(1) + integers.get(2) + "");
                    result.add(new Integer[]{first, second, third});

                }
            }
        }

        return result;
    }
}
