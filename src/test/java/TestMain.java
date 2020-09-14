import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class TestMain {



    /**
     * 测试init
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testInitAndPath() throws Exception {
        Main.main(new String[]{"--init", "C:\\Users\\forestj\\IdeaProjects\\2020-personal-java\\testFile1"});
        Main.main(new String[]{"-u", "kamilsk", "-e", "PushEvent"});
        Main.main(new String[]{"-r", "fujimura/hi", "-e", "PushEvent"});
        Main.main(new String[]{"-u", "tschortsch", "-e", "PushEvent", "--repo", "tschortsch/gulp-bootlint"});
    }


    /**
     * 测试countByUser
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testCountByUser() throws Exception {
        int count = Main.countByUser("kamilsk", "PushEvent");
        assert count == 2;
        count = Main.countByUser("kamilskasfaf", "PushEvent");
        assert count == 0;
    }


    /**
     * 测试countByRepo
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testCountByRepo() throws Exception {
        Main.main(new String[]{"-r", "fujimura/hi", "-e", "PushEvent"});
        int count = Main.countByRepo("fujimura/hi", "PushEvent");
        assert count == 9;
        count = Main.countByRepo("fujimura/hisdfdsf", "PushEvent");
        assert count == 0;
    }


    /**
     * 测试countByUserAndRepo
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testCountByUserAndRepo() throws Exception {
        int count = Main.countByUserAndRepo("tschortsch", "tschortsch/gulp-bootlint", "PushEvent");
        assert count == 7;
        count = Main.countByUserAndRepo("tschortsfdsach", "tschortsch/gulp-bootlint", "PushEvent");
        assert count == 0;
    }


    /**
     * 测试异常
     */
    @Test(expected = FileNotFoundException.class)
    public void testFileHandler() {
        new FileHandler(new File("geag"), new CountDownLatch(1)).run();
    }


    /**
     * 测试异常
     * @throws Exception
     */
    @Test
    public void testPara() throws Exception {
        Main.main(new String[]{"-e", "tschortsch"});
    }

}
