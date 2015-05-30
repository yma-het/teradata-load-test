import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
//import java.sql.*;
import java.util.logging.*;
 
public class EmulateConnections2 {
 
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
                 
                    String request = "SELECT customer_name.first_name,\n"
                                     + "             customer_name.last_name,\n"
                                     + "             customer_name.postal_code,\n"
                                     + "             customer_name.state_code,\n"
                                     + "             customer_name.city_name,\n"
                                     + "             customer_name.street_name,\n"
                                     + "             customer_name.street_nbr,\n"
                                     + "             cast50_100.ending_balance,\n"
                                     + "             cast50_100.acct_nbr\n"
                                     + "FROM (\n"
                                     + "    SELECT age50.cust_id, age50.age, acct_aggregated.ending_balance, acct_aggregated.acct_nbr\n"
                                     + "    FROM (\n"
                                     + "         SELECT customer.cust_id, customer.age\n"
                                     + "         FROM financial.customer\n"
                                     + "         WHERE customer.age >= 50) AS age50 \n"
                                     + "    INNER JOIN (\n"
                                     + "         SELECT checking_acct.ending_balance, checking_acct.cust_id, checking_acct.acct_nbr\n"
                                     + "         FROM financial.checking_acct \n"
                                     + "         WHERE checking_acct.account_active = 'Y' AND checking_acct.ending_balance >=100\n"
                                     + "         UNION\n"
                                     + "         SELECT credit_acct.ending_balance, credit_acct.cust_id, credit_acct.acct_nbr\n"
                                     + "         FROM  financial.credit_acct\n"
                                     + "         WHERE credit_acct.account_active = 'Y' AND credit_acct.ending_balance >=100\n"
                                     + "         UNION\n"
                                     + "         SELECT savings_acct.ending_balance, savings_acct.cust_id, savings_acct.acct_nbr\n"
                                     + "         FROM  financial.savings_acct\n"
                                     + "         WHERE savings_acct.account_active = 'Y' AND  savings_acct.ending_balance >=100\n"
                                     + "         ) AS acct_aggregated\n"
                                     + "    ON age50.cust_id = acct_aggregated.cust_id) AS cast50_100\n"
                                     + " LEFT JOIN  financial.customer_name ON customer_name.cust_id = cast50_100.cust_id\n"
                                     + " ORDER BY cast50_100.ending_balance;\n";



                    ResultSet result1 = statement.executeQuery(request);
                    System.out.println("Выводим statement");
                    while (result1.next()) {
                        System.out.println("Номер в выборке #" + result1.getRow()
                                //+ "\t Номер в базе #" + result1.getInt("street_name"));
                                + "\t" + result1.getString("last_name"));
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
