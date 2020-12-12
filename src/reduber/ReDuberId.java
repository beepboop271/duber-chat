package reduber;

import java.util.concurrent.atomic.AtomicInteger;

public class ReDuberId {
  private static AtomicInteger count = new AtomicInteger();

  public static long getId() {
    long id = (System.currentTimeMillis() << 16) | count.getAndIncrement();
    count.compareAndSet(1 << 16, 0);
    return id;
  }
}
