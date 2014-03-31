import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Paint extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Toolkit toolKit;
	HashMap<String, Integer> mymap = new HashMap<String, Integer>();
	JPanel all = new JPanel(null);
	JFileChooser fc = new JFileChooser();
	Dimension size;
	int i;
	static int lastaddedat = 7;
	JPanel buttons = new JPanel(null);
	JPanel buttonshorizontal = new JPanel(null);
	ShapeArrayList draw = new ShapeArrayList();
	static ArrayList<Integer> val = new ArrayList<Integer>();


	public Paint()
	{
		setTitle("Paintina");
		setSize(1015, 690);
		toolKit = getToolkit();
		size = toolKit.getScreenSize();
		setLocation((size.width - getWidth()) / 2,
				(size.height - getHeight()) / 2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		add(all);
		ImageIcon clearall = new ImageIcon("clear.png");
		ImageIcon selection = new ImageIcon("select.png");
		ImageIcon undoo = new ImageIcon("undo.png");
		ImageIcon redoo = new ImageIcon("redo.png");
		ImageIcon line = new ImageIcon("line.png");
		ImageIcon square = new ImageIcon("Square.png");
		ImageIcon rectangle = new ImageIcon("Rectangle.png");
		ImageIcon circle = new ImageIcon("Circle.png");
		ImageIcon ellipse = new ImageIcon("Ellipse.png");
		ImageIcon rTriangle = new ImageIcon("RightTriangle.png");
		ImageIcon iscTriangle = new ImageIcon("ISCTriangle.png");
		ImageIcon sTriangle = new ImageIcon("ScaleneTriangle.png");
		buttons.setBounds(0, 0, 75, 710);
		buttonshorizontal.setBounds(75, 1115 - 50, 850, 50);
		JButton clear = new JButton(clearall);
		clear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				draw.clearAll();
			}
		});
		clear.setBounds(0, 0, 75, 50);
		JButton Line = new JButton(line);
		Line.setBounds(0, 50, 75, 50);
		Line.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.unSelect();
				draw.setType(0);
			}
		});
		JButton Circle = new JButton(circle);
		Circle.setBounds(0, 100, 75, 50);
		Circle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.unSelect();
				draw.setType(1);
			}
		});
		JButton Rectangle = new JButton(rectangle);
		Rectangle.setBounds(0, 150, 75, 50);
		Rectangle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.unSelect();
				draw.setType(2);
			}
		});
		JButton IscTriangle = new JButton(iscTriangle);
		IscTriangle.setBounds(0, 200, 75, 50);
		IscTriangle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.unSelect();
				draw.setType(3);
			}
		});
		JButton Square = new JButton(square);
		Square.setBounds(0, 250, 75, 50);
		Square.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.unSelect();
				draw.setType(4);
			}
		});
		JButton RightTriangle = new JButton(rTriangle);
		RightTriangle.setBounds(0, 300, 75, 50);
		RightTriangle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.unSelect();
				draw.setType(5);
			}
		});
		JButton Ellipse = new JButton(ellipse);
		Ellipse.setBounds(0, 350, 75, 50);
		Ellipse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.unSelect();
				draw.setType(6);
			}
		});
		JButton Triangle = new JButton(sTriangle);
		Triangle.setBounds(0, 400, 75, 50);
		Triangle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.unSelect();
				draw.setType(7);
			}
		});
		ImageIcon c = new ImageIcon("bucket.png");
		JButton color = new JButton(c);
		color.setBounds(0, 450, 75, 50);
		color.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Color color = JColorChooser.showDialog(null, "Choose a Color",
						Color.BLACK);
				if (draw.getType() == -1)
				{
					draw.changeSelected(color);
				}
				else
				{
					draw.setColor(color);
				}
			}
		});
		JButton select = new JButton(selection);
		select.setBounds(0, 500, 75, 50);
		select.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.unSelect();
				draw.setType(-1);
			}
		});
		JButton undo = new JButton(undoo);
		undo.setBounds(0, 550, 75, 50);
		undo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.undo();
			}
		});
		JButton redo = new JButton(redoo);
		redo.setBounds(0, 600, 75, 50);
		redo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.redo();
			}
		});
		JButton delete = new JButton("delete");
		delete.setBounds(925, 0, 75, 50);
		delete.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				draw.delete();
			}
		});
		JButton saveXML = new JButton("SaveXML");
		saveXML.setBounds(925, 50, 75, 50);
		saveXML.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				JFileChooser saveFile = new JFileChooser();
				saveFile.showSaveDialog(null);
				String filename = saveFile.getSelectedFile().getAbsolutePath();
				draw.saveXML(filename);
			}
		});
		JButton saveJSON = new JButton("saveJSON");
		saveJSON.setBounds(925, 100, 75, 50);
		saveJSON.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				JFileChooser saveFile = new JFileChooser();
				saveFile.showSaveDialog(null);
				String filename = saveFile.getSelectedFile().getAbsolutePath();
				draw.saveJSON(filename);
			}
		});
		JButton loadXML = new JButton("loadXML");
		loadXML.setBounds(925, 200, 75, 50);
		loadXML.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				JFileChooser saveFile = new JFileChooser();
				saveFile.showOpenDialog(null);
				String filename = saveFile.getSelectedFile().getAbsolutePath();
				draw.loadXML(filename);
			}
		});
		JButton loadJSON = new JButton("loadJSON");
		loadJSON.setBounds(925, 150, 75, 50);
		loadJSON.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JFileChooser openFile = new JFileChooser();
				openFile.showOpenDialog(null);
				String filename = openFile.getSelectedFile().getAbsolutePath();
				draw.loadJSON(filename);
			}
		});
		JButton loadClass = new JButton("loadClass");
		loadClass.setBounds(925, 250, 75, 50);
		loadClass.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				JFileChooser openFile = new JFileChooser();
				openFile.showOpenDialog(null);
				String filename = openFile.getSelectedFile().getAbsolutePath();
				if (filename.contains(".class"))
				{
					filename = filename.substring(0, filename.indexOf('.'));
					lastaddedat++;
					String[] arr = filename.split("\\\\");
//					 String[] arr = filename.split("/");
					String className = arr[arr.length - 1];
					mymap.put(className, lastaddedat);
					filename = "";
					for (int i = 0; i < arr.length - 1; i++)
					{
						filename = filename + arr[i] + "\\\\";
//						 filename = filename + arr[i] + "/";
					}
					draw.dynamicLoad(filename, className);
					JButton nB = new JButton(className);
					int x = lastaddedat;
					nB.setBounds(925,
							300 + 50 * (lastaddedat - 8), 75, 50);
					all.add(nB);
					nB.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							String name = arg0.getActionCommand();
							draw.unSelect();
							draw.setType(mymap.get(name));
						}
					});
					// for (j = 0; j < bb.size(); j++)
					// {

					// }
					// bb.get(lastaddedat-8).addActionListener(new
					// ActionListener()
					// {
					//
					// @Override
					// public void actionPerformed(ActionEvent e)
					// {
					// // TODO Auto-generated method stub
					// draw.setType(lastaddedat);
					// }
					// });

					// load the new Class
				}
				else
				{
					JOptionPane.showMessageDialog(null, "INVALID CLASS TO ADD");
				}
			}
		});
		all.add(loadClass);
		// buttonshorizontal.add(delete);
		buttons.add(select);
		buttons.add(color);
		buttons.add(clear);
		buttons.add(Line);
		buttons.add(Circle);
		buttons.add(Rectangle);
		buttons.add(IscTriangle);
		buttons.add(Square);
		buttons.add(RightTriangle);
		buttons.add(Ellipse);
		buttons.add(Triangle);
		buttons.add(color);
		buttons.add(redo);
		buttons.add(undo);
		all.add(buttons);
		all.add(delete);
		all.add(saveXML);
		all.add(saveJSON);
		all.add(loadJSON);
		all.add(loadXML);
		draw.setLayout(null);
		// draw.setSize(prefer);
		draw.setBounds(75, 0, 850, 650);
		draw.setBackground(Color.WHITE);
		all.add(draw);

	}
}
