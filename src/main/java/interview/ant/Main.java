package interview.ant;

import java.util.List;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-03 01:09:32
 */
public class Main {
    public static void main(String[] args) {
        /*
         * 使用模板模式+观察者模式
         * 模板模式可以自定义数据来源及消费
         * 观察者模式用于通知消费者生产完毕
         */
        FileDataHandler fileDataHandler = new FileDataHandler();
        List<LineDataModel> lineDataModels = fileDataHandler.doHandle("E:\\workspaces\\workapace_hex\\LeetCode\\src\\main\\java\\interview\\ant\\files");
        System.out.println(lineDataModels);
    }
}
