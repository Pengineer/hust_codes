package edu.whut;
import java.awt.*;
import javax.swing.*;

public class JFrameDemo extends JFrame{
	JFrameDemo()
	{
		this.setBounds(200, 300, 400, 300);
		this.setTitle("GoodGoodStudy");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		
		Container c = this.getContentPane();
		c.setBackground(Color.BLUE);
		
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new JFrameDemo();
	}

}
