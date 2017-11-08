package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {
    private Socket clientSocket;
    private boolean isConversationActive = true;
    private String messageToServer;
    private String messageFromServer;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private BufferedReader stdIn = null;


    public ChatClient() throws IOException {
        clientSocket = new Socket("localhost", 11111);
        out = new PrintWriter(clientSocket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public void communicationWithServer(){
        try{
            if(!clientSocket.isClosed()){
                System.out.println("Write your message: ");
                String clientInput;
                while((clientInput = stdIn.readLine()) != null){
                    out.println(clientInput);
                    System.out.println("echo:" + in.readLine());
                }
            }
            else{
                isConversationActive = false;
            }
        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                in.close();
                stdIn.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void conversation(){
        while(isConversationActive){
            communicationWithServer();
        }


    }

    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient();
        chatClient.conversation();
    }
}
