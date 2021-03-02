package interview.ant;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-02 23:57:38
 */
public abstract class BaseDataHandler<T, S extends ConsumeThread> extends Observable {

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<Map<String, BlockingQueue<LineDataModel>>> future;
    BlockingQueue<LineDataModel> dataQueue = new LinkedBlockingQueue<>(100);
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(90));

    public abstract S createObserver();

    public List<LineDataModel> doHandle(T param) {
        S observer = createObserver();
        observer.setDataQueue(dataQueue);

        // 调用模板方法
        doConsume(observer);
        doProduce(param, observer);
        doShutdown();

        return doGetResult();
    }

    public abstract void doProduce(T param, S observer);

    public abstract void doConsume(Callable<Map<String, BlockingQueue<LineDataModel>>> callable);

    public void doShutdown() {
        // Step3: 优雅停机等待数据全局读取完毕，然后设置消费结束标志
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers();
        executorService.shutdown();
    }

    public List<LineDataModel> doGetResult() {
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
