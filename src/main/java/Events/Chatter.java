package Events;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;

public class Chatter extends ListenerAdapter {


    public void onMessageReceived(MessageReceivedEvent event) {
        Bot bot = new Bot(BotConfiguration.builder().name("ava").path("src\\main\\resources").build());
        Chat chatty= new Chat(bot);
        System.out.println("Message received from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        if (event.getAuthor().isBot()) {
            return;
        }
        if (event.getMessage().getContentRaw().startsWith("!")) {
            String message = event.getMessage().getContentRaw().substring(1);
            String reply = chatty.multisentenceRespond(message);
            event.getChannel().sendMessage(reply).queue();
        }

        event.getJDA().removeEventListener(this);
        event.getJDA().addEventListener(new Chatter());
    }

}
