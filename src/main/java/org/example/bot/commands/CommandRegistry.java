package org.example.bot.commands;

import java.util.HashMap;
import java.util.Map;

// 커맨드 종류별 객체 생성
public class CommandRegistry {
    private final Map<String, BotCommand> commands = new HashMap<>();

    public CommandRegistry() {
        commands.put("#help", new HelpCommand());
        commands.put("#안녕", new HelloCommand());
        commands.put("#쥬니퍼계산", new JuniperCalcCommand());
    }

    public BotCommand getCommand(String cmd) {
        return commands.get(cmd);
    }
}
