import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.example.Main.*;

public class queryBridgeWordsTest {
    @BeforeClass
    public static void init() throws IOException {
        createGraph(readText("input1.txt"));
    }

    @Test
    public void twoInvalidInputTest()  {
        // 重定向标准输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        String ans = queryBridgeWords("large", "he");
        String output = outputStream.toString().trim();
        String expectedOutput = "No \"large\" and \"he\" in the graph!";
        assertEquals(expectedOutput, output);
        assertEquals("", ans);
    }

    @Test
    public void invalidValidInputTest()  {
        // 重定向标准输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        String ans = queryBridgeWords("large", "you");
        String output = outputStream.toString().trim();
        String expectedOutput = "No \"large\" in the graph!";
        assertEquals(expectedOutput, output);
        assertEquals("", ans);
    }

    @Test
    public void validInvalidInputTest()  {
        // 重定向标准输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        String ans = queryBridgeWords("you", "large");
        String output = outputStream.toString().trim();
        String expectedOutput = "No \"large\" in the graph!";
        assertEquals(expectedOutput, output);
        assertEquals("", ans);
    }

    @Test
    public void noBridgeTest()  {
        String ans = queryBridgeWords("i", "you");
        assertEquals("", ans);
    }

    @Test
    public void oneBridgeTest()  {
        String ans = queryBridgeWords("in", "sky");
        assertEquals("the", ans);
    }

    @Test
    public void twoBridgeTest()  {
        String ans = queryBridgeWords("how", "wonder");
        assertTrue(ans.contains("i"));
        assertTrue(ans.contains("you"));
    }

    @Test
    public void noNeighborTest()  {
        String ans = queryBridgeWords("end", "ending");
        assertEquals("", ans);
    }

    @AfterClass
    public static void recover() {  // 恢复原始的输出流
        System.setOut(System.out);
    }
}
