package nccu;

import java.sql.*;

public class DBConnect {

    private static Connection conn;
    private static String url = "jdbc:mysql://localhost/test";
    private static String user = "vscode";
    private static String password = "2003";

    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        conn = DriverManager.getConnection(url, user, password);

        return conn;
    }

    public static Connection getConnection() throws SQLException {
        if (conn != null && !conn.isClosed())
            return conn;

        connect();
        return conn;

    }
}