/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatgui;

import com.mycompany.chatappclient.ChatClient;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author juandre
 */
public class ChatFrame extends JFrame implements ActionListener {
    private ChatClient client;
    
    private JScrollPane scrollPane;
    private JTextArea textArea;
    
    private JPanel topPanel;
    private JPanel bottomPanel;
    
    private JLabel statusLabel;
    private JTextField portField;
    private JTextField hostField;
    private JButton connectButton;
    
    private JTextField messageField;
    private JButton sendButton;
    
    public ChatFrame(){
        this.addWindowListener(new WindowAdapter(){
        
            @Override
            public void windowClosing(WindowEvent e){
                //close connection
                client.quit();
                System.exit(1);
            }
            
        });
        
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 4));
        
        statusLabel = new JLabel("Disconnected");
        portField = new JTextField(30);
        hostField = new JTextField(30);
        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);
        
        topPanel.add(statusLabel);
        topPanel.add(portField);
        topPanel.add(hostField);
        topPanel.add(connectButton);
        
        bottomPanel = new JPanel(new GridLayout(1, 2));
        
        messageField = new JTextField(30);
        sendButton = new JButton("send");
        sendButton.addActionListener(this);
        
        bottomPanel.add(messageField);
        bottomPanel.add(sendButton);
        
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        new ChatFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == sendButton){
            //send the message
            client.sendMessage(messageField.getText());
            messageField.setText("");
        }else if(source == connectButton){
            //Make connection
            client = new ChatClient(hostField.getText(), Integer.parseInt(portField.getText()), this.textArea);
            statusLabel.setText("Connected");
            hostField.setEditable(false);
            portField.setEditable(false);
            connectButton.setEnabled(false);
        }
    }
}
