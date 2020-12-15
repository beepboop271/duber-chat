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

public class FriendPanel {
  private JButton[] removeFriend;
  private JLabel[] friendLabel;
  private JPanel friendPanel;
  private JScrollPane friendListPane;
  private HashMap<String, JCheckBox> map;
  private JPanel mainPanel, friendListPanel;
  
  FriendPanel(){
    
  }
}
