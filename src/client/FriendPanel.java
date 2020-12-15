package client;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class FriendPanel extends JPanel implements ActionListener {
  private JButton[] removeFriend;
  private JLabel[] friendLabel;
  private JPanel friendPanel;
  private JScrollPane friendListPane;
  private HashMap<JButton, Long> map;
  private JPanel mainPanel, friendListPanel;
  private ListOfFriendID listOfFriendID;
  private JTextField typeField;
  private JButton sendFriendRequest;
  private long friendID;
  private boolean send, remove;
  
  FriendPanel(ListOfFriendID listOfFriendID){
    this.listOfFriendID = listOfFriendID;
    send = false;
    mainPanel = new JPanel();
    mainPanel.setPreferredSize(new Dimension(300,390));
    friendListPanel = new JPanel();
    friendListPanel.setPreferredSize(new Dimension(300,350));
    JPanel temp = new JPanel();
    friendListPanel.add(temp);
    friendListPane = new JScrollPane(friendPanel);
    typeField = new JTextField();
    typeField.setPreferredSize(new Dimension(225,25));
    sendFriendRequest = new JButton("Add Friend");
    sendFriendRequest.setPreferredSize(new Dimension(60,25));
    sendFriendRequest.setActionCommand("send");
    updatePanel();
    mainPanel.add(typeField);
    mainPanel.add(sendFriendRequest);
    mainPanel.add(friendListPane);
  }
  public JPanel getPanel(){
    return mainPanel;
  }
  public boolean getSend(){
    return send;
  }
  public void updatePanel(){
    //update friend panel
    friendLabel = new JLabel[listOfFriendID.getUserIDs().length];
    removeFriend = new JButton[listOfFriendID.getUserIDs().length];
    map = new HashMap<>();
    friendListPanel.removeAll();
    for (int i = 0; i < friendLabel.length; i++) {
      friendLabel[i] = new JLabel(listOfFriendID.getFriendInfo()[i].getUsername());
      friendLabel[i].setPreferredSize(new Dimension(245, 25));
      removeFriend[i] = new JButton("X");
      removeFriend[i].setActionCommand("remove");
      removeFriend[i].addActionListener(this);
      friendListPanel.add(friendLabel[i]);
      friendListPanel.add(removeFriend[i]);
      map.put(removeFriend[i], listOfFriendID.getUserIDs()[i]);
    }
  }
  public void updateListOfFriendID(ListOfFriendID listOfFriendID){
    this.listOfFriendID = listOfFriendID;
    updatePanel();
  }
  
  public void actionPerformed(ActionEvent e) {
    if ("send".equals(e.getActionCommand())) {
      send = true;
    } else if("remove".equals(e.getActionCommand())) {
      JButton temp = ((JButton)e.getSource());
      friendID = map.get(temp);
      remove = true;
    } else {
      
    }
  }
  public String getMessage(){
    String message = typeField.getText();
    typeField.setText("");
    send = false;
    return message;
  }
}
