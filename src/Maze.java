import java.io.*;
import java.util.*;

public class Maze {
    //data structure to store matrix cell coordinates
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

    // check whether given cell is valid
    public static boolean isFree(int x, int y, int[][] arr) {
        return (x >= 0 && x < arr.length) && (y >= 0 && y < arr[x].length) && (arr[x][y] == 0 || arr[x][y] == 4);
    }

    //queue to store cells
    public static Queue<Point> q = new LinkedList<>();

    // BFS algorithm to acquire the shortest path to destination
    public static Point getPathBFS(int x, int y, int[][] graph) {

        //start point
        q.add(new Point(x, y, null));

        while (!q.isEmpty()) {
            Point p = q.remove();

            //finding destination coordinate
            if (graph[p.x][p.y] == 4) {
                return p;
            }

            //checking neighbour cells for path and add path to the queue
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


    public static void main(String[] args) throws IOException {
        //File reader
        Stack<Point> stack = new Stack<>();
        File file = new File("C:\\Users\\LENOVO\\Downloads\\Compressed\\maze30_1.txt");
        FileReader fr = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fr);

        String character;
        int characterCount = 0;
        int startX = 0;
        int startY = 0;
        // counting dimension of the maze
        while ((character = bufferedReader.readLine()) != null) {
            characterCount += character.length();

        }
        double SqrRoot = Math.sqrt(characterCount);
        int dimension = (int) SqrRoot;

        // 2d array for the maze
        int[][] graph = new int[dimension][dimension];
        FileReader fr2 = new FileReader(file);
        BufferedReader br = new BufferedReader(fr2);

        //initializing maze and replacing characters into integers
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

        // finding the shortest path
        Point p = getPathBFS(startX, startY, graph);

        //output path with coordinates in reverse order
        while (true) {
            assert p != null;
            if (p.getParent() == null) break;
            stack.push(p);
            p = p.getParent();
        }
        stack.push(p);
        int[] corX = new int[stack.size()];
        int[] corY = new int[stack.size()];
        int idx = 0;

        Point curr = stack.pop();
        while (!stack.isEmpty()) {
            corX[idx] = curr.x;
            corY[idx] = curr.y;
            curr = stack.pop();
            idx++;
        }
        corX[idx] = curr.x;
        corY[idx] = curr.y;
        printDirections(corX, corY);

    }

    //acquiring directions
    public static String getDirection(int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 > y2)
            return "Left";
        if (x1 == x2 && y1 < y2)
            return "Right";
        if (y1 == y2 && x1 < x2)
            return "Down";
        if (y1 == y2 && x1 > x2)
            return "Up";
        return "undecidable";
    }

    //method to print output
    public static void printDirections(int[] x, int[] y) {
        System.out.printf("Start at ( %d , %d )", x[0], y[0]);

        String lastDirection = getDirection(x[0], y[0], x[1], y[1]);
        for (int i = 1; i < x.length - 1; i++) {
            String direction = getDirection(x[i], y[i], x[i + 1], y[i + 1]);
            if (!lastDirection.equals(direction)) {
                System.out.printf("\nMove %s to ( %d , %d )", lastDirection, x[i], y[i]);
            }
            lastDirection = direction;
        }
        System.out.printf("\nMove %s to ( %d , %d )", lastDirection, x[x.length - 1], y[y.length - 1]);
        System.out.println("\nDone !");
    }

}