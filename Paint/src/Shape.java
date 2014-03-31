import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Shape extends JPanel
{
	private static final long serialVersionUID = 1L;
	protected int x1;
	protected int y1;
	protected int x2;
	protected int y2;
	protected boolean selected;
	protected Color color;
	protected int type;

	public Shape(int x1, int y1, int x2, int y2, Color cc, int t)
	{
		selected = false;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = cc;
		type = t;
	}

	public int getX1()
	{
		return x1;
	}

	public int getX3()
	{
		return x1;
	}

	public int getY3()
	{
		return x1;
	}

	public int getType()
	{
		return type;
	}

	public int getY1()
	{
		return y1;
	}

	public int getX2()
	{
		return x2;
	}

	public int getY2()
	{
		return y2;
	}

	public Color getColor()
	{
		return color;
	}

	public boolean getSelected()
	{
		return selected;
	}

	public void setSelected(boolean ss)
	{
		selected = ss;
	}

	public void changeSelected()
	{
		if (selected == true)
			selected = false;
		else
			selected = true;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public boolean checkSelected(int x, int y)
	{
		return false;
	}

	public boolean checkBounds(int x, int y)
	{
		return false;
	}

	public int checkCorners(int x, int y)
	{
		return 0;
	}

	public void move(int x, int y)
	{
		return ;
	}
	public void resize(int order, int x, int y)
	{
		return;
	}
	public void paint(Graphics g2d)
	{
		super.paintComponent(g2d);
		g2d.setColor(color);
		((Graphics2D) g2d).setStroke(new BasicStroke(2));
		if (selected == true)
		{
			((Graphics2D) g2d).setStroke(new BasicStroke(1f,
					BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f,
					new float[] { 2f }, 2f));
		}
	}
}
