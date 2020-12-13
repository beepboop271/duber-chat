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
  private ChatList chatList;
    
  public void go() {

    /*

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

    */

    JFrame chatWindow = new JFrame("DuberChat");
    chatWindow.setResizable(false);
    chatWindow.setSize(800,450);

    //import user friends data
    long[] messageIDs = {1,2,3,4,5};
    String[] authorIDs = {"john","john","john","john","john"};
    long[] times = {10,20,30,40,50};
    String[] messages = {"hi", "its me", "john", "aaaaa", "idk if this works"};
    chatInfo = new ChatInformation(messageIDs, authorIDs, times, messages);
    ChatInformation[] chatInfoArray = {chatInfo};
    String[] chatNames = {"john"};
    long[] chatIDs = {1};
    chatList = new ChatList(chatNames, chatIDs, chatInfoArray);
    ChatPanel chatPanel = new ChatPanel(chatList);
    
    chatWindow.add(chatPanel.getPanel());
    chatWindow.setVisible(true);
    String message = "";
    boolean requestPanelOpen = false;
    do{
      try{
        Thread.sleep(100);
      } catch(IllegalArgumentException | InterruptedException e2) {
      }
      if (chatPanel.getRequest() && !requestPanelOpen) {
        //open new panel
        System.out.println("create new window");
        requestPanelOpen = true;
        chatPanel.setRequest(false);
        
      } else if (chatPanel.getRequest()) {
        //request panel is already open
        System.out.println("panel open already");
        chatPanel.setRequest(false);
      } else if (chatPanel.getSend()) {
        System.out.print("send message:");
        message = chatPanel.getMessage();
        System.out.println(message);
      }
    } while(chatPanel.getRunning());
    
    // after connecting loop and keep appending[.append()] to the JTextArea
    chatWindow.dispose();
    disconnect();
  }
  
  public void login(){
    
  }
  
  //Starts a loop waiting for server input and then displays it on the textArea
  public void disconnect() { 
    try {  //close all the sockets
      input.close();
      output.close();
      mySocket.close();
    }catch (Exception e) { 
      System.out.println("Failed to close socket");
    }
  }

  public static void main(String[] args) { 
    new ChatClient().go();
  }
}