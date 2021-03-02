package interview.ant;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-03 00:33:49
 */
public class DataSortThread implements Callable<Map<String, BlockingQueue<LineDataModel>>> {

    private final AtomicBoolean over;

    private final BlockingQueue<LineDataModel> dataQueue;

    public DataSortThread(AtomicBoolean over, BlockingQueue<LineDataModel> dataQueue) {
        this.over = over;
        this.dataQueue = dataQueue;
    }

    @Override
    public Map<String, BlockingQueue<LineDataModel>> call() {
        Map<String, BlockingQueue<LineDataModel>> dataMap = new ConcurrentHashMap<>();

        while (!over.get() || !dataQueue.isEmpty()) {
            try {
                LineDataModel lineDataModel = dataQueue.poll();
                if (lineDataModel == null) {
                    continue;
                }

                BlockingQueue<LineDataModel> dataModelBlockingQueue = dataMap.get(lineDataModel.getGroupId());
                if (dataModelBlockingQueue == null) {
                    dataModelBlockingQueue = new PriorityBlockingQueue<>();
                    dataMap.putIfAbsent(lineDataModel.getGroupId(), dataModelBlockingQueue);
                }
                dataModelBlockingQueue.put(lineDataModel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return dataMap;
    }
}
