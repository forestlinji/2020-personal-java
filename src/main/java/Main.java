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

    }

    private static void countByUserAndRepo(String user, String repo, String event) {

    }

    private static void countByUser(String user, String event) {}

    private static boolean Attention(String type){
        return type.equals("PushEvent")
                || type.equals("IssueCommentEvent")
                || type.equals("IssuesEvent")
                || type.equals("PullRequestEvent");
    }

}
