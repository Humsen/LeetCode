package leetcode.hard;

/**
 * 4. 寻找两个正序数组的中位数
 *
 * @author 何明胜 husen@hemingsheng.cn
 * @see <a href="https://leetcode-cn.com/problems/median-of-two-sorted-arrays/">https://leetcode-cn.com/problems/median-of-two-sorted-arrays/</a>
 * @since 2020-05-24 21:10
 */
public class FindMedianSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            if (nums2.length % 2 == 1) {
                return nums2[nums2.length / 2];
            } else {
                return (nums2[nums2.length / 2 - 1] + nums2[nums2.length / 2]) / 2.0;
            }
        }
        if (nums2.length == 0) {
            if (nums1.length % 2 == 1) {
                return nums1[nums1.length / 2];
            } else {
                return (nums1[nums1.length / 2 - 1] + nums1[nums1.length / 2]) / 2.0;
            }
        }

        return findMedianInSortedArrays(nums1, nums2);
    }

    private double findMedianInSortedArrays(int[] nums1, int[] nums2) {
        int middle = (nums1.length + nums2.length) / 2;
        boolean odd = (nums1.length + nums2.length) % 2 == 1;

        int index = 0;
        int index1 = 0;
        int index2 = 0;
        int current = 0;
        int nextNum = 0;
        while (index < middle && index1 < nums1.length && index2 < nums2.length) {
            if (nums1[index1] < nums2[index2]) {
                current = nums1[index1];
                index1++;
                if (index1 < nums1.length) {
                    nextNum = Math.min(nums1[index1], nums2[index2]);
                } else {
                    nextNum = nums2[index2];
                }
            } else {
                current = nums2[index2];
                index2++;
                if (index2 < nums2.length) {
                    nextNum = Math.min(nums1[index1], nums2[index2]);
                } else {
                    nextNum = nums1[index1];
                }
            }

            index++;
        }

        if (index < middle) {
            if (index1 < nums1.length) {
                while (index++ < middle) {
                    current = nums1[index1++];
                    nextNum = nums1[index1];
                }
            } else {
                while (index++ < middle) {
                    current = nums2[index2++];
                    nextNum = nums2[index2];
                }
            }
        }

        // 如果为奇数，直接返回下一个数
        return odd ? nextNum : (current + nextNum) / 2.0;
    }

    public static void main(String[] args) {
        FindMedianSortedArrays findMedianSortedArrays = new FindMedianSortedArrays();

        System.out.println(findMedianSortedArrays.findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
        System.out.println(findMedianSortedArrays.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
        System.out.println(findMedianSortedArrays.findMedianSortedArrays(new int[]{}, new int[]{1}));
        System.out.println(findMedianSortedArrays.findMedianSortedArrays(new int[]{}, new int[]{2, 3}));
        System.out.println(findMedianSortedArrays.findMedianSortedArrays(new int[]{1, 2}, new int[]{-1, 3}));
        System.out.println(findMedianSortedArrays.findMedianSortedArrays(new int[]{1}, new int[]{2, 3, 4}));
    }
}
