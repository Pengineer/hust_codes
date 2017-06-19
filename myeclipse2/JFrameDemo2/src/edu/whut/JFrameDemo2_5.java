package edu.whut;


/*
 * 1，实现真正意义上的画图（是图片，不是图形），而不是在JLabel中添加图标
 * 2，使用JFrame，而不是坦克大战中的Frame
 */

import java.awt.*;
import javax.swing.*;

public class JFrameDemo2_5 extends JFrame{

	MyPanel mp = null;
	JFrameDemo2_5()
	{
		mp = new MyPanel();
		this.setBounds(300, 200, 800, 600);
		this.setTitle("图片");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		
		this.add(mp);
	}
	public static void main(String[] args) {
		
		JFrameDemo2_5 jf = new JFrameDemo2_5();
	}

}

class MyPanel extends JPanel 
{
	public void paint(Graphics g) 
	{
		super.paint(g);
		//用绘图工具类Toolkit获取图片，将图片包装成画笔可识别的图片数据类型
		Image img = Toolkit.getDefaultToolkit().getImage("image\\car.jpg");
		g.drawImage(img, 50, 50, 550, 350, this);
		
	}
}
