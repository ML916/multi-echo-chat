package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private ServerSocket serverSocket;

    public ChatServer(){
        try
        {
            serverSocket = new ServerSocket(11111);
        }
        catch (IOException e)
        {
            System.out.println("Could not create server socket on port 11111.");
            e.printStackTrace();
            System.exit(-1);
        }
        while(true){
            try{
                Socket clientSocket = serverSocket.accept();
                ChatClientThread chatClientThread = new ChatClientThread(clientSocket);
                chatClientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args){
        new ChatServer();
    }
}
