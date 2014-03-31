import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rectangle extends Shape
{
	private static final long serialVersionUID = 1L;
	public Rectangle(int x1, int y1, int x2, int y2, Color cc, int t)
	{
		super(x1, y1, x2, y2, cc, t);
		type = t;
	}

	public boolean checkSelected(int x, int y)
	{
		int dx1, dy1, c1, dx2, dy2, c2, dx3, dy3, c3, dx4, dy4, c4;
		dx1 = x2 - x1;
		dy1 = 0;
		dx2 = 0;
		dy2 = y2 - y1;
		dx3 = x2 - x1;
		dy3 = 0;
		dx4 = 0;
		dy4 = y1 - y2;
		c1 = dx1 * y1 - dy1 * x1;
		c2 = dx2 * y2 - dy2 * x2;
		c3 = dx3 * y2 - dy3 * x2;
		c4 = dx4 * y1 - dy4 * x1;
		int xx = Math.min(x1, x2) - 10;
		int xl = Math.max(x1, x2) + 10;
		int yy = Math.min(y1, y2) - 10;
		int yl = Math.max(y1, y2) + 10;
		if (x >= (xx) && x <= (xl) && y >= (yy) && y <= (yl))
		{
			if (Math.abs(dx1 * y - dy1 * x - c1) < 1000)
				return true;
			else if (Math.abs(dx2 * y - dy2 * x - c2) < 1000)
				return true;
			else if (Math.abs(dx3 * y - dy3 * x - c3) < 1000)
				return true;
			else if (Math.abs(dx4 * y - dy4 * x - c4) < 1000)
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
		else if(x >= x1 - 2 && x <= x1 + 3 && y >= y2 - 2 && y <= y2 + 3)
			return 4;
		else if(x >= x2 - 2 && x <= x2 + 3 && y >= y1 - 2 && y <= y1 + 3)
			return 3;
		else
			return 0;
	}

	public boolean checkBounds(int x, int y)
	{
		int xmin = Math.min(x1, x2);
		int xmax = Math.max(x1, x2);
		int ymin = Math.min(y1, y2);
		int ymax = Math.max(y1, y2);
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
	}
	public void resize(int order, int x, int y)
	{
		switch(order)
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
			y1 = y;
			x2 = x;
			break;
		case 4:
			x1 = x;
			y2 = y;
			break;
		}
		
	}
	@Override
	public void paint(Graphics g2d)
	{
		// TODO Auto-generated method stub
		super.paint(g2d);
		g2d.drawLine(x1, y1, x2, y1);
		g2d.drawLine(x2, y1, x2, y2);
		g2d.drawLine(x1, y2, x2, y2);
		g2d.drawLine(x1, y2, x1, y1);
		if (selected == true)
		{
			((Graphics2D) g2d).setStroke(new BasicStroke(2));
			g2d.setColor(Color.BLACK);
			g2d.fillRect(x1 - 2, y1 - 2, 5, 5);
			g2d.fillRect(x2 - 2, y2 - 2, 5, 5);
			g2d.fillRect(x1 - 2, y2 - 2, 5, 5);
			g2d.fillRect(x2 - 2, y1 - 2, 5, 5);
		}
	}
}
