import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import org.slf4j.LoggerFactory;

public class Main
{
	public static void main(String[] args)
	{
		org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);
//		try
//		{
//			Class.forName("DriverIF");
//		}
//		catch (ClassNotFoundException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		DriverIF driver = new DriverIF();
		Properties prop = new Properties();
		prop.put("username", "root");
		prop.put("password", "root");
		try
		{
			ConnectionIF server = driver.connect(System.getenv("DBMS"), prop);
//			Connection server = DriverManager.getConnection(System.getenv("DBMS"), prop);
			StatementIF stServer = (StatementIF) server.createStatement();
			Scanner in = new Scanner(System.in);
			String line_read;
			line_read = in.nextLine();
			while (line_read.length() == 0)
				line_read = in.nextLine();
			// stServer.addBatch("use omar ;");
			String input = "";
			while (true)
			{
				input += line_read;
				input += " ";
				input = StatementIF.edit(input);
				if (line_read.contains(";"))
				{
					try
					{
						input = input.substring(0, input.length() - 1);
						if (isClose(input))
						{
							server.close();
							stServer.close();
							break;
						}
						else if (isSelect(input))
						{
							ResultSetIF rs = stServer.executeQuery(input);
							ResultSetMetaDataIF rsm = rs.getMetaData();
							rs.next();
							while (!rs.isAfterLast())
							{
								for (int j = 1; j <= rsm.getColumnCount(); j++)
									System.out.print(rs.getString(j) + " ");
								System.out.println();
								rs.next();
							}
						}
						else
						{
							stServer.execute(input);
						}
						input = "";
					}
					catch (SQLException e)
					{
						// TODO Auto-generated catch block
						log.info("");
						
						System.out.println("Error");
						input = "";
					}
				}
				line_read = in.nextLine();
				while (line_read.length() == 0)
					line_read = in.nextLine();
			}
			in.close();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			log.info("");
			
			System.out.println("Error");
		}
	}

	/*
	 * public static boolean isUse(String str) { int ind = str.indexOf(" "); if
	 * (ind == -1) { return false; } String name = str.substring(0, ind); return
	 * name.equalsIgnoreCase("use"); }
	 * public static boolean isCreateDatabase(String str) { int ind =
	 * str.indexOf(" "); if (ind == -1) { return false; } String name =
	 * str.substring(0, ind); String rest = str.substring(ind+1, str.length());
	 * if(name.equalsIgnoreCase("create")) { ind = rest.indexOf(" "); if (ind ==
	 * -1) { return false; } name = rest.substring(0, ind); return
	 * name.equalsIgnoreCase("database"); } else { return false; } }
	 */

	public static boolean isSelect(String str)
	{
		int ind = str.indexOf(" ");
		if (ind == -1)
		{
			return false;
		}
		String name = str.substring(0, ind);
		return name.equalsIgnoreCase("select");
	}

	public static boolean isClose(String str)
	{
		int ind = str.indexOf(";");
		if (ind == -1)
		{
			return false;
		}
		String name = str.substring(0, ind);
		return (name.equalsIgnoreCase("close") || name.equalsIgnoreCase("close ") || name.equalsIgnoreCase(" close") || 
				name.equalsIgnoreCase(" close "));
	}
}
