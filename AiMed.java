import java.util.*;
import java.math.*;

public class AiMed {
    public int[][] board;
    public int player,other;//true=1=red; false=2=black
    public int getv,geth,getd1,getd2,blockv,blockh,blockd1,blockd2,noth,notd1,notd2,giveh,gived1,gived2;

    public AiMed (int[][] in, boolean turn) {
        board = in;
        if (turn) {
            player = 1;
            other = 2;
        }
        else {
            player = 2;
            other = 1;
        }
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
        double[] cols = ai1();
        int i = 0;
        int x = 0;
        for (int j=0;j<cols.length;j++)
            if (cols[j]>cols[x])
                x = j;
        //System.out.println(Arrays.toString(cols));
        while (i < 6 && !done){
            if (board[i][x] == 0){
                board[i][x] = player;
                done = true;
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
    
    public double[] ai1() {
        double[] cols = new double [7];
        for (int xcor=0;xcor<cols.length;xcor++) {
            int ycor = -1;
            for (int row=5;row>=0;row--) 
                if (board[row][xcor] == 0)
                    ycor=row;
            if (ycor != -1) {//ensures that the ai doesn't play in a full column, if possible
                //System.out.println("ycor"+ycor);
                getv=0;blockv=0;geth=0;blockh=0;

                for (int i = ycor-1;i>ycor-4&&i>=0;i--) {//getv,blockv
                    if (board[i][xcor] ==  player && board[i+1][xcor] != other && blockv==0)
                        getv++;
                    if (board[i][xcor] ==  other && board[i+1][xcor] != player && getv==0)
                        blockv++;
                }
                getv++;
                blockv++;

                int[] vert= new int[1+Math.min(xcor+3,6)-Math.max(xcor-3,0)];//geth,blockh
                for (int i = Math.max(xcor-3,0);i<=Math.min(xcor+3,6);i++) 
                    vert[i-Math.max(xcor-3,0)] = board[ycor][i];
                //System.out.println(Arrays.toString(vert));
                for (int i = 0;i<vert.length-3;i++) {
                    int tempbh = 0;
                    int tempgh = 0;
                    for (int j = i;j<i+4;j++) {
                        if (vert[j] == player) {
                            tempgh++;
                            tempbh=-4;
                        }
                        if (vert[j] == other) {
                            tempbh++;
                            tempgh=-4;
                        }
                    }
                        if (tempbh>blockh)
                            blockh = tempbh;
                        if (tempgh>geth)
                            geth = tempgh;
                }
                geth++;
                blockh++;
                
                //System.out.println("gv/bv/gh/bh/pos:"+getv+blockv+geth+blockh+xcor+". ");
                cols[xcor] = geth+blockh+getv+blockv+(.1*(6-Math.abs(xcor-3)));//replace this with sensical things
            }
        }
        return cols;
    } 
}
