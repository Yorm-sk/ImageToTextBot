package com.Yorm_sk.image_to_text_bot.bot_components.bot_commands;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class StartCommand extends BotCommand {
    public StartCommand() {
        setCommand("start");
        setDescription("opens main menu");
    }
}
