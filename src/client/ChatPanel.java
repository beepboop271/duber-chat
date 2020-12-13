package client;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import messages.GetChat;

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
  private FriendInformation friendInfo;
  private HashMap<String, JPanel> map;
  private ObjectInputStream input; //Stream for network input
  private ObjectOutputStream output;
  
  ChatPanel(FriendInformation friendInfo, ObjectInputStream input, ObjectOutputStream output){
    this.input = input;
    this.output = output;
    this.friendInfo = friendInfo;
    mainPanel = new JPanel();
    map = new HashMap<>();
    friendPanel = new JPanel();
    friendListButton = new JButton[friendInfo.getFriendUsernames().length];
    for (int i = 0; i < friendInfo.getFriendUsernames().length; i++) {
      map.put(friendInfo.getFriendUsernames()[i], createChatPanel(friendInfo, i));//add the j panel
      friendListButton[i] = new JButton(friendInfo.getFriendUsernames()[i]);
      friendListButton[i].addActionListener(this);
      friendPanel.add(friendListButton[i]);
    }
    chatPanel = new JPanel();
    mainPanel.add(friendPanel);
    mainPanel.add(chatPanel);
  }
  public JPanel getPanel(){
    return mainPanel;
  }
  public JPanel createChatPanel(FriendInformation friendInfo, int i){
    JPanel panel = new JPanel();
    JLabel chat = new JLabel();
    String chatString ="";
    ChatInformation chatInfo = friendInfo.getChatInfo()[i];
    for (int j = 0; j < chatInfo.getMessages().length; j++){
      chatString += chatInfo.getUserIDs()[j] + ":" + chatInfo.getMessages()[i] + "\n";//adds the author, a semicolon and their message. it then adds a newline
    }
    chat.setText(chatString);
    panel.add(chat);
    return panel;
  }
  public void actionPerformed(ActionEvent e) {
    String friendChatName = ((JButton)e.getSource()).getText();
    JPanel tempPanel = map.get(friendChatName);
    chatPanel.removeAll();
    chatPanel.add(tempPanel);
  }
  
}
