package client;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import messages.Reply;
import messages.DoLogin;
import messages.Reply.Status;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class LoginPanel extends JPanel implements ActionListener {
  private Reply reply;
  private JButton loginButton;
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JPanel westPanel, panel;
  private ObjectInputStream input; //Stream for network input
  private ObjectOutputStream output;
  private boolean running = true; //thread status via boolean
  private String pass;
  private boolean loggedIn;
  private JLabel userLabel, passLabel, errorLabel;
  
  LoginPanel(ObjectInputStream input, ObjectOutputStream output){
    this.input = input;
    this.output = output;
    usernameField = new JTextField(10);
    usernameField.setPreferredSize(new Dimension(200,20));
    passwordField = new JPasswordField(10);
    passwordField.setPreferredSize(new Dimension(200,20));
    loginButton = new JButton("Login");
    loginButton.addActionListener(this);
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
    panel.add(loginButton);
    panel.add(errorLabel);
    loggedIn = false;
  }
  public JPanel getPanel(){
    return panel;
  }
  public boolean getLoggedIn(){
    return loggedIn;
  }
  public String getUsername(){
    return usernameField.getText();
  }
  public void setInputOutput(ObjectInputStream input, ObjectOutputStream output){
    this.input = input;
    this.output = output;
  }
  public void actionPerformed(ActionEvent e) {
    pass = "";//converts a char[] to String
    for (int i = 0; i < passwordField.getPassword().length; i++){
      pass += passwordField.getPassword()[i];
    }

    try{//catchs connection errors
      synchronized (output) {
        try {//catches IOExecptions (error sending object)
          output.writeObject(new DoLogin(usernameField.getText(), pass));
          output.flush();
        } catch (IOException error) {
          errorLabel.setText("Error logging in");
          error.printStackTrace();
        }
      }

      try {//catches errors reading the object
        reply = (Reply)input.readObject();
        if (reply.getStatus() == Status.OK) {
          loggedIn = true;
        } else {
          errorLabel.setText(reply.getDetailMessage());
        }
      } catch (IOException | ClassNotFoundException error) {
        System.out.print("Error reading server response");
        error.printStackTrace();
      }
      
    } catch(NullPointerException error) {
      errorLabel.setText("Error connecting to server");
    }
  }
  
  public void disconnect(){
    try {  //close all the sockets
      input.close();
      System.out.println("closed input");
      output.close();
      System.out.println("closed output");
    }catch (Exception e) { 
      System.out.println("Failed to close socket");
    }
  }
}
