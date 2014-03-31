import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Ellipse extends Shape
{
	private static final long serialVersionUID = 1L;

	public Ellipse(int x1, int y1, int x2, int y2, Color cc, int t)
	{
		// TODO Auto-generated constructor stub
		super(x1, y1, x2, y2, cc, t);
		type = t;
	}

	public boolean checkSelected(int x, int y)
	{
		int h = Math.abs(x2 - x1) / 2;
		int k = Math.abs(y2 - y1) / 2;
		int hh = Math.min(x1, x2) + h;
		int kk = Math.min(y1, y2) + k;
		if (h == 0 || k == 0)
			return false;
		if (((x - hh) * (x - hh) / (double) (h * h))
				+ ((y - kk) * (y - kk) / (double) (k * k)) <= 1.1
				&& ((x - hh) * (x - hh) / (double) (h * h))
						+ ((y - kk) * (y - kk) / (double) (k * k)) >= 0.9)
			return true;
		else
			return false;
	}

	public boolean checkBounds(int x, int y)
	{
		if (x > Math.min(x1, x2) && y > Math.min(y1, y2)
				&& x < Math.max(x1, x2) && y < Math.max(y1, y2))
			return true;
		return false;
	}

	public int checkCorners(int x, int y)
	{
		int xx = x1;
		int yy = y1;
		x1 = Math.min(xx, x2);
		x2 = Math.max(xx, x2);
		y1 = Math.min(yy, y2);
		y2 = Math.max(yy, y2);
		if ((x >= Math.min(x1, x2) -2 && x <= Math.min(x1, x2) +3
				&& y >= Math.min(y1, y2) + (int) (0.5 * Math.abs(y2 - y1)) - 2
				&& y <= Math.min(y1, y2) + (int) (0.5 * Math.abs(y2 - y1)) + 3))
		{
			return 1;//left
		}
		else if((x >= Math.min(x1, x2) + (int) (Math.abs(x2 - x1)) - 2
				&& x <= Math.min(x1, x2) + (int) (Math.abs(x2 - x1)) +3
				&& y >= Math.min(y1, y2) + (int) (0.5 * Math.abs(y2 - y1)) - 2
				&& y <= Math.min(y1, y2) + (int) (0.5 * Math.abs(y2 - y1)) + 3))
		{
			return 2;//right
		}
		else if((x >= Math.min(x1, x2) + (int) (0.5 * Math.abs(x2 - x1)) - 2
			&& x <= Math.min(x1, x2) + (int) (0.5 * Math.abs(x2 - x1)) + 3
			&& y >= Math.min(y1, y2)-2 && y <= Math.min(y1, y2)+3))
		{
			return 3;//top
		}
		else if(x >= Math.min(x1, x2) + (int) (0.5 * Math.abs(x2 - x1)) - 2
			&& x <= Math.min(x1, x2) + (int) (0.5 * Math.abs(x2 - x1)) + 3
			&& y >= Math.min(y1, y2) + (int) (Math.abs(y2 - y1)) - 2
			&& y <= Math.min(y1, y2) + (int) (Math.abs(y2 - y1)) + 3)
		{
			return 4;//down
		}
		else
		{
			return 0;
		}
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
				break;
			case 2:
				x2 = x;
				break;
			case 3:
				y1 = y;
				break;
			case 4:
				y2 = y;
				break;
		}
	}

	@Override
	public void paint(Graphics g2d)
	{
		// TODO Auto-generated method stub
		super.paint(g2d);
		g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1),
				Math.abs(y2 - y1));
		if (selected == true)
		{
			((Graphics2D) g2d).setStroke(new BasicStroke(2));
			g2d.setColor(Color.BLACK);
			g2d.fillRect(
					Math.min(x1, x2) + (int) (0.5 * Math.abs(x2 - x1)) - 2,
					Math.min(y1, y2) + (int) (0.5 * Math.abs(y2 - y1)) - 2, 5,
					5);
			g2d.fillRect(
					Math.min(x1, x2) + (int) (0.5 * Math.abs(x2 - x1)) - 2,
					Math.min(y1, y2) + -2, 5, 5);
			g2d.fillRect(Math.min(x1, x2) - 2, Math.min(y1, y2)
					+ (int) (0.5 * Math.abs(y2 - y1)) - 2, 5, 5);
			g2d.fillRect(Math.min(x1, x2) + (int) (Math.abs(x2 - x1)) - 2,
					Math.min(y1, y2) + (int) (0.5 * Math.abs(y2 - y1)) - 2, 5,
					5);
			g2d.fillRect(
					Math.min(x1, x2) + (int) (0.5 * Math.abs(x2 - x1)) - 2,
					Math.min(y1, y2) + (int) (Math.abs(y2 - y1)) - 2, 5, 5);

		}
	}

}
