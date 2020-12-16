package reduber;

import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * An operation which registers the given ObjectOutputStream
 * as a logged in user. All streams are stored with a weak
 * set, so they can be garbage collected if no longer
 * referenced outside of the database.
 *
 * @author Kevin Qiao
 */
class PubSubLogin extends OperationData<Long, ReDuber.Status> {
  private final ObjectOutputStream out;

  PubSubLogin(Long args, ObjectOutputStream out) {
    super("", args);
    this.out = out;
  }

  void login(Map<Long, Set<ObjectOutputStream>> db) {
    Set<ObjectOutputStream> streams = db.get(this.getArgs());
    if (streams == null) {
      // https://www.ibm.com/developerworks/library/j-jtp11225/index.html
      // https://stackoverflow.com/questions/4062919/why-does-exist-weakhashmap-but-absent-weakset
      streams = Collections.newSetFromMap(
        new WeakHashMap<ObjectOutputStream, Boolean>()
      );
      db.put(this.getArgs(), streams);
    }
    streams.add(this.out);
    this.getResult().complete(ReDuber.Status.OK);
  }
}
