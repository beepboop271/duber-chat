package reduber;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * An operation which attempts to send a single message to a
 * specific user.
 *
 * @author Kevin Qiao
 */
class PublishDirect extends OperationData<Long, ReDuber.Status> {
  private final Serializable message;

  PublishDirect(Long args, Serializable message) {
    super("", args);
    this.message = message;
  }

  @Override
  public String toString() {
    return "PublishDirect[key="
      +this.getKey()
      +", args="+this.getArgs()
      +", message="+this.message
      +", id="+this.getId()
      +", result="+this.getResult()
      +"]";
  }

  void publishDirect(
    Map<Long, Set<ObjectOutputStream>> loggedInUsers,
    ReDuberPubSub pubSub
  ) {
    Long sub = this.getArgs();
    if (sub == null) {
      this.getResult().complete(ReDuber.Status.OK);
      return;
    }
    Set<ObjectOutputStream> streams = loggedInUsers.get(sub);
    if (streams != null) {
      for (ObjectOutputStream stream : streams) {
        pubSub.submit(this.message, stream);
      }
    }
    this.getResult().complete(ReDuber.Status.OK);
  }
}
