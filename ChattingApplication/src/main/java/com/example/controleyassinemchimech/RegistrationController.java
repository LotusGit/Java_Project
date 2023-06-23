package com.example.controleyassinemchimech;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationController {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/chattingapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void registerAccount() throws IOException{
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate the input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter a username and password");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DATABASE_URL,USERNAME,PASSWORD)) {
            // Prepare the SQL statement
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the statement
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Account registered successfully");
                clearFields();
                Stage stage=(Stage)usernameField.getScene().getWindow();
                FXMLLoader fx=new FXMLLoader(MainAppYM.class.getResource("Scene2YM.fxml"));
                Scene scene=new Scene(fx.load());
                stage.setScene(scene);
                Scene2YMController scene2Controller = fx.getController();
                scene2Controller.setUsernameLabel(username);
            } else {
                showAlert("Error", "Failed to register the account");
            }

            statement.close();
        } catch (SQLException e) {
            showAlert("Error", "Failed to connect to the database or register the account!\nReason: "+e.getMessage());
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void oncancel(ActionEvent event) throws IOException {
        clearFields();
        Stage stage=(Stage)usernameField.getScene().getWindow();
        FXMLLoader fx=new FXMLLoader(MainAppYM.class.getResource("Scene1YM.fxml"));
        Scene scene=new Scene(fx.load());
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }
}
