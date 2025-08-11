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
                .addField("#환산", "환산주스탯 가져와서 보여주는데 개오래걸리니까 하지마셈", false)
                .addField("", "그 외에 뭔가 있을지도", false)
                // 기능 추가 시 작성
                .setFooter("궁금한 점이 있으면 직접 찾아봐야지 #help나 치고있어;;");

        event.getChannel()
                .sendMessageEmbeds(eb.build())
                .queue();
    }
}
