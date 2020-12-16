package client;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ChatPanel extends JPanel implements ActionListener {
  private JButton[] chatListButton;
  private JButton sendButton, requestButton, quitButton;
  private JTextField typeField;
  private JPanel mainPanel, chatPanel, friendListPanel, leftPanel;
  private JScrollPane chatListPane;
  private JLabel chatLabel;
  private ChatList chatList;
  private HashMap<String, String> map;
  private String openChat;
  private boolean running, request, send;
  
  ChatPanel(ChatList chatList){
    //initialize variables
    this.chatList = chatList;
    running = true;
    request = false;
    send = false;
    //coment things that use chat list to reference
    mainPanel = new JPanel();
    
    updatePanel();
  }
  //updates the panel
  public void updatePanel(){
    mainPanel.removeAll();

    friendListPanel = new JPanel();
    chatListPane = new JScrollPane(friendListPanel);
    chatListPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    chatListPane.setPreferredSize(new Dimension(270,450));
    chatPanel = new JPanel();
    chatLabel = new JLabel("");
    chatLabel.setPreferredSize(new Dimension(500,370));
    chatLabel.setHorizontalAlignment(10);
    chatLabel.setVerticalAlignment(1);

    requestButton = new JButton("Manage chats and friends");
    requestButton.setActionCommand("request");
    requestButton.setPreferredSize(new Dimension(240, 50));
    requestButton.addActionListener(this);

    friendListPanel.setPreferredSize(new Dimension(270,330));
    friendListPanel.add(new JPanel());

    friendListPanel.removeAll();
    map = new HashMap<>();//
    chatListButton = new JButton[chatList.getChatNames().length];//
    //entire loop
    for (int i = 0; i < chatList.getChatNames().length; i++) {
      map.put(chatList.getChatNames()[i], createChat(chatList, i));//adds chat name and chat messages into the map
      chatListButton[i] = new JButton(chatList.getChatNames()[i]);//create a new button with chat name
      chatListButton[i].addActionListener(this);//adds the button listener
      chatListButton[i].setActionCommand("chatbutton");//action command of the button
      chatListButton[i].setPreferredSize(new Dimension(240, 25));//prefered size of the button
      chatListButton[i].setHorizontalAlignment(2);//alligns the text left
      friendListPanel.add(chatListButton[i]);//adds the button to the chat list panel
    }

    typeField = new JTextField();
    typeField.setPreferredSize(new Dimension(400, 25));

    quitButton = new JButton("Quit");
    quitButton.setActionCommand("quit");
    quitButton.addActionListener(this);

    sendButton = new JButton("Send");
    sendButton.setActionCommand("send");
    sendButton.addActionListener(this);
    sendButton.setPreferredSize(new Dimension(95,25));

    //adds chat, typefield and the send button to the chat panel
    chatPanel.setPreferredSize(new Dimension(540, 450));
    chatPanel.add(chatLabel);

    leftPanel = new JPanel();
    leftPanel.setLayout(new BorderLayout());
    leftPanel.add(requestButton, BorderLayout.NORTH);
    leftPanel.add(friendListPanel, BorderLayout.CENTER);
    leftPanel.add(quitButton, BorderLayout.SOUTH);

    mainPanel.setLayout(new BorderLayout());
    mainPanel.add(leftPanel, BorderLayout.WEST);
    mainPanel.add(chatPanel, BorderLayout.CENTER);

  }
  //updates the chatList and then imeadiatly updates the panel
  public void chatUpdate(ChatList chatList){
    this.chatList = chatList;
    updatePanel();
  }
  public JPanel getPanel(){
    return mainPanel;
  }
  //creates a string to bes output as the chat
  public String createChat(ChatList chatList, int i){
    String chatString ="";
    ChatInformation chatInfo = chatList.getChatInfo()[i];
    chatString += "<html>";
    for (int j = 0; j < chatInfo.getMessages().length; j++){
      chatString += chatInfo.getUserIDs()[j] + ":" + chatInfo.getMessages()[j] + "<br/>";//adds the author, a semicolon and their message. it then adds a newline
    }
    chatString += "<html>";
    return chatString;
  }
  //if a button is pressed, sort it by its action
  public void actionPerformed(ActionEvent e) {
    if ("request".equals(e.getActionCommand())) {//opens up the request panel
      request = true;
    } else if("send".equals(e.getActionCommand())) {//sends a message
      send = true;
    }else if("quit".equals(e.getActionCommand())) {//quits the program
      running = false;
    } else {//opens a chat room
      openChat = ((JButton)e.getSource()).getText();
      String chat = map.get(openChat);
      chatLabel.setText(chat);
      chatLabel.setBorder(BorderFactory.createTitledBorder(openChat));

      chatPanel.removeAll();
      chatPanel.add(chatLabel);
      chatPanel.add(typeField);
      chatPanel.add(sendButton);
    }
  }
  //getters and setters
  public boolean getRunning(){
    return running;
  }
  public boolean getRequest(){
    return request;
  }
  public boolean getSend(){
    return send;
  }
  public void setRunning(boolean running){
    this.running = running;
  }
  public void setRequest(boolean request){
    this.request = request;
  }
  public String getMessage(){
    String message = typeField.getText();
    typeField.setText("");
    send = false;
    return message;
  }
}
