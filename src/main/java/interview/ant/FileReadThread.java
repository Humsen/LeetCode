package interview.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

/**
 * @author 何明胜 husen@hemingsheng.cn
 * @since 2021-03-03 00:02:29
 */
public class FileReadThread implements Runnable {

    private final File file;

    private final BlockingQueue<LineDataModel> dataQueue;

    public FileReadThread(File file, BlockingQueue<LineDataModel> dataQueue) {
        this.file = file;
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                handleLineData(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLineData(String line) {
        String[] strings = line.split(",");
        if (strings.length != 3) {
            return;
        }

        LineDataModel lineDataModel = new LineDataModel(strings[0], strings[1], Float.parseFloat(strings[2]));
        try {
            dataQueue.put(lineDataModel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
