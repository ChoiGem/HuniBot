package org.example.bot.commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DdubiCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        String jjahwangUserId = "362114583592304640";
        User targetUser = event.getJDA().getUserById(jjahwangUserId);

        if (targetUser != null) {
            event.getChannel().sendMessage(targetUser.getAsMention() + " 견장없는석윤").queue();
        }
    }
}