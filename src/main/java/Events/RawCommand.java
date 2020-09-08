package Events;

public class RawCommand {

    public static String rawify(String command) {
        String newString;
        newString = command.replace("<", "");
        newString = newString.replace(">", "");
        newString = newString.replace("@", "");
        return newString;
    }
}
