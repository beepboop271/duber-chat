package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import messages.*;
import messages.GetUser.GetUserReply;
import messages.PubSubFriendUpdate.Type;
import messages.Reply.Status;

public class PubSubInput implements Runnable {
  private ObjectInputStream input, pubsubInput; //Stream for network input
  private ObjectOutputStream output;
  private ChatPanel chatPanel;
  private ChatList chatList;
  private CreateGroupChatPanel createGroupChat;
  private ListOfFriendID listOfFriendID;
  private FriendPanel friendPanel;
  private FriendRequestPanel friendRequestPanel;
  private FriendRequestInformation friendRequestInformation;
  private boolean running;
  private Object pubSubMessage;
  private PubSubDmJoined dmJoined;
  private PubSubFriendRequestCreated friendRequestCreated;
  private PubSubFriendRequestFailed friendRequestFailed;
  private PubSubFriendUpdate friendUpdate;
  private PubSubGroupChatJoined groupChatJoined;
  private PubSubMessageReceived messageReceived;
  private PubSubStatusChange statusChange;
  private GetUserReply getUser;
  private Reply reply;
  private String username;

  //import all major panels and their main object used
  PubSubInput(ObjectInputStream pubsubInput, ObjectInputStream input, ObjectOutputStream output,
            ChatPanel chatPanel, ChatList chatList, CreateGroupChatPanel createGroupChat, ListOfFriendID listOfFriendID, 
            FriendPanel friendPanel, FriendRequestPanel friendRequestPanel, FriendRequestInformation friendRequestInformation, boolean running, String username){
    this.input = input;
    this.output = output;
    this.pubsubInput = pubsubInput;
    this.chatPanel = chatPanel;
    this.chatList = chatList;
    this.createGroupChat = createGroupChat;
    this.listOfFriendID = listOfFriendID;
    this.friendPanel = friendPanel;
    this.friendRequestPanel = friendRequestPanel;
    this.friendRequestInformation = friendRequestInformation;
    this.username = username;
    this.running = true;
  }

  public void run() {
    while(running){
      try {
        pubSubMessage = pubsubInput.readObject();

      } catch (IOException | ClassNotFoundException error) {
        System.out.print("Error reading pubsub message");
        error.printStackTrace();
        return;
      }
      //tests to see what pubsub mesage it is
      if (pubSubMessage instanceof PubSubDmJoined){//joins a dm
        dmJoined = ((PubSubDmJoined)pubSubMessage);
        try{//catchs connection errors
          synchronized (output) {
            try {
              output.writeObject(new GetUser(dmJoined.getUserId()));
              output.flush();
            } catch (IOException e) {
            }
          }
          try {//catches errors reading the object
            getUser = (GetUserReply)input.readObject();
            if (getUser.getStatus() == Status.OK){
              
              //updates the chat list
              chatList.addChat(getUser.getUsername(), dmJoined.getChatId(), new ChatInformation(), dmJoined.getChatId());//created a new chat with empty list
            }

          } catch (IOException | ClassNotFoundException error) {
            System.out.print("Error reading server response");
            error.printStackTrace();
          }
        } catch(NullPointerException error) {
          System.out.println("error connecting");
        }

        dmJoined.getChatId();
      } else if (pubSubMessage instanceof PubSubFriendRequestCreated){//friend request info to be stored
        friendRequestCreated = ((PubSubFriendRequestCreated)pubSubMessage);
        friendRequestInformation.addRequest(friendRequestCreated.getFriendRequestId(), friendRequestCreated.getSourceUsername(), friendRequestCreated.getTargetUsername());
        friendRequestPanel.updateFriendRequestInformation(friendRequestInformation);
        System.out.println("got friend req");

      } else if (pubSubMessage instanceof PubSubFriendRequestFailed){//cancelled or rejected friend requests
        friendRequestFailed = ((PubSubFriendRequestFailed)pubSubMessage);
        friendRequestInformation.removeRequest(friendRequestCreated.getFriendRequestId());
        friendRequestPanel.updateFriendRequestInformation(friendRequestInformation);
        System.out.print("friend req no accepted");

      } else if (pubSubMessage instanceof PubSubFriendUpdate){//updates the friend list
        friendUpdate = ((PubSubFriendUpdate)pubSubMessage);
        if (friendUpdate.getType() == Type.ADD){
          System.out.println("friend has been obtained");
          try{//catchs connection errors
            synchronized (output) {
              synchronized (input) {
                try {
                  output.writeObject(new GetUser(friendUpdate.getUserId()));
                  System.out.println("getting friend name");
                  output.flush();
                } catch (IOException e) {
                  System.out.println("could not get friend name");
                }
              }
              try {//catches errors reading the object
                getUser = (GetUserReply)input.readObject();
                if (getUser.getStatus() == Status.OK){
                  //updates the friends list
                  friendRequestInformation.removeRequest(getUser.getUsername(), username);
                  friendRequestPanel.updateFriendRequestInformation(friendRequestInformation);
                  System.out.println("new friend: " + getUser.getUsername());
                  listOfFriendID.addFriend(friendUpdate.getUserId(), new FriendInformation(getUser.getUsername(), getUser.getUserStatus(), getUser.getUserMessage()));//AAAAAAAAAAAAAAAAAAAAApain
                  createGroupChat.updateListOfFriendID(listOfFriendID);
                  friendPanel.updateListOfFriendID(listOfFriendID);
                }
              } catch (IOException | ClassNotFoundException error) {
                System.out.print("Error reading server response");
                error.printStackTrace();
              }
            }
          } catch(NullPointerException error) {
            System.out.println("error connecting");
          }
          
        } else {
          listOfFriendID.removeFriend(friendUpdate.getUserId());//removes friend
          friendPanel.updateListOfFriendID(listOfFriendID);
        }
      } else if (pubSubMessage instanceof PubSubGroupChatJoined){//joins a group chat
        groupChatJoined = ((PubSubGroupChatJoined)pubSubMessage);
        chatList.addChat(groupChatJoined.getName(), groupChatJoined.getChatId(), new ChatInformation(), groupChatJoined.getLastMessageId());
        chatPanel.chatUpdate(chatList);

      } else if (pubSubMessage instanceof PubSubMessageReceived){//adds a message to a chat
        messageReceived = ((PubSubMessageReceived)pubSubMessage);
        for (int i = 0; i < chatList.getChatIDs().length; i++){
          if (chatList.getChatIDs()[i] == messageReceived.getChatId()){
            chatList.getChatInfo()[i].addMessage(messageReceived.getMessageId(), messageReceived.getUserId(), messageReceived.getTime(), messageReceived.getMessage());
          }
        }
        
        
      } else if (pubSubMessage instanceof PubSubStatusChange){//changes a user's status
        statusChange = ((PubSubStatusChange)pubSubMessage);
        
      }
      
    }
    //exits and closes sockets
    try{
      pubsubInput.close();
    }catch (Exception e) { 
      System.out.println("Failed to close socket");
    }
  }
}
