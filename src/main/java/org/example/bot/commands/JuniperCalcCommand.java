package org.example.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

// 쥬니퍼베리 오일 손익 계산기 커맨드
public class JuniperCalcCommand implements BotCommand {
    private static final int SEEDS_PER_OIL = 6;
    private static final int EXTRA_COST = 1000;
    private static final double SUCCESS_PROB = 0.9;

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        String msg = event.getMessage().getContentRaw();
        String[] parts = msg.split("\\s+");

        if (parts.length < 3) {
            sendUsage(event);
            return;
        }

        try {
            int seedPrice = Integer.parseInt(parts[1]);
            int oilPrice  = Integer.parseInt(parts[2]);
            int makeCost  = seedPrice * SEEDS_PER_OIL + EXTRA_COST;

            // n: 제작 횟수 (기본 100, 씨앗개수 있으면 그걸로)
            int n;
            if (parts.length >= 4) {
                int seedQuantity = Integer.parseInt(parts[3]);
                n = seedQuantity / SEEDS_PER_OIL;
                if (n <= 0) {
                    event.getChannel().sendMessage("⚠️ 씨앗이 모자람. 최소 " + SEEDS_PER_OIL + "개 이상 넣어라.").queue();
                    return;
                }
            } else {
                n = 100;
            }

            int totalCost  = makeCost * n;
            int successAvg = (int) Math.round(n * SUCCESS_PROB);
            int profitAvg  = oilPrice * successAvg - totalCost;

            EmbedBuilder embed = buildResultEmbed(seedPrice, oilPrice, makeCost, n, successAvg, profitAvg);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();

        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("⚠️ 가격/개수는 숫자로 입력하도록 해. 예: `#쥬니퍼계산 5000 40000`, `#쥬니퍼계산 5000 40000 500`").queue();
        }
    }

    private void sendUsage(MessageReceivedEvent event) {
        event.getChannel().sendMessage("사용법: `#쥬니퍼계산 씨앗가격 오일가격 [씨앗개수]`").queue();
    }

    private EmbedBuilder buildResultEmbed(int seedPrice, int oilPrice, int makeCost,
                                          int n, int successAvg, int profitAvg) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.KOREA);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("🧪 특별히 쥬니퍼베리 오일 수익을 계산해주지");
        embed.setColor(new Color(108, 193, 245));
        embed.setDescription(n + "개 제작 기준 시뮬임");

        embed.addField("📦 씨앗 가격은", nf.format(seedPrice) + " 메소임", true);
        embed.addField("🧴 오일 가격은", nf.format(oilPrice) + " 메소임", true);
        embed.addField("🔧 오일 1개 제작비는", nf.format(makeCost) + " 메소다. 개싼듯 ㅇㅇ", false);

        embed.addBlankField(false);

        embed.addField("🧪 오일 제작할 때 시뮬임", "▼ 너 미래니까 잘 보셈", false);
        embed.addField("🎯 예상 성공 수는 90%로", successAvg + "개 제작함. ㅈ망겜이네 ㄷㄷ", true);
        embed.addField("💸 예상 수익은", formatProfit(profitAvg), true);

        embed.setFooter("쥬니퍼베리 오일 계산기 by HanbinBot");
        return embed;
    }

    private String formatProfit(int profit) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.KOREA);
        String formatted = nf.format(Math.abs(profit));
        return (profit >= 0 ? "🟢 +" : "🔴 -") + formatted +
                (profit >= 0 ? " 메소다. 이거 벌려고 채집하네 ㅋㅋ" : " 메소임. 개손해네 ㅋㅋ");
    }
}