import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Triangle extends Shape
{
	private static final long serialVersionUID = 1L;
	private int x3, y3;

	public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, Color cc, int t)
	{
		super(x1, y1, x2, y2, cc, 7);
		type = t;
		this.x3 = x3;
		this.y3 = y3;
	}

	public int getX3()
	{
		return x3;
	}

	public int getY3()
	{
		return y3;
	}

	public boolean checkSelected(int x, int y)
	{
		int dx1, dy1, c1, dx2, dy2, c2, dx3, dy3, c3;
		dx1 = x2 - x1;
		dy1 = y2 - y1;
		dx2 = x3 - x2;
		dy2 = y3 - y2;
		dx3 = x1 - x3;
		dy3 = y1 - y3;
		c1 = dx1 * y1 - dy1 * x1;
		c2 = dx2 * y2 - dy2 * x2;
		c3 = dx3 * y3 - dy3 * x3;
		int xx = Math.min(x1, Math.min(x2, x3));
		int xl = Math.max(x1, Math.max(x2, x3));
		int yy = Math.min(y1, Math.min(y2, y3));
		int yl = Math.max(y1, Math.max(y2, y3));
		if (x >= (xx) && x <= (xl) && y >= (yy) && y <= (yl))
		{
			if (Math.abs(dx1 * y - dy1 * x - c1) < 1000)
				return true;
			else if (Math.abs(dx2 * y - dy2 * x - c2) < 1000)
				return true;
			else if (Math.abs(dx3 * y - dy3 * x - c3) < 1000)
				return true;
			else
				return false;
		} else
			return false;
	}

	public int checkCorners(int x, int y)
	{
		if(x >= x1 - 2 && x <= x1 + 3 && y >= y1 - 2 && y <= y1 + 3)
			return 1;
		else if(x >= x2 - 2 && x <= x2 + 3 && y >= y2 - 2 && y <= y2 + 3)
			return 2;
		else if(x >= x3 - 2 && x <= x3 + 3 && y >= y3 - 2 && y <= y3 + 3)
			return 3;
		else
			return 0;
	}

	public boolean checkBounds(int x, int y)
	{
		int xmin = Math.min(x1, Math.min(x2, x3));
		int ymin = Math.min(y1, Math.min(y2, y3));
		int xmax = Math.max(x1, Math.max(x2, x3));
		int ymax = Math.max(y1, Math.max(y2, y3));
		if (x > xmin && y > ymin && x < xmax && y < ymax)
			return true;
		return false;
	}

	public void move(int x, int y)
	{
		x1 += x;
		y1 += y;
		x2 += x;
		y2 += y;
		x3 += x;
		y3 += y;

	}

	public void resize(int order, int x, int y)
	{
		switch (order)
		{
			case 1:
				x1 = x;
				y1 = y;
				break;
			case 2:
				x2 = x;
				y2 = y;
				break;
			case 3:
				x3 = x;
				y3 = y;
				break;
		}
	}

	@Override
	public void paint(Graphics g2d)
	{
		// TODO Auto-generated method stub
		super.paint(g2d);
		g2d.drawLine(x1, y1, x3, y3);
		g2d.drawLine(x1, y1, x2, y2);
		g2d.drawLine(x3, y3, x2, y2);
		if (selected == true)
		{
			((Graphics2D) g2d).setStroke(new BasicStroke(2));
			g2d.setColor(Color.BLACK);
			g2d.fillRect(x1 - 2, y1 - 2, 5, 5);
			g2d.fillRect(x2 - 2, y2 - 2, 5, 5);
			g2d.fillRect(x3 - 2, y3 - 2, 5, 5);
		}
	}
}
