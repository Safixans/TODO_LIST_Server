package Networking;

import frame.TodoListGUI;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Networking implements Runnable{
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader in;
    BufferedWriter out;
    TodoListGUI app;
    public Networking(TodoListGUI app){
        this.app = app;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(8000);
            socket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true){
                String messageFromClient = in.readLine();
                if (Objects.equals(messageFromClient, "update table")){
                    app.refreshTasksView();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
