package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
    // final
    // fields
    // constructors
    // getters & setters
    // methods
    // static methods

    public static String forConsole() {
        LocalTime localTimeNow = LocalTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeForConsole = "[" + localTimeNow.format(format) + "]";
        return timeForConsole;
    }
}
