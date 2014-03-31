import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Square extends Shape
{
	private static final long serialVersionUID = 1L;
	private int length;

	public Square(int x1, int y1, int x2, int y2, Color cc, int t)
	{
		super(x1, y1, x2, y2, cc, t);
		type = t;
		this.length = 2*(int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		/*if (x1 > x2)
		{
			x1-length/2 = x1;
			x1+length/2 = x1 - length;
			x1-length/2 = x1 - length;
		}
		else
		{
			x1-length/2 = x1;
			x1+length/2 = x1 + length;
			x1-length/2 = x1 + length;
		}
		if (y1 > y2)
		{
			y1-length/2 = y1;
			y1+length/2 = y1 - length;
			y1+length/2 = y1 - length;
		}
		else
		{
			y1-length/2 = y1;
			y1+length/2 = y1 + length;
			y1+length/2 = y1 + length;
		}*/
	}

	public boolean checkSelected(int x, int y)
	{
		int dx1, dy1, c1, dx2, dy2, c2, dx3, dy3, c3, dx4, dy4, c4;
		dx1 = length;
		dy1 = 0;
		dx2 = 0;
		dy2 = length;
		dx3 = length;
		dy3 = 0;
		dx4 = 0;
		dy4 = length;
		c1 = dx1 * (y1-length/2) - dy1 * (x1-length/2);
		c2 = dx2 * (y1-length/2) - dy2 * (x1-length/2);
		c3 = dx3 * (y1+length/2) - dy3 * (x1+length/2);
		c4 = dx4 * (y1+length/2) - dy4 * (x1+length/2);
		int xx = Math.min(x1+length/2, x1-length/2) - 10;
		int xl = Math.max(x1+length/2, x1-length/2) + 10;
		int yy = Math.min(y1+length/2, y1-length/2) - 10;
		int yl = Math.max(y1+length/2, y1-length/2) + 10;
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
		}
		else
			return false;
	}
	
	public void resize(int order, int x, int y)
	{
		x2 = x;
		y2 = y;
		this.length = 2*(int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	public int checkCorners(int x, int y)
	{
		if(x >= x1 - 2 && x <= x1 + 3 && y >= y1-length/2 - 2 && y <= y1-length/2 + 3)
		{
			return 1;
		}
		else if(x >= x1- 2 && x <= x1 + 3 && y >= y1+length/2 - 2 && y <= y1+length/2 + 3)
		{
			return 1;
		}
		else if(x >= x1+length/2 - 2 && x <= x1+length/2 + 3 && y >= y1 - 2 && y <= y1 + 3)
		{
			return 2;
		}
		else if(x >= x1-length/2 - 2 && x <= x1-length/2 + 3 && y >= y1 - 2 && y <= y1 + 3)
		{
			return 2;
		}
		else
		{
			return 0;
		}
	}

	public boolean checkBounds(int x, int y)
	{
		if (x > x1-length/2 && y > y1-length/2 && x < x1+length/2 && y < y1+length/2)
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

	@Override
	public void paint(Graphics g2d)
	{
		// TODO Auto-generated method stub
		super.paint(g2d);
		g2d.drawLine(x1-length/2, y1-length/2, x1+length/2, y1-length/2);
		g2d.drawLine(x1-length/2, y1-length/2, x1-length/2, y1+length/2);
		g2d.drawLine(x1+length/2, y1-length/2, x1+length/2, y1+length/2);
		g2d.drawLine(x1+length/2, y1+length/2, x1-length/2, y1+length/2);
		if (selected == true)
		{
			((Graphics2D) g2d).setStroke(new BasicStroke(2));
			g2d.setColor(Color.BLACK);
			g2d.fillRect(x1 - 2, y1-length/2 - 2, 5, 5);
			g2d.fillRect(x1 - 2, y1+length/2 - 2, 5, 5);
			g2d.fillRect(x1+length/2 - 2, y1 - 2, 5, 5);
			g2d.fillRect(x1-length/2 - 2, y1 - 2, 5, 5);
		}
	}
}
