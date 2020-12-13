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
  private JTextArea msgArea, usernameField;  
  private JPasswordField passwordField;
  private JPanel southPanel, westPanel, eastPanel;
  private JPanel southEastPanel, southWestPanel;
  private Socket mySocket; //socket for connection
  private ObjectInputStream input; //Stream for network input
  private ObjectOutputStream output;
  private boolean running = true; //thread status via boolean
  private String[] names;
  private JTabbedPane tabbedPane;

  
    
  public void go() {
    JFrame window = new JFrame("DuberChat");
    window.setResizable(false);

    ServerPortPanel serverPortPanel = new ServerPortPanel();
    LoginPanel loginPanel = new LoginPanel(input,output);
    CreateAccountPanel createAccountPanel = new CreateAccountPanel(input, output);

    tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Login", loginPanel.getPanel());
    tabbedPane.addTab("Create Account", createAccountPanel.getPanel());
    tabbedPane.addTab("Server", serverPortPanel.getPanel());
    //tabbedPane.addTab("Create Account", component);
    //ServerPortPanel serverPortPanel = new ServerPortPanel();
    
    //login screen
    window.add(tabbedPane);
    window.setSize(500,200);
    window.setVisible(true);
    while(!loginPanel.getLoggedIn()) {
      input = serverPortPanel.
      loginPanel.
    }

    window.setVisible(false);
    window.setSize(1600,900);
    //account information retrival
    names = new String[5];
    names[0] = "ryan";
    names[1] = "kevin";
    names[2] = "shari";
    names[3] = "candice";
    names[4] = "viraj";
    for(int i =0; i < names.length; i++){
      friendButtonList = new JButton(names[i]);//creates a new botton with user name on it
      friendButtonList.setMaximumSize(new Dimension(20, 10));
      //friendButtonList.setSize(new Dimension(200, 100));//sets the size of the button
      //PreferredSize(new Dimension(200, 50));
      friendButtonList.addActionListener(new SendButtonListener());//allows the button to be interacted with
      westPanel.add(friendButtonList);//adds the new buttom to the list on the left
    }
    //chat gui
    westPanel = new JPanel();
    eastPanel = new JPanel();
    southPanel = new JPanel();
    southEastPanel = new JPanel();
    southWestPanel = new JPanel();
    
    westPanel.setLayout(new GridLayout(0,1));
    southPanel.setLayout(new GridLayout(0,2));
    eastPanel.setLayout(new GridLayout(0,1));
    southEastPanel.setLayout(new GridLayout(0,1));
    southWestPanel.setLayout(new GridLayout(0,1));

    sendButton = new JButton("SEND");
    sendButton.addActionListener(new SendButtonListener());
    clearButton = new JButton("QUIT");
    clearButton.addActionListener(new QuitButtonListener());
    
    JLabel errorLabel = new JLabel("");
    
    typeField = new JTextField(10);
    
    msgArea = new JTextArea();
    
    southWestPanel.add(typeField);
    southEastPanel.add(sendButton);
    southEastPanel.add(clearButton);
    //southPanel.add(errorLabel);
    southPanel.add(southWestPanel);
    southPanel.add(southEastPanel);
    //southPanel.setMaximumSize(new Dimension(900,100));
    
    //eastPanel.add(msgArea);
    //eastPanel.add(southPanel);

    //window.add(BorderLayout.CENTER,eastPanel);
    window.add(BorderLayout.CENTER,msgArea);
    window.add(BorderLayout.WEST,westPanel);
    window.add(BorderLayout.SOUTH,southPanel);
    
    
    window.setSize(1600,900);
    window.setVisible(true);
    
    
    // after connecting loop and keep appending[.append()] to the JTextArea
    
    readMessagesFromServer();
    window.dispose();
  }
  
  //Attempts to connect to the server and creates the socket and streams
  public Socket connect(String ip, int port) { 
    System.out.println("Attempting to make a connection..");
    
    try {
      mySocket = new Socket(ip, port); //attempt socket connection (local address). This will wait until a connection is made

      ObjectInputStream input = new ObjectInputStream(mySocket.getInputStream()); //Stream for network input
      ObjectOutputStream output = new ObjectOutputStream(mySocket.getOutputStream()); //Stream for network output

      ObjectInputStream ping = new ObjectInputStream(mySocket.getInputStream());//pings from server to update for messages
    } catch (IOException e) {  //connection error occured
      System.out.println("Connection to Server Failed");
      e.printStackTrace();
    }
    
    System.out.println("Connection made.");
    return mySocket;
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