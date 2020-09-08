import Events.Chatter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;


import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WorkerProcess {
    public static void main(String[] args) throws FileNotFoundException, LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        //File file = new File("src/main/resources/token.txt");
        //Scanner scan = new Scanner(file);
        //String token = scan.nextLine();
        String token = "NzQzMjA0OTI0NDQ3NTIyOTQ3.XzRRhg.cE0--sbLt1GU8YrMD2oSrI-Gq-Y";

        builder.setToken(token);
        builder.addEventListener(new Chatter());
        builder.setGame(Game.playing("with your heart rn"));
        builder.buildAsync();
    }
}