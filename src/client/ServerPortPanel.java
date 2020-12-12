package client;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.GridLayout;
import java.net.Socket;

public class ServerPortPanel extends JPanel implements ActionListener {
  private JButton connectButton;
  private JTextField IPField, portField;
  private JPanel panel, eastPanel, westPanel;
  private JLabel IPLabel, portLabel, errorLabel;
  private Socket mySocket;

  ServerPortPanel() {
    panel = new JPanel();
    westPanel =  new JPanel();
    connectButton = new JButton("Connect");
    connectButton.addActionListener(new ConnectButtonListener());
    IPField = new JTextField("127.0.0.1",10);
    portField = new JTextField("5000", 10);
    IPLabel = new JLabel("IP Address:");
    portLabel = new JLabel("Port:");
    errorLabel = new JLabel("");
    westPanel.add(portLabel);
    westPanel.add(portField);
    westPanel.add(IPLabel);
    westPanel.add(IPField);
    panel.add(westPanel);
    panel.add(connectButton);
    panel.add(errorLabel);
    //auto connects
    int port;
    try {
      port = Integer.parseInt(portField.getText());
      connect(IPField.getText(), port);
    } catch (NumberFormatException error) {
      errorLabel.setText("Port was not a number");
    }
  }

  public JPanel getPanel() {
    return panel;
  }
  public Socket getSocket(){
    return mySocket;
  }

  public Socket connect(String ip, int port) {
    System.out.println("Attempting to make a connection..");
    try {
      mySocket = new Socket(ip, port); // attempt socket connection (local address). This will wait until a connection is made
      ObjectInputStream input = new ObjectInputStream(mySocket.getInputStream()); // Stream for network input
      ObjectOutputStream output = new ObjectOutputStream(mySocket.getOutputStream()); // Stream for network output
      errorLabel.setText("Connection made.");
    } catch (IOException e) { // connection error occured
      errorLabel.setText("Connection to Server Failed");
      e.printStackTrace();
    }
    return mySocket;
  }

  class ConnectButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int port;
      try {
        port = Integer.parseInt(portField.getText());
        connect(IPField.getText(), port);
      } catch (NumberFormatException error) {
        errorLabel.setText("Port was not a number");
      }
    }
  }
}

