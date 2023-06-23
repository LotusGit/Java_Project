package com.example.controleyassinemchimech;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Scene2YMController {
    @FXML
    private TextField MymsgID;
    @FXML
    private TextField PortID;
    @FXML
    private TextField HostID;
    @FXML
    private ListView testview;
    @FXML
    private Label usernameLabel;

    public void setUsernameLabel(String message) {
        usernameLabel.setText(message);
    }
    PrintWriter pw;
    @FXML
    public void onconnect() throws Exception {
        String host = HostID.getText();
        int port = Integer.parseInt(PortID.getText());
        //Socket
        Socket s = new Socket(host,port);
        InputStream is = s.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = s.getOutputStream();
        DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
        // Send the username to the server
        outputStream.writeUTF(usernameLabel.getText().toString());
        String Ip = s.getRemoteSocketAddress().toString();
        pw = new PrintWriter(os,true);
        //creating Thread
        new Thread(()->{
            while(true){
                try {
                    String response = br.readLine();
                    Platform.runLater(()->{
                        testview.getItems().add(response);
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
    @FXML
    public void onsubmit(){
        String message = MymsgID.getText();
        testview.getItems().add(message);
        MymsgID.clear();
        pw.println(message);
    }
    @FXML
    private void handleFileUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("Image Files", "*.jpg", "*.png"),
                new ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(testview.getScene().getWindow());
        if (selectedFile != null) {
            // Perform file upload logic here, e.g., send the file to the server

            // Get the file name and add it to the ListView
            String fileName = selectedFile.getName();
            testview.getItems().add(fileName);
        }
    }
}
