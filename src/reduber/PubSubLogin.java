package reduber;

import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

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
