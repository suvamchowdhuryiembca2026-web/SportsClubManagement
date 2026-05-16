package db;
import java.sql.Connection;
import java.sql.DriverManager;
    
public class DbConnection
{
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";

    private static final String USER = "hr";
 
    private static final String PASSWORD = "hr";

    public static Connection getConnection()
    {
        Connection con = null;

        try
        {
            Class.forName
            ("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection
            (
                URL,
                USER,
                PASSWORD
            );

            System.out.println
            ("Database Connected");
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return con;
    }
}

