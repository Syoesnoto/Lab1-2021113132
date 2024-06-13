package main.java.org.example;

import org.apache.commons.lang3.tuple.Pair;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.ui.view.Viewer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static java.lang.Thread.sleep;

/**
 * 主函数类.
 */
public class Main {
    static Map<String, Nodes> map = new HashMap<>();

    static Scanner scanner = new Scanner(System.in);

    /**
     * 使用邻接表表示的图，Nodes类为某节点的邻节点链表的头指针.
     */
    public static class Nodes {
        public Node head;
        public int size;

        public Nodes() {
            this.head = new Node(0, "");
            this.size = 0;
        }

        public boolean empty() {
            return size == 0;
        }

        /**
         * 插入新节点.
         *
         * @param weight 节点到word对应节点的边权值
         * @param word 邻节点的值
         */
        public void insert(int weight, String word) {
            Node node = new Node(weight, word);
            node.next = head.next;
            head.next = node;
            this.size++;
        }

        /**
         * 查找链表中是否包含关键字key.
         *
         * @param key 待查询邻节点的值
         * @return 查询到的邻节点
         */
        public Node find(String key) {
            Node cur = this.head.next;
            while (cur != null) {
                if (key.equals(cur.word)) {
                    return cur;
                }
                cur = cur.next;
            }
            return null;
        }

        static class Node {
            public int weight;
            public String word;
            public Node next;

            public Node(int weight, String word) {
                this.weight = weight;
                this.word = word;
            }
        }
    }

    /**
     * 读取文本内容并进行分词.
     *
     * @param fileName 文本所在路径
     * @return 返回文本的分词
     * @throws IOException IO异常
     */
    public static String[] readText(String fileName) throws IOException {
        String s = Files.readString(Paths.get(fileName));
        s = s.toLowerCase().replaceAll("\\p{P}", " ").replaceAll("[^a-z\\s]", "");
        return s.split("\\s+");
    }

    /**
     * 根据输入的分词创建图.
     * 若两单词相邻则在图中建立一条有向边，边的权值表示出现次数.
     *
     * @param words 分词数组
     * @throws IOException IO异常
     */
    public static void createGraph(String[] words) throws IOException {
        for (int i = 0; i < words.length - 1; i++) {
            connect(words[i], words[i + 1]);
        }
        connect(words[words.length - 1], "");

        Graph graph = new SingleGraph("graph");
        String styleSheet = "node { size: 20px; fill-color: gray; text-size: 20px; text-color: black;}"
                + "edge { size: 3px; text-size: 20px; text-color: black; arrow-size: 10px, 4px; arrow-shape: arrow;}";
        graph.setAttribute("ui.stylesheet", styleSheet);
        for (String word : map.keySet()) {
            Node node = graph.addNode(word);
            node.setAttribute("ui.label", word);
        }
        for (String word : map.keySet()) {
            Nodes.Node node = map.get(word).head.next;
            while (node != null) {
                Edge edge = graph.addEdge(word + " --> " + node.word, word, node.word, true);
                if (word.equals(node.word)) {
                    edge.setAttribute("ui.class", "loop");
                }
                edge.addAttribute("directed", 1);
                edge.addAttribute("ui.label", node.weight);
                node = node.next;
            }
        }
        graph.write("graph.gml");
        disableLog();
    }

    /**
     * 在word1对应的节点与word2对应的节点之间建立一条边.
     *
     * @param word1 第一个节点的值
     * @param word2 第二个节点的值
     */
    public static void connect(String word1, String word2) {
        Nodes head = map.get(word1);
        boolean flag = word2.equals("");
        if (head == null) {
            Nodes h = new Nodes();
            if (!flag) {
                h.insert(1, word2);
            }
            map.put(word1, h);
        } else if (!flag) {
            if (head.find(word2) == null) {
                head.insert(1, word2);
            } else {
                head.find(word2).weight++;
            }
        }
    }

    /**
     * 禁用日志消息.
     */
    static void disableLog() {
        LogManager logManager = LogManager.getLogManager();
        java.util.logging.Logger rootLogger = logManager.getLogger("");

        // 设置根日志记录器的级别为OFF
        rootLogger.setLevel(Level.OFF);
    }

    /**
     * 用户交互功能界面.
     */
    static void printInterface() {
        System.out.println("功能1：展示有向图。");
        System.out.println("功能2：寻找两个词之间的桥接词。");
        System.out.println("功能3：根据桥接词创建新文本。");
        System.out.println("功能4：计算两个词之间的最短路径。");
        System.out.println("功能5：计算某个词到其他所有词的最短路径。");
        System.out.println("功能6：随机游走。");
        System.out.println("功能7：退出。");
        System.out.print("请输入你想执行的功能的序号：");
    }

    /**
     * 用户交互模块.
     * 根据用户输入执行对应的功能.
     *
     * @throws IOException IO异常
     * @throws InterruptedException 中断异常
     * @throws GraphParseException 图解析异常
     */
    public static void userInteract(Scanner scanner) throws IOException, InterruptedException, GraphParseException {
        boolean flag = true;
        while (flag) {
            printInterface();
            String a = scanner.nextLine();
            switch (a) {
                case "1":
                    showDirectedGraph();
                    break;
                case "2":
                    System.out.print("请输入两个单词：");
                    String line = scanner.nextLine();
                    String[] words = line.split(" ");
                    if (words.length != 2) {
                        System.out.println("你输入的单词个数不对！");
                        break;
                    }
                    queryBridgeWords(words[0], words[1]);
                    break;
                case "3":
                    System.out.print("请输入文本：");
                    String text = scanner.nextLine();
                    System.out.println(generateNewText(text));
                    break;
                case "4":
                    System.out.print("请输入两个单词：");
                    line = scanner.nextLine();
                    words = line.split(" ");
                    if (words.length != 2) {
                        System.out.println("你输入的单词个数不对！");
                        break;
                    }
                    String path = calcShortestPath(words[0], words[1]);
                    if (path.equals("")) {
                        System.out.println("\"" + words[0] + "\" 与 \"" + words[1] + "\" 不可达！");
                    } else {
                        System.out.println(path);
                    }
                    break;
                case "5":
                    System.out.print("请输入一个单词：");
                    line = scanner.nextLine();
                    words = line.split(" ");
                    if (words.length != 1) {
                        System.out.println("你输入的单词个数不对！");
                        break;
                    }
                    System.out.println(calcShortestPath(line));
                    break;
                case "6":
                    FileOutputStream fos = new FileOutputStream("randomWalk.txt", true);
                    String result = randomWalk() + "\n";
                    fos.write(result.getBytes());
                    break;
                case "7":
                    flag = false;
                    break;
                default:
                    System.out.println("你输入的内容不满足要求！");
                    break;
            }
            System.out.println("-------------------------------------------");
        }
    }

    /**
     * 以图形化方式展示根据文本创建的有向图.
     *
     * @throws IOException IO异常
     * @throws GraphParseException 图解析异常
     * @throws InterruptedException 中断异常
     */
    static void showDirectedGraph() throws IOException, GraphParseException, InterruptedException {
        Graph tmpGraph = new SingleGraph("tmpGraph");
        tmpGraph.read("graph.gml");
        showChosenGraph(tmpGraph, "展示图片中，输入回车结束展示。");
    }

    /**
     * 以图形化方式展示指定的有向图.
     * 开启一个新的线程展示有向图，通过读取回车关闭该有向图的展示.
     *
     * @param graph 欲展示的有向图
     * @param s 欲输出的提示信息
     * @throws InterruptedException 中断异常
     */
    static void showChosenGraph(Graph graph, String s) throws InterruptedException {
        System.out.print(s);
        AtomicBoolean flag = new AtomicBoolean(true);
        Thread thread = new Thread(() -> {
            Viewer viewer = graph.display();
            // 设置布局算法
            viewer.enableAutoLayout();
            scanner.nextLine();
            // 关闭图形视图
            flag.set(false);
            viewer.close();
        });
        thread.start();
        while (flag.get()) {}
        sleep(200);
        thread.stop();
    }

    /**
     * 查询word1对应的节点与word2对应的节点之间的桥接词.
     * 桥接词word是指在文本中出现了形如 word1 word word2 的排列顺序.
     *
     * @param word1 第一个节点的值
     * @param word2 第二个节点的值
     * @return 查询到的桥接词
     */
    public static String queryBridgeWords(String word1, String word2) {
        boolean flag1 = (map.get(word1) == null);
        boolean flag2 = (map.get(word2) == null);
        String bridge = "";

        if (flag1 && flag2) {
            System.out.println("No \"" + word1 + "\" and \"" + word2 + "\" in the graph!");
        } else if (flag1) {
            System.out.println("No \"" + word1 + "\" in the graph!");
        } else if (flag2) {
            System.out.println("No \"" + word2 + "\" in the graph!");
        } else {
            bridge = findBridgeWords(word1, word2);
            if (bridge.equals("")) {
                System.out.println("No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!");
            } else if (bridge.split(" ").length == 1) {
                System.out.println("The bridge words from \"" + word1 + "\" to \"" + word2 + "\" is: " + bridge.replaceAll(" +", ", "));
            } else {
                System.out.println("The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: " + bridge.replaceAll(" +", ", "));
            }
        }
        return bridge;
    }

    /**
     * 查询桥接词的辅助函数.
     *
     * @param word1 第一个节点的值
     * @param word2 第二个节点的值
     * @return 查询到的桥接词
     */
    static String findBridgeWords(String word1, String word2) {
        String bridge = "";

        if ((map.get(word1) != null) && (map.get(word2) != null)) {
            Nodes.Node tmp1 = map.get(word1).head.next;
            while (tmp1 != null) {
                String word = tmp1.word;
                tmp1 = tmp1.next;
                if (map.get(word) == null) {
                    continue;
                }
                Nodes.Node tmp2 = map.get(word).head.next;
                while (tmp2 != null) {
                    if (word2.equals(tmp2.word)) {
                        bridge = word + "  " + bridge;
                    }
                    tmp2 = tmp2.next;
                }
            }
        }
        return bridge.strip();
    }

    /**
     * 根据桥接词生成新文本.
     * 扫描输入文本，若两个相邻词有桥接词则插入文本中，最后输出插入桥接词的新文本.
     *
     * @param inputText 输入文本
     * @return 生成的新文本
     */
    static String generateNewText(String inputText) {
        String[] words = inputText.toLowerCase().split(" ");
        String outputText = "";
        for (int i = 0; i < words.length - 1; i++) {
            String[] bridges = findBridgeWords(words[i], words[i + 1]).split(" +");
            int idx = (int) (Math.random() * bridges.length);
            outputText = outputText + " " + words[i] + " " + bridges[idx];
        }
        outputText = outputText + " " + words[words.length - 1];
        return outputText.replaceAll(" +", " ").strip();
    }

    /**
     * 计算两个单词之间的最短路径.
     *
     * @param word1 第一个节点的值
     * @param word2 第二个节点的值
     * @return 两个单词间最短路的字符串表示
     * @throws IOException IO异常
     * @throws GraphParseException 图解析异常
     * @throws InterruptedException 中断异常
     */
    public static String calcShortestPath(String word1, String word2) throws IOException, GraphParseException, InterruptedException {
        boolean flag1 = (map.get(word1) == null);
        boolean flag2 = (map.get(word2) == null);
        if (flag1 && flag2) {
            return "No \"" + word1 + "\" and \"" + word2 + "\" in the graph!";
        } else if (flag1) {
            return "No \"" + word1 + "\" in the graph!";
        } else if (flag2) {
            return "No \"" + word2 + "\" in the graph!";
        } else if (word1.equals(word2)) {
            return "\"" + word1 + "\" 与 \"" + word2 + "\" 之间的路径长度为：0";
        } else {
            Pair<Map<String, String>, Map<String, Integer>> pair = dijkstra(word1);
            Map<String, String> paths = pair.getKey();
            final Map<String, Integer> distance = pair.getValue();
            Graph tmpGraph = new SingleGraph("tmpGraph");
            tmpGraph.read("graph.gml");

            String tmp = word2;
            String path = "";
            if (!paths.get(tmp).equals("")) {
                while (!tmp.equals("")) {
                    if (paths.get(tmp) != null) {
                        Edge edge1 = tmpGraph.getEdge(paths.get(tmp) + " --> " + tmp);
                        if (edge1 != null) {
                            edge1.setAttribute("ui.style", "fill-color: red;");
                        }
                    }
                    path = tmp + " " + path;
                    tmp = paths.get(tmp);
                }
            }
            showChosenGraph(tmpGraph, "展示最短路中，输入回车结束展示。");
            System.out.println("\"" + word1 + "\" 与 \"" + word2 + "\" 之间的路径长度为：" + distance.get(word2));
            return path.strip().replaceAll(" ", "->");
        }
    }

    /**
     * 计算单词到其他所有单词的最短路径.
     *
     * @param word1 源节点的值
     * @return 源节点到其他节点的最短路的字符串表示
     */
    static String calcShortestPath(String word1) {
        if (map.get(word1) == null) {
            return "No \"" + word1 + "\" in the graph!";
        } else {
            Pair<Map<String, String>, Map<String, Integer>> pair = dijkstra(word1);
            Map<String, String> paths = pair.getKey();
            Map<String, Integer> distance = pair.getValue();

            String allPath = "";
            for (String i : map.keySet()) {
                if (i.equals(word1)) {
                    continue;
                }

                String path = "";
                String tmp = i;
                if (!paths.get(i).equals("")) {
                    while (!tmp.equals("")) {
                        path = tmp + " " + path;
                        tmp = paths.get(tmp);
                    }
                }
                path = "\"" + word1 + "\" 到 \"" + i + "\" 的路径为：" + path.strip().replaceAll(" ", "->") + "\t路径长度为：" + distance.get(i);
                allPath = allPath + "\n" + path;
            }
            return allPath.strip();
        }
    }

    /**
     * dijkstra算法.
     *
     * @param word1 源节点的值
     * @return 源节点到各节点的最短距离以及路径
     */
    static Pair<Map<String, String>, Map<String, Integer>> dijkstra(String word1) {
        Map<String, String> paths = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();
        Map<String, Boolean> visited = new HashMap<>();
        for (String i : map.keySet()) {
            paths.put(i, "");
            distance.put(i, 0x3f3f3f3f);
            visited.put(i, false);
        }
        distance.put(word1, 0);
        PriorityQueue<Pair<Integer, String>> heap = new PriorityQueue<>();
        heap.add(Pair.of(0, word1));

        while (!heap.isEmpty()) {
            Pair<Integer, String> pair = heap.poll();
            String word = pair.getValue();
            int dis = pair.getKey();
            if (visited.get(word)) {
                continue;
            }
            visited.put(word, true);

            Nodes.Node tmp = map.get(word).head.next;
            while (tmp != null) {
                if (distance.get(tmp.word) > dis + tmp.weight) {
                    distance.put(tmp.word, dis + tmp.weight);
                    paths.put(tmp.word, word);
                    heap.add(Pair.of(dis + tmp.weight, tmp.word));
                }
                tmp = tmp.next;
            }
        }
        return Pair.of(paths, distance);
    }

    /**
     * 随机游走.
     *
     * @return 随机游走的路径的字符串表示
     * @throws InterruptedException IO异常
     */
    static String randomWalk() throws InterruptedException {
        int randomNum = (int) (Math.random() * map.size());
        String begin = "";
        for (String word : map.keySet()) {
            if (randomNum == 0) {
                begin = word;
                break;
            }
            randomNum--;
        }

        AtomicBoolean run = new AtomicBoolean(true);
        //创建线程以支持用户通过外部输入终止随机游走
        Thread thread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    if (System.in.available() > 0) {
                        scanner.nextLine();
                        run.set(false);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        System.out.println("开始随机游走，输入回车结束...");

        String result = begin;
        System.out.print(result);
        TimeUnit.MILLISECONDS.sleep(500);
        Nodes.Node next = map.get(begin).head.next;
        int range = map.get(begin).size;
        while (run.get() && next != null) {
            randomNum = (int) (Math.random() * range);
            for (int i = 0; i < randomNum; i++) {
                next = next.next;
            }
            if (result.contains(begin + " " + next.word)) {
                break;  //判断是否重边
            }
            begin = next.word;
            result = result + " " + next.word;
            System.out.print(" -> " + next.word);
            TimeUnit.MILLISECONDS.sleep(500);
            range = map.get(next.word).size;
            if (range != 0) {
                next = map.get(next.word).head.next;
            } else {
                break;
            }
        }
        if (run.get()) {
            System.out.println("\n随机游走结束");
        } else {
            System.out.println("人为终止随机游走");
        }
        return result;
    }

    /**
     * 主函数.
     *
     * @param args 用户输入参数
     * @throws IOException IO异常
     * @throws InterruptedException 中断异常
     * @throws GraphParseException 图解析异常
     */
    public static void main(String[] args) throws IOException, InterruptedException, GraphParseException {
        System.out.println("请输入文本所在的路径：");
        String text = scanner.nextLine();
        String[] data = readText(text);
        createGraph(data);
        userInteract(scanner);
    }
}
