package Events;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.alicebot.ab.Chat;

public class Chatter extends ListenerAdapter {
    Chat chatty;
    public Chatter(Chat chatSession) {
        chatty = chatSession;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("Message received from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        if (event.getAuthor().isBot()) {
            return;
        }
        if (event.getMessage().getContentRaw().startsWith("!")) {
            String message = event.getMessage().getContentRaw();
            message = message.substring(1);
            String reply = chatty.multisentenceRespond(message);
            event.getChannel().sendMessage(reply).queue();
        }

        event.getJDA().removeEventListener(this);
        event.getJDA().addEventListener(new Chatter(chatty));
    }

}
