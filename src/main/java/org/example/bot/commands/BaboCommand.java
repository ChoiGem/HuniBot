package org.example.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BaboCommand implements BotCommand {
    String baboId = "661162635852513300";

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("<@&" + baboId + ">").queue();
    }
}