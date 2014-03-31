import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;




//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ShapeArrayList extends JPanel implements MouseMotionListener,
		MouseListener
{
	static int moving = -1;
	static int order = 0;
	static int resizing = -1;
	static int prevX, prevY;
	static boolean drawScalene = false;
	private int x1, x2, y1, y2, type;
	private Stack<ArrayList<Shape>> inn;
	private Stack<ArrayList<Shape>> outt;
	private Color color = Color.BLACK;
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private static ArrayList<Class> classes = new ArrayList<Class>();
	@SuppressWarnings("rawtypes")
	private static ArrayList<Constructor[]> constructors = new ArrayList<Constructor[]>();

	public ShapeArrayList()
	{
		addMouseListener(this);
		addMouseMotionListener(this);
		type = 0;

		try
		{
			classes.add(0, Class.forName("Line"));// 0
			classes.add(1, Class.forName("Circle"));// 1
			classes.add(2, null);// 2
			classes.add(3, Class.forName("IscTriangle"));// 3
			classes.add(4, Class.forName("Square"));// 4
			classes.add(5, Class.forName("RightTriangle"));// 5
			classes.add(6, Class.forName("Ellipse"));// 6
			classes.add(7, null);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < classes.size(); i++)
		{
			if (i == 7 || i == 2)
			{
				constructors.add(i, null);
				continue;
			}
			constructors.add(i, classes.get(i).getConstructors());
			System.out.println(constructors.get(i)[0].toString());
		}
		inn = new Stack<ArrayList<Shape>>();
		inn.push(new ArrayList<Shape>());
		outt = new Stack<ArrayList<Shape>>();
	}

	public void setColor(Color cc)
	{
		color = cc;
	}

	public void clearAll()
	{
		inn.push(new ArrayList<Shape>());
		repaint();
	}

	public void undo()
	{
		if (inn.empty() == false)
		{
			outt.push(inn.pop());
			repaint();
		}
	}

	public void redo()
	{
		if (outt.empty() == false)
		{
			inn.push(outt.pop());
			repaint();
		}
	}

	public void normal()
	{
		while (outt.empty() == false)
		{
			outt.pop();
		}
	}

	public void setType(int type)
	{
		normal();
		this.type = type;
	}

	public int getType()
	{
		return type;
	}

	public void paint(Graphics g2d)
	{
		super.paintComponent(g2d);
		if (inn.empty() == false)
		{
			for (int i = 0; i < inn.peek().size(); i++)
			{
				inn.peek().get(i).paint(g2d);
			}
		}
	}

	public void unSelect()
	{
		boolean anyChange = false;
		ArrayList<Shape> toAdd = new ArrayList<Shape>();
		if (inn.empty() == false)
		{
			for (int i = 0; i < inn.peek().size(); i++)
			{
				Shape a = inn.peek().get(i);
				if (a.getType() == 7)
				{
					toAdd.add(new Triangle(a.getX1(), a.getY1(), a.getX2(), a
							.getY2(), a.getX3(), a.getY3(), a.getColor(), 7));
				}
				else
				{
					try
					{
						Shape toadd = (Shape) constructors.get(a.getType())[0]
								.newInstance(a.getX1(), a.getY1(), a.getX2(),
										a.getY2(), a.getColor(), a.getType());
//						toadd.setType(a.getType());
						toAdd.add(toadd);
					}
					catch (InstantiationException | IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (a.getSelected() == true)
				{
					anyChange = true;
				}
			}
		}
		if (anyChange == true)
		{
			inn.push(toAdd);
		}
		repaint();
	}

	public void changeSelected(Color cc)
	{
		boolean anyChange = false;
		ArrayList<Shape> toAdd = new ArrayList<Shape>();
		if (inn.empty() == false)
		{
			for (int i = 0; i < inn.peek().size(); i++)
			{
				Shape a = inn.peek().get(i);
				if (a.getType() == 7)
				{
					toAdd.add(new Triangle(a.getX1(), a.getY1(), a.getX2(), a
							.getY2(), a.getX3(), a.getY3(), a.getColor(), 7));
				}
				else
				{
					try
					{
						Shape toadd = (Shape) constructors.get(a.getType())[0]
								.newInstance(a.getX1(), a.getY1(), a.getX2(),
										a.getY2(), a.getColor(), a.getType());
//						toadd.setType(a.getType());
						toAdd.add(toadd);
					}
					catch (InstantiationException | IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				toAdd.get(toAdd.size() - 1).setSelected(a.getSelected());
			}
		}
		inn.push(toAdd);
		for (int i = 0; i < inn.peek().size(); i++)
		{
			if (inn.peek().get(i).getSelected())
			{
				anyChange = true;
				inn.peek().get(i).setColor(cc);
				inn.peek().get(i).changeSelected();
			}
		}
		if (anyChange == false)
		{
			inn.pop();
		}
		repaint();
	}

	public void delete()
	{
		for (int k = 0; k < inn.peek().size(); k++)
		{
			if (inn.peek().get(k).getSelected())
			{
				ArrayList<Shape> toAdd = new ArrayList<Shape>();
				if (inn.empty() == false)
				{
					for (int i = 0; i < inn.peek().size(); i++)
					{
						Shape a = inn.peek().get(i);
						if (a.getType() == 7)
						{
							toAdd.add(new Triangle(a.getX1(), a.getY1(), a
									.getX2(), a.getY2(), a.getX3(), a.getY3(),
									a.getColor(), 7));
						}
						else
						{
							try
							{
								Shape toadd = (Shape) constructors.get(a
										.getType())[0].newInstance(a.getX1(),
										a.getY1(), a.getX2(), a.getY2(),
										a.getColor(), a.getType());
//								toadd.setType(a.getType());
								toAdd.add(toadd);
							}
							catch (InstantiationException
									| IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						toAdd.get(toAdd.size() - 1)
								.setSelected(a.getSelected());
					}
					inn.push(toAdd);
				}
				inn.peek().remove(k);
				repaint();
				break;
			}

		}
	}

	public void dynamicLoad(String filename, String className)
	{
		File file = new File(filename);
		try
		{
			// Convert File to a URL
			URL url = file.toURI().toURL(); // file:/c:/myclasses/
			URL[] urls = new URL[] { url };

			// Create a new class loader with the directory
			@SuppressWarnings("resource")
			ClassLoader cl = new URLClassLoader(urls);
			// Load in the class; MyClass.class should be located in
			// the directory file:/c:/myclasses/com/mycompany
			@SuppressWarnings({ "unused", "rawtypes" })
			Class cls = cl.loadClass(className);
			classes.add(cls);
			constructors.add(classes.get(classes.size() - 1).getConstructors());
		}
		catch (MalformedURLException e)
		{
		}
		catch (ClassNotFoundException e)
		{
		}
	}

	@SuppressWarnings("unchecked")
	public void saveJSON(String filename)
	{
		JSONObject obj = new JSONObject();
		obj.put("size", Integer.toString(inn.peek().size()));
		for (int i = 0; i < inn.peek().size(); i++)
		{
			obj.put(Integer.toString(i) + "type",
					Integer.toString(inn.peek().get(i).getType()));
			obj.put(Integer.toString(i) + "x1",
					Integer.toString(inn.peek().get(i).getX1()));
			obj.put(Integer.toString(i) + "x2",
					Integer.toString(inn.peek().get(i).getX2()));
			obj.put(Integer.toString(i) + "y1",
					Integer.toString(inn.peek().get(i).getY1()));
			obj.put(Integer.toString(i) + "y2",
					Integer.toString(inn.peek().get(i).getY2()));
			obj.put(Integer.toString(i) + "color",
					Integer.toString(inn.peek().get(i).getColor().getRGB()));
			if (inn.peek().get(i).getType() == 7)
			{
				obj.put(Integer.toString(i) + "x3",
						Integer.toString(inn.peek().get(i).getX3()));
				obj.put(Integer.toString(i) + "y3",
						Integer.toString(inn.peek().get(i).getY3()));
			}
		}
		try
		{
			FileWriter file = new FileWriter(filename);
			file.write(obj.toJSONString());
			file.flush();
			file.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void loadJSON(String filename)
	{
		JSONParser parser = new JSONParser();
		try
		{
			Object obj2 = parser.parse(new FileReader(filename));
			JSONObject jsonObject = (JSONObject) obj2;
			int size = Integer.parseInt((String) jsonObject.get("size"));
			ArrayList<Shape> toAdd = new ArrayList<Shape>();
			inn = new Stack<ArrayList<Shape>>();
			outt = new Stack<ArrayList<Shape>>();
			for (int i = 0; i < size; i++)
			{
				int type = Integer.parseInt((String) jsonObject.get(Integer
						.toString(i) + "type"));
				int x1 = Integer.parseInt((String) jsonObject.get(Integer
						.toString(i) + "x1"));
				int x2 = Integer.parseInt((String) jsonObject.get(Integer
						.toString(i) + "x2"));
				int y1 = Integer.parseInt((String) jsonObject.get(Integer
						.toString(i) + "y1"));
				int y2 = Integer.parseInt((String) jsonObject.get(Integer
						.toString(i) + "y2"));
				Color c = new Color(Integer.parseInt((String) jsonObject
						.get(Integer.toString(i) + "color")));
				// if (type == 0)
				// toAdd.add(new Line(x1, y1, x2, y2, c));
				// else if (type == 1)
				// toAdd.add(new Circle(x1, y1, x2, y2, c));
				// else if (type == 2)
				// toAdd.add(new Rectangle(x1, y1, x2, y2, c));
				// else if (type == 3)
				// toAdd.add(new IscTriangle(x1, y1, x2, y2, c));
				// else if (type == 4)
				// toAdd.add(new Square(x1, y1, x2, y2, c));
				// else if (type == 5)
				// toAdd.add(new RightTriangle(x1, y1, x2, y2, c));
				// else if (type == 6)
				// toAdd.add(new Ellipse(x1, y1, x2, y2, c));
				if (type == 7)
				{
					int x3 = Integer.parseInt((String) jsonObject.get(Integer
							.toString(i) + "x3"));
					int y3 = Integer.parseInt((String) jsonObject.get(Integer
							.toString(i) + "y3"));
					toAdd.add(new Triangle(x1, y1, x2, y2, x3, y3, c, 7));
				}
				else
				{
					try
					{
						Shape toadd = (Shape) constructors.get(type)[0]
								.newInstance(x1, y1, x2, y2, c, type);
//						toadd.setType(type);
						toAdd.add(toadd);
					}
					catch (InstantiationException | IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			inn.push(toAdd);
			repaint();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveXML(String filename)
	{
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("shapes");
			doc.appendChild(rootElement);

			for (int i = 0; i < inn.peek().size(); i++)
			{
				// Shape elements
				Element Shape = doc.createElement("Shape");
				rootElement.appendChild(Shape);

				// set attribute to Shape element
				Attr attr = doc.createAttribute("id");
				attr.setValue(Integer.toString(i));
				Shape.setAttributeNode(attr);

				// shorten way
				// Shape.setAttribute("id", "1");

				Element type = doc.createElement("type");
				type.appendChild(doc.createTextNode(Integer.toString(inn.peek()
						.get(i).getType())));
				Shape.appendChild(type);

				Element x1 = doc.createElement("x1");
				x1.appendChild(doc.createTextNode(Integer.toString(inn.peek()
						.get(i).getX1())));
				Shape.appendChild(x1);

				Element y1 = doc.createElement("y1");
				y1.appendChild(doc.createTextNode(Integer.toString(inn.peek()
						.get(i).getY1())));
				Shape.appendChild(y1);

				Element x2 = doc.createElement("x2");
				x2.appendChild(doc.createTextNode(Integer.toString(inn.peek()
						.get(i).getX2())));
				Shape.appendChild(x2);

				Element y2 = doc.createElement("y2");
				y2.appendChild(doc.createTextNode(Integer.toString(inn.peek()
						.get(i).getY2())));
				Shape.appendChild(y2);

				Element color = doc.createElement("color");
				color.appendChild(doc.createTextNode(Integer.toString(inn
						.peek().get(i).getColor().getRGB())));
				Shape.appendChild(color);

				if (inn.peek().get(i).type == 7)
				{
					Element x3 = doc.createElement("x3");
					x3.appendChild(doc.createTextNode(Integer.toString(inn
							.peek().get(i).getX3())));
					Shape.appendChild(x3);
					Element y3 = doc.createElement("y3");
					y3.appendChild(doc.createTextNode(Integer.toString(inn
							.peek().get(i).getY3())));
					Shape.appendChild(y3);
				}

			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
		}
	}

	public void loadXML(String filename)
	{
		try
		{
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Shape");

			ArrayList<Shape> toAdd = new ArrayList<Shape>();
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;
					int x1, x2, y1, y2;

					x1 = Integer.parseInt(eElement.getElementsByTagName("x1")
							.item(0).getTextContent());
					x2 = Integer.parseInt(eElement.getElementsByTagName("x2")
							.item(0).getTextContent());
					y1 = Integer.parseInt(eElement.getElementsByTagName("y1")
							.item(0).getTextContent());
					y2 = Integer.parseInt(eElement.getElementsByTagName("y2")
							.item(0).getTextContent());
					Color c = new Color(Integer.parseInt(eElement
							.getElementsByTagName("color").item(0)
							.getTextContent()));
					int type = Integer.parseInt(eElement.getElementsByTagName("type").item(0).getTextContent());
					if(type == 7)
					{
						int x3, y3;
						x3 = Integer.parseInt(eElement
								.getElementsByTagName("x3").item(0)
								.getTextContent());
						y3 = Integer.parseInt(eElement
								.getElementsByTagName("y3").item(0)
								.getTextContent());
						toAdd.add(new Triangle(x1, y1, x2, y2, x3, y3,
								color, 7));
					}
					else
					{
						try
						{
							Shape toadd = (Shape) constructors.get(type)[0]
									.newInstance(x1, y1, x2, y2, c, type);
//							toadd.setType(type);
							toAdd.add(toadd);
						}
						catch (InstantiationException | IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
//					switch (type)
//					{
//						case 0:
//							toAdd.add(new Line(x1, y1, x2, y2, c));
//							break;
//						case 1:
//							toAdd.add(new Circle(x1, y1, x2, y2, c));
//							break;
//						case 2:
//							toAdd.add(new Rectangle(x1, y1, x2, y2, c));
//							break;
//						case 3:
//							toAdd.add(new IscTriangle(x1, y1, x2, y2, c));
//							break;
//						case 4:
//							toAdd.add(new Square(x1, y1, x2, y2, c));
//							break;
//						case 5:
//							toAdd.add(new RightTriangle(x1, y1, x2, y2, c));
//							break;
//						case 6:
//							toAdd.add(new Ellipse(x1, y1, x2, y2, c));
//							break;
//						case 7:
//							int x3, y3;
//							x3 = Integer.parseInt(eElement
//									.getElementsByTagName("x3").item(0)
//									.getTextContent());
//							y3 = Integer.parseInt(eElement
//									.getElementsByTagName("y3").item(0)
//									.getTextContent());
//							toAdd.add(new Triangle(x1, y1, x2, y2, x3, y3,
//									color));
//							break;
//					}
				}
			}
			inn = new Stack<ArrayList<Shape>>();
			outt = new Stack<ArrayList<Shape>>();
			inn.add(toAdd);
			repaint();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
		if (type == -1)
		{
			boolean anyChange = false;
			ArrayList<Shape> toAdd = new ArrayList<Shape>();
			if (inn.empty() == false)
			{
				for (int i = 0; i < inn.peek().size(); i++)
				{
					Shape a = inn.peek().get(i);
					if (a.getType() == 7)
					{
						toAdd.add(new Triangle(a.getX1(), a.getY1(), a.getX2(),
								a.getY2(), a.getX3(), a.getY3(), a.getColor(), 7));
					}
					else
					{
						try
						{
							Shape toadd = (Shape) constructors.get(a.getType())[0]
									.newInstance(a.getX1(), a.getY1(),
											a.getX2(), a.getY2(), a.getColor(), a.getType());
//							toadd.setType(a.getType());
							toAdd.add(toadd);
						}
						catch (InstantiationException | IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					toAdd.get(toAdd.size() - 1).setSelected(a.getSelected());
				}
			}
			inn.push(toAdd);

			for (int i = inn.peek().size() - 1; i >= 0; i--)
			{
				boolean flag = inn.peek().get(i)
						.checkSelected(e.getX(), e.getY());
				if (flag == true)
				{
					unSelect();
					anyChange = true;
					inn.peek().get(i).changeSelected();
					break;
				}
			}
			if (anyChange == false)
			{
				inn.pop();
			}
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		x1 = (e.getX());
		y1 = (e.getY());
		x2 = x1;
		y2 = y1;
		if ((type != -1) || (type == -1 && moving != -1))
		{
			ArrayList<Shape> toAdd = new ArrayList<Shape>();
			if (inn.empty() == false)
			{
				for (int i = 0; i < inn.peek().size(); i++)
				{
					Shape a = inn.peek().get(i);
					if (a.getType() == 7)
					{
						toAdd.add(new Triangle(a.getX1(), a.getY1(), a.getX2(),
								a.getY2(), a.getX3(), a.getY3(), a.getColor(), 7));
					}
					else
					{
						try
						{
							Shape toadd = (Shape) constructors.get(a.getType())[0]
									.newInstance(a.getX1(), a.getY1(),
											a.getX2(), a.getY2(), a.getColor(), a.getType());
//							toadd.setType(a.getType());
							toAdd.add(toadd);
						}
						catch (InstantiationException | IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					toAdd.get(toAdd.size() - 1).setSelected(a.getSelected());
				}
			}
			inn.push(toAdd);
		}

		if (type == 7)
		{
			inn.peek().add(new Triangle(x1, y1, x2, y2, x1, y1, color, 7));
			if (drawScalene == true)
			{
				drawScalene = false;
			}
		}
		else if (type != -1)
		{
			try
			{
				Shape toadd = (Shape) constructors.get(type)[0].newInstance(x1,
						y1, x2, y2, color, type);
//				toadd.setType(type);
				inn.peek().add(toadd);
				repaint();
			}
			catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (moving != -1)
		{
			prevX = e.getX();
			prevY = e.getY();
		}
		// if (type == 0 || type == 1 || type == 2 || type == 3 || type == 4
		// || type == 5 || type == 6 || type == 7
		// || (type == -1 && moving != -1))
		// {
		// ArrayList<Shape> toAdd = new ArrayList<Shape>();
		// if (inn.empty() == false)
		// {
		// for (int i = 0; i < inn.peek().size(); i++)
		// {
		// Shape a = inn.peek().get(i);
		// if (a.getType() == 0)
		// toAdd.add(new Line(a.getX1(), a.getY1(), a.getX2(), a
		// .getY2(), a.getColor()));
		// else if (a.getType() == 1)
		// toAdd.add(new Circle(a.getX1(), a.getY1(), a.getX2(), a
		// .getY2(), a.getColor()));
		// else if (a.getType() == 2)
		// toAdd.add(new Rectangle(a.getX1(), a.getY1(),
		// a.getX2(), a.getY2(), a.getColor()));
		// else if (a.getType() == 3)
		// toAdd.add(new IscTriangle(a.getX1(), a.getY1(), a
		// .getX2(), a.getY2(), a.getColor()));
		// else if (a.getType() == 4)
		// toAdd.add(new Square(a.getX1(), a.getY1(), a.getX2(), a
		// .getY2(), a.getColor()));
		// else if (a.getType() == 5)
		// toAdd.add(new RightTriangle(a.getX1(), a.getY1(), a
		// .getX2(), a.getY2(), a.getColor()));
		// else if (a.getType() == 6)
		// toAdd.add(new Ellipse(a.getX1(), a.getY1(), a.getX2(),
		// a.getY2(), a.getColor()));
		// else if (a.getType() == 7)
		// toAdd.add(new Triangle(a.getX1(), a.getY1(), a.getX2(),
		// a.getY2(), a.getX3(), a.getY3(), a.getColor()));
		// toAdd.get(toAdd.size() - 1).setSelected(a.getSelected());
		// }
		// }
		// inn.push(toAdd);
		// }
		// if (type == 0)
		// {
		// inn.peek().add(new Line(x1, y1, x2, y2, color));
		// }
		// else if (type == 1)
		// {
		// inn.peek().add(new Circle(x1, y1, x2, y2, color));
		// }
		// else if (type == 2)
		// {
		// inn.peek().add(new Rectangle(x1, y1, x2, y2, color));
		// }
		// else if (type == 3)
		// {
		// inn.peek().add(new IscTriangle(x1, y1, x2, y2, color));
		// }
		// else if (type == 4)
		// {
		// inn.peek().add(new Square(x1, y1, x2, y2, color));
		// }
		// else if (type == 5)
		// {
		// inn.peek().add(new RightTriangle(x1, y1, x2, y2, color));
		// }
		// else if (type == 6)
		// {
		// inn.peek().add(new Ellipse(x1, y1, x2, y2, color));
		// }
		// else if (type == 7)
		// {
		// inn.peek().add(new Triangle(x1, y1, x2, y2, x1, y1, color));
		// if (drawScalene == true)
		// {
		// drawScalene = false;
		// }
		// }
		// if (moving != -1)
		// {
		// prevX = e.getX();
		// prevY = e.getY();
		// }
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub
		x2 = (e.getX());
		y2 = (e.getY());
		if (type != -1 && moving == -1 && resizing == -1)
		{
			if (inn.peek().size() != 0)
			{
				inn.peek().remove(inn.peek().size() - 1);
			}
			if (type == 7)
			{
				drawScalene = true;
				inn.peek().add(new Triangle(x1, y1, x2, y2, x1, y1, color, 7));
			}
			else if (type != -1)
			{
				try
				{
					Shape toadd = (Shape) constructors.get(type)[0]
							.newInstance(x1, y1, x2, y2, color, type);
//					toadd.setType(type);
					inn.peek().add(toadd);
				}
				catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			repaint();

		}
		else if (moving != -1) // moving != -1
		{
			inn.peek().get(moving).move(e.getX() - prevX, e.getY() - prevY);
			prevX = e.getX();
			prevY = e.getY();
			repaint();
		}
		else if (resizing != -1)
		{
			inn.peek().get(resizing).resize(order, e.getX(), e.getY());
			repaint();
		}
		// if (moving == -1 && resizing == -1)
		// {
		// if (type == 0)
		// {
		// if (inn.peek().size() != 0)
		// {
		// inn.peek().remove(inn.peek().size() - 1);
		// }
		// inn.peek().add(new Line(x1, y1, x2, y2, color));
		// }
		// else if (type == 1)
		// {
		// if (inn.peek().size() != 0)
		// {
		// inn.peek().remove(inn.peek().size() - 1);
		// }
		// inn.peek().add(new Circle(x1, y1, x2, y2, color));
		// }
		// else if (type == 2)
		// {
		// if (inn.peek().size() != 0)
		// {
		// inn.peek().remove(inn.peek().size() - 1);
		// }
		// inn.peek().add(new Rectangle(x1, y1, x2, y2, color));
		// }
		// else if (type == 3)
		// {
		// if (inn.peek().size() != 0)
		// {
		// inn.peek().remove(inn.peek().size() - 1);
		// }
		// inn.peek().add(new IscTriangle(x1, y1, x2, y2, color));
		// }
		// else if (type == 4)
		// {
		// if (inn.peek().size() != 0)
		// {
		// inn.peek().remove(inn.peek().size() - 1);
		// }
		// inn.peek().add(new Square(x1, y1, x2, y2, color));
		// }
		// else if (type == 5)
		// {
		// if (inn.peek().size() != 0)
		// {
		// inn.peek().remove(inn.peek().size() - 1);
		// }
		// inn.peek().add(new RightTriangle(x1, y1, x2, y2, color));
		// }
		// else if (type == 6)
		// {
		// if (inn.peek().size() != 0)
		// {
		// inn.peek().remove(inn.peek().size() - 1);
		// }
		// inn.peek().add(new Ellipse(x1, y1, x2, y2, color));
		// }
		// else if (type == 7)
		// {
		// if (inn.peek().size() != 0)
		// {
		// inn.peek().remove(inn.peek().size() - 1);
		// }
		// drawScalene = true;
		// inn.peek().add(new Triangle(x1, y1, x2, y2, x1, y1, color));
		// }
		// repaint();
		// }
		// else if (moving != -1) // moving != -1
		// {
		// inn.peek().get(moving).move(e.getX() - prevX, e.getY() - prevY);
		// prevX = e.getX();
		// prevY = e.getY();
		// repaint();
		// }
		// else if (resizing != -1)
		// {
		// inn.peek().get(resizing).resize(order, e.getX(), e.getY());
		// repaint();
		// }

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		// TODO Auto-generated method stub
		if (type == 7 && drawScalene == true)
		{
			if (inn.peek().size() != 0)
			{
				inn.peek().remove(inn.peek().size() - 1);
			}
			int x3 = (e.getX());
			int y3 = (e.getY());
			inn.peek().add(new Triangle(x1, y1, x2, y2, x3, y3, color, 7));
			repaint();
		}
		else if (type == -1)
		{
			int x = e.getX();
			int y = e.getY();
			if (!inn.isEmpty())
			{
				for (int i = 0; i < inn.peek().size(); i++)
				{
					if (inn.peek().get(i).getSelected())
					{
						int temp = inn.peek().get(i).checkCorners(x, y);
						if (temp != 0)
						{
							moving = -1;
							resizing = i;
							order = temp;
							this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));// North
							if (inn.peek().get(i).getType() == 1)
							{
								if (temp == 1)
								{
									this.setCursor(new Cursor(
											Cursor.N_RESIZE_CURSOR));// North
								}
								else
								{
									this.setCursor(new Cursor(
											Cursor.E_RESIZE_CURSOR));// North
								}
							}
							else if (inn.peek().get(i).getType() == 2)
							{
								this.setCursor(new Cursor(
										Cursor.NE_RESIZE_CURSOR));// North
							}
							else if (inn.peek().get(i).getType() == 4)
							{
								if (temp == 1)
								{
									this.setCursor(new Cursor(
											Cursor.N_RESIZE_CURSOR));// North
								}
								else
								{
									this.setCursor(new Cursor(
											Cursor.E_RESIZE_CURSOR));// North
								}
							}
							else if (inn.peek().get(i).getType() == 6)
							{
								if (temp == 1 || temp == 2)
								{
									this.setCursor(new Cursor(
											Cursor.E_RESIZE_CURSOR));// North
								}
								else
								{
									this.setCursor(new Cursor(
											Cursor.N_RESIZE_CURSOR));// North
								}
							}
							else
							{

							}
						}
						else if (inn.peek().get(i).checkBounds(x, y))
						{
							this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
							moving = i;
							resizing = -1;
							order = 0;
						}
						else
						{
							this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							moving = -1;
							resizing = -1;
							order = 0;
						}
						break;
					}
				}
			}
		}
	}
}
