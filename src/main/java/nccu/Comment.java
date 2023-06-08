package nccu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Comment extends Application {
    private final String DB_URL = "jdbc:mysql://localhost:3306/nccu";
    private final String DB_USERNAME = "vscode";
    private final String DB_PASSWORD = "2003";

    private TextField usernameTextField;
    private TextArea contentTextArea;
    private ListView<String> messagesListView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        usernameTextField = new TextField();
        contentTextArea = new TextArea();
        Button postButton = new Button("Post");
        messagesListView = new ListView<>();
        postButton.setOnAction(event -> postMessage());

        VBox root = new VBox(10);
        root.getChildren().addAll(
                new Label("Username:"),
                usernameTextField,
                new Label("Content:"),
                contentTextArea,
                postButton,
                messagesListView);

        updateMessageList();

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void postMessage() {
        String username = usernameTextField.getText();
        String content = contentTextArea.getText();

        // 插入留言到資料庫
        insertMessage(username, content);

        // 清空顯示文字
        usernameTextField.clear();
        contentTextArea.clear();

        // 更新留言列表
        updateMessageList();
    }

    private void insertMessage(String username, String content) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO comment (username, content) VALUES (?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, content);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateMessageList() {
        messagesListView.getItems().clear();

        // 從資料庫取得留言
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM comment")) {
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String content = resultSet.getString("content");
                messagesListView.getItems().add(username + ": " + content);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
