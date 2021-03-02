package interview.ant.template;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-03 02:32:09
 */
public abstract class BaseConsumeThread<M> implements Callable<Map<String, BlockingQueue<M>>>, Observer {

    protected boolean over;

    protected BlockingQueue<M> dataQueue;

    public void setDataQueue(BlockingQueue<M> dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public Map<String, BlockingQueue<M>> call() {
        return null;
    }
}
