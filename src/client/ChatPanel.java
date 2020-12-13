package client;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import messages.GetChat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ChatPanel extends JPanel implements ActionListener {
  private JButton[] chatListButton;
  private JButton sendButton, requestButton;
  private JTextField typeField;
  private JPanel mainPanel, friendPanel, chatPanel;
  private JLabel chatLabel;
  private ChatList chatList;
  private HashMap<String, String> map;
  private String openChat;
  private boolean running, request, send;
  
  ChatPanel(ChatList chatList){
    this.chatList = chatList;
    running = true;
    request = false;
    send = false;
    mainPanel = new JPanel();
    friendPanel = new JPanel();
    chatPanel = new JPanel();
    map = new HashMap<>();
    chatLabel = new JLabel("");
    chatLabel.setPreferredSize(new Dimension(500,360));
    chatLabel.setHorizontalAlignment(10);
    chatLabel.setVerticalAlignment(1);
    chatListButton = new JButton[chatList.getChatNames().length];

    requestButton = new JButton("Manage chats and friends");
    requestButton.setActionCommand("request");
    requestButton.setPreferredSize(new Dimension(250, 50));
    requestButton.addActionListener(this);
    friendPanel.setPreferredSize(new Dimension(260,450));
    friendPanel.add(requestButton);
    for (int i = 0; i < chatList.getChatNames().length; i++) {
      map.put(chatList.getChatNames()[i], createChat(chatList, i));//add the j panel
      chatListButton[i] = new JButton(chatList.getChatNames()[i]);
      chatListButton[i].addActionListener(this);
      chatListButton[i].setActionCommand("chatbutton");
      chatListButton[i].setPreferredSize(new Dimension(250, 25));
      chatListButton[i].setHorizontalAlignment(2);
      friendPanel.add(chatListButton[i]);
    }

    typeField = new JTextField();
    typeField.setPreferredSize(new Dimension(400, 25));
    sendButton = new JButton("Send");
    sendButton.setActionCommand("send");
    sendButton.addActionListener(this);
    sendButton.setPreferredSize(new Dimension(95,25));



    //adds chat, typefield and the send button to the chat panel
    chatPanel.setPreferredSize(new Dimension(540, 450));
    chatPanel.add(chatLabel);
    chatPanel.add(typeField);
    chatPanel.add(sendButton);

    mainPanel.setLayout(new BorderLayout());
    mainPanel.add(friendPanel, BorderLayout.WEST);
    mainPanel.add(chatPanel, BorderLayout.CENTER);
    
  }
  public JPanel getPanel(){
    return mainPanel;
  }
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
  public void actionPerformed(ActionEvent e) {
    if ("request".equals(e.getActionCommand())) {
      System.out.println("request");
      request = true;
    } else if("send".equals(e.getActionCommand())) {
      System.out.println("send");
      send = true;
    } else {
      String chatName = ((JButton)e.getSource()).getText();
      String chat = map.get(chatName);
      chatLabel.setText(chat);
      chatLabel.setBorder(BorderFactory.createTitledBorder(chatName));
    }
  }
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
