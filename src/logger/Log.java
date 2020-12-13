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

  private static void log(String level, String source, String message) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source
      + " '" + message + "'"
    );
  }

  private static void log(String level, String source, String message, Object object) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source
      + " '" + message
      + "' " + object
    );
  }

  private static void log(String level, String source, String message, Exception error) {
    Log.out.println(
      level
      + " " + Instant.now()
      + " " + source
      + " '" + message
      + "'"
    );
    error.printStackTrace(out);
  }
  
  private static void log(
    String level,
    String source,
    String message,
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

  public static void info(String source, String message) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", source, message);
    } else {
      log("INFO", source, message);
    }
  }

  public static void info(String source, String message, Object object) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", source, message, object);
    } else {
      log("INFO", source, message, object);
    }
  }

  public static void info(String source, String message, Exception error) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", source, message, error);
    } else {
      log("INFO", source, message, error);
    }
  }

  public static void info(
    String source,
    String message,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.INFO_COLOUR+"INFO", source, message, object, error);
    } else {
      log("INFO", source, message, object, error);
    }
  }

  public static void warn(String source, String message) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", source, message);
    } else {
      log("WARN", source, message);
    }
  }

  public static void warn(String source, String message, Object object) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", source, message, object);
    } else {
      log("WARN", source, message, object);
    }
  }

  public static void warn(String source, String message, Exception error) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", source, message, error);
    } else {
      log("WARN", source, message, error);
    }
  }

  public static void warn(
    String source,
    String message,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.WARN_COLOUR+"WARN", source, message, object, error);
    } else {
      log("WARN", source, message, object, error);
    }
  }

  public static void error(String source, String message) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", source, message);
    } else {
      log("ERROR", source, message);
    }
  }

  public static void error(String source, String message, Object object) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", source, message, object);
    } else {
      log("ERROR", source, message, object);
    }
  }

  public static void error(String source, String message, Exception error) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", source, message, error);
    } else {
      log("ERROR", source, message, error);
    }
  }

  public static void error(
    String source,
    String message,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(Log.ERROR_COLOUR+"ERROR", source, message, object, error);
    } else {
      log("ERROR", source, message, object, error);
    }
  }
}
