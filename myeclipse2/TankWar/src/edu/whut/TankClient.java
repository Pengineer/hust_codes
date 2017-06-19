package edu.whut;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;  //����ָ������Ϊawt��Ҳ��List
import java.util.ArrayList;
/*
 * ��Ҫʹ��JFrame,���ǾͲ�����JFrame��дpaint����ΪJFrame���ж��ģ���ʱ������Ӧ�õ���дһ���࣬�����̳�JPanel��
 * Ȼ�������������дpaint����������TankClient�̳�JFrame�������һ�������Ǹ���Ķ���
 */


public class TankClient extends Frame
{
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	Image buffimage = null;
	
	Tank mytank = new Tank(100,350,true,Tank.Direction.STOP,this);
	
	List<Tank> enemytanks = new ArrayList<Tank>();
	List<Explore> explores = new ArrayList<Explore>();
	List<Missile> mymissiles = new ArrayList<Missile>();
	
	TankClient()//----------------------------------------���캯������Frame����
	{
		super("TankWar");
		lanchFrame();
	}
    public void lanchFrame()//----------------------------���ô�������
    {
    	this.setBounds(250, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
    	//this.setTitle("TankWar");
    	this.setBackground(Color.GREEN);
    	this.setResizable(false);
    	this.setVisible(true);
    	
    	
    	
    	for(int i=0 ; i<10 ; i++)
    	{
    		enemytanks.add(new Tank(700,20+50*i,false,Tank.Direction.L,this));
    	}
    	
    	this.addKeyListener(new KeyMonitor());
    	this.addWindowListener(new WindowAdapter()
    	{
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
			} 		
       	});
    	
    	new Thread(new PaintThread()).start();
    }
    
    public void paint(Graphics g)//-----------------------ÿ�ε���������ʱ���Զ����ô˷���
    {
    	super.paint(g); //�ز����٣�ϰ��
    //	g.drawString("Missiles count��"+mymissiles.size(), 10, 50);
    //	g.drawString("Explores count��"+explores.size(), 10, 70);
    	g.drawString("������Ƽ����� �� �� ��������ϣ�", 10, 50);
    	g.drawString("�ӵ�����������²��ͷ�ctrl", 10, 70);
    	g.drawString("���¿�ʼ�����²��ͷ�F2��", 10, 90);
    	g.drawString("enemytank count��"+enemytanks.size(), 10, 110);
    	
    	mytank.drawTank(g);
    	
    	for(int i=0 ; i<mymissiles.size();i++)
    	{
    		Missile m = mymissiles.get(i);
    		m.hitTanks(enemytanks);
    		m.hitTank(mytank);
    		m.drawMissile(g);
    	}
    	
    	for(int i=0 ; i<explores.size();i++)
    	{
    		Explore e = explores.get(i);
    		e.drawExplore(g);
    	}
    	for(int i=0 ;i<enemytanks.size() ; i++)
    	{
    		Tank t = enemytanks.get(i);
    		t.drawTank(g);
    	}
    }
    
    public void update(Graphics g)//---------------------����˫���弼�������ӵ��˶������е���˸����(Java����Ͳ��ʺ���������Ϸ),��ͼ���Ȼ���һ����̨ͼƬ��
    {
    	if(buffimage == null)
    		buffimage = this.createImage(WINDOW_WIDTH, WINDOW_HEIGHT);
    	Graphics penbuffimage = buffimage.getGraphics();//��ȡbuffimage�Ļ���(Graphics����)
    	Color c = penbuffimage.getColor();
    	penbuffimage.setColor(Color.GREEN);
    	penbuffimage.fillRect(0, 0, 800, 600);
    	penbuffimage.setColor(c);
    	paint(penbuffimage);
    	g.drawImage(buffimage, 0, 0, null);
    }
	
    public static void main(String[] args) 
	{
		new TankClient();
	}

	private class PaintThread implements Runnable//------����һ�����̣߳�ÿ��һ��ʱ���ػ�һ�δ��壬�Ӷ�ʵ���ƶ�Ч��
	{		
		public void run() 
		{
			while(true)
			{
				repaint();//�ȵ���update���ڵ���paint
				try 
				{
					Thread.sleep(100);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}	
	}

	public class KeyMonitor extends KeyAdapter//---------��������
	{
		public void keyReleased(KeyEvent e) {
			mytank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			mytank.keyPressed(e);
		}
		
	}
}
