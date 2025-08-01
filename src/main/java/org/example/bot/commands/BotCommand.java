package org.example.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

// 커맨드 인터페이스
public interface BotCommand {
    void execute(MessageReceivedEvent event, String[] args);
}
