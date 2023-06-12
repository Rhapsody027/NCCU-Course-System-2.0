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

            return conn;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // * maybe get data after search?
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

    public static Course getCourseByID(String id) {
        try {
            Connection conn = connect();
            String sql = "SELECT * FROM nccu WHERE id = '" + id + "'";
            PreparedStatement stat;
            stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();

            Course course = null;

            while (rs.next()) {
                course = new Course(rs.getString("name").strip(),
                        rs.getString("time").strip(),
                        rs.getString("pro").strip(),
                        Integer.parseInt(rs.getString("sweet")),
                        Integer.parseInt(rs.getString("cool")),
                        rs.getString("att").strip(), rs.getString("id"));
            }

            return course;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void saveWishList(String id, String name, String time, String pro) {
        try (Connection connection = DBConnect.connect();
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO wishList " +
                                "(id, name, time, pro) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, time);
            statement.setString(4, pro);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}