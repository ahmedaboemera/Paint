import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class IscTriangle extends Shape
{
	private static final long serialVersionUID = 1L;
	private int x3;
	private int y3;

	public IscTriangle(int x1, int y1, int x2, int y2, Color cc, int t)
	{
		// TODO Auto-generated constructor stub
		super(x1, y1, x2, y2, cc, t);
		type = t;
		this.x3 = x1 - (x2 - x1);
		this.y3 = y2;
	}

	public boolean checkSelected(int x, int y)
	{
		int dx1, dy1, c1, dx2, dy2, c2, dx3, dy3, c3;
		dx1 = x2 - x1;
		dy1 = y2 - y1;
		dx2 = x3 - x2;
		dy2 = 0;
		dx3 = x1 - x3;
		dy3 = y1 - y3;
		c1 = dx1 * y2 - dy1 * x2;
		c2 = dx2 * y3 - dy2 * x3;
		c3 = dx3 * y1 - dy3 * x1;
		int xx = Math.min(x1, Math.min(x2, x3));
		int xl = Math.max(x1, Math.max(x2, x3));
		int yy = Math.min(y1, Math.min(y2, y3));
		int yl = Math.max(y1, Math.max(y2, y3));
		if (x >= (xx - 10) && x <= (xl + 10) && y >= (yy - 10)
				&& y <= (yl + 10))
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
		else if(x >= x3 - 2 && x <= x3 + 3 && y >= y3 - 2 && y <= y3 + 3)
			return 3;
		else return 0;
	}

	public boolean checkBounds(int x, int y)
	{
		if (x < x3 && y > y1 && x > x2 && y < y2)
			return true;
		return false;
	}

	public void move(int x, int y)
	{
		x1 += x;
		y1 += y;
		x2 += x;
		y2 += y;
		x3+=x;
		y3+=y;
	}

	public void resize(int order, int x, int y)
	{
		switch (order)
		{
			case 1:
				x1 = x;
				y1 = y;
				x3 = x1 - (x2 - x1);
				break;
			case 2:
				x2 = x;
				y2 = y;
				x3 = x1 - (x2 - x1);
				y3 = y2;
				break;
			case 3:
				x3 = x;
				y3 = y;
				x2 = x1 - (x3 - x1);
				y2 = y3;
				break;
		}
	}

	@Override
	public void paint(Graphics g2d)
	{
		// TODO Auto-generated method stub
		super.paint(g2d);
		g2d.drawLine(x1, y1, x2, y2);
		g2d.drawLine(x1, y1, x3, y3);
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
