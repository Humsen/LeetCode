package interview.ant;

import interview.ant.template.BaseDataHandler;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-03 02:08:36
 */
public class FileDataHandler extends BaseDataHandler<String, FileReadThread, DataSortThread, LineDataModel> {
    @Override
    public DataSortThread createConsumer() {
        return new DataSortThread(dataQueue);
    }

    @Override
    public FileReadThread createProducer() {
        return new FileReadThread(dataQueue);
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
        Arrays.stream(files)
                .map(f -> {
                    FileReadThread producer = createProducer();
                    producer.setFile(f);
                    return producer;
                })
                .forEach(threadPoolExecutor::execute);
    }

    @Override
    public void doConsume(Callable<Map<String, BlockingQueue<LineDataModel>>> callable) {
        future = executorService.submit(callable);
    }
}
