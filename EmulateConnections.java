import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
//import java.sql.*;
import java.util.logging.*;
 
public class EmulateConnections {
 
	public static void main(String[] argv) {
 
		System.out.println("-------- Teradata Database "
				+ "JDBC Connection Testing ------------");
 
		try {
 
			Class.forName("com.teradata.jdbc.TeraDriver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your Teradata Database JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
 
		}
 
		System.out.println("Teradata Database JDBC Driver Registered!");
 
		Connection connection = null;
 
		try {
 
			connection = DriverManager.getConnection(
                                        "jdbc:teradata://localhost/financial", "dbc",
					"dbc");
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
 
		}
 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}

                try {
             
                    Statement statement = null;

                    statement = connection.createStatement();
                 
                    String request = "SELECT customer_name.cust_id, customer_name.first_name, "
                                     + "trans.tran_total FROM\n"
                                     + "financial.customer_name  LEFT JOIN financial.trans "
                                     + "ON trans.cust_id = customer_name.cust_id;";



                    ResultSet result1 = statement.executeQuery(request);
                    System.out.println("Выводим statement");
                    while (result1.next()) {
                        System.out.println("Номер в выборке #" + result1.getRow()
                                + "\t Номер в базе #" + result1.getInt("cust_id")
                                + "\t" + result1.getString("first_name"));
                    }

                } catch (Exception ex) {
                    //выводим наиболее значимые сообщения
                    Logger.getLogger(EmulateConnections.class.getName()).log(Level.SEVERE, null, ex);
                  //finally {
                  //  if (connection != null) {
                  //      try {
                  //         connection.close();
                  //      } catch (SQLException ex) {
                  //          Logger.getLogger(EmulateConnections.class.getName()).log(Level.SEVERE, null, ex);
                 //     }
            //}
        }
	}
 
}
