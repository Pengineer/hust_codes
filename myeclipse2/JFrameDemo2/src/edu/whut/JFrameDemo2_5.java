package edu.whut;


/*
 * 1��ʵ�����������ϵĻ�ͼ����ͼƬ������ͼ�Σ�����������JLabel�����ͼ��
 * 2��ʹ��JFrame��������̹�˴�ս�е�Frame
 */

import java.awt.*;
import javax.swing.*;

public class JFrameDemo2_5 extends JFrame{

	MyPanel mp = null;
	JFrameDemo2_5()
	{
		mp = new MyPanel();
		this.setBounds(300, 200, 800, 600);
		this.setTitle("ͼƬ");
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
		//�û�ͼ������Toolkit��ȡͼƬ����ͼƬ��װ�ɻ��ʿ�ʶ���ͼƬ��������
		Image img = Toolkit.getDefaultToolkit().getImage("image\\car.jpg");
		g.drawImage(img, 50, 50, 550, 350, this);
		
	}
}
