package org.example.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ByeCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (event.getMessage().getContentRaw().equals("#그럼 이만")
                || event.getMessage().getContentRaw().equals("#그럼이만")) {
            event.getChannel().sendMessage("가보도록 하지").queue();
        } else {
            event.getChannel().sendMessage("사람말을 해;; `#help`나 입력해 보셈.").queue();
        }

    }
}