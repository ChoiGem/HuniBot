package org.example.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

// 커맨드 종류별 객체 생성
public class CommandRegistry {
    private final Map<String, BotCommand> commands = new HashMap<>();

    public CommandRegistry() {
        // 잡다한 기능들
        commands.put("#help", new HelpCommand());
        commands.put("#안녕", new HelloCommand());
        commands.put("#쥬니퍼계산", new JuniperCalcCommand());
        commands.put("#환산", new MaplescouterCommand());

        // 이스터에그
        commands.put("#잠깐", new WaitCommand());
        commands.put("#그럼", new ByeCommand());
        commands.put("#그럼이만", new ByeCommand());
        commands.put("#바보", new BaboCommand());
        commands.put("#환승", new BaboCommand());
        commands.put("#화살없는뚜비", new DdubiCommand());
        commands.put("#최원석", new GodCommand());
        commands.put("#원석", new GodCommand());

        // 나쁜말
        commands.put("#싸가지", new LolCommand());
        commands.put("#ㅋ", new LolCommand());
        commands.put("#어쩌라고", new DieCommand());
        commands.put("#죽어", new DieCommand());
        commands.put("#시발", new BadWordCommand());
        commands.put("#씨발", new BadWordCommand());
        commands.put("#병신", new BadWordCommand());
        commands.put("#좆까", new BadWordCommand());
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

class BadWordCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("욕 ㄴ").queue();
    }
}

class GodCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("신 그자체").queue();
    }
}