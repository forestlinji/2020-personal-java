import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class TestMain {

    @Test
    public void testPath() {
//        C:\Users\forestj\IdeaProjects\2020-personal-java\testFile1
//        File Dir = new File("C:\\Users\\forestj\\IdeaProjects\\2020-personal-java\\testFile1");
//        File[] files = Dir.listFiles(file -> file.getName().endsWith(".json"));
//        for (File file : files) {
//            System.out.println(file.getName());
//        }
    }


    /**
     * 测试init
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testInit() throws Exception {
        Main.main(new String[]{"--init", "C:\\Users\\forestj\\IdeaProjects\\2020-personal-java\\testFile1"});
    }


    /**
     * 测试countByUser， 结果：2 0
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testCountByUser() throws Exception {
        Main.main(new String[]{"-u", "kamilsk", "-e", "PushEvent"});
        Main.main(new String[]{"-u", "kamilskasfaf", "-e", "PushEvent"});
    }


    /**
     * 测试countByRepo， 结果 9 0
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testCountByRepo() throws Exception {
        Main.main(new String[]{"-r", "fujimura/hi", "-e", "PushEvent"});
        Main.main(new String[]{"-r", "fujimura/hisdfdsf", "-e", "PushEvent"});
    }


    /**
     * 测试countByUserAndRepo， 结果 7 0
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testCountByUserAndRepo() throws Exception {
        Main.main(new String[]{"-u", "tschortsch", "-e", "PushEvent", "--repo", "tschortsch/gulp-bootlint"});
        Main.main(new String[]{"-u", "tschortsfasfch", "-e", "PushEvent", "--repo", "tschortsch/gulp-bootlint"});
//        UserRepo {user='tschortsch', repo='tschortsch/gulp-bootlint'}":{"IssueCommentEvent":0,"IssuesEvent":0,"PullRequestEvent":0,"PushEvent":7}
    }


    /**
     * 测试异常
     */
    @Test(expected = FileNotFoundException.class)
    public void testFileHandler(){
        new FileHandler(new File("geag"), new CountDownLatch(1)).run();
    }


    /**
     * 测试异常
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testPara() throws Exception {
        Main.main(new String[]{"-e", "tschortsch"});

    }
}
