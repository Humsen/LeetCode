package interview;

import java.util.Objects;

/**
 * 面试 成都百词斩
 * 从头开始遍历字符串，使用两个指针start和end分别标记重复字符的起始和结束位置<br/>
 * 当start和end对应字符不相同等，揭露结果；每次将当前结果记录后，start指针刷新为当前end位置<br/>
 * 针对最后一个字符串，分两种情况特别处理。一是最后一个字符串与上一个字符不同，二是相同
 *
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2020-05-24 14:11
 */
public class Solution1 {
    public String compress(String input) {
        if (Objects.requireNonNull(input).length() == 0) {
            return input;
        }
        // 长度校验
        if (input.length() > 100) {
            throw new IllegalArgumentException("the length of input can't more than 100");
        }

        // 使用双指针记录重复字符的起始和结束
        int start = 0;
        // 用字符串实际的数字来表示字符出现的次数
        String num = "";
        char[] chars = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int end = 0; end < chars.length; end++) {
            char c = chars[end];
            // 非法字符检测
            boolean isLetter = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            if (!isLetter) {
                throw new IllegalArgumentException("Invalid parameter, found non-english letter: " + c);
            }

            // 匹配到重复字符并且当前不是最后一个字符，继续遍历
            if (c == chars[start] && end < chars.length - 1) {
                // 判断当前重复字符数量
                num = end > start ? String.valueOf(end - start + 1) : "";
                continue;
            }

            builder.append(chars[start]);
            // 匹配到重复字符并且当前是最后一个字符，判断是否加数字后跳出循环
            if (c == chars[start] && end == chars.length - 1) {
                if (end - start >= 1) {
                    builder.append(end - start + 1);
                }
                break;
            }

            builder.append(num);
            // 如果匹配不到重复，并且如果当前是最后一个字符，将当前字符添加到结果
            if (end == chars.length - 1) {
                builder.append(c);
                break;
            }

            // 头指针移动到下一个起点
            start = end;
            // 数字置空
            num = "";
        }

        return builder.toString();
    }
}
