package com.example.controleyassinemchimech;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Scene1YMController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onButtonClick(ActionEvent event){
        String username = usernameField.getText();
        String password = passwordField.getText();

        String jdbcUrl = "jdbc:mysql://localhost:3306/chattingapp";
        String dbUsername = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Login successful, navigate to Scene2YM.fxml
                Stage stage=(Stage)usernameField.getScene().getWindow();
                FXMLLoader fx=new FXMLLoader(MainAppYM.class.getResource("Scene2YM.fxml"));
                Scene scene=new Scene(fx.load());
                stage.setScene(scene);
                Scene2YMController scene2Controller = fx.getController();
                scene2Controller.setUsernameLabel(username);
                clearFields();

            } else {
                // Invalid username or password
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid Username or password!");
                alert.show();
            }
        } catch (SQLException | IOException e) {
            // Error connecting to database
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error connecting to database");
            alert.show();
            e.printStackTrace();
        }
    }
    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }
    @FXML
    protected void toRegister(ActionEvent event) throws IOException{
        Stage stage=(Stage)usernameField.getScene().getWindow();
        FXMLLoader fx=new FXMLLoader(MainAppYM.class.getResource("Registration.fxml"));
        Scene scene=new Scene(fx.load());
        stage.setScene(scene);
    }
}
