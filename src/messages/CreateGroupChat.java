package messages;
public class CreateGroupChat {
    long[] userIds;
    //boolean private;
    String name;
    CreateGroupChat(long[] users, String groupname){
        this.userIds = users;
        this.name = groupname;
    }
}
