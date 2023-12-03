package Networking;

import java.io.*;

import java.net.Socket;

import java.util.Scanner;

public class Networking implements Runnable{
    Socket socket = null;
    BufferedReader in = null;
    BufferedWriter out = null;

    @Override
    public void run() {
        try {
            socket = new Socket("localhost", 8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true){
                String messageToServer = "update table";
                out.write(messageToServer);
                out.newLine();
                out.flush();
                Thread.sleep(100);
                if (messageToServer.equals("exit")){
                    break;
                }
            }
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
