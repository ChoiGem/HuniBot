package org.example.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.bot.commands.BotCommand;
import org.example.bot.commands.CommandRegistry;

public class BotListener extends ListenerAdapter {
    private final CommandRegistry registry = new CommandRegistry();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentRaw();
        if (!content.startsWith("#")) return;

        String[] parts = content.split("\\s+");
        String cmd = parts[0];
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        BotCommand command = registry.getCommand(cmd);
        if (command != null) {
            command.execute(event, args);
        } else {
            event.getChannel().sendMessage("알 수 없는 명령어입니다. `#help`를 입력해 보세요.").queue();
        }
    }
}
