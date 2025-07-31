package org.example.bot;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");
        JDABuilder.createDefault(token,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new BotListener())
                .build();
    }
}

class BotListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().startsWith("#")) {
            if (event.getMessage().getContentRaw().equals("#help")) {
                event.getChannel().sendMessage("ㅋㅋ뭘봐").queue();
            }

            if (event.getMessage().getContentRaw().startsWith("#쥬니퍼계산")) {
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
                    double sd = Math.sqrt(n * p * (1 - p));

                    int successAvg = (int) Math.round(mean);
                    int success50 = (int) Math.round(mean + 0.0 * sd);
                    int success30 = (int) Math.round(mean + 0.52 * sd);
                    int success80 = (int) Math.round(mean + 0.84 * sd);

                    int profitAvg = oilPrice * successAvg - totalCost;
                    int profit50 = oilPrice * success50 - totalCost;
                    int profit30 = oilPrice * success30 - totalCost;
                    int profit80 = oilPrice * success80 - totalCost;

                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.KOREA);

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("🧪 쥬니퍼베리 오일 수익 계산기");
                    embed.setColor(new Color(108, 193, 245));
                    embed.setDescription("100개 제작 기준 시뮬레이션");

                    embed.addField("📦 씨앗 가격", nf.format(seedPrice) + " 메소", true);
                    embed.addField("🧴 오일 가격", nf.format(oilPrice) + " 메소", true);
                    embed.addField("🔧 오일 1개 제작비", nf.format(makeCost) + " 메소", false);

                    embed.addBlankField(false);

                    embed.addField("🧪 오일 제작했을 경우", "▼ 아래는 오일을 제작했을 때의 시뮬레이션 결과입니다.", false);
                    embed.addField("🎯 평균 성공 수 (90%)", successAvg + "개", true);
                    embed.addField("💸 평균 수익", formatProfit(profitAvg), true);
                    embed.addField("📊 상위 50% 운 (≈ " + success50 + "개)", formatProfit(profit50), true);
                    embed.addField("📈 상위 30% 운 (≈ " + success30 + "개)", formatProfit(profit30), true);
                    embed.addField("🎉 상위 80% 운 (≈ " + success80 + "개)", formatProfit(profit80), true);

                    embed.setFooter("쥬니퍼 오일 계산기 by HuniBot");

                    event.getChannel().sendMessageEmbeds(embed.build()).queue();

                } catch (NumberFormatException e) {
                    event.getChannel().sendMessage("⚠️ 가격은 숫자로 입력해주세요. 예: `#쥬니퍼계산 5000 40000`").queue();
                }
            }
        }
    }

    private String formatProfit(int profit) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.KOREA);
        String formatted = nf.format(Math.abs(profit));
        return (profit >= 0 ? "🟢 +" : "🔴 -") + formatted + " 메소";
    }
}