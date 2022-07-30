package com.Yorm_sk;

import com.Yorm_sk.image_to_text_bot.ImageToTextBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
/**
 * Here class for starting bot
 * **/
public class StartServer {
    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            ImageToTextBot imageToTextBot = new ImageToTextBot();
            telegramBotsApi.registerBot(imageToTextBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
