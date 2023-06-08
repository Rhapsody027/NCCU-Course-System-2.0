package nccu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.*;

public class Comment extends DBConnect {

    public static void insertMessage(String content) {
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO comment (content) VALUES (?)");
            statement.setString(1, content);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // * add a argument for course id
    public static ListView<String> updateMessageList(ListView<String> comment_list) {
        comment_list.getItems().clear();

        try {
            Statement statement = conn.createStatement();

            // * change this to get filtered comment
            ResultSet resultSet = statement.executeQuery("SELECT * FROM comment");

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String content = resultSet.getString("content");
                String date = resultSet.getString("post_at").substring(0, 10);

                comment_list.getItems().add(date + "\t" + username + ": " + content);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comment_list;
    }
}
