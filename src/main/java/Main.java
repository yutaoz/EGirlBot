import Events.Chat;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        File file = new File("\\src\\main\\resources\\token.txt");
        Scanner scan = new Scanner(file);
        String token = scan.nextLine();
        builder.setToken(token);
        builder.addEventListener(new Chat());
        builder.setGame(Game.playing("say hey ava!"));
        builder.buildAsync();
    }
}