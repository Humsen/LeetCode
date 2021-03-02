package interview.ant.template;

import interview.ant.LineDataModel;

import java.util.concurrent.BlockingQueue;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-03 02:52:32
 */
public abstract class BaseProduceThread implements Runnable {

    protected BlockingQueue<LineDataModel> dataQueue;

    public void setDataQueue(BlockingQueue<LineDataModel> dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {

    }
}
