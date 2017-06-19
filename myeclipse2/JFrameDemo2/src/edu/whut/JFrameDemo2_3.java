package edu.whut;
import java.awt.*;

import javax.swing.*;

public class JFrameDemo2_3 extends JFrame{

	JPanel jp1,jp2,jp3;
	JLabel jl1,jl2;
	JTextField jtf;
	JPasswordField jpw;
	JButton jb1,jb2;
	private JFrameDemo2_3()
	{
		this.setBounds(300,200,300,150);
		this.setTitle("Frame3");
		this.setLayout(new GridLayout(3,1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		jl1 = new JLabel("账   号",JLabel.CENTER); //关于Label的相关设置
		jl2 = new JLabel("密   码",JLabel.CENTER);
		jl1.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
		jl1.setForeground(Color.RED);
		jl2.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
		jl2.setForeground(Color.GREEN);
		
		jtf = new JTextField(20);
		jpw = new JPasswordField(20);
		jpw.setEchoChar('*');
		
		jb1 = new JButton("登   录");
		jb2 = new JButton("注   册");
		
		Container c = this.getContentPane();
		c.add(jp1);
		c.add(jp2);
		c.add(jp3);
		
		jp1.add(jl1);
		jp1.add(jtf);
		
		jp2.add(jl2);
		jp2.add(jpw);
		
		jp3.add(jb1);
		jp3.add(jb2);
	}
	
	public static void main(String[] args) {
		new JFrameDemo2_3();
	}

}
