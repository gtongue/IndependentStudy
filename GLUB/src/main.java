import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class main extends JFrame {
	public main()
	{
		this.setSize(800,600);
		this.setContentPane(new painting());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String [] args){
		new main();
	}
}

class painting extends JPanel{
	double  x = 0;
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.RED);
		g.drawString("GLUB", (int)x, 300);
		x+=.01;
		if(x>800)
			x = 0;
		repaint();
	}
}