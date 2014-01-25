import java.util.*;
import java.math.*;

public class Ai {
    public int[][] board;
    public int player,other;//true=1=red; false=2=black
    public int getv,geth,getd1,getd2,blockv,blockh,blockd1,blockd2,noth,notd1,notd2,giveh,gived1,gived2; 
    
    //not=don't let block, give = don't let get

    public Ai (int[][] in, boolean turn) {
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

    public void test() { //remove this eventually
        for (int i=board.length-1;i>=0;i--) {
            for (int j=0;j<board[i].length;j++) {
                System.out.print(board[i][j]);
            }
            System.out.println("");
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
	    System.out.println("column full");
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
		getv=0;blockv=0;geth=0;blockh=0;noth=0;giveh=0;getd1=0;blockd1=0;notd1=0;gived1=0;getd2=0;blockd2=0;notd2=0;gived2=0;

		for (int i = ycor-1;i>ycor-4&&i>=0;i--) {//getv,blockv
		    if (board[i][xcor] ==  player && board[i+1][xcor] != other && blockv==0)
			getv++;
		    if (board[i][xcor] ==  other && board[i+1][xcor] != player && getv==0)
			blockv++;
		}
		getv++;
		blockv++;

		int[] horiz= new int[1+Math.min(xcor+3,6)-Math.max(xcor-3,0)];//geth,blockh
		for (int i = Math.max(xcor-3,0);i<=Math.min(xcor+3,6);i++) 
		    horiz[i-Math.max(xcor-3,0)] = board[ycor][i];
		//System.out.println(Arrays.toString(horiz));
		for (int i = 0;i<horiz.length-3;i++) {
		    int tempbh = 0;
		    int tempgh = 0;
		    for (int j = i;j<i+4;j++) {
			if (horiz[j] == player) {
			    tempgh++;
			    tempbh=-4;
			}
			if (horiz[j] == other) {
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

		int[] diag1= new int[Math.min(3,Math.min(6-xcor,5-ycor))+Math.min(3,Math.min(xcor,ycor))+1];//getd1,blockd1
		for (int i = -1*Math.min(3,Math.min(xcor,ycor));i<=Math.min(3,Math.min(6-xcor,5-ycor));i++) 
		    diag1[i+Math.min(3,Math.min(xcor,ycor))] = board[ycor+i][xcor+i];
		//System.out.println(Arrays.toString(diag1));
		for (int i = 0;i<diag1.length-3;i++) {
		    int tempbd1 = 0;
		    int tempgd1 = 0;
		    for (int j = i;j<i+4;j++) {
			if (diag1[j] == player) {
			    tempgd1++;
			    tempbd1=-4;
			}
			if (diag1[j] == other) {
			    tempbd1++;
			    tempgd1=-4;
			}
		    }
		    if (tempbd1>blockd1)
			blockd1 = tempbd1;
		    if (tempgd1>getd1)
			getd1 = tempgd1;
		}
		getd1++;
		blockd1++;

		int[] diag2= new int[Math.min(3,Math.min(6-xcor,ycor))+Math.min(3,Math.min(xcor,5-ycor))+1];//getd2,blockd2
		for (int i = -1*Math.min(3,Math.min(xcor,5-ycor));i<=Math.min(3,Math.min(6-xcor,ycor));i++) 
		    diag2[i+Math.min(3,Math.min(xcor,5-ycor))] = board[ycor-i][xcor+i];
		//System.out.println(Arrays.toString(diag2));
		for (int i = 0;i<diag2.length-3;i++) {
		    int tempbd2 = 0;
		    int tempgd2 = 0;
		    for (int j = i;j<i+4;j++) {
			if (diag2[j] == player) {
			    tempgd2++;
			    tempbd2=-4;
			}
			if (diag2[j] == other) {
			    tempbd2++;
			    tempgd2=-4;
			}
		    }
		    if (tempbd2>blockd2)
			blockd2 = tempbd2;
		    if (tempgd2>getd2)
			getd2 = tempgd2;
		}
		getd2++;
		blockd2++;
		
		if (ycor != 5) {
		    int[] horizup= new int[1+Math.min(xcor+3,6)-Math.max(xcor-3,0)];//noth,giveh
		    for (int i = Math.max(xcor-3,0);i<=Math.min(xcor+3,6);i++) 
			horizup[i-Math.max(xcor-3,0)] = board[ycor+1][i];
		    //System.out.println(Arrays.toString(horizup));
		    for (int i = 0;i<horizup.length-3;i++) {
			int tempnh = 0;
			int tempgvh = 0;
			for (int j = i;j<i+4;j++) {
			    if (horizup[j] == player) {
				tempnh++;
				tempgvh=-4;
			    }
			    if (horizup[j] == other) {
				tempgvh++;
				tempnh=-4;
			    }
			}
			if (tempnh>noth)
			    noth = tempnh;
			if (tempgvh>giveh)
			    giveh = tempgvh;
		    }
		    giveh++;

		int[] diag1up= new int[Math.min(3,Math.min(6-xcor,4-ycor))+Math.min(3,Math.min(xcor,ycor+1))+1];//notd1,gived1
		    for (int i = -1*Math.min(3,Math.min(xcor,ycor+1));i<=Math.min(3,Math.min(6-xcor,4-ycor));i++) 
			diag1up[i+Math.min(3,Math.min(xcor,ycor+1))] = board[ycor+i+1][xcor+i];
		    //System.out.println(Arrays.toString(diag1up));
		    for (int i = 0;i<diag1up.length-3;i++) {
			int tempnd1 = 0;
			int tempgvd1 = 0;
			for (int j = i;j<i+4;j++) {
			    if (diag1up[j] == player) {
				tempnd1++;
				tempgvd1=-4;
			    }
			    if (diag1up[j] == other) {
				tempgvd1++;
				tempnd1=-4;
			    }
			}
			if (tempnd1>notd1)
			    notd1 = tempnd1;
			if (tempgvd1>gived1)
			    gived1 = tempgvd1;
		    }
		    gived1++;

		    int[] diag2up= new int[Math.min(3,Math.min(6-xcor,ycor+1))+Math.min(3,Math.min(xcor,4-ycor))+1];//notd2,gived2
		    for (int i = -1*Math.min(3,Math.min(xcor,4-ycor));i<=Math.min(3,Math.min(6-xcor,ycor+1));i++) 
			diag2up[i+Math.min(3,Math.min(xcor,4-ycor))] = board[ycor+1-i][xcor+i];
		    System.out.println(Arrays.toString(diag2up));
		    for (int i = 0;i<diag2up.length-3;i++) {
			int tempnd2 = 0;
			int tempgvd2 = 0;
			for (int j = i;j<i+4;j++) {
			    if (diag2up[j] == player) {
				tempnd2++;
				tempgvd2=-4;
			    }
			    if (diag2up[j] == other) {
				tempgvd2++;
				tempnd2=-4;
			    }
			}
			if (tempnd2>notd2)
			    notd2 = tempnd2;
			if (tempgvd2>gived2)
			    gived2 = tempgvd2;
		    }
		    gived2++;
		}
	    
		System.out.println("gv/bv/gh/bh/gvh/nh/gd1/bd1/gvd1/nd1/gd2/bd2/gvd2/nd2/pos:");
		System.out.println(""+getv+blockv+geth+blockh+giveh+noth+getd1+blockd1+gived1+notd1+getd2+blockd2+gived2+notd2+xcor+". ");
		cols[xcor] = .1*(6-Math.abs(xcor-3));
		cols[xcor] += Math.pow(getv+.003,5)/64;	  
		cols[xcor] += Math.pow(geth+.003,2);
		cols[xcor] += Math.pow(getd1+.003,2);
		cols[xcor] += Math.pow(getd2+.003,2);
		cols[xcor] += Math.pow(blockv+.002,5)/64;
		cols[xcor] += Math.pow(blockh+.002,2);
		cols[xcor] += Math.pow(blockd1+.002,2);
		cols[xcor] += Math.pow(blockd2+.002,2);
		cols[xcor] -= Math.pow(giveh+.001,2);
		cols[xcor] -= Math.pow(gived1+.001,2);
		cols[xcor] -= Math.pow(gived2+.001,2);
		cols[xcor] -= Math.pow(noth,2);
		cols[xcor] -= Math.pow(notd1,2);
		cols[xcor] -= Math.pow(notd2,2);
	    }
	    else
		cols[xcor] = -999;
	}
	return cols;
    }
}
