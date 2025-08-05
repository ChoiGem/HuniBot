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
            WebElement hexaElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("div.text-\\[\\#1D4ED8\\].font-extrabold")
                    )
            );

            String hexaValue = hexaElement.getText();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("헥사환산 결과", url);
            eb.setColor(Color.CYAN);
            eb.addField("캐릭터명", name, true);
            eb.addField("헥사환산", hexaValue, true);
            eb.setFooter("maplescouter.com 실시간 데이터");

            event.getChannel().sendMessageEmbeds(eb.build()).queue();

            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
            event.getChannel().sendMessage("헥사환산 값을 가져오는 데 실패했습니다.").queue();
        }
    }
}
