package org.example.bot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DdubiCommand implements BotCommand {
    String jjahwangUserId = "362114583592304640";

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getJDA().retrieveUserById(jjahwangUserId).queue(user -> {
            event.getChannel().sendMessage(user.getAsMention() + " 견장없는석윤").queue();
        }, failure -> {});
    }
}