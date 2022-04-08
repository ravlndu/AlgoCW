import java.io.*;
import java.util.*;

public class Maze {


    //to store matrix cell coordinates
    private static class Point {
        int x;
        int y;
        Point parent;

        public Point(int x, int y, Point parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }

        public Point getParent() {
            return this.parent;
        }

        public String toString() {
            return "x = " + x + " y = " + y;
        }
    }

    public static Queue<Point> q = new LinkedList<>();


    public static Point getPathBFS(int x, int y, int[][] graph) {

        q.add(new Point(x, y, null));

        while (!q.isEmpty()) {
            Point p = q.remove();

            if (graph[p.x][p.y] == 4) {
                System.out.println("Exit is reached!");
                return p;
            }

            if (isFree(p.x + 1, p.y, graph)) {
                graph[p.x][p.y] = -1;
                Point nextP = new Point(p.x + 1, p.y, p);
                q.add(nextP);
            }

            if (isFree(p.x - 1, p.y, graph)) {
                graph[p.x][p.y] = -1;
                Point nextP = new Point(p.x - 1, p.y, p);
                q.add(nextP);
            }

            if (isFree(p.x, p.y + 1, graph)) {
                graph[p.x][p.y] = -1;
                Point nextP = new Point(p.x, p.y + 1, p);
                q.add(nextP);
            }

            if (isFree(p.x, p.y - 1, graph)) {
                graph[p.x][p.y] = -1;
                Point nextP = new Point(p.x, p.y - 1, p);
                q.add(nextP);
            }

        }
        return null;
    }


    public static boolean isFree(int x, int y, int[][] arr) {
        return (x >= 0 && x < arr.length) && (y >= 0 && y < arr[x].length) && (arr[x][y] == 0 || arr[x][y] == 4);
    }

    public static void main(String[] args) throws IOException {
        Stack<Point> stack = new Stack<>();
        File file = new File("C:\\Users\\LENOVO\\Downloads\\Compressed\\maze10_2.txt");
        FileReader fr = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fr);

        String character;
        int characterCount = 0;
        int startX = 0;
        int startY = 0;
        while ((character = bufferedReader.readLine()) != null) {
            characterCount += character.length();

        }

        double SqrRoot = Math.sqrt(characterCount);
        int dimension = (int) SqrRoot;

        System.out.println("character count" + characterCount);
        System.out.println("rows & columns " + dimension);

        int[][] graph = new int[dimension][dimension];

        FileReader fr2 = new FileReader(file);
        BufferedReader br = new BufferedReader(fr2);

        String line;
        int i = 0;
        int j = 0;
        while ((line = br.readLine()) != null) {
            String[] split = line.split("");
            for (String c : split) {
                switch (c) {
                    case "S" -> {
                        graph[i][j] = 0;
                        startX = i;
                        startY = j;
                    }
                    case "." -> graph[i][j] = 0;
                    case "0" -> graph[i][j] = 5;
                    case "F" -> graph[i][j] = 4;

                }
                j++;
                if (j > dimension - 1) {
                    j = 0;
                }
            }
            i++;

        }
        Point p = getPathBFS(startX, startY, graph);


        while (true) {
            assert p != null;
            if (p.getParent() == null) break;
            stack.push(p);
            p = p.getParent();


        }
        stack.push(p);
        System.out.println("...................................");
        Point curr = stack.pop();


        System.out.println("Start - " + curr);
        curr = stack.pop();


        while (!stack.isEmpty()) {
            System.out.println(curr);
            curr = stack.pop();
        }
        System.out.println(curr);

    }

}