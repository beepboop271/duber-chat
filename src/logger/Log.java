package logger;

import java.io.PrintStream;
import java.time.Instant;

public class Log {
  private static final boolean USE_COLOURS = true;
  private static final String INFO_COLOUR = "\u001B[94m";
  private static final String WARN_COLOUR = "\u001B[93m";
  private static final String ERROR_COLOUR = "\u001B[91m";

  private static PrintStream out = System.out;

  private Log() {
  }

  private static void log(String level, String message, String source) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source
      + " '" + message + "'"
    );
  }

  private static void log(String level, String message, String source, Object object) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source
      + " '" + message
      + "' " + object
    );
  }

  private static void log(String level, String message, String source, Exception error) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source
      + " '" + message + "'"
    );
    error.printStackTrace(out);
  }
  
  private static void log(
    String level,
    String message,
    String source,
    Object object,
    Exception error
  ) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source
      + " '" + message
      + "' " + object
    );
    error.printStackTrace(out);
  }

  private static void log(
    String level,
    String message,
    String source,
    long id
  ) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source + "-" + id
      + " '" + message + "'"
    );
  }

  private static void log(
    String level,
    String message,
    String source,
    long id,
    Object object
  ) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source + "-" + id
      + " '" + message
      + "' " + object
    );
  }

  private static void log(
    String level,
    String message,
    String source,
    long id,
    Exception error
  ) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source + "-" + id
      + " '" + message + "'"
    );
    error.printStackTrace(out);
  }

  private static void log(
    String level,
    String message,
    String source,
    long id,
    Object object,
    Exception error
  ) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source + "-" + id
      + " '" + message
      + "' " + object
    );
    error.printStackTrace(out);
  }

  public static void info(String message, String source) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", message, source);
    } else {
      log("INFO", message, source);
    }
  }

  public static void info(String message, String source, Object object) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", message, source, object);
    } else {
      log("INFO", message, source, object);
    }
  }

  public static void info(String message, String source, Exception error) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", message, source, error);
    } else {
      log("INFO", message, source, error);
    }
  }

  public static void info(
    String message,
    String source,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", message, source, object, error);
    } else {
      log("INFO", message, source, object, error);
    }
  }

  public static void info(String message, String source, long id) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", message, source, id);
    } else {
      log("INFO", message, source, id);
    }
  }

  public static void info(
    String message,  
    String source,
    long id,
    Object object
  ) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", message, source, id, object);
    } else {
      log("INFO", message, source, id, object);
    }
  }

  public static void info(
    String message,  
    String source,
    long id,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", message, source, id, error);
    } else {
      log("INFO", message, source, id, error);
    }
  }

  public static void info(
    String message,  
    String source,
    long id,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", message, source, id, object, error);
    } else {
      log("INFO", message, source, id, object, error);
    }
  }

  public static void warn(String message, String source) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", message, source);
    } else {
      log("WARN", message, source);
    }
  }

  public static void warn(String message, String source, Object object) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", message, source, object);
    } else {
      log("WARN", message, source, object);
    }
  }

  public static void warn(String message, String source, Exception error) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", message, source, error);
    } else {
      log("WARN", message, source, error);
    }
  }

  public static void warn(
    String message,
    String source,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", message, source, object, error);
    } else {
      log("WARN", message, source, object, error);
    }
  }

  public static void warn(String message, String source, long id) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", message, source, id);
    } else {
      log("WARN", message, source, id);
    }
  }

  public static void warn(
    String message,  
    String source,
    long id,
    Object object
  ) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", message, source, id, object);
    } else {
      log("WARN", message, source, id, object);
    }
  }

  public static void warn(
    String message,  
    String source,
    long id,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", message, source, id, error);
    } else {
      log("WARN", message, source, id, error);
    }
  }

  public static void warn(
    String message,  
    String source,
    long id,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", message, source, id, object, error);
    } else {
      log("WARN", message, source, id, object, error);
    }
  }

  public static void error(String message, String source) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", message, source);
    } else {
      log("ERROR", message, source);
    }
  }

  public static void error(String message, String source, Object object) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", message, source, object);
    } else {
      log("ERROR", message, source, object);
    }
  }

  public static void error(String message, String source, Exception error) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", message, source, error);
    } else {
      log("ERROR", message, source, error);
    }
  }

  public static void error(
    String message,
    String source,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", message, source, object, error);
    } else {
      log("ERROR", message, source, object, error);
    }
  }
  
  public static void error(String message, String source, long id) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", message, source, id);
    } else {
      log("ERROR", message, source, id);
    }
  }

  public static void error(
    String message,  
    String source,
    long id,
    Object object
  ) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", message, source, id, object);
    } else {
      log("ERROR", message, source, id, object);
    }
  }

  public static void error(
    String message,  
    String source,
    long id,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", message, source, id, error);
    } else {
      log("ERROR", message, source, id, error);
    }
  }

  public static void error(
    String message,  
    String source,
    long id,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", message, source, id, object, error);
    } else {
      log("ERROR", message, source, id, object, error);
    }
  }
}
