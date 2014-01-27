import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Gui extends JFrame implements ActionListener {  //this code began as the sample gui code. Thanks, Mr. Z!
    
    private Container pane;
    private JButton exitButton,aiToggle;
    private JPanel buttonsBorder,boardBorder;
    private int[][] board = new int[6][7];
    private Container buttons,buttons2;
    private JButton b1,b2,b3,b4,b5,b6,b7;
    private boolean won = false;
    private boolean turn = true;
    private boolean useAi = false;
    private JLabel singlePlayer,gameInfo,gameStatus;
    private Image redslot,blackslot,blueslot;

    public void changeTurn() {
        turn = !turn;
        if (turn)
            this.gameInfo.setText("Red to play");
        else
            this.gameInfo.setText("Black to play");
    }
    
    private class myKeyListener implements KeyListener {
        public void keyPressed(KeyEvent e) {
            System.out.println("button");
        }
        
        public void keyTyped(KeyEvent e) {
            System.out.println("potato");
        }
        public void keyReleased(KeyEvent e) {
            System.out.println("potato");
        }
        
    }
        
    public void actionPerformed(ActionEvent e) {        
        if (e.getSource() == exitButton) {
            System.exit(0);
        }
        else if (e.getSource() == aiToggle) {
            useAi = !useAi;
            if (useAi) 
                this.singlePlayer.setText("Singleplayer ON");
            else
                this.singlePlayer.setText("Singleplayer OFF");
        }
        else{
            buttonAct(e.getSource(), ((JButton) e.getSource()).getText());
        }
    }

    public void updateBoard() {
        boardBorder.removeAll();
        for (int i=board.length-1;i>=0;i--) {
            for (int j=0;j<board[i].length;j++) {
                JPanel jpanel = new JPanel();
		JLabel thumb = new JLabel();
		ImageIcon icon = new ImageIcon(blueslot); 
                if (board[i][j] == 1)
		    icon = new ImageIcon(redslot); 
                if (board[i][j] == 2)
		    icon = new ImageIcon(blackslot); 
                jpanel.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
		thumb.setIcon(icon);
                jpanel.add(thumb);
                boardBorder.add(jpanel); 
            }
        }
        boardBorder.revalidate();
    }
    
    public void buttonAct(Object jb, String t){
        if (won){
            System.out.println("The game is already over.");
        }
        else{
            String p;
            if (turn)
                p = "Red";
            else
                p = "Black";
            System.out.println(p + " picked column " + t);
            int x = Integer.parseInt(t)-1;
            int i = 0;
            boolean done = false;
            while (i < 6 && !done){
                if (board[i][x] == 0){
                    if (turn){
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
                winCheck();
                changeTurn();
                updateBoard();
                if (useAi && !won) {
                    Ai a =new Ai(board,turn);
                    board =a.dummy();
                    winCheck();
                    changeTurn();
                    updateBoard();
                }
            }
            else
                System.out.println("Invalid move. Column full.");
        }
    }

    public void winCheck(){
        for (int j = 5; j >= 0; j--){
            for (int i = 0; i < 4; i++){
                if (board[j][i] != 0 &&  ((board[j][i]==board[j][i+1]) && (board[j][i]==board[j][i+2]) && (board[j][i]==board[j][i+3]))       )
                    won = true;                                    
            }
        }
        for (int j = 5; j >= 3; j--){
            for (int i = 0; i < 7; i++){
                if (board[j][i] != 0 &&  ((board[j][i]==board[j-1][i]) && (board[j][i]==board[j-2][i]) && (board[j][i]==board[j-3][i]))       )
                    won = true;
            } 
        }
        for (int j = 5; j >= 3; j--){
            for (int i = 0; i < 4; i++){
                if (board[j][i] != 0 &&  ((board[j][i]==board[j-1][i+1]) && (board[j][i]==board[j-2][i+2]) && (board[j][i]==board[j-3][i+3]))       )
                    won = true;
            } 
        }
        for (int j = 5; j >= 3; j--){
            for (int i = 3; i < 7; i++){
                if (board[j][i] != 0 &&  ((board[j][i]==board[j-1][i-1]) && (board[j][i]==board[j-2][i-2]) && (board[j][i]==board[j-3][i-3]))       )
                    won = true;
            } 
        }
        if (won){
            String pl;
            if (turn)
                pl = "Red";
            else
                pl = "Black";
            System.out.println("Game over. " + pl + " has won!");
            this.gameStatus.setText("GAME OVER: " + pl + " wins!" );
        }
    }
    
    public Gui() {
        this.setTitle("Connect 4 by Aaron Mortenson and Stone Moore");
        this.setSize(725,725);
        this.setLocation(0,0);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {                
          redslot = ImageIO.read(new File("images/red2.jpg"));
          blackslot = ImageIO.read(new File("images/black2.jpg"));
	  blueslot = ImageIO.read(new File("images/blue2.jpg"));

       } catch (IOException ex) {
            // lolololol
       }
        pane = this.getContentPane();
        pane.setLayout(new BorderLayout());
        exitButton = new JButton("Exit");
        aiToggle = new JButton("Toggle Singleplayer");
        boardBorder = new JPanel();
        boardBorder.setBorder(BorderFactory.createLineBorder(Color.yellow,5));
        boardBorder.setLayout(new GridLayout(6,7));
        board = new int[6][7];
        b1= new JButton("1");
        b2= new JButton("2");
        b3= new JButton("3");
        b4= new JButton("4");
        b5= new JButton("5");
        b6= new JButton("6");
        b7= new JButton("7");
        singlePlayer = new JLabel("Singleplayer OFF");
        gameInfo = new JLabel("Red to play");
        gameStatus = new JLabel(" ");
        buttons = new Container();
        buttons.setLayout(new GridLayout(1,4));
        buttons.add(exitButton);
        buttons.add(gameInfo);
        buttons.add(gameStatus);
        buttons.add(singlePlayer);
        buttons.add(aiToggle);
        buttonsBorder = new JPanel();
        buttonsBorder.setBorder(BorderFactory.createLineBorder(Color.blue,5));
        buttonsBorder.setLayout(new GridLayout(1,1));
        buttons2 = new Container();
        buttons2.setLayout(new GridLayout(1,7));
        buttons2.add(b1);
        buttons2.add(b2);
        buttons2.add(b3);
        buttons2.add(b4);
        buttons2.add(b5);
        buttons2.add(b6);
        buttons2.add(b7);
        buttonsBorder.add(buttons2);
        pane.add(buttons,BorderLayout.PAGE_START);
        pane.add(boardBorder,BorderLayout.CENTER);
        pane.add(buttonsBorder,BorderLayout.PAGE_END);
        for (int[] panels:board) {
            for (int panel:panels) {
                JPanel jpanel = new JPanel();
		JLabel thumb = new JLabel();
		ImageIcon icon = new ImageIcon(blueslot); 
                jpanel.setBorder(BorderFactory.createLineBorder(Color.yellow,2));
		thumb.setIcon(icon);
                jpanel.add(thumb);
                boardBorder.add(jpanel);
            }
        }
        
        exitButton.addActionListener(this);
        aiToggle.addActionListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
    }
        
    public static void main(String[] args) {
        Gui g = new Gui();
        g.setVisible(true);
    }
}