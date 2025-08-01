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
                .setDescription("사용 가능한 명령어 목록임:")
                .setColor(Color.CYAN)
                .addField("#안녕", "인사를 받아 줄지도", false)
                .addField("#쥬니퍼계산", "쥬니퍼베리 씨앗 오일 제작 이득을 계산하긴 할거임. 누가 요즘 채집하냐?", false)
                // 필요하면 더 추가
                .setFooter("궁금한 점이 있으면 직접 찾아봐야지 #help나 치고있어;;");

        event.getChannel()
                .sendMessageEmbeds(eb.build())
                .queue();
    }
}
