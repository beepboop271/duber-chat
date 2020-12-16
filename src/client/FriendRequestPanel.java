package client;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;

public class FriendRequestPanel extends JPanel implements ActionListener {
  private JButton[] accept, reject, cancel;
  private JLabel[] requestLabel;
  private JPanel[] friendRequestPanel;
  private JScrollPane requestListPane;
  private HashMap<JButton, Long> map;
  private JPanel mainPanel, listPanel;
  private FriendRequestInformation friendRequestInfo;
  private String username;
  private boolean rejected, accepted, cancelled;
  private long requestID;
  
  FriendRequestPanel(String username, FriendRequestInformation friendRequestInfo){
    rejected = false;
    accepted = false;
    cancelled = false;
    this.username = username;
    this.friendRequestInfo = friendRequestInfo;
    mainPanel = new JPanel();
    mainPanel.setPreferredSize(new Dimension(300, 370));
    updatePanel();
    
  }

  public JPanel getPanel(){
    return mainPanel;
  }
  public boolean getAccepted(){
    return accepted;
  }
  public boolean getRejected(){
    return rejected;
  }
  public boolean getCancelled(){
    return cancelled;
  }
  public long getRequestID(){
    return requestID;
  }
  public void setAccepted(boolean b){
    accepted = b;
  }
  public void setRejected(boolean b){
    rejected = b;
  }
  public void setCancelled(boolean b){
    cancelled = b;
  }
  public void updatePanel(){
    int numberOfRequests = friendRequestInfo.getFriendRequestIds().length;
    mainPanel.removeAll();
    accept = new JButton[numberOfRequests];
    reject = new JButton[numberOfRequests];
    cancel = new JButton[numberOfRequests];
    requestLabel = new JLabel[numberOfRequests];
    friendRequestPanel = new JPanel[numberOfRequests];
    listPanel = new JPanel();
    requestListPane = new JScrollPane(listPanel);
    requestListPane.setPreferredSize(new Dimension(300, 370));
    requestListPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    listPanel.setPreferredSize(new Dimension(300, 370));
    listPanel.setLayout(new GridLayout(0, 1));
    map = new HashMap<>();
    for (int i = 0; i < numberOfRequests; i++){
      friendRequestPanel[i] = new JPanel();
      friendRequestPanel[i].setPreferredSize(new Dimension(300, 35));;
      if (username.equals(friendRequestInfo.getTargetUsernames()[i])){//if the client is the target user
        requestLabel[i] = new JLabel();
        requestLabel[i].setText("From: " + friendRequestInfo.getSourceUsernames()[i]);
        requestLabel[i].setPreferredSize(new Dimension(150,25));
        accept[i] = new JButton("Y");
        accept[i].addActionListener(this);
        accept[i].setActionCommand("accept");
        accept[i].setPreferredSize(new Dimension(50, 25));
        reject[i] = new JButton("N");
        reject[i].addActionListener(this);
        reject[i].setActionCommand("reject");
        reject[i].setPreferredSize(new Dimension(50, 25));
        System.out.println("friend request from: " + friendRequestInfo.getSourceUsernames()[i]);
        friendRequestPanel[i].add(requestLabel[i]);
        friendRequestPanel[i].add(accept[i]);
        friendRequestPanel[i].add(reject[i]);
        map.put(accept[i], friendRequestInfo.getFriendRequestIds()[i]);//maps the button to the request id
        map.put(reject[i], friendRequestInfo.getFriendRequestIds()[i]);
      } else {//someone else is the target user
        requestLabel[i] = new JLabel();
        requestLabel[i].setText("Sent to: " + friendRequestInfo.getTargetUsernames()[i]);
        requestLabel[i].setPreferredSize(new Dimension(150,25));
        cancel[i] = new JButton("X");
        cancel[i].addActionListener(this);
        cancel[i].setActionCommand("cancel");
        cancel[i].setPreferredSize(new Dimension(105, 25));

        System.out.println("sent friend request to: " + friendRequestInfo.getTargetUsernames()[i]);
        friendRequestPanel[i].add(requestLabel[i]);
        friendRequestPanel[i].add(cancel[i]);
        map.put(cancel[i], friendRequestInfo.getFriendRequestIds()[i]);
      }
      listPanel.add(friendRequestPanel[i]);
    }
    mainPanel.add(requestListPane);
  }
  public void updateFriendRequestInformation(FriendRequestInformation friendRequestInfo){
    this.friendRequestInfo = friendRequestInfo;
    updatePanel();
  }
  public void actionPerformed(ActionEvent e) {
    if ("accept".equals(e.getActionCommand())) {
      JButton temp = ((JButton)e.getSource());//text is the name of the account that the users sends or recieves a message from
      requestID = map.get(temp);
      System.out.println("accept requestID: " + requestID);
      accepted = true;
    } else if("reject".equals(e.getActionCommand())) {
      JButton temp = ((JButton)e.getSource());
      requestID = map.get(temp);
      System.out.println("reject requestID: " + requestID);
      rejected = true;
    }else if("cancel".equals(e.getActionCommand())) {
      JButton temp = ((JButton)e.getSource());
      requestID = map.get(temp);
      System.out.println("cancel requestID: " + requestID);
      cancelled = true;
    } else {
      
    }
  }
}
