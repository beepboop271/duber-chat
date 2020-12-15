package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import messages.*;

public class PubSubInput implements Runnable {
  private ObjectInputStream pubsubInput;
  private ChatPanel chatPanel;
  private ChatList chatList;
  private CreateGroupChat createGroupChat;
  private ListOfFriendID listOfFriendID;
  private FriendPanel friendPanel;
  private FriendRequestPanel friendRequestPanel;
  private boolean running;
  private Object pubSubMessage;
  private PubSubDmJoined dmJoined;
  private PubSubFriendRequestCreated friendRequestCreated;
  private PubSubFriendRequestFailed friendRequestFailed;
  private PubSubFriendUpdate friendUpdate;
  private PubSubGroupChatJoined groupChatJoined;
  private PubSubMessageReceived messageReceived;
  private PubSubStatusChange statusChange;

  PubSubInput(ObjectInputStream pubsubInput, ChatPanel chatPanel, ChatList chatList, CreateGroupChat createGroupChat, ListOfFriendID listOfFriendID, FriendPanel friendPanel, FriendRequestPanel friendRequestPanel, boolean running){
    this.pubsubInput = pubsubInput;
    this.chatPanel = chatPanel;
    this.chatList = chatList;
    this.createGroupChat = createGroupChat;
    this.listOfFriendID = listOfFriendID;
    this.friendPanel = friendPanel;
    this.friendRequestPanel = friendRequestPanel;
    this.running = true;
  }

  public void run() {
    while(running){
      try {
        pubSubMessage = pubsubInput.readObject();

      } catch (IOException | ClassNotFoundException error) {
        System.out.print("Error reading pubsub message");
        error.printStackTrace();
      }
      if (pubSubMessage instanceof PubSubDmJoined){
        dmJoined = ((PubSubDmJoined)pubSubMessage);
        //dmJoined.getChatId();
      } else if (pubSubMessage instanceof PubSubFriendRequestCreated){

      } else if (pubSubMessage instanceof PubSubFriendRequestFailed){

      } else if (pubSubMessage instanceof PubSubFriendUpdate){

      } else if (pubSubMessage instanceof PubSubGroupChatJoined){

      } else if (pubSubMessage instanceof PubSubMessageReceived){

      } else if (pubSubMessage instanceof PubSubStatusChange){

      }
      
    }
    try{
      pubsubInput.close();
    }catch (Exception e) { 
      System.out.println("Failed to close socket");
    }
  }
}
