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
  private CreateGroupChat createGroupChat;
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


  PubSubInput(ObjectInputStream pubsubInput, ObjectInputStream input, ObjectOutputStream output,
            ChatPanel chatPanel, ChatList chatList, CreateGroupChat createGroupChat, ListOfFriendID listOfFriendID, 
            FriendPanel friendPanel, FriendRequestPanel friendRequestPanel, FriendRequestInformation friendRequestInformation, boolean running){
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
      if (pubSubMessage instanceof PubSubDmJoined){
        dmJoined = ((PubSubDmJoined)pubSubMessage);
        System.out.println("inv to join dm");
        try{//catchs connection errors
          synchronized (output) {
            try {
              output.writeObject(new GetUser(dmJoined.getUserId()));
              output.flush();
            } catch (IOException e) {
              System.out.println("could not get friend name");
            }
          }
          try {//catches errors reading the object
            getUser = (GetUserReply)input.readObject();
            if (getUser.getStatus() == Status.OK){
              
              //updates the chat list
              chatList.addChat(getUser.getUsername(), dmJoined.getChatId(), new ChatInformation(new long[0], new long[0], new long[0], new String[0]));//created a new chat with empty list
            }

          } catch (IOException | ClassNotFoundException error) {
            System.out.print("Error reading server response");
            error.printStackTrace();
          }
        } catch(NullPointerException error) {
          System.out.println("error connecting");
        }

        dmJoined.getChatId();
      } else if (pubSubMessage instanceof PubSubFriendRequestCreated){
        friendRequestCreated = ((PubSubFriendRequestCreated)pubSubMessage);
        friendRequestInformation.addRequest(friendRequestCreated.getFriendRequestId(), friendRequestCreated.getSourceUsername(), friendRequestCreated.getTargetUsername());
        friendRequestPanel.updateFriendRequestInformation(friendRequestInformation);
        System.out.println("got friend req");

      } else if (pubSubMessage instanceof PubSubFriendRequestFailed){
        friendRequestFailed = ((PubSubFriendRequestFailed)pubSubMessage);
        friendRequestInformation.removeRequest(friendRequestCreated.getFriendRequestId());
        friendRequestPanel.updateFriendRequestInformation(friendRequestInformation);
        System.out.print("friend req no accepted");

      } else if (pubSubMessage instanceof PubSubFriendUpdate){
        friendUpdate = ((PubSubFriendUpdate)pubSubMessage);
        if (friendUpdate.getType() == Type.ADD){
          System.out.println("friend has been obtained");
          try{//catchs connection errors
            synchronized (output) {
              try {
                output.writeObject(new GetUser(friendUpdate.getUserId()));
                output.flush();
              } catch (IOException e) {
                System.out.println("could not get friend name");
              }
            }
            try {//catches errors reading the object
              getUser = (GetUserReply)input.readObject();
              if (getUser.getStatus() == Status.OK){
                //updates the friends list
                listOfFriendID.addFriend(friendUpdate.getUserId(), new FriendInformation(getUser.getUsername(), getUser.getUserStatus(), getUser.getUserMessage()));//AAAAAAAAAAAAAAAAAAAAApain
                friendPanel.updateListOfFriendID(listOfFriendID);
              }
            } catch (IOException | ClassNotFoundException error) {
              System.out.print("Error reading server response");
              error.printStackTrace();
            }
          } catch(NullPointerException error) {
            System.out.println("error connecting");
        }

        try{//catchs connection errors
          synchronized (output) {
            //creates a dm
            try {//sends a get command to accept friend request
              output.writeObject(new CreateDm(friendUpdate.getUserId()));
              output.flush();
            } catch (IOException e) {
              System.out.println("could not reject friend request");
            }

            try {//catches errors reading the object
              reply = (Reply)input.readObject();
              if (reply.getStatus() == Status.OK){
                System.out.println("dm created");
              } else {
                System.out.println("could not create dm");
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

          listOfFriendID.removeFriend(friendUpdate.getUserId());
        }
      } else if (pubSubMessage instanceof PubSubGroupChatJoined){
        groupChatJoined = ((PubSubGroupChatJoined)pubSubMessage);

      } else if (pubSubMessage instanceof PubSubMessageReceived){
        messageReceived = ((PubSubMessageReceived)pubSubMessage);
        int index = -1;
        for (int i = 0; i < chatList.getChatIDs().length; i++){
          if (messageReceived.getChatId() == chatList.getChatIDs()[i]){
            index = i;
          }
        }
        if (index != -1){
          chatList.getChatInfo()[index].addMessage(messageReceived.getMessageId(), messageReceived.getUserId(), messageReceived.getTime(), messageReceived.getMessage());
          chatPanel.chatUpdate(chatList);
        }
      } else if (pubSubMessage instanceof PubSubStatusChange){
        statusChange = ((PubSubStatusChange)pubSubMessage);
        
      }
      
    }
    try{
      pubsubInput.close();
    }catch (Exception e) { 
      System.out.println("Failed to close socket");
    }
  }
}
