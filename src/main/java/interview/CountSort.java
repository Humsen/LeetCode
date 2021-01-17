package interview;

import java.util.Arrays;

/**
 * 叽里呱啦笔试题
 *
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2020-05-30 19:09
 */
public class CountSort {
    public int[] countSort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int[] countArray = new int[10];
        for (int num : nums) {
            // 参数校验
            if (num < 0 || num > 9) {
                throw new IllegalArgumentException("the num less than 0 or more than 9");
            }
            // 统计每个数字出现的次数
            countArray[num]++;
        }

        // 结果数组的下标
        int index = 0;
        int[] result = new int[nums.length];
        for (int i = 0; i < countArray.length; i++) {
            int count = countArray[i];
            // 为0表示该数组不存在该数
            if (count == 0) {
                continue;
            }
            // 对出现count次的数组依次排序
            for (int j = 0; j < count; j++) {
                result[index++] = i;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        CountSort countSort = new CountSort();
        System.out.println(Arrays.toString(countSort.countSort(new int[]{0, 1, 0, 4, 8, 2})));
    }
}
