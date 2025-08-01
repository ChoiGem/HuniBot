package org.example.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

// 쥬니퍼베리 오일 손익 계산기 커맨드
public class JuniperCalcCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        String msg = event.getMessage().getContentRaw();

        String[] parts = msg.split("\\s+");
        if (parts.length < 3) {
            event.getChannel().sendMessage("사용법: `#쥬니퍼계산 씨앗가격 오일가격`").queue();
            return;
        }

        try {
            int seedPrice = Integer.parseInt(parts[1]);
            int oilPrice = Integer.parseInt(parts[2]);

            int makeCost = seedPrice * 6 + 1000;
            int totalCost = makeCost * 100;

            int n = 100;
            double p = 0.9;
            double mean = n * p;

            int successAvg = (int) Math.round(mean);

            int profitAvg = oilPrice * successAvg - totalCost;

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.KOREA);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("🧪 특별히 쥬니퍼베리 오일 수익을 계산해주지");
            embed.setColor(new Color(108, 193, 245));
            embed.setDescription("100개 제작 기준 시뮬임");

            embed.addField("📦 씨앗 가격은", nf.format(seedPrice) + " 메소임", true);
            embed.addField("🧴 오일 가격은", nf.format(oilPrice) + " 메소임", true);
            embed.addField("🔧 오일 1개 제작비는", nf.format(makeCost) + " 메소다. 개싼듯 ㅇㅇ", false);

            embed.addBlankField(false);

            embed.addField("🧪 오일 제작할 때 시뮬임", "▼ 너 미래니까 잘 보셈", false);
            embed.addField("🎯 평균 성공 수는 90%로", successAvg + "개 제작함. ㅈ망겜이네 ㄷㄷ", true);
            embed.addField("💸 평균 수익은", formatProfit(profitAvg), true);

            embed.setFooter("쥬니퍼베리 오일 계산기 by HanbinBot");

            event.getChannel().sendMessageEmbeds(embed.build()).queue();

        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("⚠️ 가격은 숫자로 입력해주세요. 예: `#쥬니퍼계산 5000 40000`").queue();
        }
    }

    private String formatProfit(int profit) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.KOREA);
        String formatted = nf.format(Math.abs(profit));
        return (profit >= 0 ? "🟢 +" : "🔴 -") + formatted + " 메소다. 이거 벌려고 채집하네 ㅋㅋ";
    }
}
