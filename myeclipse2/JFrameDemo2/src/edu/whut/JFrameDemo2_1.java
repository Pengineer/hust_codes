package edu.whut;
/*
 *@author:Psir
 *funtion:Demo about ��ѡ��(����ѡ����)  and ��ѡ��(ֻ��ѡ��һ��) 
 *data��2014/3/22
 */

import java.awt.*;
import javax.swing.*;

public class JFrameDemo2_1 extends JFrame{

	JPanel jp1,jp2,jp3;
	JLabel jl1,jl2;
	JCheckBox jcb1,jcb2,jcb3;  //��ѡ��
	ButtonGroup bg;
	JRadioButton jrb1,jrb2;    //��ѡ��
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
		
		jl1 = new JLabel("��  ��");
		jl2 = new JLabel("��  ��");
		
		jcb1 = new JCheckBox("��     ��");
		jcb2 = new JCheckBox("��     ��");
		jcb3 = new JCheckBox("��ë��");
		
		bg = new ButtonGroup();
		jrb1 = new JRadioButton("��");
		jrb2 = new JRadioButton("Ů");
		
		jb1 = new JButton("��   ¼");
		jb2 = new JButton("ע   ��");
		
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
	//	jp2.add(bg);ע�⣬��ѡ�����Ҫ��װ��һ��Group���棬��������ӵ�ʱ���ǵ���ӵ�ѡ�򣬶�����ֱ�����Group
		jp2.add(jrb1);
		jp2.add(jrb2);
		
		jp3.add(jb1);
		jp3.add(jb2);
	}
	
	public static void main(String[] args) {
		new JFrameDemo2_1();
	}

}
