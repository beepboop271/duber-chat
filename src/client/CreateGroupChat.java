package client;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
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

public class CreateGroupChat extends JPanel implements ActionListener {
  private JCheckBox[] friendIDsBox;
  private JButton createButton;
  private JTextField typeField;
  private JPanel mainPanel, friendListPanel;
  private JScrollPane friendListPane;
  private JLabel chatLabel;
  private HashMap<String, JCheckBox> map;
  private boolean running, request, send;
  private ListOfFriendID friendIDs;

  CreateGroupChat(ListOfFriendID friendIDs){
    this.friendIDs = friendIDs;
    map = new HashMap<>();
    friendListPanel = new JPanel();
    friendListPane = new JScrollPane(friendListPanel);
    friendListPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    friendListPanel.setPreferredSize(new Dimension(200, 270));
    friendListPane.setPreferredSize(new Dimension(200, 270));
    String tempFriendName;
    friendIDsBox = new JCheckBox[friendIDs.getFriendInfo().length];
    for (int i = 0; i < friendIDs.getFriendInfo().length; i++) {
      tempFriendName = friendIDs.getFriendInfo()[i].getUsername();//temp name holder
      friendIDsBox[i] = new JCheckBox(tempFriendName);//new checkbox with friend name
      friendIDsBox[i].addActionListener(this);//add action listener
      friendIDsBox[i].setSelected(false);//forces it to be empty check box
      friendIDsBox[i].setActionCommand("include");//sets the actions command
      friendIDsBox[i].setPreferredSize(new Dimension(180, 25));//set the prefered size
      friendIDsBox[i].setHorizontalAlignment(2);//sets the allignment to the left
      map.put(tempFriendName, friendIDsBox[i]);
      friendListPanel.add(friendIDsBox[i]);
    }
    createButton = new JButton("Create group chat");
    createButton.setActionCommand("create");
    createButton.addActionListener(this);
    createButton.setPreferredSize(new Dimension(180, 25));

    mainPanel = new JPanel();
    mainPanel.add(friendListPane);
    mainPanel.add(createButton);
  }

  public JPanel getPanel(){
    return mainPanel;
  }

  public void actionPerformed(ActionEvent e) {
    if ("create".equals(e.getActionCommand())) {
      
    } else {
      String checkboxName = ((JCheckBox)e.getSource()).getText();
      JCheckBox checkBox = map.get(checkboxName);
      if (checkBox.isSelected()){
        checkBox.setSelected(false);
      } else {
        checkBox.setSelected(true);
      }

    }
    
  }
}
