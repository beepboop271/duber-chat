package client;

/* [Client.java]
 * duber's client
 * @author Ryan
 * @ version 1.0
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import messages.DoLogin;

import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class ChatClient {
  private JButton sendButton, clearButton, friendButtonList;
  private JTextField typeField;
  private JTextArea msgArea;
  private JPasswordField passwordField;
  private JPanel southPanel, westPanel, eastPanel, loginPanel;
  private JPanel southEastPanel, southWestPanel;
  private Socket mySocket, pubSubSocket; //socket for connection
  private ObjectInputStream input; //Stream for network input
  private ObjectOutputStream output;
  private boolean running = true; //thread status via boolean
  private String[] names;
  private JTabbedPane tabbedPane;
  private ChatInformation chatInfo;
  private FriendInformation friendInfo;
    
  public void go() {
    JFrame loginWindow = new JFrame("DuberChat Login");

    loginWindow.setResizable(false);
    ServerPortPanel serverPortPanel = new ServerPortPanel();
    loginPanel = serverPortPanel.getPanel();
    loginWindow.add(loginPanel);
    loginWindow.setSize(500,200);
    loginWindow.setVisible(true);
    do{
      try{
        Thread.sleep(100);
      } catch(IllegalArgumentException | InterruptedException e2) {
      }
    }while(!serverPortPanel.getConnected());
    input = serverPortPanel.getInput();
    output = serverPortPanel.getOutput();
    loginWindow.remove(loginPanel);

    //login screen
    LoginPanel loginPanel = new LoginPanel(input,output);
    CreateAccountPanel createAccountPanel = new CreateAccountPanel(input, output);
    tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Login", loginPanel.getPanel());
    tabbedPane.addTab("Create Account", createAccountPanel.getPanel());
    loginWindow.add(tabbedPane);
    while(!loginPanel.getLoggedIn()) {
      try{
        Thread.sleep(100);
      } catch(IllegalArgumentException | InterruptedException e2) {
      }
    }
    loginWindow.dispose();

    JFrame chatWindow = new JFrame("DuberChat");
    chatWindow.setResizable(false);
    chatWindow.setSize(1600,900);
    

    
    // after connecting loop and keep appending[.append()] to the JTextArea
    readMessagesFromServer();
  }
  
  public void login(){
    
  }
  
  //Starts a loop waiting for server input and then displays it on the textArea
  public void readMessagesFromServer() { 

    /*
    while(running) {  // loop unit a message is received
      try {
        input.readObject();
        
        
      }catch (IOException e) { 
        System.out.println("Failed to receive msg from the server");
        e.printStackTrace();
      }
    }
    */

    try {  //after leaving the main loop we need to close all the sockets
      input.close();
      output.close();
      mySocket.close();
    }catch (Exception e) { 
      System.out.println("Failed to close socket");
    }
  }
  //****** Inner Classes for Action Listeners ****
  
    // send - send msg to server (also flush), then clear the JTextField
    class SendButtonListener implements ActionListener { 
      public void actionPerformed(ActionEvent event)  {
        //Send a message to the client            
        typeField.setText(""); 
      }     
    }

  // QuitButtonListener - Quit the program
    class QuitButtonListener implements ActionListener { 
      public void actionPerformed(ActionEvent event)  {
        running=false;
      }     
    }

  class FriendButtonListener implements ActionListener { 
    private int friendID;
    public void actionPerformed(ActionEvent event)  {
      
    }
    public void setFriendID(int ID){
      this.friendID = ID;
    }
  }
  public static void main(String[] args) { 
    new ChatClient().go();
  }
}