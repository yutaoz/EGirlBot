package Events;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


import java.io.*;

import static Events.AniLink.anilink;
import static Events.RawCommand.rawify;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.LongArgumentType.*;
import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class Chatter extends ListenerAdapter {
    Object source = new Object();
    CommandDispatcher<Object> dispatcher = new CommandDispatcher<>();

    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("Message received from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        System.out.println(event.getMessage().getContentRaw());
        if (event.getAuthor().isBot()) {
            return;
        }
        String cmd = event.getMessage().getContentRaw();
        String rawCmd = rawify(cmd);

        System.out.println(rawCmd);

        dispatcher.register(
                literal("/ping")
                        .then(
                                argument("user", longArg() )
                                            .then(
                                                    argument("count", integer())
                                                            .then(
                                                                    argument("message", string())
                                                                    .executes(c -> {
                                                                        int count = getInteger(c, "count");
                                                                        if (count < 1 || count > 10) {
                                                                            event.getChannel().sendMessage("count must be from 1-10").queue();
                                                                        }
                                                                        else {
                                                                            for (int i = 0; i < getInteger(c, "count"); i++) {
                                                                                event.getChannel().sendMessage("<@" + getLong(c, "user") + ">" + " "
                                                                                + getString(c, "message")).queue();
                                                                            }
                                                                        }
                                                                        return 1;
                                                                    })
                                                            )
                                                    .executes(c -> {
                                                        int count = getInteger(c, "count");
                                                        if (count < 1 || count > 10) {
                                                            event.getChannel().sendMessage("count must be from 1-10").queue();
                                                        }
                                                        else {
                                                            for (int i = 0; i < getInteger(c, "count"); i++) {
                                                                event.getChannel().sendMessage("<@" + getLong(c, "user") + ">").queue();
                                                            }
                                                        }
                                                        return 1;
                                                    })
                                            )
                                .executes(c -> { // if only user argument is provided, ping user argument
                                    event.getChannel().sendMessage("<@" + getLong(c, "user") + ">").queue();
                                    return 1;
                                })
                        )
                        .executes(c -> { // if just /ping, send command syntax
                            event.getChannel().sendMessage("Syntax: /ping <@user> <count> <\"message\">").queue();
                            return 1;
                        })
        );
        dispatcher.register(
          literal("/opgg")
                  .then(
                          argument("summoner", greedyString())
                          .executes(c -> {
                              String smnr = getString(c, "summoner");
                              event.getChannel().sendMessage("https://na.op.gg/summoner/userName=" + smnr).queue();
                              return 1;
                          })
                  )
          .executes(c -> {
              event.getChannel().sendMessage("Syntax: /opgg <summonername>").queue();
              return 1;
          })
        );
        dispatcher.register(
                literal("/anime")
                        .then(
                                argument("title", greedyString())
                                .executes(c -> {
                                    String title = getString(c, "title");
                                    event.getChannel().sendMessage("Searching...").queue();
                                    try {
                                        event.getChannel().sendMessage(anilink(title)).queue();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        event.getChannel().sendMessage("urlError, try again later").queue();
                                    }
                                    return 1;
                                })
                        )
                .executes(c -> {
                    event.getChannel().sendMessage("Syntax: /anime <title of anime").queue();
                    return 1;
                })
        );



        try {
            if (event.getMessage().getContentRaw().startsWith("/"))
            dispatcher.execute(rawCmd, source);
        } catch (CommandSyntaxException e){
            event.getChannel().sendMessage("Syntax error").queue();
        }

    }

}
