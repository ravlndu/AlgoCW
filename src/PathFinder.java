import java.io.*;

public class PathFinder {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\LENOVO\\Downloads\\Compressed\\maze30_5.txt");
        FileReader fr = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fr);

        String character;
        int characterCount = 0;
        while ((character = bufferedReader.readLine()) != null) {
                characterCount += character.length();

            }

        double SqrRoot = Math.sqrt(characterCount);
        int dimension = (int)SqrRoot;

        System.out.println("character count"+characterCount);
        System.out.println("rows & columns "+dimension);

        String[][] graph = new String[dimension][dimension];

        FileReader fr2 = new FileReader(file);
        BufferedReader br = new BufferedReader(fr2);

        String line;
        int i = 0;
        int j = 0;
        while ((line = br.readLine()) != null) {
            String[] split = line.split("");
            for (String c : split) {
                    graph[i][j] = c;
                j++;
                if(j>dimension-1){
                    j=0;
                }
            }
            i++;

        }


        for (String[] strings : graph) { //this equals to the row in our matrix.
            for (String string : strings) { //this equals to the column in each row.
                System.out.print(string);
            }
            System.out.println();
        }
    }
}
