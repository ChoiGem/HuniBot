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
import java.util.concurrent.ThreadLocalRandom;

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
    private final String[] greetings = {
            "ㅋㅋ뭘봐",
            "어쩌라고",
            "뭐래 ㅋㅋ",
            "ㅋ",
            "아니"
    };

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().startsWith("#")) {
            if (event.getMessage().getContentRaw().equals("#help")) {
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
            if (event.getMessage().getContentRaw().equals("#안녕")) {
                int idx = ThreadLocalRandom.current().nextInt(greetings.length);
                String reply = greetings[idx];

                event.getChannel().sendMessage(reply).queue();
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

                    int successAvg = (int) Math.round(mean);

                    int profitAvg = oilPrice * successAvg - totalCost;

                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.KOREA);

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("🧪 쥬니퍼베리 오일 수익 계산기");
                    embed.setColor(new Color(108, 193, 245));
                    embed.setDescription("100개 제작 기준 시뮬레이션");

                    embed.addField("📦 씨앗 가격", nf.format(seedPrice) + " 메소", true);
                    embed.addField("🧴 오일 가격", nf.format(oilPrice) + " 메소", true);
                    embed.addField("🔧 오일 1개 제작비", nf.format(makeCost) + " 메소", false);

                    embed.addBlankField(false);

                    embed.addField("🧪 오일 제작의 경우", "▼ 아래는 오일을 제작했을 때의 시뮬레이션 결과입니다.", false);
                    embed.addField("🎯 평균 성공 수 (90%)", successAvg + "개", true);
                    embed.addField("💸 평균 수익", formatProfit(profitAvg), true);

                    embed.setFooter("쥬니퍼베리 오일 계산기 by HuniBot");

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