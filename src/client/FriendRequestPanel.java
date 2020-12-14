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

public class FriendRequestPanel {
  private JButton[] acceptReject;
  private JLabel[] requestLabel;
  private JPanel[] friendRequestPanel;
  private JScrollPane requestListPane;
  private HashMap<String, JCheckBox> map;
  private JPanel mainPanel;
  private FriendRequestInformation friendRequestInfo;
  
  FriendRequestPanel(){
    int numberOfRequests = friendRequestInfo.getFriendRequestIds().length;
    acceptReject = new JButton[numberOfRequests * 2];//two buttons per request (accept rejects)
    requestLabel = new JLabel[numberOfRequests];
    friendRequestPanel = new JPanel[numberOfRequests];
    mainPanel = new JPanel();
    requestListPane = new JScrollPane();
    requestListPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    mainPanel.setPreferredSize(new Dimension(300, 380));
    requestListPane.setPreferredSize(new Dimension(300, 390));
    for (int i = 0; i < numberOfRequests; i++){
      
    }
  }
}
