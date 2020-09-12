import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiFunction;

/**
 * 读取并解析json文件
 */
public class FileHandler implements Runnable{
    private File file;
    private CountDownLatch countDownLatch;

    @Override
    public void run(){
        LineIterator it = null;
        try {
//            按行读取防止 OOM
            it = FileUtils.lineIterator(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            countDownLatch.countDown();
            return;
        }
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                JSONObject jsonObject = JSONObject.parseObject(line);
                String type = jsonObject.getString("type");

                if (Main.attention(type)) {
//                    解析json
                    String userStr = jsonObject.getJSONObject("actor").getString("login");
                    String repoStr = jsonObject.getJSONObject("repo").getString("name");
                    Main.map1.compute(userStr, updateResult(type));
                    Main.map2.compute(repoStr, updateResult(type));
                    Main.map3.compute(userStr + "_" + repoStr, updateResult(type));
                }
            }

        } finally {
            LineIterator.closeQuietly(it);
            countDownLatch.countDown();
        }
    }

    /**
     * 根据事件类型更新值
     * @param type 事件类型
     * @return
     */
    private static BiFunction<String, Result, Result> updateResult(String type) {
        return (key, value) -> {
            if (value == null) {
                value = new Result();
            }
            switch (type) {
                case "PushEvent":
                    value.PushEvent++;
                    break;
                case "IssueCommentEvent":
                    value.IssueCommentEvent++;
                    break;
                case "IssuesEvent":
                    value.IssuesEvent++;
                    break;
                case "PullRequestEvent":
                    value.PullRequestEvent++;
                    break;
            }
            return value;
        };
    }

    public FileHandler(File file, CountDownLatch countDownLatch) {
        this.file = file;
        this.countDownLatch = countDownLatch;
    }
}
