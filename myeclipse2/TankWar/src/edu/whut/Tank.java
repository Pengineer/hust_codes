package edu.whut;

/*
      function：构造坦克类
      	坦克运动说明：当按下UP键时，向上运动；当按下UP+RIGHT组合键时，向右上方运动；当按下SPACE键时，发射子弹。
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {
	private int x,y;
	TankClient tc ;
	
	public static final double PI = 3.1415926;
	public static final int movestep  = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	private boolean bL = false,bU = false,bR = false,bD = false; 
	private boolean id = true;//id=true,表示我方人马
	public boolean isId() {
		return id;
	}

	private boolean live = true;

	private static Random r = new Random();
	
	private int step = r.nextInt(12)+3;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};
	
	private Direction dir = Direction.STOP;//坦克方向
	private Direction ptdir = Direction.D;//炮筒方向
	
	public Tank(int x, int y, boolean id, Direction dir, TankClient tc) 
	{
		this.x = x;
		this.y = y;
		this.id = id;
		this.dir = dir;
		this.tc = tc;
	}

	public void drawTank(Graphics g)
	{
		if(!live)	
		{
			if(!id)
				tc.enemytanks.remove(this);//如果是敌方坦克，挂了就表示不显示
			return;
		}
		Color preColor = g.getColor();
    	if(id)g.setColor(Color.RED);
    	else  g.setColor(Color.BLUE);
    	g.fillOval(x, y, WIDTH, HEIGHT);
    	drawpt(g);
    	g.setColor(preColor);
    	
    	move();//TankClient中的paint方法被PaintThread线程每隔50ms调用一次，而drawTank方法被paint调用，因此子弹每隔50ms运动一个step。
	}
	
	public void drawpt(Graphics g)//-----------------------画坦克的炮筒
	{
		Color preColor = g.getColor();
		g.setColor(Color.GRAY);
    	switch(ptdir)  //先画出朝左的炮筒，然后依次旋转45°，得到其它7个方向的炮筒，注意一定要将原来的坐标系转回来 
    	{
			case L:
				g.fillRect(this.x-WIDTH/4, this.y+HEIGHT/3, WIDTH , HEIGHT/3);
				break;
			case LU:
				Graphics2D g2d1 = (Graphics2D) g;
				g2d1.rotate(PI/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				g2d1.fillRect(this.x-WIDTH/4, this.y+HEIGHT/3, WIDTH , HEIGHT/3);
				g2d1.rotate(PI*7/4,this.x+WIDTH/2,this.y+HEIGHT/2); //将原坐标系转回来
				break;
			case U:
				Graphics2D g2d2 = (Graphics2D) g;
				g2d2.rotate(PI/2,this.x+WIDTH/2,this.y+HEIGHT/2);
				g2d2.fillRect(this.x-WIDTH/4, this.y+HEIGHT/3, WIDTH , HEIGHT/3);
				g2d2.rotate(PI*3/2,this.x+WIDTH/2,this.y+HEIGHT/2);
				break;
			case RU:
				Graphics2D g2d3 = (Graphics2D) g;
				g2d3.rotate(PI*3/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				g2d3.fillRect(this.x-WIDTH/4, this.y+HEIGHT/3, WIDTH , HEIGHT/3);
				g2d3.rotate(PI*5/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				break;
			case R:
				Graphics2D g2d4 = (Graphics2D) g;
				g2d4.rotate(PI*4/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				g2d4.fillRect(this.x-WIDTH/4, this.y+HEIGHT/3, WIDTH , HEIGHT/3);
				g2d4.rotate(PI*4/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				break;
			case RD:
				Graphics2D g2d5 = (Graphics2D) g;
				g2d5.rotate(PI*5/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				g2d5.fillRect(this.x-WIDTH/4, this.y+HEIGHT/3, WIDTH , HEIGHT/3);
				g2d5.rotate(PI*3/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				break;
			case D:
				Graphics2D g2d6 = (Graphics2D) g;
				g2d6.rotate(PI*6/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				g2d6.fillRect(this.x-WIDTH/4, this.y+HEIGHT/3, WIDTH , HEIGHT/3);
				g2d6.rotate(PI*2/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				break;
			case LD:
				Graphics2D g2d7 = (Graphics2D) g;
				g2d7.rotate(PI*7/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				g2d7.fillRect(this.x-WIDTH/4, this.y+HEIGHT/3, WIDTH , HEIGHT/3);
				g2d7.rotate(PI*1/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				break;
			case STOP:
				Graphics2D g2d8 = (Graphics2D) g;
				g2d8.rotate(PI*6/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				g2d8.fillRect(this.x-WIDTH/4, this.y+HEIGHT/3, WIDTH , HEIGHT/3);
				g2d8.rotate(PI*2/4,this.x+WIDTH/2,this.y+HEIGHT/2);
				break;
		}
    	g.setColor(preColor);
	}
		
	public void keyPressed(KeyEvent e) //---------当有键被按下时，相应的方向标志位设置为true，并通过locateDirection来确定方向 
	{
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_F2:
			tc.mytank.live = true ;
			tc.enemytanks.removeAll(tc.enemytanks);
			for(int i=0 ; i<10 ; i++)
	    	{
	    		tc.enemytanks.add(new Tank(700,20+50*i,false,Tank.Direction.L,tc));
	    	}
			break;
		case KeyEvent.VK_LEFT :
			bL = true;
			break;
		case KeyEvent.VK_UP :
			bU = true;
			break;
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		case KeyEvent.VK_DOWN :
			bD = true;
			break;
		}
		locateDirection();
	}
	
	public void locateDirection() //--------------通过判定4个方向标志位来确定具体方向
	{
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}

	public void keyReleased(KeyEvent e) //--------当键被释放时，复位对应的方向标志位，并通过locateDirection重新确定方向
	{
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT :
			bL = false;
			break;
		case KeyEvent.VK_UP :
			bU = false;
			break;
		case KeyEvent.VK_RIGHT :
			bR = false;
			break;
		case KeyEvent.VK_DOWN :
			bD = false;
			break;
		}
		locateDirection();		
	}
	
	public void move() //-------------------------向指定的方向移动：4个按键操作8个方向
	{
		switch(dir) {
		case L:
		{
			x-=movestep;
			if(x<0)
				x=0;
			break;
		}
		case LU:
		{
			x-=movestep;
			if(x<0)
				x=0;
			y-=movestep;
			if(y<25)
				y=25;
			break;
		}
		case U:
		{
			y-=movestep;
			if(y<25)
				y=25;
			break;
		}
		case RU:
		{
			x+=movestep;
			if(x>TankClient.WINDOW_WIDTH-25)
				x=TankClient.WINDOW_WIDTH-25;
			y-=movestep;
			if(y<25)
				y=25;
			break;
		}
		case R:
		{
			x+=movestep;
			if(x>TankClient.WINDOW_WIDTH-25)
				x=TankClient.WINDOW_WIDTH-25;
			break;
		}
		case RD:
		{
			x+=movestep;
			if(x>TankClient.WINDOW_WIDTH-25)
				x=TankClient.WINDOW_WIDTH-25;
			y+=movestep;
			if(y>TankClient.WINDOW_HEIGHT-25)
				y=TankClient.WINDOW_HEIGHT-25;
			break;
		}
		case D:
		{
			y+=movestep;
			if(y>TankClient.WINDOW_HEIGHT-25)
				y=TankClient.WINDOW_HEIGHT-25;
			break;
		}
		case LD:
		{
			x -= movestep;
			if(x<0)
				x=0;
			y += movestep;
			if(y>TankClient.WINDOW_HEIGHT-25)
				y=TankClient.WINDOW_HEIGHT-25;
			break;
		}
		case STOP:
			break;
		}
		
		if(this.dir != Direction.STOP)
			this.ptdir = this.dir ;
		
		if(!id) //如果是敌方坦克，就产生随机方向
		{
			Direction[] dirs = Direction.values();
			if(step == 0)
			{
				step = r.nextInt(12)+3;
				int rn = r.nextInt(dirs.length);//产生从0-length之间的随机整数
				dir = dirs[rn];
			}
			step--;
			
			if(r.nextInt(40)>36)
				fire();
		}
	}
	
	public Missile fire()//-----------------------每弹起ctrl键时，就会向容器中增加一颗子弹,TankClient中paint方法每隔50ms就会画出所有
	{                    //-----------------------子弹，之所以弹起时添加，就是为了防止按着ctrl键不动时，出现连发的情况。
		if(!live) return null;
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x,y,ptdir,id,this.tc);
		tc.mymissiles.add(m);
		return m;
	}
	
	public Rectangle getRect()//---------------------------------获取坦克所属的框的大小
	{
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
}
