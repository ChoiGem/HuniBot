package org.example.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

// 도움말 커맨드
public class HelpCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("봇 도움말 🛠️")
                .setDescription("사용 가능한 명령어 목록입니다:")
                .setColor(Color.CYAN)
                .addField("#안녕", "봇이 인사를 합니다.", false)
                .addField("#쥬니퍼계산", "쥬니퍼베리 씨앗 오일 제작 이득을 계산합니다.", false)
                // 필요하면 더 추가
                .setFooter("궁금한 점이 있으면 언제든 #help 를 입력해 보세요!");

        event.getChannel()
                .sendMessageEmbeds(eb.build())
                .queue();
    }
}
