import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Line extends Shape
{
	private static final long serialVersionUID = 1L;

	public Line(int x1, int y1, int x2, int y2, Color cc, int t)
	{
		super(x1, y1, x2, y2, cc, t);
		type = t;
	}

	public boolean checkSelected(int x, int y)
	{
		int dy, dx, c;
		dy = this.y2 - this.y1;
		dx = this.x2 - this.x1;
		c = (dx * y2 - dy * x2);
		int xx = Math.min(x1, x2);
		int xl = Math.max(x1, x2);
		int yy = Math.min(y1, y2);
		int yl = Math.max(y1, y2);
		if (x >= (xx) && x <= (xl) && y >= (yy) && y <= (yl))
		{
			if (Math.abs(dx * y - dy * x - c) < 1000)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	public boolean checkBounds(int x, int y)
	{
		int dy, dx, c;
		dy = this.y2 - this.y1;
		dx = this.x2 - this.x1;
		c = (dx * y2 - dy * x2);
		int xx = Math.min(x1, x2);
		int xl = Math.max(x1, x2);
		int yy = Math.min(y1, y2);
		int yl = Math.max(y1, y2);
		if (x >= (xx) && x <= (xl) && y >= (yy) && y <= (yl))
		{
			if (Math.abs(dx * y - dy * x - c) < 1000)
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
		else
			return 0;
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
				break;
			case 2:
				x2 = x;
				y2 = y;
				break;
		}
	}

	public void paint(Graphics g2d)
	{
		super.paint(g2d);
		g2d.drawLine(x1, y1, x2, y2);
		if (selected == true)
		{
			((Graphics2D) g2d).setStroke(new BasicStroke(2));
			g2d.setColor(Color.BLACK);
			g2d.fillRect(x1 - 2, y1 - 2, 5, 5);
			g2d.fillRect(x2 - 2, y2 - 2, 5, 5);
		}
	}

}
