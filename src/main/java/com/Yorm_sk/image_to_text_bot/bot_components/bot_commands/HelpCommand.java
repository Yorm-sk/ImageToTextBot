package com.Yorm_sk.image_to_text_bot.bot_components.bot_commands;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class HelpCommand extends BotCommand {

    public HelpCommand() {
        setCommand("help");
        setDescription("shows how bot works");
    }
}
