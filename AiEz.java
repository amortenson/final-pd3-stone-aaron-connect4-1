import java.util.*;
import java.math.*;

public class AiEz {
    public int[][] board;
    public boolean player;//true=1=red; false=2=black

    public AiEz (int[][] in, boolean turn) {
        board = in;
        player = turn;
    }

    public void test() {
        for (int i=board.length-1;i>=0;i--) {
            for (int j=0;j<board[i].length;j++) {
                //System.out.print(board[i][j]);
            }
            //System.out.println("");
        }
    }

    public int[][] dummy() {
        boolean done = false;
        double[] cols = new double [7];
        int i = 0;
        for (int j=0;j<cols.length;j++) {
            if (board[5][j] == 0)//ensures that the ai doesn't play in a full column, if possible.
                cols[j] = Math.random();//replace this with sensical things
        }
        int x = 0;
        for (int j=0;j<cols.length;j++)
            if (cols[j]>cols[x])
                x = j;
        //System.out.println(Arrays.toString(cols));
        while (i < 6 && !done){
            if (board[i][x] == 0){
                if (player){
                    board[i][x] = 1;
                    done = true;
                }
                else{
                    board[i][x] = 2;
                    done = true;
                }
            }
            else
                i++;
        }
        if (done) {   
            return board;
        }
        else {
            //System.out.println("column full");
            return board;
        }        
    }
}
