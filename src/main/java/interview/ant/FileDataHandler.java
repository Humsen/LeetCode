package interview.ant;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-03 02:08:36
 */
public class FileDataHandler extends BaseDataHandler<String, DataSortThread> {
    @Override
    public DataSortThread createObserver() {
        return new DataSortThread();
    }

    @Override
    public void doProduce(String directoryPath, DataSortThread observer) {
        // 添加观察者
        addObserver(observer);

        // Step1: 拿到所有文件
        File file = new File(directoryPath);
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("directoryPath is not directory");
        }
        File[] files = file.listFiles();
        assert files != null;

        // Step2: 通过线程池并发读取数据
        Arrays.stream(files).forEach(f -> threadPoolExecutor.execute(new FileReadThread(f, dataQueue)));
    }

    @Override
    public void doConsume(Callable<Map<String, BlockingQueue<LineDataModel>>> callable) {
        future = executorService.submit(callable);
    }
}
