package com.example.controleyassinemchimech;


import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javax.naming.CommunicationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatWithServer extends Thread{
    private List<Communication> clientconnectés = new ArrayList<>();
    public static void main(String[] args) {
        new ChatWithServer().start();

    }
    @Override
    public  void run(){
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Le serveur essaie de demarrer...");
            while(true)
            {
                Socket s = ss.accept();
                //To read the username
                DataInputStream inputStream = new DataInputStream(s.getInputStream());
                String username = inputStream.readUTF();
                Communication NewCommunication = new Communication(s,username);
                clientconnectés.add(NewCommunication);
                NewCommunication.start();
                System.out.println("Received username from client: " + username);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public class Communication extends Thread{
        private Socket s;
        private String clientName;
        Communication(Socket s, String clientName)
        {
            this.s=s;
            this.clientName= clientName;
        }
        @Override
        public  void run() {
            try {
                InputStream is = s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = s.getOutputStream();
                String Ip = s.getRemoteSocketAddress().toString();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("Bienvenue "+clientName);
                pw.println("Envoyer le message que vous voulez ... ;)");
                while (true) {
                    String UserRequest = br.readLine();
                    if(UserRequest.contains("=>")){
                        String[] usermessage = UserRequest.split("=>");
                        if(usermessage.length==2){
                            String msg = usermessage[1];
                            String Client = usermessage[0];
                            Send(msg,s,Client);
                        }
                    }else{
                        Send(UserRequest,s,"");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        void Send(String UserRequest, Socket socket, String clientName) throws IOException{
            for(Communication client: clientconnectés){
                if(client.s != socket){
                    if(client.clientName.equals(clientName)){
                        PrintWriter pw = new PrintWriter(client.s.getOutputStream(),true);
                        pw.println("Private message from "+this.clientName+" : "+UserRequest);
                    }
                    if(clientName.isEmpty()){
                        PrintWriter pw = new PrintWriter(client.s.getOutputStream(),true);
                        pw.println(this.clientName+" : "+UserRequest);
                    }
                }
            }
        }
    }
}

