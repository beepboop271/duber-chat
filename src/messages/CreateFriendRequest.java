package messages;
public class CreateFriendRequest {
    String targetUsername;
    CreateFriendRequest(String targetUser) {
        this.targetUsername = targetUser;
    }
}
