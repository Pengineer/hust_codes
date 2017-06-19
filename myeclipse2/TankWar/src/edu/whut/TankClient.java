package edu.whut;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;  //必须指明，因为awt中也有List
import java.util.ArrayList;
/*
 * 若要使用JFrame,我们就不能在JFrame中写paint，因为JFrame是有多层的，这时，我们应该单独写一个类，让它继承JPanel，
 * 然后在这个类中重写paint方法，而让TankClient继承JFrame，并添加一个上面那个类的对象。
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
	
	TankClient()//----------------------------------------构造函数创建Frame窗体
	{
		super("TankWar");
		lanchFrame();
	}
    public void lanchFrame()//----------------------------设置窗体属性
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
    
    public void paint(Graphics g)//-----------------------每次当窗体重现时，自动调用此方法
    {
    	super.paint(g); //必不可少，习惯
    //	g.drawString("Missiles count："+mymissiles.size(), 10, 50);
    //	g.drawString("Explores count："+explores.size(), 10, 70);
    	g.drawString("方向控制键：↑ ↓ ← →（可组合）", 10, 50);
    	g.drawString("子弹发射键：按下并释放ctrl", 10, 70);
    	g.drawString("重新开始：按下并释放F2键", 10, 90);
    	g.drawString("enemytank count："+enemytanks.size(), 10, 110);
    	
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
    
    public void update(Graphics g)//---------------------利用双缓冲技术消除子弹运动过程中的闪烁现象(Java本身就不适合做单机游戏),将图像先画到一个后台图片上
    {
    	if(buffimage == null)
    		buffimage = this.createImage(WINDOW_WIDTH, WINDOW_HEIGHT);
    	Graphics penbuffimage = buffimage.getGraphics();//获取buffimage的画笔(Graphics类型)
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

	private class PaintThread implements Runnable//------创建一个新线程，每隔一段时间重画一次窗体，从而实现移动效果
	{		
		public void run() 
		{
			while(true)
			{
				repaint();//先调用update，在调用paint
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

	public class KeyMonitor extends KeyAdapter//---------监听按键
	{
		public void keyReleased(KeyEvent e) {
			mytank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			mytank.keyPressed(e);
		}
		
	}
}
