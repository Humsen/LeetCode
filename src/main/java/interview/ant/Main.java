package interview.ant;

import java.util.List;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-03 01:09:32
 */
public class Main {
    public static void main(String[] args) {
        FileDataHandler fileDataHandler = new FileDataHandler();
        List<LineDataModel> lineDataModels = fileDataHandler.doHandle("E:\\workspaces\\workapace_hex\\LeetCode\\src\\main\\java\\interview\\ant\\files");
        System.out.println(lineDataModels);
    }
}
