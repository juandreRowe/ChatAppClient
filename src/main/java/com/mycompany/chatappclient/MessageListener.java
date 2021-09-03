/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatappclient;

import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.JTextArea;

/**
 *
 * @author juandre
 */
public class MessageListener extends Thread {
    private final BufferedReader reader;
    private boolean running;
    private JTextArea textArea;
    
    public MessageListener(BufferedReader reader, JTextArea textArea){
        this.reader = reader;
        this.textArea = textArea;
        running = true;
    }
    
    @Override
    public void run(){
        while(running){
            try{
                String message = reader.readLine();
                if(message != null){
                    if(textArea != null){
                        textArea.append(message.concat("\n"));
                    }else{
                        System.out.println(message);
                    }
                }else{
                    running = false;
                    System.out.println("running = false");
                }
            }catch(IOException ex){
                System.err.println(ex.getMessage());
                running = false;
                System.out.println("running = false");
            }
        }
    }
    
    public void quit(){
        running = false;
        if(reader != null){
            try{
                reader.close();
            }catch(IOException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
}
