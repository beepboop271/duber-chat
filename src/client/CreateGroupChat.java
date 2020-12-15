package client;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CreateGroupChat extends JPanel implements ActionListener {
  private JCheckBox[] friendIDsBox;
  private JButton createButton;
  private JPanel mainPanel, friendListPanel;
  private JScrollPane friendListPane;
  private HashMap<String, JCheckBox> map;
  private ListOfFriendID friendIDs;

  CreateGroupChat(ListOfFriendID friendIDs){
    this.friendIDs = friendIDs;
    map = new HashMap<>();
    friendListPanel = new JPanel();
    friendListPane = new JScrollPane(friendListPanel);
    friendListPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    friendListPanel.setPreferredSize(new Dimension(300, 360));
    friendListPane.setPreferredSize(new Dimension(300, 370));
    String tempFriendName;
    friendIDsBox = new JCheckBox[friendIDs.getFriendInfo().length];
    for (int i = 0; i < friendIDs.getFriendInfo().length; i++) {
      tempFriendName = friendIDs.getFriendInfo()[i].getUsername();//temp name holder
      friendIDsBox[i] = new JCheckBox(tempFriendName);//new checkbox with friend name
      friendIDsBox[i].addActionListener(this);//add action listener
      friendIDsBox[i].setSelected(false);//forces it to be empty check box
      friendIDsBox[i].setActionCommand("include");//sets the actions command
      friendIDsBox[i].setPreferredSize(new Dimension(280, 25));//set the prefered size
      friendIDsBox[i].setHorizontalAlignment(2);//sets the allignment to the left
      map.put(tempFriendName, friendIDsBox[i]);
      friendListPanel.add(friendIDsBox[i]);
    }
    createButton = new JButton("Create group chat");
    createButton.setActionCommand("create");
    createButton.addActionListener(this);
    createButton.setPreferredSize(new Dimension(280, 25));

    
    mainPanel = new JPanel();
    mainPanel.add(createButton);
    mainPanel.add(friendListPane);
    
  }

  public JPanel getPanel(){
    return mainPanel;
  }

  public void actionPerformed(ActionEvent e) {
    if ("create".equals(e.getActionCommand())) {
      System.out.println("Creating group with following members:");
      for (int i = 0; i < friendIDsBox.length; i++){
        if (friendIDsBox[i].isSelected()){
          System.out.println(friendIDsBox[i].getText());
        }
      }
    } else {
      String checkboxName = ((JCheckBox)e.getSource()).getText();
      JCheckBox checkBox = map.get(checkboxName);//creates a reference
      if (!checkBox.isSelected()){
        map.get(checkboxName).setSelected(false);//changes the checkbox in the hashmap
      } else {
        map.get(checkboxName).setSelected(true);
      }

    }
    
  }
}
