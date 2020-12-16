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
import messages.Reply.Status;

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
  private String ip, username, password;
  private Reply commandReply;
  private PubSubMessage pubSubMessage;
    
  public void go() {
    JFrame loginWindow = new JFrame("DuberChat Login");
    //creates the connection socket window
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
    //takes some info back from serverPortPanel
    System.out.println("Connected to server");
    input = serverPortPanel.getInput();
    output = serverPortPanel.getOutput();
    mySocket = serverPortPanel.getSocket();
    ip = serverPortPanel.getIP();
    port = serverPortPanel.getPort();
    loginWindow.remove(loginPanel);
    System.out.println("server connections saved");
    
    try{
      Thread.sleep(1000);
    } catch(IllegalArgumentException | InterruptedException e2) {
    }


    //login screen
    LoginPanel loginPanel = new LoginPanel(input,output);
    CreateAccountPanel createAccountPanel = new CreateAccountPanel(input, output);
    JPanel loginPanelHolder = loginPanel.getPanel();
    JPanel createAccountPanelHolder = createAccountPanel.getPanel();
    tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Login", loginPanelHolder);
    tabbedPane.addTab("Create Account", createAccountPanelHolder);
    loginWindow.add(tabbedPane);
    loginWindow.repaint();
    while(!loginPanel.getLoggedIn()) {
      try{
        Thread.sleep(100);
      } catch(IllegalArgumentException | InterruptedException e2) {
      }
    }
    
    username = loginPanel.getUsername();
    password = loginPanel.getPassword();
    loginWindow.dispose();
    

    //connects pubsub socket
    try {
      pubSubSocket = new Socket(ip, port); // attempt socket connection (local address). This will wait until a connection is made
      ObjectOutputStream pubsubOutput = new ObjectOutputStream(pubSubSocket.getOutputStream());
      pubsubInput = new ObjectInputStream(pubSubSocket.getInputStream()); // Stream for network input
      pubsubOutput.writeObject(new DoPubSubLogin(username, password));
      System.out.println("pubsub Sock and input connected");
    } catch (IOException e) { // connection error occured
      System.out.println("Connection to pubsub socket failed");
    }

    JFrame chatWindow = new JFrame("DuberChat");
    chatWindow.setResizable(false);
    chatWindow.setSize(800,450);

    //it was supposed to import user friends data
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

    //creeating empty variables
    long[] messageIDs = {};
    long[] authorIDs = {};
    long[] times = {};
    String[] messages = {};
    long[] lastMessageId = {};
    chatInfo = new ChatInformation(messageIDs, authorIDs, times, messages);

    ChatInformation[] chatInfoArray = {};
    String[] chatNames = {};
    long[] chatIDs = {};
    chatList = new ChatList(chatNames, chatIDs, chatInfoArray, lastMessageId);
    ChatPanel chatPanel = new ChatPanel(chatList);

    chatWindow.add(chatPanel.getPanel());
    chatWindow.setVisible(true);
    String message = "";
    boolean requestPanelOpen = false;

    long[] userIDs = {};
    FriendInformation[] friendInfo = new  FriendInformation[0];

    ListOfFriendID friendIDs = new ListOfFriendID(userIDs, friendInfo);
    CreateGroupChatPanel createGroupChat = new CreateGroupChatPanel(friendIDs);

    JPanel createGroupPanel = createGroupChat.getPanel();

    long[] friendRequestIds = {};
    String[] sourceUsernames = {};
    String[] targetUsernames = {};

    FriendRequestInformation friendRequestInfo = new FriendRequestInformation(friendRequestIds, sourceUsernames, targetUsernames);
    FriendRequestPanel friendRequestPanel = new FriendRequestPanel(username, friendRequestInfo);

    JPanel friendRequest = friendRequestPanel.getPanel();

    FriendPanel friendPanel = new FriendPanel(friendIDs);
    JPanel friendListPanel = friendPanel.getPanel();

    //creates a tabbed pane with the group creation, friend list and friend requests
    tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Group Chat", createGroupPanel);
    tabbedPane.addTab("Friends", friendListPanel);
    tabbedPane.addTab("Friend Requests", friendRequest);
    JFrame requestWindow = new JFrame();

    long requestID;
    boolean updateFriendRequestList = false;
    boolean updateFriendList = false;
    
    Runnable pubSub = new PubSubInput(pubsubInput, input, output, chatPanel, chatList, createGroupChat, friendIDs, friendPanel, friendRequestPanel, friendRequestInfo, running, username);
    Thread t = new Thread(pubSub);
    t.start();

    String friendName;

    do{//while running loop

      try{
        Thread.sleep(100);
      } catch(IllegalArgumentException | InterruptedException e2) {
      }
      
      if (chatPanel.getRequest() && !requestPanelOpen) {//opens a request panel
        //open new panel
        requestPanelOpen = true;
        requestWindow = new JFrame("DuberChat Request");
        requestWindow.setResizable(false);
        requestWindow.setSize(300,400);
        requestWindow.add(tabbedPane);
        requestWindow.setVisible(true);
        chatPanel.setRequest(false);
      } else if (chatPanel.getRequest()) {//makes sure there is only one open request panel
        //request panel is already open
        System.out.println("panel open already");
        chatPanel.setRequest(false);
      }
      
      if (chatPanel.getSend()) {//sends a message
        message = chatPanel.getMessage();
      }

      if (friendRequestPanel.getAccepted()){//test to see if there is a friend request accepted
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
          synchronized (input) {
            try {//catches errors reading the object
              commandReply = (Reply)input.readObject();
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
          }
        } catch(NullPointerException error) {
          System.out.println("error connecting");
        }
        friendRequestPanel.setAccepted(false);
      } else if (friendRequestPanel.getRejected()){//tests for if there is a rejected friend request
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
          synchronized (input) {
            try {//catches errors reading the object
              commandReply = (Reply)input.readObject();
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
          }
        } catch(NullPointerException error) {
          System.out.println("error connecting");
        }
        friendRequestPanel.setRejected(false);
      } else if (friendRequestPanel.getCancelled()){//tests for if there is a cancelled friend request
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
          synchronized (input) {
            try {//catches errors reading the object
              commandReply = (Reply)input.readObject();
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
          }
        } catch(NullPointerException error) {
          System.out.println("error connecting");
        }
        friendRequestPanel.setCancelled(false);
      }

      if (friendPanel.getSend()){//tests for it there is a new out going send friend request
        friendName = friendPanel.getMessage();
        System.out.println(friendName);
        try{//catchs connection errors
          synchronized (output) {
            try {//sends a get command to cancel friend request
              output.writeObject(new CreateFriendRequest(friendName));
              output.flush();
            } catch (IOException e) {
              System.out.println("could not create friend request");
            }
          }
          synchronized (input) {
            try {//catches errors reading the object
              commandReply = (Reply)input.readObject();
              if (commandReply.getStatus() == Status.OK){
                System.out.println("friend request created");
              } else {
                System.out.println("could not create friend request");
              }
            } catch (IOException | ClassNotFoundException error) {
              System.out.print("Error reading server response");
              error.printStackTrace();
            }
          }
        } catch(NullPointerException error) {
          System.out.println("error connecting");
        }
      }

      if (createGroupChat.getCreate()){//tests for if there is a creation of a group chat
        String name = createGroupChat.getMessage();
        System.out.println(name);
        try{//catchs connection errors
          synchronized (output) {
            try {//sends a get command to cancel group chat
              output.writeObject(new CreateGroupChat(createGroupChat.getMembers(), name));
              output.flush();
            } catch (IOException e) {
              System.out.println("could not create group chat");
            }
          }
          synchronized (input) {
            try {//catches errors reading the object
              commandReply = (Reply)input.readObject();
              if (commandReply.getStatus() == Status.OK){
                System.out.println("group chat created");
              } else {
                System.out.println("could not create group chat");
              }
            } catch (IOException | ClassNotFoundException error) {
              System.out.print("Error reading server response");
              error.printStackTrace();
            }
          }
        } catch(NullPointerException error) {
          System.out.println("error connecting");
        }
      }

    } while(chatPanel.getRunning());
    //disposes of all window and disconnects all sockets
    chatWindow.dispose();
    requestWindow.dispose();
    serverPortPanel.disconnect();
    createAccountPanel.disconnect();
    loginPanel.disconnect();
    disconnect();
  }
  
  public void disconnect() { 
    try {  //close all the sockets
      input.close();
      output.close();
      mySocket.close();
      pubsubInput.close();
      pubSubSocket.close();
    }catch (Exception e) { 
      System.out.println("Failed to close socket");
    }
  }

  public static void main(String[] args) { 
    new ChatClient().go();
  }
}