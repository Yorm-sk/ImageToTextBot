package com.Yorm_sk.image_to_text_bot.utils.bot_logic;

import com.Yorm_sk.image_to_text_bot.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class CheckMethods {

    public boolean isUserInDatabase(List<User> users, User user){
        return users.contains(user);
    }

    public boolean isUserTelegramIdInDatabase(List<User> users, long telegramId){
        List<Long> ids = users.stream().map(User::getTelegramId)
                .collect(Collectors.toList());
        return ids.contains(telegramId);
    }
}
