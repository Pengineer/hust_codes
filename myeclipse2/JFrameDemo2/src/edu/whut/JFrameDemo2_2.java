package edu.whut;

import java.awt.*;

import javax.swing.*;

public class JFrameDemo2_2 extends JFrame{

	JPanel jp1,jp2;
	JLabel jl1,jl2;
	JComboBox jcb;     //�����б�
	JList list; 
	JScrollPane jsp;   //������
	
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
		
		jl1 = new JLabel("��  ��");
		jl2 = new JLabel("��  ��");
		
		String[] str1 = {"����","�Ϻ�","�人"};
		jcb = new JComboBox(str1);
		
		String[] str2 = {"�ʹ�","����","�ɽ�","��̲","�ƺ�¥","����"};
		list = new JList(str2);
		list.setVisibleRowCount(3);// listһ����ʾ������
		jsp = new JScrollPane(list);
		
		Container c = this.getContentPane();
		c.add(jp1);
		c.add(jp2);
		
		jp1.add(jl1);
		jp1.add(jcb);
		
		jp2.add(jl2);
		jp2.add(jsp);//��ӵ��ǹ�������������list
	}
	
	public static void main(String[] args) {
		new JFrameDemo2_2();
	}

}
