package client;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import messages.GetChat;

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
  private JButton[] friendListButton;
  private JButton sendButton;
  private JTextField typeField;
  private JTextArea msgArea;
  private JPanel mainPanel, friendPanel, chatPanel;
  private JLabel chatLabel;
  private ChatList chatList;
  private HashMap<String, String> map;
  
  ChatPanel(ChatList chatList){
    this.chatList = chatList;
    mainPanel = new JPanel();
    map = new HashMap<>();
    friendPanel = new JPanel();
    chatLabel = new JLabel("");
    chatLabel.setPreferredSize(new Dimension(1000,825));
    chatLabel.setHorizontalAlignment(10);
    chatLabel.setVerticalAlignment(1);
    friendListButton = new JButton[chatList.getFriendUsernames().length];
    for (int i = 0; i < chatList.getFriendUsernames().length; i++) {
      map.put(chatList.getFriendUsernames()[i], createChat(chatList, i));//add the j panel
      friendListButton[i] = new JButton(chatList.getFriendUsernames()[i]);
      friendListButton[i].addActionListener(this);
      friendPanel.add(friendListButton[i]);
    }
    typeField = new JTextField();
    typeField.setPreferredSize(new Dimension(1200, 25));
    sendButton = new JButton("Send");
    sendButton.setPreferredSize(new Dimension(350,25));
    
    mainPanel.add(friendPanel);
    mainPanel.add(chatLabel);
    mainPanel.add(typeField);
    mainPanel.add(sendButton);
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
    String friendChatName = ((JButton)e.getSource()).getText();
    String chat = map.get(friendChatName);
    System.out.print(chat);
    chatLabel.setText(chat);
  }
  
}
