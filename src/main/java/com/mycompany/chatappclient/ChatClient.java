/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatappclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JTextArea;

/**
 *
 * @author juandre
 */
public class ChatClient {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private MessageListener listener;
    
    public ChatClient(String host, int port, JTextArea textArea){
        try{
            socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            listener = new MessageListener(reader, textArea);
            listener.start();
        }catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public void sendMessage(String message){
        if(writer != null){
            writer.println(message);
            writer.flush();
        }
    }
    
    public void quit(){
        System.out.println("closing writer");
        this.closeCloseable(writer);
        System.out.println("closing reader");
        this.closeCloseable(reader);
        System.out.println("closing socket");
        this.closeCloseable(socket);
    }
    
    private void closeCloseable(AutoCloseable closeable){
        if(closeable != null){
            try{
                closeable.close();
            }catch(Exception ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChatClient client = new ChatClient("localhost", 6969, null);
        String message = "";
        while(!(message = scanner.nextLine()).equals("QUIT!!!")){
            client.sendMessage(message);
        }
        client.quit();
    }
}
