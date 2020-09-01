package Events;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;

public class Chatter extends ListenerAdapter {


    public void onMessageReceived(MessageReceivedEvent event) {
        Bot bot = new Bot(BotConfiguration.builder().name("ava").path("src\\main\\resources").build());
        Chat chatty= new Chat(bot);
        System.out.println("Message received from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        if (event.getAuthor().isBot()) {
            return;
        }
        if (event.getMessage().getContentRaw().equals("!egirl")) {
            event.getChannel().sendMessage("ayo stfu").queue();

        }

        event.getJDA().removeEventListener(this);
        event.getJDA().addEventListener(new Chatter());
    }

}
