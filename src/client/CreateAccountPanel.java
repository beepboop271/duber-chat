package client;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import messages.CommandReply;
import messages.CreateAccount;
import messages.CommandReply.Status;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Flow;

public class CreateAccountPanel extends JPanel implements ActionListener {
  private CommandReply reply;
  private JButton createAccountButton;
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JPanel westPanel, panel;
  private FlowLayout layout;
  private ObjectInputStream input; //Stream for network input
  private ObjectOutputStream output;
  private boolean running = true; //thread status via boolean
  private String pass;

  private JLabel userLabel, passLabel, errorLabel;
  
  CreateAccountPanel(ObjectInputStream input, ObjectOutputStream output){
    this.input = input;
    this.output = output;
    layout = new FlowLayout();
    usernameField = new JTextField(10);
    usernameField.setPreferredSize(new Dimension(200,20));
    passwordField = new JPasswordField(10);
    passwordField.setPreferredSize(new Dimension(200,20));
    createAccountButton = new JButton("Create");
    createAccountButton.addActionListener(this);
    userLabel = new JLabel("Dubername");
    passLabel = new JLabel("Password");
    errorLabel = new JLabel("");
    westPanel = new JPanel();
    panel = new JPanel();
    westPanel.add(userLabel);
    westPanel.add(usernameField);
    westPanel.add(passLabel);
    westPanel.add(passwordField);
    panel.add(westPanel);
    panel.add(createAccountButton);
    panel.add(errorLabel);
  }
  public JPanel getPanel(){
    return panel;
  }
  
  public void setInputOutput(ObjectInputStream input, ObjectOutputStream output){
    this.input = input;
    this.output = output;
  }
  public void actionPerformed(ActionEvent e) {
    pass = "";
    for (int i = 0; i < passwordField.getPassword().length; i++){
      pass += passwordField.getPassword()[i];
    }
    try{
      synchronized (output) {

        try {
          output.writeObject(new CreateAccount(usernameField.getText(), pass));
          output.flush();
        } catch (IOException error) {
          errorLabel.setText("Error creating account");
          error.printStackTrace();
        }
      }

      try {
        reply = (CommandReply)input.readObject();
        if (reply.getStatus() == Status.OK) {
          errorLabel.setText("Account created! Please login though the login tab.");
        } else {
          errorLabel.setText(reply.getDetailMessage());
        }
      } catch (IOException | ClassNotFoundException error) {
        System.out.print("Error reading server response");
        error.printStackTrace();
      }
      
    } catch(NullPointerException error) {
      errorLabel.setText("Error connecting to server");
      error.printStackTrace();
    }
    
    
  }

}
