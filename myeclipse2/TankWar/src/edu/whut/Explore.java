package edu.whut;

import java.awt.Color;
import java.awt.Graphics;


public class Explore {
	int x, y;
	TankClient tc;
	
	int[] diameter ={5,30,50,70,55,30,5};
	int num=0;
	
	private boolean live = true;
	
	Explore(int x, int y, TankClient tc)
	{
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void drawExplore(Graphics g)
	{
		if(!live)
		{
			tc.explores.remove(this);
			return;
		}
			
		if(num == diameter.length)
		{
			live = false;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[num], diameter[num]);
		g.setColor(c);
		num++;
	}
}
