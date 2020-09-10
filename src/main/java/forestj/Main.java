package forestj;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Main {
    static Map<String, Result> map1 = new HashMap<String, Result>(); //存放user
    static Map<String, Result> map2 = new HashMap<String, Result>(); //存放repo
    static Map<String, Result> map3 = new HashMap<String, Result>(); //存放userRepo


    public static void main(String[] args) throws ParseException, IOException {
//        System.out.println("Hello World");
//        System.out.println(args.length);
        CommandLineParser parser = new BasicParser();
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
            countByUserAndRepo(commandLine.getOptionValue("u"), commandLine.getOptionValue("repo"), commandLine.getOptionValue("e"));
        } else if (commandLine.hasOption("u") && commandLine.hasOption("e")) {
            // 查询个人的 4 种事件的数量。
            countByUser(commandLine.getOptionValue("u"), commandLine.getOptionValue("e"));
        } else if (commandLine.hasOption("r") && commandLine.hasOption("e")) {
            // 查询每一个项目的 4 种事件的数量
            countByRepo(commandLine.getOptionValue("r"), commandLine.getOptionValue("e"));
        }
    }


    private static void init(String path) throws IOException {
//        long start = System.currentTimeMillis();
//        Thread.sleep(10 * 1000);
//        System.out.println("start");
//        LineIterator it = FileUtils.lineIterator(new File(path), "UTF-8");
        // 获取指定目录下后缀是.json的文件
        File Dir = new File(path);
        File[] files = Dir.listFiles(file -> file.getName().endsWith(".json"));
        for (File file : files) {
            // 使用流读取防止 OOM
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            try {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    JSONObject jsonObject = JSONObject.parseObject(line);
                    String type = jsonObject.getString("type");

                    if (attention(type)) {
                        UserRepo userRepo = new UserRepo();
                        userRepo.setUser(jsonObject.getJSONObject("actor").getString("login"));
                        userRepo.setRepo(jsonObject.getJSONObject("repo").getString("name"));
                        Result user = map1.getOrDefault(userRepo.getUser(), new Result());
                        Result repo = map2.getOrDefault(userRepo.getRepo(), new Result());
                        Result userAndRepo = map3.getOrDefault(userRepo.toString(), new Result());
                        switch (type) {
                            case "PushEvent":
                                user.PushEvent++;
                                repo.PushEvent++;
                                userAndRepo.PushEvent++;
                                break;
                            case "IssueCommentEvent":
                                user.IssueCommentEvent++;
                                repo.IssueCommentEvent++;
                                userAndRepo.IssueCommentEvent++;
                                break;
                            case "IssuesEvent":
                                user.IssuesEvent++;
                                repo.IssuesEvent++;
                                userAndRepo.IssuesEvent++;
                                break;
                            case "pullRequestEvent":
                                user.PullRequestEvent++;
                                repo.PullRequestEvent++;
                                userAndRepo.PullRequestEvent++;
                                break;
                        }
                        map1.put(userRepo.getUser(), user);
                        map2.put(userRepo.getRepo(), repo);
                        map3.put(userRepo.toString(), userAndRepo);
                    }
                    // do something with line
                }

            } finally {
                LineIterator.closeQuietly(it);
            }
        }
//        System.out.println(map1.keySet().size());
//        System.out.println(map2.keySet().size());
//        System.out.println(map3.keySet().size());
        String s1 = JSONObject.toJSONString(map1);
//        System.out.println(s1.substring(1, 1000));
        String s2 = JSONObject.toJSONString(map2);
        String s3 = JSONObject.toJSONString(map3);
        // 将json写入文件
        FileUtils.writeStringToFile(new File("out1.json"), s1, "UTF-8");
        FileUtils.writeStringToFile(new File("out2.json"), s2, "UTF-8");
        FileUtils.writeStringToFile(new File("out3.json"), s3, "UTF-8");
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
    }


    /**
     * 查询每一个人在每一个项目的 4 种事件的数量。
     * @param user 用户名
     * @param repo 仓库名
     * @param event 事件类型
     * @throws IOException
     */
    private static void countByUserAndRepo(String user, String repo, String event) throws IOException {
        String s = FileUtils.readFileToString(new File("out3.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        Integer result = jsonObject.getJSONObject(new UserRepo(user, repo).toString()).getInteger(event);
        System.out.println(result);

    }


    /**
     * 查询个人的 4 种事件的数量。
     * @param user 用户名
     * @param event 事件类型
     * @throws IOException
     */
    private static void countByUser(String user, String event) throws IOException {
        String s = FileUtils.readFileToString(new File("out1.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        Integer result = jsonObject.getJSONObject(user).getInteger(event);
        System.out.println(result);

    }


    /**
     * 查询每一个项目的 4 种事件的数量。
     * @param repo 仓库名
     * @param event 事件类型
     * @throws IOException
     */
    private static void countByRepo(String repo, String event) throws IOException {
        String s = FileUtils.readFileToString(new File("out2.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        Integer result = jsonObject.getJSONObject(repo).getInteger(event);
        System.out.println(result);
    }


    /**
     * 判断给定的事件是否是指定的事件类型
     * @param type 事件类型
     * @return
     */
    private static boolean attention(String type) {
        switch (type) {
            case "PushEvent":
            case "IssueCommentEvent":
            case "IssuesEvent":
            case "pullRequestEvent":
                return true;
            default:
                return false;
        }

    }
}