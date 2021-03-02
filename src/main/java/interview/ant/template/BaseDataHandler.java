package interview.ant.template;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
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
 * T - 消费者参数
 * P - 生产者线程类
 * C - 消费者线程类
 * M - 最终结果模型
 *
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-02 23:57:38
 */
public abstract class BaseDataHandler<T, P extends BaseProduceThread, C extends BaseConsumeThread<M>, M> extends Observable {

    protected Future<Map<String, BlockingQueue<M>>> future;
    protected ExecutorService executorService = Executors.newSingleThreadExecutor();
    protected BlockingQueue<M> dataQueue = new LinkedBlockingQueue<>(100);
    protected ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(90));

    public List<M> doHandle(T param) {
        // Step1: 创建消费者
        C observer = createConsumer();
        // Step2: 启动消费
        doConsume(observer);
        // Step3: 启动生产
        doProduce(param, observer);
        // Step4: 生产完毕优雅停机
        doShutdown();
        // Step5: 通知消费者生产完毕
        setChanged();
        notifyObservers();
        // Step6: 获取结果
        return doGetResult();
    }

    public abstract C createConsumer();

    public abstract P createProducer();

    public abstract void doProduce(T param, C observer);

    public abstract void doConsume(Callable<Map<String, BlockingQueue<M>>> callable);

    public void doShutdown() {
        // 优雅停机等待数据全局读取完毕，然后设置消费结束标志
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public List<M> doGetResult() {
        try {
            Map<String, BlockingQueue<M>> resultMap = future.get(10, TimeUnit.HOURS);
            // 每个分组下面的最小指标值
            return resultMap.values().stream().map(Queue::poll).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
