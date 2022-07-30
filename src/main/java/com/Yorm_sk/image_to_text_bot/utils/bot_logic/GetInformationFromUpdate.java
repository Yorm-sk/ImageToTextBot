package com.Yorm_sk.image_to_text_bot.utils.bot_logic;

import com.Yorm_sk.image_to_text_bot.models.User;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Contain information that this bot need from update
 * **/
public class GetInformationFromUpdate {
    private org.telegram.telegrambots.meta.api.objects.User telegramUser;
    private final CallbackQuery callbackQuery;
    private final Message message;

    public GetInformationFromUpdate(Update update) {
        callbackQuery = update.getCallbackQuery();
        message = update.getMessage();
        if (message!= null) telegramUser = update.getMessage().getFrom();
        else if (callbackQuery!= null) telegramUser = update.getCallbackQuery().getFrom();
    }

    public User getUserFromUpdate(){
        return new User(telegramUser.getUserName(), telegramUser.getFirstName(), telegramUser.getLastName(),
                telegramUser.getLanguageCode(), telegramUser.getId());
    }


    public CallbackQuery getCallbackQuery() {
        return callbackQuery;
    }

    public Message getMessage() {
        return message;
    }

}
