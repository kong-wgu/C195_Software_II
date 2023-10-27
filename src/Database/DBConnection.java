package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/***
 * This class is a Data Access Object that will validate the connection of the database.
 */
public class DBConnection {

    private static final String databaseName = "client_schedule";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + databaseName + "?connectionTimeZone = SERVER";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    private static final String mysql_jdbc_driver = "com.mysql.cj.jdbc.Driver";
    public static Connection conn;

    /***
     * Gets and make the connection to the database open
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static Connection makeConnection() throws ClassNotFoundException, SQLException, Exception{

        try{
            Class.forName(mysql_jdbc_driver);
            conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
        }
        catch (SQLException e){
            throw new SQLException("Failed to open the DB Connection");
        }
        return conn;
    }

    /***
     * closes the connection to the database
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void closeConnection() throws  ClassNotFoundException, SQLException, Exception{
        try{
            conn.close();
            conn = null;
        } catch (SQLException e){
            throw new SQLException("Failed to close the DB Connection");
        }

    }

    /***
     * Returns the object connection of the currrent connection to the database.
     * @return
     */
    public static Connection getCurrentConnection() {

        return conn;

    }


}
