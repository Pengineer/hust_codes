package edu.whut;

import java.awt.*;
import java.util.List;

public class Missile {
	private int x,y;
	private TankClient tc = null;

	Tank.Direction ptdir;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public static final int SPEED  = 20;

	private boolean live = true;
	private boolean id = true;
	
	public boolean isLive() {
		return live;
	}

	public Missile(int x, int y, Tank.Direction ptdir,boolean id,TankClient tc) 
	{
		this.x = x;
		this.y = y;
		this.ptdir = ptdir;
		this.id = id;
		this.tc = tc;
	}
	
	public void drawMissile(Graphics g)
	{
		if(!live)	
		{
			tc.mymissiles.remove(this);//必须要把飞出边界的子弹从容器清除
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}

	private void move() 
	{
		switch(ptdir) 
		{
			case L:
				x-=SPEED;
				break;
			case LU:
				x-=SPEED;
				y-=SPEED;
				break;
			case U:
				y-=SPEED;
				break;
			case RU:
				x+=SPEED;
				y-=SPEED;
				break;
			case R:
				x+=SPEED;
				break;
			case RD:
				x+=SPEED;
				y+=SPEED;
				break;
			case D:
				y+=SPEED;
				break;
			case LD:
				x -= SPEED;
				y += SPEED;
				break;		
		}
		if(x<0 || y<0 || x>TankClient.WINDOW_WIDTH || y>TankClient.WINDOW_HEIGHT)//子弹飞出边界
		{
			live=false;
		}
	}
	
	public Rectangle getRect()//---------------------------------获取子弹所属的框的大小
	{
		return new Rectangle(x,y,Missile.WIDTH,Missile.HEIGHT);
	}
	
	public boolean hitTank(Tank t) //----------------------------碰撞检测
	{
		if(this.getRect().intersects(t.getRect()) && t.isLive() && this.id != t.isId() && this.live)  //坦克的框碰到子弹的框，两者就都消失，同一方的子弹不打同一方的坦克
		{
			Explore e = new Explore(x,y,tc);
			tc.explores.add(e);
			t.setLive(false);
			this.live = false;
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> ts)
	{
		for(int i=0 ; i<ts.size() ; i++)
		{
			if(hitTank(ts.get(i)))
				return true;
		}
		return true;
	}
}
