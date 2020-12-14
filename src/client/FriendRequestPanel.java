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
import java.util.HashMap;

public class FriendRequestPanel extends JPanel implements ActionListener {
  private JButton[] accept, reject, cancel;
  private JLabel[] requestLabel;
  private JPanel[] friendRequestPanel;
  private JScrollPane requestListPane;
  private HashMap<String, JCheckBox> map;
  private JPanel mainPanel;
  private FriendRequestInformation friendRequestInfo;
  private String username;
  
  FriendRequestPanel(String username, FriendRequestInformation friendRequestInfo){
    this.username = username;
    this.friendRequestInfo = friendRequestInfo;
    int numberOfRequests = friendRequestInfo.getFriendRequestIds().length;
    accept = new JButton[numberOfRequests];
    reject = new JButton[numberOfRequests];
    cancel = new JButton[numberOfRequests];
    requestLabel = new JLabel[numberOfRequests];
    friendRequestPanel = new JPanel[numberOfRequests];
    mainPanel = new JPanel();
    requestListPane = new JScrollPane();
    requestListPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    mainPanel.setPreferredSize(new Dimension(300, 380));
    requestListPane.setPreferredSize(new Dimension(300, 390));
    for (int i = 0; i < numberOfRequests; i++){
      friendRequestPanel[i] = new JPanel();
      if (username.equals(friendRequestInfo.getTargetUsernames()[i])){//if the client is the target user
        requestLabel[i] = new JLabel();
        requestLabel[i].setText(friendRequestInfo.getSourceUsernames()[i]);
        requestLabel[i].setPreferredSize(new Dimension(240,25));
        accept[i] = new JButton();
        accept[i].addActionListener(this);
        accept[i].setActionCommand("accept");
        accept[i].setPreferredSize(new Dimension(25, 25));
        reject[i] = new JButton();
        reject[i].addActionListener(this);
        reject[i].setActionCommand("reject");
        reject[i].setPreferredSize(new Dimension(25, 25));
        friendRequestPanel[i].add(requestLabel[i]);
        friendRequestPanel[i].add(accept[i]);
        friendRequestPanel[i].add(reject[i]);
      } else {//someone else is the target user
        requestLabel[i] = new JLabel();
        requestLabel[i].setText(friendRequestInfo.getTargetUsernames()[i]);
        requestLabel[i].setPreferredSize(new Dimension(240,25));
        cancel[i] = new JButton();
        cancel[i].addActionListener(this);
        cancel[i].setActionCommand("cancel");
        cancel[i].setPreferredSize(new Dimension(55, 25));
        friendRequestPanel[i].add(requestLabel[i]);
        friendRequestPanel[i].add(cancel[i]);
      }
      requestListPane.add(friendRequestPanel[i]);
    }
    mainPanel.add(requestListPane);
  }
  public void actionPerformed(ActionEvent e) {
    if ("accept".equals(e.getActionCommand())) {
      
    } else if("reject".equals(e.getActionCommand())) {
      
    }else if("cancel".equals(e.getActionCommand())) {
      
    } else {
      
    }
  }
}
