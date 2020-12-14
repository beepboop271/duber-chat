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
  private JLabel[] chatLabel;
  private JPanel[] friendRequestPanel;
  private JScrollPane requestListPane;
  private HashMap<String, JCheckBox> map;
  private JPanel mainPanel, friendListPanel;
  
  FriendRequestPanel(){
    
  }
}
