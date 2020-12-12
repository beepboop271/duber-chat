package client;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import messages.CommandReply;
import messages.DoLogin;
import messages.CommandReply.Status;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginPanel extends JPanel implements ActionListener {
  private CommandReply reply;
  private JButton loginButton;
  private JTextArea usernameField;  
  private JPasswordField passwordField;
  private JPanel panel;
  private ObjectInputStream input; //Stream for network input
  private ObjectOutputStream output;
  private boolean running = true; //thread status via boolean
  private String pass;
  private JLabel userLabel, passLabel;
  
  LoginPanel(ObjectInputStream input, ObjectOutputStream output){
    this.input = input;
    this.output = output;
    usernameField = new JTextArea();
    passwordField = new JPasswordField();
    loginButton = new JButton("Login");
    userLabel = new JLabel("Dubername");
    passLabel = new JLabel("Password");
    panel = new JPanel();
    panel.setLayout(new GridLayout(0,1));
    panel.add(userLabel);
    panel.add(usernameField);
    panel.add(passLabel);
    panel.add(passwordField);
    panel.add(loginButton);

  }
  public JPanel getPanel(){
    return panel;
  }
  public void actionPerformed(ActionEvent e) {
    pass = "";
    for (int i = 0; i < passwordField.getPassword().length; i++){
      pass += passwordField.getPassword()[i];
    }
    synchronized (this.output) {
      try {
        this.output.writeObject(new DoLogin(usernameField.getText(), pass));
        this.output.flush();
      } catch (IOException error) {
        System.out.print("Error logging in");
        error.printStackTrace();
      }
    }
    try {
      reply = (CommandReply)this.input.readObject();
    } catch (IOException | ClassNotFoundException error) {
      System.out.print("Error reading info");
      error.printStackTrace();
    }

    
  }

}
