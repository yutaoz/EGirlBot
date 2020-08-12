package Events;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Chat extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("Message received from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        if (event.getAuthor().isBot()) {
            return;
        }
        event.getJDA().removeEventListener(this);
        event.getJDA().addEventListener(new Chat());
    }

}
