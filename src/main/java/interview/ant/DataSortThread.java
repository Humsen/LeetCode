package interview.ant;

import java.util.Map;
import java.util.Observable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-03 00:33:49
 */
public class DataSortThread extends ConsumeThread {

    @Override
    public Map<String, BlockingQueue<LineDataModel>> call() {
        Map<String, BlockingQueue<LineDataModel>> dataMap = new ConcurrentHashMap<>();

        while (!over || !dataQueue.isEmpty()) {
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

    @Override
    public void update(Observable o, Object arg) {
        over = true;
    }
}
