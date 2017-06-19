package edu.whut;
import java.awt.*;
import javax.swing.*;
/*
 * function:��������������֣�������һ�����Է���ͼƬ��JLable����������JPanel������������
 *           ��Panel����JSplitPane���ڷָ�塣
 * 
 * relation: window = jl0 + jp0 ;
 * 			 
 * 			 jl0 = image ;
 *           jp0 = jp1 + jp2 + jp3 ;
 *          
 *           jp1 = jl1 + jtf;
 *           jp2 = jl2 + jpw;
 *           jp3 = jb1 + jb2;
 */


public class JFrameDemo2_4 extends JFrame{

	JPanel jp0,jp1,jp2,jp3;
	JSplitPane jsp;
	JLabel jl0,jl1,jl2;
	JTextField jtf;
	JPasswordField jpw;
	JButton jb1,jb2;
	private JFrameDemo2_4()
	{
		this.setBounds(300,200,300,230);
		this.setTitle("QQ2010");
		this.setIconImage(new ImageIcon("image\\qqhead.jpg").getImage());//����ŵ�ʵ������ͼ��
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		jp0 = new JPanel();//�°벿�ֵ���Panel--->jp0=jp1+jp2+jp2
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		jl0 = new JLabel(new ImageIcon("image\\qq.jpg"));//ͼƬ���Է���JLable����
		jl1 = new JLabel("��   ��");
		jl2 = new JLabel("��   ��");
		
		jtf = new JTextField(20);
		jpw = new JPasswordField(20);
		jpw.setEchoChar('*');
		
		jb1 = new JButton("��   ¼");
		jb2 = new JButton("ע   ��");
		
		Container c = this.getContentPane();
		
		jp0.setLayout(new GridLayout(3,1));
		jp0.add(jp1);
		jp0.add(jp2);
		jp0.add(jp3);
		
		jp1.add(jl1);
		jp1.add(jtf);
		
		jp2.add(jl2);
		jp2.add(jpw);
		
		jp3.add(jb1);
		jp3.add(jb2);
		
		jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,jl0,jp0);
		jsp.setOneTouchExpandable(false);
		c.add(jsp);
		
	}
	
	public static void main(String[] args) {
		new JFrameDemo2_4();
	}

}
