package org.example.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.List;
import java.time.Duration;

public class MaplescouterCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        String msg = event.getMessage().getContentRaw();

        String[] parts = msg.split("\\s+");
        if (parts.length < 2) {
            event.getChannel().sendMessage("사용법: `#환산 캐릭터명`").queue();
            return;
        }

        String name = parts[1];
        String preset = "00000"; // 필요 시 커스터마이즈

        EmbedBuilder eb = new EmbedBuilder().setTitle("불러오는 중...\n오래 걸림");
        event.getChannel().sendMessageEmbeds(eb.build()).queue(sentMessage -> {
            try {
                // ChromeDriver 경로 설정
                System.setProperty("webdriver.chrome.driver", "chromedriver-win64/chromedriver.exe");

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless"); // 창 없이 실행
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");

                WebDriver driver = new ChromeDriver(options);
                String url = "https://maplescouter.com/info?name=" + name + "&preset=" + preset;
                driver.get(url);

                // WebDriverWait로 요소 대기
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                List<WebElement> elements = wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                By.cssSelector("div.text-\\[\\#1D4ED8\\].font-extrabold")
                        )
                );

                String powerValue = elements.get(0).getText();
                String itemValue = elements.get(1).getText();
                String hexaValue = elements.get(2).getText();

                EmbedBuilder result = new EmbedBuilder()
                        .setTitle("헥사환산 결과", url)
                        .setColor(Color.CYAN)
                        .addField("캐릭터명", name, true)
                        .addField("전투력", powerValue, false)
                        .addField("아이템환산", itemValue, true)
                        .addField("헥사환산", hexaValue, true)
                        .setFooter("maplescouter.com 실시간 데이터");

                sentMessage.editMessageEmbeds(result.build()).queue();

                driver.quit();
            } catch (Exception e) {
                e.printStackTrace();
                EmbedBuilder result = new EmbedBuilder()
                        .setTitle("헥사환산 가져오는거 실패해버림. 아쉽지뭐")
                        .setColor(Color.CYAN);
                sentMessage.editMessageEmbeds(result.build()).queue();
            }
        });
    }
}
