package client;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.GridLayout;
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

    updatePanel();
  }
  //getters
  public JPanel getPanel(){
    return mainPanel;
  }
  public boolean getSend(){
    return send;
  }
  public String getMessage(){
    String message = typeField.getText();
    typeField.setText("");
    send = false;
    return message;
  }
  //updates the panel
  public void updatePanel(){
    //update friend panel
    mainPanel.removeAll();
    friendListPanel = new JPanel();
    friendListPanel.setPreferredSize(new Dimension(300,340));
    friendListPane = new JScrollPane(friendPanel);
    typeField = new JTextField();
    typeField.setPreferredSize(new Dimension(280,25));
    sendFriendRequest = new JButton("Add Friend");
    sendFriendRequest.setPreferredSize(new Dimension(280,25));
    sendFriendRequest.addActionListener(this);
    sendFriendRequest.setActionCommand("send");
    friendLabel = new JLabel[listOfFriendID.getUserIDs().length];
    removeFriend = new JButton[listOfFriendID.getUserIDs().length];
    JPanel tempPanel = new JPanel();
    map = new HashMap<>();
    friendListPanel = new JPanel();
    friendListPane = new JScrollPane(friendPanel);
    friendListPanel.setPreferredSize(new Dimension(280,340));
    friendListPane.setPreferredSize(new Dimension(280,350));
    friendListPanel.setLayout(new GridLayout(0, 1));
    for (int i = 0; i < friendLabel.length; i++) {
      
      friendLabel[i] = new JLabel(listOfFriendID.getFriendInfo()[i].getUsername());
      friendLabel[i].setPreferredSize(new Dimension(200, 25));
      removeFriend[i] = new JButton("X");
      removeFriend[i].setPreferredSize(new Dimension(50,25));
      removeFriend[i].setActionCommand("remove");
      removeFriend[i].addActionListener(this);

      //friendListPanel.add(friendLabel[i]);
      //friendListPanel.add(removeFriend[i]);

      tempPanel.add(friendLabel[i]);
      tempPanel.add(removeFriend[i]);
      friendListPanel.add(tempPanel);
      map.put(removeFriend[i], listOfFriendID.getUserIDs()[i]);

    }
    mainPanel.add(typeField);
    mainPanel.add(sendFriendRequest);
    mainPanel.add(friendListPane);
    friendListPanel.repaint();
    friendListPane.repaint();
    mainPanel.repaint();
  }
  //updates info and then updates panel
  public void updateListOfFriendID(ListOfFriendID listOfFriendID){
    this.listOfFriendID = listOfFriendID;
    updatePanel();
  }
  
  public void actionPerformed(ActionEvent e) {
    if ("send".equals(e.getActionCommand())) {//send a friend request
      send = true;
    } else if("remove".equals(e.getActionCommand())) {//remove a friend
      JButton temp = ((JButton)e.getSource());
      friendID = map.get(temp);
      remove = true;
    } else {
      
    }
  }
}
