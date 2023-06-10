package nccu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.*;

public class Comment extends DBConnect {

    // * find a better way (sweet, like...) to make comments
    public static void insertMessage(String courseID, String content) {
        try {
            String id = courseID;

            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO comment (id, content) VALUES (?, ?)");
            statement.setString(1, id);
            statement.setString(2, content);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ListView<String> updateMessageList(String courseID,
            ListView<String> comment_list) {

        comment_list.getItems().clear();

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM comment WHERE id LIKE '%" + courseID + "%'");

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
