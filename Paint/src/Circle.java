import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Circle extends Shape
{
	private static final long serialVersionUID = 1L;
	private int a, upX, upY;

	public Circle(int x1, int y1, int x2, int y2, Color cc, int t)
	{
		super(x1, y1, x2, y2, cc, t);
		type = t;
		a = 2 * (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		upX = x1 - a / 2;
		upY = y1 - a / 2;
	}

	public boolean checkSelected(int x, int y)
	{
		if (Math.abs((x - x1) * (x - x1) + (y - y1) * (y - y1) - (a / 2)
				* (a / 2)) < 1000)
			return true;
		else
			return false;
	}

	public int checkCorners(int x, int y)
	{
		if (x >= x1 + a / 2 - 2 && x <= x1 + a / 2 + 3 && y >= y1 - 2 && y <= y1 + 3
		 || x >= x1 - a / 2 - 2 && x <= x1 - a / 2 + 3 && y >= y1 - 2 && y <= y1 + 3 )
		{
			return 2;
		}
		else if(x >= x1 - 2 && x <= x1 + 3 && y >= y1 + a / 2 - 2 && y <= y1 + a / 2 + 3 
			 || x >= x1 - 2 && x <= x1 + 3 && y >= y1 - a / 2 - 2 && y <= y1 - a / 2 + 3)
		{
			return 1;
		} 
		else
		{
			return 0;
		}
	}
	
	public void resize(int order, int x, int y)
	{
		x2 = x;
		y2 = y;
		a = 2 * (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		upX = x1 - a / 2;
		upY = y1 - a / 2;
	}

	public void move(int x, int y)
	{
//		this.x1 = x;
//		this.y1 = y;
//		this.x2 = x + a / 2;
//		this.y2 = y;
//		this.upX = x - a / 2;
//		this.upY = y - a / 2;
		upX+=x;
		upY+=y;
		x1 += x;
		y1 += y;
		x2 += x;
		y2 += y;
	}

	public boolean checkBounds(int x, int y)
	{
		if (x < x1 + a / 2 && y < y1 + a / 2 && x > x1 - a / 2
				&& y > y1 - a / 2)
			return true;
		return false;
	}

	public void paint(Graphics g2d)
	{
		super.paint(g2d);
		g2d.drawOval(upX, upY, a, a);
		if (selected == true)
		{
			((Graphics2D) g2d).setStroke(new BasicStroke(2));
			g2d.setColor(Color.BLACK);
			g2d.fillRect(x1 - 2, y1 - 2, 5, 5);
			g2d.fillRect(x1 + a / 2 - 2, y1 - 2, 5, 5);
			g2d.fillRect(x1 - a / 2 - 2, y1 - 2, 5, 5);
			g2d.fillRect(x1 - 2, y1 + a / 2 - 2, 5, 5);
			g2d.fillRect(x1 - 2, y1 - a / 2 - 2, 5, 5);
		}
	}
}
