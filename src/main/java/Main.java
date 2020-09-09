import com.alibaba.fastjson.JSONObject;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Main {
    static Map<String, Result> map1 = new HashMap<String, Result>(); //user
    static Map<String, Result> map2 = new HashMap<String, Result>(); //repo
    static Map<String, Result> map3 = new HashMap<String, Result>();

    public static void main(String[] args) throws ParseException, IOException {
//        System.out.println("Hello World");
        System.out.println(args.length);
        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        options.addOption("i","init",true, "11111");
        options.addOption("u","user", true, "11111");
        options.addOption("e","event", true, "11111");
        options.addOption("r","repo", true, "11111");
        CommandLine commandLine = parser.parse( options, args );
        if(commandLine.hasOption("i")){
            init(commandLine.getOptionValue("i"));
        }else if(commandLine.hasOption("u") && commandLine.hasOption("e") && commandLine.hasOption("repo")){
            countByUserAndRepo(commandLine.getOptionValue("u"), commandLine.getOptionValue("repo"), commandLine.getOptionValue("e"));
        }else if(commandLine.hasOption("u") && commandLine.hasOption("e")){
            countByUser(commandLine.getOptionValue("u"), commandLine.getOptionValue("e"));
        }else if(commandLine.hasOption("r") && commandLine.hasOption("e")){
            countByRepo(commandLine.getOptionValue("r"), commandLine.getOptionValue("e"));
        }

        System.out.println(commandLine.getOptionValue("e"));
    }

    private static void countByRepo(String repo, String event) {

    }

    private static void init(String path) throws IOException {
//        long start = System.currentTimeMillis();
//        Thread.sleep(10 * 1000);
        System.out.println("start");
        LineIterator it = FileUtils.lineIterator(new File(path), "UTF-8");
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                JSONObject jsonObject = JSONObject.parseObject(line);
                String type = jsonObject.getString("type");

                if (Attention(type)) {
                    UserRepo userRepo = new UserRepo();
                    userRepo.setUser(jsonObject.getJSONObject("actor").getString("login"));
                    userRepo.setRepo(jsonObject.getJSONObject("repo").getString("name"));
                    Result user = map1.getOrDefault(userRepo.getUser(), new Result());
                    Result repo = map2.getOrDefault(userRepo.getUser(), new Result());
                    Result userr = map3.getOrDefault(userRepo.toString(), new Result());
                    switch (type) {
                        case "PushEvent":
                            user.pushEvent++;
                            repo.pushEvent++;
                            userr.pushEvent++;
                            break;
                        case "IssueCommentEvent":
                            user.issueCommentEvent++;
                            repo.issueCommentEvent++;
                            userr.issueCommentEvent++;
                            break;
                        case "IssuesEvent":
                            user.issuesEvent++;
                            repo.issuesEvent++;
                            userr.issuesEvent++;
                            break;
                        case "pullRequestEvent":
                            user.pullRequestEvent++;
                            repo.pullRequestEvent++;
                            userr.pullRequestEvent++;
                            break;

                    }
                    map1.put(userRepo.getUser(), user);
                    map2.put(userRepo.getRepo(), repo);
                    map3.put(userRepo.toString(), userr);
                }
                // do something with line
            }
            System.out.println(map1.keySet().size());
            System.out.println(map2.keySet().size());
            System.out.println(map3.keySet().size());
        } finally {
            LineIterator.closeQuietly(it);
        }
        String s1 = JSONObject.toJSONString(map1);
        String s2 = JSONObject.toJSONString(map2);
        String s3 = JSONObject.toJSONString(map3);
        FileUtils.writeStringToFile(new File("out1.json"), s1, "UTF-8");
        FileUtils.writeStringToFile(new File("out2.json"), s2, "UTF-8");
        FileUtils.writeStringToFile(new File("out3.json"), s3, "UTF-8");
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
//        try {
//            Thread.sleep(100000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private static void countByUserAndRepo(String user, String repo, String event) {

    }

    private static void countByUser(String user, String event) {}

    private static boolean Attention(String type){
//        return type.equals("PushEvent")
//                || type.equals("IssueCommentEvent")
//                || type.equals("IssuesEvent")
//                || type.equals("PullRequestEvent");
        switch (type){
            case "PushEvent":
            case "IssueCommentEvent":
            case "IssuesEvent":
            case "pullRequestEvent":
                return true;
            default:
                return false;
    }

}
