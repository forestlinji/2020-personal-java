import com.alibaba.fastjson.JSONObject;
import factory.ThreadPoolFactory;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;


public class Main {
    static Map<String, Result> map1 = new ConcurrentHashMap<>(); //存放user
    static Map<String, Result> map2 = new ConcurrentHashMap<>(); //存放repo
    static Map<String, Result> map3 = new ConcurrentHashMap<>(); //存放userRepo


    public static void main(String[] args) throws Exception {
//        System.out.println("Hello World");
//        System.out.println(args.length);
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("i", "init", true, "init");
        options.addOption("u", "user", true, "user");
        options.addOption("e", "event", true, "event");
        options.addOption("r", "repo", true, "repo");
        CommandLine commandLine = parser.parse(options, args);
        if (commandLine.hasOption("i")) {
            // 初始化事件
            init(commandLine.getOptionValue("i"));
        } else if (commandLine.hasOption("u") && commandLine.hasOption("e") && commandLine.hasOption("repo")) {
            // 查询每一个人在每一个项目的 4 种事件的数量。
            int result = countByUserAndRepo(commandLine.getOptionValue("u"), commandLine.getOptionValue("repo"), commandLine.getOptionValue("e"));
            System.out.println(result);
        } else if (commandLine.hasOption("u") && commandLine.hasOption("e")) {
            // 查询个人的 4 种事件的数量。
            int result = countByUser(commandLine.getOptionValue("u"), commandLine.getOptionValue("e"));
            System.out.println(result);
        } else if (commandLine.hasOption("r") && commandLine.hasOption("e")) {
            // 查询每一个项目的 4 种事件的数量
            int result = countByRepo(commandLine.getOptionValue("r"), commandLine.getOptionValue("e"));
            System.out.println(result);
        } else {
//            throw new Exception("参数不合法");
            System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
        }
    }


    private static void init(String path) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        File Dir = new File(path);
//        获取后缀是json格式的文件列表
        File[] files = Dir.listFiles(file -> file.getName().endsWith(".json"));
        ThreadPoolExecutor pool = ThreadPoolFactory.getPool();
        CountDownLatch countDownLatch = new CountDownLatch(files.length);
        for (File file : files) {
            pool.execute(new FileHandler(file, countDownLatch));
        }
//        利用 CountDownLatch 实现线程同步
        countDownLatch.await();

        String s1 = JSONObject.toJSONString(map1);
        String s2 = JSONObject.toJSONString(map2);
        String s3 = JSONObject.toJSONString(map3);
        // 将json写入文件
        FileUtils.writeStringToFile(new File("out1.json"), s1, "UTF-8");
        FileUtils.writeStringToFile(new File("out2.json"), s2, "UTF-8");
        FileUtils.writeStringToFile(new File("out3.json"), s3, "UTF-8");
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    /**
     * 查询每一个人在每一个项目的 4 种事件的数量。
     *
     * @param user  用户名
     * @param repo  仓库名
     * @param event 事件类型
     * @throws IOException
     */
    private static int countByUserAndRepo(String user, String repo, String event) throws IOException {
        String s = FileUtils.readFileToString(new File("out3.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject object = jsonObject.getJSONObject(user + "_" + repo);
        if (object != null) {
            return object.getInteger(event);
        } else {
            return 0;
        }
    }


    /**
     * 查询个人的 4 种事件的数量。
     *
     * @param user  用户名
     * @param event 事件类型
     * @throws IOException
     */
    private static int countByUser(String user, String event) throws IOException {
        String s = FileUtils.readFileToString(new File("out1.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject object = jsonObject.getJSONObject(user);
        if (object != null) {
            return object.getInteger(event);
        } else {
            return 0;
        }

    }


    /**
     * 查询每一个项目的 4 种事件的数量。
     *
     * @param repo  仓库名
     * @param event 事件类型
     * @throws IOException
     */
    private static int countByRepo(String repo, String event) throws IOException {
        String s = FileUtils.readFileToString(new File("out2.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject object = jsonObject.getJSONObject(repo);
        if (object != null) {
            return object.getInteger(event);
        } else {
            return 0;
        }
    }


    /**
     * 判断给定的事件是否是指定的事件类型
     *
     * @param type 事件类型
     * @return
     */
    public static boolean attention(String type) {
        switch (type) {
            case "PushEvent":
            case "IssueCommentEvent":
            case "IssuesEvent":
            case "PullRequestEvent":
                return true;
            default:
                return false;
        }

    }
}