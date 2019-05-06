import org.junit.jupiter.api.Test;
import static junit.framework.Assert.assertEquals;
import java.io.*;
import java.util.*;
public class Tests {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private TreeMap<String, String> rightAnswer = new TreeMap<>();

    @Test
    public void testLong() throws IOException {
        String[] args = {"-l", "/C:/Users/User/LowSkill"};
        Parser.main(args);
        rightAnswer.put("RealLs.txt", "rwx  08:19  3855332" );
        rightAnswer.put("1.png", "rwx  07:09  367048");
        assertEquals(rightAnswer, Ls.answer);
    }
    @Test
    public void testAll() throws IOException {
        String[] args = {"-l", "-h", "-r", "/C:/Users/User/LowSkill"};
        Parser.main(args);
        rightAnswer.put("RealLs.txt", "rwx  08:19  3,68 MB");
        rightAnswer.put("1.png", "rwx  07:09  358,45 KB");
        assertEquals(rightAnswer, Ls.answer);

    }

    @Test
    public void testEmpty() throws IOException {
        String[] args = {"-l", "-h", "-r", "/C:/Users/User/HightSkill"};
        Parser.main(args);
        rightAnswer.put("Пусто","");
        assertEquals(rightAnswer, Ls.answer);

    }
    @Test
    public void testR() throws IOException {
        String[] args = { "-l","-r", "/C:/Users/User/LowSkill"};
        Parser.main(args);
        rightAnswer.put("RealLs.txt", "rwx  08:19  3855332" );
        rightAnswer.put("1.png", "rwx  07:09  367048");
        assertEquals(rightAnswer, Ls.answer);

    }

    @Test
    public void testLongHumRead() throws IOException {
        String[] args = {"-l", "-h",  "/C:/Users/User/LowSkill"};
        Parser.main(args);
        rightAnswer.put("RealLs.txt", "rwx  08:19  3,68 MB");
        rightAnswer.put("1.png", "rwx  07:09  358,45 KB");
        assertEquals(rightAnswer, Ls.answer);
    }

    @Test
    public void testOutput() throws IllegalArgumentException, IOException {
        String[] args = {"-o", "example.txt", "example.txt", "/C:/Users/User/LowSkill/1.png"};
        Parser.main(args);
        String rightAnswer = "rwx  07:09  367048   1.png";
        String fromFile = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("example.txt"));
            fromFile = reader.readLine();
        } catch (Exception e) {
            System.out.println("runtime error");
        }
        assertEquals(rightAnswer, fromFile);
    }
}