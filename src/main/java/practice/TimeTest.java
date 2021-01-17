package practice;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 时间测试
 *
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2020-05-31 23:01
 */
public class TimeTest {
    public static void main(String[] args) {
        arrayAndList();
    }

    public static void arrayAndList() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>(64);

        // 纳秒
        long start1 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list1.add(i);
        }
        System.out.println("1: " + (System.nanoTime() - start1));

        long start2 = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list2.add(i);
        }
        System.out.println("2: " + (System.nanoTime() - start2));
    }
}
