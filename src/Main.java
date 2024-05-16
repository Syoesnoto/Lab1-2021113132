import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import java.util.concurrent.TimeUnit;

public class Main {

    static Map<String, Nodes> map = new HashMap<>();

    public static class Nodes {
        public Node head;
        public int size;
        public Nodes() {
            this.head = new Node(0, "");
            this.size = 0;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        // 插入新节点
        public void insert(int weight, String word) {
            Node node = new Node(weight, word);
            node.next = head.next;
            head.next = node;
            this.size++;
        }

        //查找链表中是否包含关键字key
        public Node find(String key) {
            Node cur = this.head.next;
            while (cur != null) {
                if (key.equals(cur.word)) return cur;
                cur = cur.next;
            }
            return null;
        }

        public int size() {
            return this.size;
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

    public static String[] readText(String fileName) throws IOException {
        String s = Files.readString(Paths.get(fileName));
        s = s.toLowerCase().replaceAll("\\p{P}", " ").replaceAll("[^a-z\\s]", "");
        return s.split("\\s+");
    }

    public static void createGraph(String[] words) {
        for(int i = 0; i < words.length - 1; i++) connect(words[i], words[i + 1]);
        connect(words[words.length - 1], "");
    }

    public static void connect(String word1, String word2) {
        Nodes head = map.get(word1);
        boolean flag = word2.equals("");
        if (head == null) {
            Nodes h = new Nodes();
            if (!flag) h.insert(1, word2);
            map.put(word1, h);
        } else if (!flag) {
            if (head.find(word2) == null) head.insert(1, word2);
            else head.find(word2).weight ++ ;
        }
    }

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

    static volatile boolean run = true;

    static class MyThread extends Thread {
        //重写Thread类的run()
        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            String in = scanner.nextLine();
            run = false;
        }
    }

    static void userInteract() throws IOException, InterruptedException {
        while (true) {
            printInterface();
            Scanner scanner = new Scanner(System.in);
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
                    String bridge = queryBridgeWords(words[0], words[1]);
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
                    if (path.equals("")) System.out.println("\"" + words[0] + "\" 与 \"" + words[1] + "\" 不可达！");
                    else System.out.println(path);
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
                    MyThread t = new MyThread();
                    t.start();
                    String result = randomWalk() + "\n";
                    fos.write(result.getBytes());
                    break;
                case "7":
                    return;
                default:
                    System.out.println("你输入的内容不满足要求！");
                    break;
            }
            System.out.println("-------------------------------------------");
        }
    }

    //展示有向图
    static void showDirectedGraph() {
        for(String word : map.keySet()) {
            System.out.print("[ " + word + " ]");
            Nodes.Node node = map.get(word).head.next;
            while (node != null) {
                System.out.print(" --> " + node.word);
                node = node.next;
            }
            System.out.println();
        }
    }

    //查询桥接词
    static String queryBridgeWords(String word1, String word2) {
        boolean flag1 = (map.get(word1) == null);
        boolean flag2 = (map.get(word2) == null);
        String bridge = "";

        if (flag1 && flag2) System.out.println("No \"" + word1 + "\" and \"" + word2 + "\" in the graph!");
        else if (flag1) System.out.println("No \"" + word1 + "\" in the graph!");
        else if (flag2) System.out.println("No \"" + word2 + "\" in the graph!");
        else {
            bridge = findBridgeWords(word1, word2);
            if (bridge.equals("")) System.out.println("No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!");
            else System.out.println("The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: " + bridge.replaceAll(" +", ", "));
        }
        return bridge;
    }

    //查询桥接词辅助函数
    static String findBridgeWords(String word1, String word2) {
        String bridge = "";

        if ((map.get(word1) != null) && (map.get(word2) != null)) {
            Nodes.Node tmp1 = map.get(word1).head.next;
            while (tmp1 != null) {
                String word = tmp1.word;
                tmp1 = tmp1.next;
                if (map.get(word) == null) continue;
                Nodes.Node tmp2 = map.get(word).head.next;
                while (tmp2 != null) {
                    if (word2.equals(tmp2.word)) bridge = word + "  " + bridge;
                    tmp2 = tmp2.next;
                }
            }
        }
        return bridge.strip();
    }

    //根据bridge word生成新文本
    static String generateNewText(String inputText) {
        String[] words = inputText.split(" ");
        String outputText = "";
        for (int i = 0; i < words.length - 1; i++) {
            String bridge = findBridgeWords(words[i], words[i+1]);
            outputText = outputText + " " + words[i] + " " + bridge;
        }
        outputText = outputText + words[words.length - 1];
        return outputText.replaceAll(" +", " ").strip();
    }

    //计算两个单词之间的最短路径
    static String calcShortestPath(String word1, String word2) {
        boolean flag1 = (map.get(word1) == null);
        boolean flag2 = (map.get(word2) == null);
        if (flag1 && flag2) return "No \"" + word1 + "\" and \"" + word2 + "\" in the graph!";
        else if (flag1) return "No \"" + word1 + "\" in the graph!";
        else if (flag2) return "No \"" + word2 + "\" in the graph!";
        else {
            Map<String, String> pathes = dijkstra(word1);

            String tmp = word2;
            String path = "";
            if (!pathes.get(tmp).equals("")) {
                while (!tmp.equals("")) {
                    path = tmp + " " + path;
                    tmp = pathes.get(tmp);
                }
            }
            return path.strip().replaceAll(" ", "->");
        }
    }

    //计算单词到其他所有单词的最短路径
    static String calcShortestPath(String word1) {
        if (map.get(word1) == null) return "No \"" + word1 + "\" in the graph!";
        else {
            Map<String, String> pathes = dijkstra(word1);

            String allPath = "";
            for (String i : map.keySet()) {
                if (i.equals(word1)) continue;

                String path = "";
                String tmp = i;
                if (!pathes.get(i).equals("")) {
                    while (!tmp.equals("")) {
                        path = tmp + " " + path;
                        tmp = pathes.get(tmp);
                    }
                }
                path = "\"" + word1 + "\" 到 \"" + i + "\" 的路径为：" + path.strip().replaceAll(" ", "->");
                allPath = allPath + "\n" + path;
            }
            return allPath;
        }
    }

    static Map<String, String> dijkstra(String word1) {
        Map<String, String> pathes = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();
        Map<String, Boolean> visited = new HashMap<>();
        for (String i : map.keySet()) {
            pathes.put(i, "");
            distance.put(i, 0x3f3f3f3f);
            visited.put(i, false);
        }
        distance.put(word1, 0);
        PriorityQueue<String> heap1 = new PriorityQueue<>();
        PriorityQueue<Integer> heap2 = new PriorityQueue<>();
        heap1.add(word1);
        heap2.add(0);

        while (!heap1.isEmpty()) {
            String word = heap1.poll();
            int dis = heap2.poll();
            if (visited.get(word)) continue;
            visited.put(word, true);

            Nodes.Node tmp = map.get(word).head.next;
            while (tmp != null) {
                if (distance.get(tmp.word) > dis + tmp.weight) {
                    distance.put(tmp.word, dis + tmp.weight);
                    pathes.put(tmp.word, word);
                    heap1.add(tmp.word);
                    heap2.add(dis + tmp.weight);
                }
                tmp = tmp.next;
            }
        }
        return pathes;
    }

    //随机游走
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
        System.out.println("开始随机游走，按Enter键结束...");
        String result = begin;
        System.out.print(result);
        TimeUnit.MILLISECONDS.sleep(500);
        Nodes.Node next = map.get(begin).head.next;
        int range = map.get(begin).size;
        while (next != null) {
            if(!run){
                System.out.println("人为终止游走");
                break;
            }
            randomNum = (int) (Math.random() * range);
            for (int i = 0; i < randomNum; i++) next = next.next;
            if (result.contains(begin + " " + next.word)) break;  //判断是否重边
            begin = next.word;
            result = result + " " + next.word;
            System.out.print(" -> " + next.word);
            TimeUnit.MILLISECONDS.sleep(500);
            range = map.get(next.word).size;
            if (range != 0) next = map.get(next.word).head.next;
            else break;
        }
        while (run) {}
        System.out.println("随机游走结束");
        run = true;
        return result;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String filePath = "text.txt";
        String[] data = readText(filePath);
        //String[] data = readText(args[0]);
        createGraph(data);
        userInteract();
    }
}
