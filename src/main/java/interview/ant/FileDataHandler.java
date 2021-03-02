package interview.ant;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-02 23:57:38
 */
public class FileDataHandler {

    public List<LineDataModel> doHandle(String directoryPath) {
        // Step1: 拿到所有文件
        File file = new File(directoryPath);
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("directoryPath is not directory");
        }

        File[] files = file.listFiles();
        assert files != null;
        BlockingQueue<LineDataModel> dataQueue = new LinkedBlockingQueue<>(100);

        // Step2: 通过线程池并发读取数据
        AtomicBoolean over = new AtomicBoolean(false);
        DataSortThread dataSortThread = new DataSortThread(over, dataQueue);
        Future<Map<String, BlockingQueue<LineDataModel>>> future = Executors.newSingleThreadExecutor().submit(dataSortThread);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(90));
        Arrays.stream(files).forEach(f -> threadPoolExecutor.execute(new FileReadThread(f, dataQueue)));

        // Step3: 优雅停机等待数据全局读取完毕，然后设置消费结束标志
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        over.set(true);

        try {
            Map<String, BlockingQueue<LineDataModel>> resultMap = future.get(10, TimeUnit.HOURS);
            // 每个分组下面的最小指标值
            return resultMap.values().stream().map(Queue::poll).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
