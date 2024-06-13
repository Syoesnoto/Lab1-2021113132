import org.graphstream.stream.GraphParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.example.Main.*;

//需要将Main文件中第429行注释后进行单元测试，否则会展示图片干扰测试
public class calcShortestPathTest {
    static String input;
    static String output;

    @BeforeClass
    public static void init() throws IOException {
        createGraph(readText("input.txt"));
    }

    @Test
    public void validInputTest() throws IOException, GraphParseException, InterruptedException {
        String s = calcShortestPath("little", "you");
        assertEquals("little->star->how->you", s);
    }

    @Test
    public void oneInvalidInputTest() throws IOException, GraphParseException, InterruptedException {
        String s = calcShortestPath("little", "he");
        assertEquals("No \"he\" in the graph!", s);
    }

    @Test
    public void twoInvalidInputTest() throws IOException, GraphParseException, InterruptedException {
        String s = calcShortestPath("large", "he");
        assertEquals("No \"large\" and \"he\" in the graph!", s);
    }

    @Test
    public void zeroInputTest() throws IOException, GraphParseException, InterruptedException {
        input = "2\n" + "\n" + "7\n";

        // 重定向标准输入流
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(reader);

        // 重定向标准输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        userInteract(scanner);

        output = outputStream.toString().trim();
        String expectedOutput = "你输入的单词个数不对！";
        assertTrue(output.contains(expectedOutput));
    }

    @Test
    public void oneInputTest() throws IOException, GraphParseException, InterruptedException {
        input = "2\n" + "he\n" + "7\n";

        // 重定向标准输入流
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(reader);

        // 重定向标准输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        userInteract(scanner);

        output = outputStream.toString().trim();
        String expectedOutput = "你输入的单词个数不对！";
        assertTrue(output.contains(expectedOutput));
    }

    @Test
    public void manyInputTest() throws IOException, GraphParseException, InterruptedException {
        input = "2\n" + "i you he\n" + "7\n";

        // 重定向标准输入流
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(reader);

        // 重定向标准输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        userInteract(scanner);

        output = outputStream.toString().trim();
        String expectedOutput = "你输入的单词个数不对！";
        assertTrue(output.contains(expectedOutput));
    }

    @AfterClass
    public static void recover() {  // 恢复原始的标准输入和输出流
        System.setIn(System.in);
        System.setOut(System.out);
    }
}