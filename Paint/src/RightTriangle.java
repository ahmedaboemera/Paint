import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RightTriangle extends Shape
{
	private static final long serialVersionUID = 1L;
	protected int x3, y3;

	public RightTriangle(int x1, int y1, int x2, int y2, Color cc, int t)
	{
		// TODO Auto-generated constructor stub
		super(x1, y1, x2, y2, cc, t);
		x3 = x1;
		y3 = y2;
		type = t;
	}

	public boolean checkSelected(int x, int y)
	{
		int dx1, dy1, c1, dx2, dy2, c2, dx3, dy3, c3;
		dx1 = x2 - x1;
		dy1 = y2 - y1;
		dx2 = x2 - x1;
		dy2 = 0;
		dx3 = 0;
		dy3 = y2 - y1;
		c1 = dx1 * y1 - dy1 * x1;
		c2 = dx2 * y2 - dy2 * x2;
		c3 = dx3 * y1 - dy3 * x1;
		int xx = Math.min(x1, x2);
		int xl = Math.max(x1, x2);
		int yy = Math.min(y1, y2);
		int yl = Math.max(y1, y2);
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
		}
		else
			return false;
	}

	public int checkCorners(int x, int y)
	{
		if(x >= x1 - 2 && x <= x1 + 3 && y >= y1 - 2 && y <= y1 + 3)
			return 1;
		else if(x >= x2 - 2 && x <= x2 + 3 && y >= y2 - 2 && y <= y2 + 3)
			return 2;
		else if(x >= x1 - 2 && x <= x1 + 3 && y >= y2 - 2 && y <= y2 + 3)
			return 3;
		else
			return 0;
	}

	public boolean checkBounds(int x, int y)
	{
		if (x > x1 && y > y1 && x < x2 && y < y2)
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
		switch (order)
		{
			case 1:
				x1 = x;
				y1 = y;
				x3 = x;
				y3 = y2;
				break;
			case 2:
				x2 = x;
				y2 = y;
				y3 = y;
				x3 = x1;
				break;
			case 3:
				x3 = x;
				x1 = x;
				y3 = y;
				y2 = y;
				break;
		}
	}

	@Override
	public void paint(Graphics g2d)
	{
		// TODO Auto-generated method stub
		super.paint(g2d);
		g2d.drawLine(x1, y1, x2, y2);
		g2d.drawLine(x1, y1, x1, y2);
		g2d.drawLine(x1, y2, x2, y2);
		if (selected == true)
		{
			((Graphics2D) g2d).setStroke(new BasicStroke(2));
			g2d.setColor(Color.BLACK);
			g2d.fillRect(x1 - 2, y1 - 2, 5, 5);
			g2d.fillRect(x2 - 2, y2 - 2, 5, 5);
			g2d.fillRect(x1 - 2, y2 - 2, 5, 5);
		}
	}
}
