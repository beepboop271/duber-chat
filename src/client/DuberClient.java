package client;

/* [Client.java]
 * duber's client
 * @author Ryan
 * @ version 1.0
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import messages.*;
import messages.CommandReply.Status;

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
  private ObjectInputStream input, pubsubInput; //Stream for network input
  private ObjectOutputStream output;
  private boolean running = true; //thread status via boolean
  private String[] names;
  private JTabbedPane tabbedPane;
  private ChatInformation chatInfo;
  private ChatList chatList;
  private int port;
  private String ip, username;
  private CommandReply commandReply;
  //private GetReply reply;
    
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
    System.out.println("Connected to server");
    input = serverPortPanel.getInput();
    output = serverPortPanel.getOutput();
    mySocket = serverPortPanel.getSocket();
    ip = serverPortPanel.getIP();
    port = serverPortPanel.getPort();
    loginWindow.remove(loginPanel);
    System.out.println("server connections saved");


    //login screen
    LoginPanel loginPanel = new LoginPanel(input,output);
    CreateAccountPanel createAccountPanel = new CreateAccountPanel(input, output);
    JPanel loginPanelHolder = loginPanel.getPanel();
    JPanel createAccountPanelHolder = createAccountPanel.getPanel();
    tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Login", loginPanelHolder);
    tabbedPane.addTab("Create Account", createAccountPanelHolder);
    loginWindow.add(tabbedPane);
    System.out.println("created tabbed pane");
    loginWindow.repaint();
    while(!loginPanel.getLoggedIn()) {
      try{
        Thread.sleep(100);
        System.out.print("ping");
      } catch(IllegalArgumentException | InterruptedException e2) {
      }
    }
    System.out.println();
    username = loginPanel.getUsername();
    loginWindow.dispose();
    serverPortPanel.disconnect();
    createAccountPanel.disconnect();

    //connects pubsub socket
    try {
      pubSubSocket = new Socket(ip, port); // attempt socket connection (local address). This will wait until a connection is made
      pubsubInput = new ObjectInputStream(pubSubSocket.getInputStream()); // Stream for network input
      System.out.println("pubsub Sock and input connected");
    } catch (IOException e) { // connection error occured
      System.out.println("Connection to pubsub socket failed");
    }

    JFrame chatWindow = new JFrame("DuberChat");
    chatWindow.setResizable(false);
    chatWindow.setSize(800,450);

    //import user friends data
    /*
    try{//catchs connection errors
      synchronized (output) {
        try {//sends a get command to retrieve friends
          output.writeObject(new GetFriends());
          output.flush();
        } catch (IOException e) {
          System.out.println("could not get friends");
        }
      }
      try {//catches errors reading the object
        reply = input.readObject();
        
      } catch (IOException | ClassNotFoundException error) {
        System.out.print("Error reading server response");
        error.printStackTrace();
      }

    } catch(NullPointerException error) {
      System.out.println("error connecting");
    }
    */

    long[] messageIDs = {1,2,3,4,5};
    String[] authorIDs = {"john","john","john","john","john"};
    long[] times = {10,20,30,40,50};
    String[] messages = {"hi, this is going to be a very long message to test if there is text wrapping, but it still needs to be longer", "its me", "john", "aaaaa", "idk if this works"};
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

    //import UserIDs
    //for every user id import the friendInfo of it and add to a FriendInformation[]
    long[] userIDs = {1,2,3,4,5};
    FriendInformation[] friendInfo = new FriendInformation[5];
    friendInfo[0] = new FriendInformation("john","online","i hate everything");
    friendInfo[1] = new FriendInformation("jimmy","online","want die");
    friendInfo[2] = new FriendInformation("jane","online","why is this so hard");
    friendInfo[3] = new FriendInformation("joe","online","kevin send help");
    friendInfo[4] = new FriendInformation("janet","online","end my suffering");

    ListOfFriendID friendIDs = new ListOfFriendID(userIDs, friendInfo);
    CreateGroupChat createGroupChat = new CreateGroupChat(friendIDs);
    JPanel createGroupPanel = createGroupChat.getPanel();

    long[] friendRequestIds = {1,4,15,22,23,42};
    String[] sourceUsernames = {"aaaa", "john", "jenny", "joe", "aaaa", "aaaa"};
    String[] targetUsernames = {"jane", "aaaa", "aaaa", "aaaa", "jennifer", "joel"};

    FriendRequestInformation friendRequestInfo = new FriendRequestInformation(friendRequestIds, sourceUsernames, targetUsernames);
    FriendRequestPanel friendRequestPanel = new FriendRequestPanel(username, friendRequestInfo);
    JPanel friendRequest = friendRequestPanel.getPanel();

    tabbedPane = new JTabbedPane();
    JPanel temp2 = new JPanel();
    tabbedPane.addTab("Group Chat", createGroupPanel);
    tabbedPane.addTab("Friends", temp2);
    tabbedPane.addTab("Friend Requests", friendRequest);
    JFrame requestWindow = new JFrame();

    long requestID;
    boolean updateFriendRequestList = false;
    boolean updateFriendList = false;
    
    do{

      try{
        Thread.sleep(100);
      } catch(IllegalArgumentException | InterruptedException e2) {
      }

      if (chatPanel.getRequest() && !requestPanelOpen) {
        //open new panel
        System.out.println("create new window");
        requestPanelOpen = true;
        requestWindow = new JFrame("DuberChat Request");
        requestWindow.setResizable(false);
        requestWindow.setSize(300,400);
        requestWindow.add(tabbedPane);
        requestWindow.setVisible(true);
        chatPanel.setRequest(false);
      } else if (chatPanel.getRequest()) {
        //request panel is already open
        System.out.println("panel open already");
        chatPanel.setRequest(false);
      }
      
      if (chatPanel.getSend()) {
        System.out.print("send message:");
        message = chatPanel.getMessage();
        System.out.println(message);
      }

      if (friendRequestPanel.getAccepted()){
        requestID = friendRequestPanel.getRequestID();
        
        try{//catchs connection errors
          synchronized (output) {
            try {//sends a get command to accept friend request
              output.writeObject(new DoFriendAccept(requestID));
              output.flush();
            } catch (IOException e) {
              System.out.println("could not accept friend request");
            }
          }
          try {//catches errors reading the object
            commandReply = (CommandReply)input.readObject();
            if (commandReply.getStatus() == Status.OK){
              System.out.println("friend request accepted");
              updateFriendRequestList = true;
            } else {
              System.out.println("could not accept friend request");
            }
          } catch (IOException | ClassNotFoundException error) {
            System.out.print("Error reading server response");
            error.printStackTrace();
          }
        } catch(NullPointerException error) {
          System.out.println("error connecting");
        }
        friendRequestPanel.setAccepted(false);
      } else if (friendRequestPanel.getRejected()){
        requestID = friendRequestPanel.getRequestID();
        try{//catchs connection errors
          synchronized (output) {
            try {//sends a get command to accept friend request
              output.writeObject(new DoFriendReject(requestID));
              output.flush();
            } catch (IOException e) {
              System.out.println("could not reject friend request");
            }
          }
          try {//catches errors reading the object
            commandReply = (CommandReply)input.readObject();
            if (commandReply.getStatus() == Status.OK){
              System.out.println("friend request rejected");
              updateFriendRequestList = true;
            } else {
              System.out.println("could not reject friend request");
            }
          } catch (IOException | ClassNotFoundException error) {
            System.out.print("Error reading server response");
            error.printStackTrace();
          }
        } catch(NullPointerException error) {
          System.out.println("error connecting");
        }
        friendRequestPanel.setRejected(false);
      } else if (friendRequestPanel.getCancelled()){
        requestID = friendRequestPanel.getRequestID();
        try{//catchs connection errors
          synchronized (output) {
            try {//sends a get command to cancel friend request
              output.writeObject(new DoFriendCancel(requestID));
              output.flush();
            } catch (IOException e) {
              System.out.println("could not cancel friend request");
            }
          }
          try {//catches errors reading the object
            commandReply = (CommandReply)input.readObject();
            if (commandReply.getStatus() == Status.OK){
              System.out.println("friend request cancelled");
              updateFriendRequestList = true;
            } else {
              System.out.println("could not cancel friend request");
            }
          } catch (IOException | ClassNotFoundException error) {
            System.out.print("Error reading server response");
            error.printStackTrace();
          }
        } catch(NullPointerException error) {
          System.out.println("error connecting");
        }
        friendRequestPanel.setRejected(false);
      }

      if (updateFriendRequestList) {
        
        friendRequestPanel.updateList(friendRequestInfo);
      }

      if (updateFriendList) {

      }
    } while(chatPanel.getRunning());
    
    // after connecting loop and keep appending[.append()] to the JTextArea

    chatWindow.dispose();
    requestWindow.dispose();
    loginPanel.disconnect();
    disconnect();
  }
  
  public void login(){
    
  }
  
  //Starts a loop waiting for server input and then displays it on the textArea
  public void disconnect() { 
    try {  //close all the sockets
      input.close();
      System.out.println("closed input");
      output.close();
      System.out.println("closed output");
      mySocket.close();
      System.out.println("closed MySocket");
      pubsubInput.close();
      System.out.println("closed pubsubInput");
      pubSubSocket.close();
      System.out.println("closed pubsubSocket");
    }catch (Exception e) { 
      System.out.println("Failed to close socket");
    }
  }

  public static void main(String[] args) { 
    new ChatClient().go();
  }
}