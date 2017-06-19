package com.ipanel.http;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ipanel.tools.HttpDownLoadListener;
import com.ipanel.tools.J2meFile;


public class Test extends JFrame {
	String urlpath1,urlpath2,savepath1,savepath2;
	boolean isSuccess;
	HttpDownloadManager httpDownLoadManager = null;
	
	JPanel jp,jp1,jp2,jp3,jp4,jp5;
	JLabel jl1,jl2,jl3,jl4;
	JTextField jtf1,jtf2,jtf3,jtf4;
	JButton jb1,jb2;
	static JTextArea jta;
	JScrollPane jsp;
	private Test()
	{
		this.setBounds(300,200,600,400);
		this.setTitle("多任务极速下载器");
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		jp = new JPanel();
		jp.setLayout(new GridLayout(5,1));
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
		
		jtf1 = new JTextField(40);
		jtf2 = new JTextField(40);
		jtf3 = new JTextField(40);
		jtf4 = new JTextField(40);

		
		jb1 = new JButton("1号开始下载");
		jb2 = new JButton("2号开始下载");
		
		jta = new JTextArea("提示：保存路径最后必须加上待下载文件的文件名",8,50);
		jta.setEditable(false);
		jsp = new JScrollPane(jta);
		jsp.setVisible(true);
		
		Container c = this.getContentPane();
		c.add(jp);
		c.add(jsp);
		
		jp.add(jp1);
		jp.add(jp2);
		jp.add(jp3);
		jp.add(jp4);
		jp.add(jp5);
		
		
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
		
		myEvent();
		httpDownLoadManager = HttpDownloadManager.getManager();
	}
	
	public void myEvent(){
		jb1.addActionListener(new ActionListener()  
		{
			public void actionPerformed(ActionEvent e)
			{
				urlpath1 = jtf1.getText();
				savepath1 = jtf3.getText();			
				isSuccess = httpDownLoadManager.load(urlpath1,savepath1 , new HttpDownLoadListenerImpl());
				jta.append("\r\n"+new Boolean(isSuccess).toString()+"\r\n");				
			}
		});
		jb2.addActionListener(new ActionListener()   
		{
			public void actionPerformed(ActionEvent e)
			{
				urlpath2 = jtf2.getText();
				savepath2 = jtf4.getText();			
				isSuccess = httpDownLoadManager.load(urlpath2,savepath2 , new HttpDownLoadListenerImpl());
				jta.append("\r\n"+new Boolean(isSuccess).toString()+"\r\n");
			}
		});
	}
	
public static void main(String[] args){
	
/*		// TODO Auto-generated method stub
	try{
		System.loadLibrary("J2meFileDll");//加载动态库
	}catch(Exception e){
		e.printStackTrace();
	}
	*/
	
	//String savePath1 = "C:/Users/liangjian/Desktop/coco.apk";
	//String savePath2 = "C:/Users/liangjian/Desktop/365rili.exe";
	//String url = "http://d2.365rili.com/coco.apk";
	//String url2 = "http://d2.365rili.com/pc/update/365rili.exe";
	//String savePath1 = "C:/Users/liangjian/Desktop/1.mp3";
	//String savePath2 = "C:/Users/liangjian/Desktop/2.mp3";
	//String url = "http://stream11.qqmusic.qq.com/35144031.mp3";	
	//String url2 = "http://stream11.qqmusic.qq.com/35144031.mp3";
	
	new Test();
	
	//下载文件
	
	
	
	//取消下载功能测试
//	for(long n=0;n<1000000000;n++){}
	
//	HttpDownloadManager.getManager().cancel(url);
	
	}
}