import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame implements ActionListener {
    
    private Container pane;
    private JButton exitButton;
    private JPanel canvas, buttonsBorder;
    private int[][] board = new int[6][7];
    private Container buttons,buttons2;
    private JButton b1,b2,b3,b4,b5,b6,b7;
    private boolean turn = true;

    
    private class myKeyListener implements KeyListener {
	public void keyPressed(KeyEvent e) {
	    System.out.println("button");
	    //fill in some box
	}
	
	public void keyTyped(KeyEvent e) {
	    System.out.println("potato");
	}
	public void keyReleased(KeyEvent e) {
	    System.out.println("RELEASED: " + e.getKeyChar()+ " - "+ e.getKeyCode());
	}
    }
	
    public void actionPerformed(ActionEvent e) {	
	if (e.getSource() == exitButton) {
	    System.exit(0);
	}else{
	    buttonAct(e.getSource(), ((JButton) e.getSource()).getText());
	}
    }
	
	
    public void buttonAct(Object jb, String t){
	int p;
	if (turn)
	    p = 1;
	else
	    p = 2;
	System.out.println("player " + p + " picked column " + t);
	int x = Integer.parseInt(t);
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
	if (done)
	    turn = !turn;
	else
	    System.out.println("Invalid move. Column full.");
    }
    
    public Gui() {
	this.setTitle("My first GUI");
	this.setSize(700,700);
	this.setLocation(0,0);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	pane = this.getContentPane();
	pane.setLayout(new BorderLayout());
	exitButton = new JButton("Exit");
	canvas = new JPanel();
	canvas.setBorder(BorderFactory.createLineBorder(Color.blue,5));
	canvas.setLayout(new GridLayout(6,7));
	board = new int[6][7];
	b1= new JButton("1");
	b2= new JButton("2");
	b3= new JButton("3");
	b4= new JButton("4");
	b5= new JButton("5");
	b6= new JButton("6");
	b7= new JButton("7");
	buttons = new Container();
	buttons.setLayout(new GridLayout(1,1));
	buttons.add(exitButton);
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
	pane.add(canvas,BorderLayout.CENTER);
	pane.add(buttonsBorder,BorderLayout.PAGE_END);
	for (int[] panels:board) {
	    for (int panel:panels) {
		JPanel jpanel = new JPanel();
		jpanel.setBorder(BorderFactory.createLineBorder(Color.yellow,2));		
		canvas.add(jpanel);
	    }
	}
	
	exitButton.addActionListener(this);
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

