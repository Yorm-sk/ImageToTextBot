package com.Yorm_sk.image_to_text_bot.bot_components.bot_commands;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class SettingCommand extends BotCommand {
    public SettingCommand() {
        setCommand("setting");
        setDescription("set user setting");
    }
}
