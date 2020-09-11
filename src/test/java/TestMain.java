import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.io.IOException;

public class TestMain {

    @Test
    public void testPath(){
//        C:\Users\forestj\IdeaProjects\2020-personal-java\testFile1
//        File Dir = new File("C:\\Users\\forestj\\IdeaProjects\\2020-personal-java\\testFile1");
//        File[] files = Dir.listFiles(file -> file.getName().endsWith(".json"));
//        for (File file : files) {
//            System.out.println(file.getName());
//        }
    }


    @Test
    public void testInit() throws IOException, ParseException {
        Main.main(new String[]{"-i", "C:\\Users\\forestj\\IdeaProjects\\2020-personal-java\\testFile1"});
    }


    @Test
    public void testCountByUser() throws IOException, ParseException {
        Main.main(new String[]{"-u", "kamilsk", "-e", "PushEvent"});
    }


    @Test
    public void testCountByRepo() throws IOException, ParseException {
        Main.main(new String[]{"-r", "fujimura/hi", "-e", "PushEvent"});
    }


    @Test
    public void countByUserAndRepo() throws IOException, ParseException {
        Main.main(new String[]{"-u", "tschortsch", "-e", "PushEvent", "--repo", "tschortsch/gulp-bootlint"});
//        UserRepo {user='tschortsch', repo='tschortsch/gulp-bootlint'}":{"IssueCommentEvent":0,"IssuesEvent":0,"PullRequestEvent":0,"PushEvent":7}
    }
}
