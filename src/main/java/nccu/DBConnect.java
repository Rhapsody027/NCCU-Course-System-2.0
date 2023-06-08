package nccu;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnect {

    protected static Connection conn;
    private static String sever = "jdbc:mysql://localhost/";
    private static String dbName = "nccu";
    private static String user = "vscode";
    private static String password = "2003";

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(sever + dbName, user, password);
            System.out.println("Connect!");

            return conn;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ObservableList<Course> getCourseData() {
        try {
            Connection conn = connect();
            ObservableList<Course> list = FXCollections.observableArrayList();
            String sql = "SELECT * FROM nccu LIMIT 149";
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                list.add(new Course(rs.getString("name").strip(),
                        rs.getString("time").strip(),
                        rs.getString("pro").strip(),
                        Integer.parseInt(rs.getString("sweet")),
                        Integer.parseInt(rs.getString("cool")),
                        rs.getString("att").strip(), rs.getString("id")));
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}