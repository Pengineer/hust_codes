package edu.whut;

import java.awt.*;

import javax.swing.*;

public class JFrameDemo2_2 extends JFrame{

	JPanel jp1,jp2;
	JLabel jl1,jl2;
	JComboBox jcb;     //下拉列表
	JList list; 
	JScrollPane jsp;   //滚动条
	
	private JFrameDemo2_2()
	{
		this.setBounds(300, 200, 300, 200);
		this.setTitle("Frame2");
		this.setLayout(new GridLayout(2,1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		
		jl1 = new JLabel("籍  贯");
		jl2 = new JLabel("景  点");
		
		String[] str1 = {"北京","上海","武汉"};
		jcb = new JComboBox(str1);
		
		String[] str2 = {"故宫","长城","松江","外滩","黄鹤楼","东湖"};
		list = new JList(str2);
		list.setVisibleRowCount(3);// list一次显示多少行
		jsp = new JScrollPane(list);
		
		Container c = this.getContentPane();
		c.add(jp1);
		c.add(jp2);
		
		jp1.add(jl1);
		jp1.add(jcb);
		
		jp2.add(jl2);
		jp2.add(jsp);//添加的是滚动条，而不是list
	}
	
	public static void main(String[] args) {
		new JFrameDemo2_2();
	}

}
