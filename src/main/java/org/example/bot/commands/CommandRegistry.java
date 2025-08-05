package org.example.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

// 커맨드 종류별 객체 생성
public class CommandRegistry {
    private final Map<String, BotCommand> commands = new HashMap<>();

    public CommandRegistry() {
        commands.put("#help", new HelpCommand());
        commands.put("#안녕", new HelloCommand());
        commands.put("#쥬니퍼계산", new JuniperCalcCommand());
        commands.put("#환산", new MaplescouterCommand());

        commands.put("#싸가지", new LolCommand());
        commands.put("#ㅋ", new LolCommand());
        commands.put("#어쩌라고", new DieCommand());
        commands.put("#죽어", new DieCommand());
        commands.put("#잠깐", new WaitCommand());
        commands.put("#그럼", new ByeCommand());
    }

    public BotCommand getCommand(String cmd) {
        return commands.get(cmd);
    }
}

class DieCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("그럼 죽어").queue();
    }
}

class LolCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("ㅋ").queue();
    }
}

class WaitCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("조금 있다가 오도록 하지").queue();
    }
}

class ByeCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (event.getMessage().getContentRaw().equals("#그럼 이만")) {
            event.getChannel().sendMessage("가보도록 하지").queue();
        } else {
            event.getChannel().sendMessage("사람말을 해;; `#help`나 입력해 보셈.").queue();
        }

    }
}


