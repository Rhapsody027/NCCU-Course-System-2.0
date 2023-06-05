package nccu;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnect {

    private static Connection conn;
    private static String sever = "jdbc:mysql://localhost/";
    // private static String severPhp = "jdbc:mysql:// 140.119.19.73:3315/";
    private static String dbName = "nccu";
    private static String user = "vscode";
    private static String password = "2003";

    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(sever + dbName, user, password);
            System.out.println("Connect!");

            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static ObservableList<Course> getData() throws SQLException {
        Connection conn = connect();
        ObservableList<Course> list = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM nccu LIMIT 149;";
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                list.add(new Course(rs.getString("name"), rs.getString("time"), rs.getString("pro"),
                        Integer.parseInt(rs.getString("sweet")),
                        Integer.parseInt(rs.getString("cool")),
                        rs.getString("att")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}