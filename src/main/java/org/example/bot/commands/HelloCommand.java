package org.example.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.ThreadLocalRandom;

// 랜덤 인사 커맨드
public class HelloCommand implements BotCommand {
    private final String[] greetings = {
            "ㅋㅋ뭘봐",
            "어쩌라고",
            "뭐래 ㅋㅋ",
            "ㅋ",
            "아니",
            "ㅎㅇ"
    };

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        int idx = ThreadLocalRandom.current().nextInt(greetings.length);
        String reply = greetings[idx];

        event.getChannel().sendMessage(reply).queue();
    }
}
