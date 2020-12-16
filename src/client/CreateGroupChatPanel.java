package client;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CreateGroupChatPanel extends JPanel implements ActionListener {
  private JCheckBox[] friendIDsBox;
  private JButton createButton;
  private JPanel mainPanel, friendListPanel;
  private JScrollPane friendListPane;
  private HashMap<String, JCheckBox> map;
  private ListOfFriendID friendIDs;
  private JTextField typeField;
  private boolean create;
  private Long[] members;

  CreateGroupChatPanel(ListOfFriendID friendIDs){
    this.friendIDs = friendIDs;
    create = false;
    friendListPanel = new JPanel();
    friendListPane = new JScrollPane(friendListPanel);
    friendListPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    friendListPanel.setPreferredSize(new Dimension(300, 360));
    friendListPane.setPreferredSize(new Dimension(300, 370));
    friendListPanel.add(new JPanel());//temp panel

    updatePanel();

    typeField = new JTextField();
    typeField.setPreferredSize(new Dimension(280,25));

    createButton = new JButton("Create group chat");
    createButton.setActionCommand("create");
    createButton.addActionListener(this);
    createButton.setPreferredSize(new Dimension(280, 25));
    
    mainPanel = new JPanel();
    mainPanel.add(typeField);
    mainPanel.add(createButton);
    mainPanel.add(friendListPane);
    
  }
  //updates the panel with new info
  public void updatePanel(){
    friendListPanel.removeAll();
    map = new HashMap<>();
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
  }
  //updates the info, then updates the panel
  public void updateListOfFriendID(ListOfFriendID friendIDs) {
    this.friendIDs = friendIDs;
    updatePanel();
  }
  //getters
  public JPanel getPanel(){
    return mainPanel;
  }
  public boolean getCreate(){
    return create;
  }
  public Long[] getMembers(){
    return members;
  }
  public String getMessage(){
    String message = typeField.getText();
    typeField.setText("");
    create = false;
    return message;
  }
  //buttons presses that are then sorted
  public void actionPerformed(ActionEvent e) {
    if ("create".equals(e.getActionCommand())) {//creates a croup chat
      create = true;
      System.out.println("Creating group with following members:");
      int count = 0;
      for (int i = 0; i < friendIDsBox.length; i++){//count how many members
        if (friendIDsBox[i].isSelected()){
          System.out.println(friendIDsBox[i].getText());
          count += 1;
        }
      }
      if (count > 0) {//checks that there is at least one member
        members = new Long[count];
        for (int i = 0; i < friendIDsBox.length; i++){
          if (friendIDsBox[i].isSelected()){
            members[i] = friendIDs.getUserIDs()[i];
          }
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
