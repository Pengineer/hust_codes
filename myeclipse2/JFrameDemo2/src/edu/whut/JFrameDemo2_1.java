package edu.whut;
/*
 *@author:Psir
 *funtion:Demo about 复选框(可以选择多个)  and 单选框(只能选择一个) 
 *data：2014/3/22
 */

import java.awt.*;
import javax.swing.*;

public class JFrameDemo2_1 extends JFrame{

	JPanel jp1,jp2,jp3;
	JLabel jl1,jl2;
	JCheckBox jcb1,jcb2,jcb3;  //复选框
	ButtonGroup bg;
	JRadioButton jrb1,jrb2;    //单选框
	JButton jb1,jb2;
	
	private JFrameDemo2_1()
	{
		this.setBounds(300,200,300,150);
		this.setTitle("Frame1");
		this.setLayout(new GridLayout(3,1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		jl1 = new JLabel("爱  好");
		jl2 = new JLabel("性  别");
		
		jcb1 = new JCheckBox("篮     球");
		jcb2 = new JCheckBox("足     球");
		jcb3 = new JCheckBox("羽毛球");
		
		bg = new ButtonGroup();
		jrb1 = new JRadioButton("男");
		jrb2 = new JRadioButton("女");
		
		jb1 = new JButton("登   录");
		jb2 = new JButton("注   册");
		
		Container c = this.getContentPane();
		c.add(jp1);
		c.add(jp2);
		c.add(jp3);
		
		jp1.add(jl1);
		jp1.add(jcb1);
		jp1.add(jcb2);
		jp1.add(jcb3);
		
		bg.add(jrb1);
		bg.add(jrb2);
		jp2.add(jl2);
	//	jp2.add(bg);注意，单选框必须要封装在一个Group里面，但是再添加的时候还是得添加单选框，而不能直接添加Group
		jp2.add(jrb1);
		jp2.add(jrb2);
		
		jp3.add(jb1);
		jp3.add(jb2);
	}
	
	public static void main(String[] args) {
		new JFrameDemo2_1();
	}

}
