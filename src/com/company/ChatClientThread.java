package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatClientThread extends Thread {
    Socket clientSocket;
    ServerSocket serverSocket;
    private boolean chatActive;
    private BufferedReader in = null;
    private PrintWriter out = null;
    public ChatClientThread(Socket socket){
        super();

        chatActive = true;
        try {
            clientSocket = socket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        System.out.println("Thread started");
        while(chatActive){
            conversation();
        }
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void conversation() {
        try {
            String userInput;
            while((userInput = in.readLine()) != null){
                System.out.println("Message received from client:" + userInput);
                if(userInput.equals("Bye!")){
                    chatActive = false;
                    out.println("Thanks for talking with chatprogram, have a nice day!" + "Bye!");
                    in.close();
                    out.close();
                    clientSocket.close();
                }
                else{
                    out.println(userInput);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                in.close();
                out.close();
                clientSocket.close();
            } catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
    }
}
