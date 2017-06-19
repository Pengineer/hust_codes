package edu.whut;
import java.awt.*;

import javax.swing.*;

public class demo extends JFrame{

	JPanel jp1,jp2,jp3,jp4,jp5;
	JLabel jl1,jl2,jl3,jl4;
	JTextField jtf1,jtf2,jtf3,jtf4;
	JButton jb1,jb2;
	private demo()
	{
		this.setBounds(300,200,500,300);
		this.setTitle("急速下载器");
		this.setLayout(new GridLayout(5,1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		jp5 = new JPanel();
		
		jl1 = new JLabel("1号下载路径",JLabel.CENTER); 
		jl2 = new JLabel("2号下载路径",JLabel.CENTER);
		jl3 = new JLabel("1号保存路径",JLabel.CENTER);
		jl4 = new JLabel("2号保存路径",JLabel.CENTER);
		jl1.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
		jl1.setForeground(Color.RED);
		jl2.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
		jl2.setForeground(Color.RED);
		jl3.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
		jl3.setForeground(Color.GREEN);
		jl4.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
		jl4.setForeground(Color.GREEN);
		
		jtf1 = new JTextField(20);
		jtf2 = new JTextField(20);
		jtf3 = new JTextField(20);
		jtf4 = new JTextField(20);

		
		jb1 = new JButton("1号开始下载");
		jb2 = new JButton("2号开始下载");
		
		Container c = this.getContentPane();
		c.add(jp1);
		c.add(jp2);
		c.add(jp3);
		c.add(jp4);
		c.add(jp5);
		
		jp1.add(jl1);
		jp1.add(jtf1);
		
		jp2.add(jl2);
		jp2.add(jtf2);
		
		jp3.add(jl3);
		jp3.add(jtf3);
		
		jp4.add(jl4);
		jp4.add(jtf4);
		
		jp5.add(jb1);
		jp5.add(jb2);
	}
	
	public static void main(String[] args) {
		new demo();
	}

}
