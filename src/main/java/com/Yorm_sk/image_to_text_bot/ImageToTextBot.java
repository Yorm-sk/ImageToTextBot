package com.Yorm_sk.image_to_text_bot;

import com.Yorm_sk.image_to_text_bot.bot_components.bot_commands.HelpCommand;
import com.Yorm_sk.image_to_text_bot.bot_components.bot_commands.SettingCommand;
import com.Yorm_sk.image_to_text_bot.bot_components.bot_commands.StartCommand;
import com.Yorm_sk.image_to_text_bot.exceptions.NotSuchDocumentType;
import com.Yorm_sk.image_to_text_bot.exceptions.UserWithNoData;
import com.Yorm_sk.image_to_text_bot.exceptions.UserWithSuchTelegramIdExists;
import com.Yorm_sk.image_to_text_bot.models.User;
import com.Yorm_sk.image_to_text_bot.utils.bot_logic.CheckMethods;
import com.Yorm_sk.image_to_text_bot.utils.bot_logic.GetInformationFromUpdate;
import com.Yorm_sk.image_to_text_bot.utils.bot_logic.handlers.CallBackHandler;
import com.Yorm_sk.image_to_text_bot.utils.bot_logic.handlers.MessageHandler;
import com.Yorm_sk.image_to_text_bot.utils.datase.DatabaseFunctionality;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Contain main logic for update processing
 * Get token and bot name from resources bot.properties
 **/
public class ImageToTextBot extends TelegramLongPollingBot {
    private final static Logger LOGGER = LogManager.getLogger(ImageToTextBot.class);
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("bot");
    private static final String botName = resourceBundle.getString("name");
    private static final String botToken = resourceBundle.getString("token");

    private final DatabaseFunctionality databaseFunctionality;

    public ImageToTextBot() {
        databaseFunctionality = new DatabaseFunctionality();
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     * process update file from telegram api
     **/
    @Override
    public void onUpdateReceived(Update update) {
        GetInformationFromUpdate information = new GetInformationFromUpdate(update);
        CheckMethods checkMethods = new CheckMethods();
        User user = information.getUserFromUpdate();
        if (!checkMethods.isUserTelegramIdInDatabase(databaseFunctionality.getUsers(), user.getTelegramId())) {
            try {
                databaseFunctionality.addUser(user);
                user = databaseFunctionality.getUserByTelegramId(user.getTelegramId());
            } catch (UserWithNoData | UserWithSuchTelegramIdExists e) {
                LOGGER.error(e.getMessage());
            }
        } else user = databaseFunctionality.getUserByTelegramId(user.getTelegramId());
        if (update.hasMessage()) {
            File file = null;
            try {
                MessageHandler messageHandler = new MessageHandler(databaseFunctionality.getSettingForUser(user));
                if (information.getMessage().hasPhoto()) {
                    String filepath = execute(messageHandler.getPhoto(information.getMessage().getPhoto())).getFilePath();
                    file = downloadFile(filepath, new File(filepath));
                    if (databaseFunctionality.getSettingForUser(user).isSendByFile())
                        execute(messageHandler.sendTextFromPictureAsFile(file, update.getMessage().getChatId().toString()));
                    else
                        execute(messageHandler.sendTextFromPictureAsMessage(file, update.getMessage().getChatId().toString()));
                } else if (information.getMessage().hasDocument()) {
                    String filepath = execute(messageHandler.getDocument(information.getMessage().getDocument())).getFilePath();
                    file = downloadFile(filepath, new File(filepath));
                    if (databaseFunctionality.getSettingForUser(user).isSendByFile())
                        execute(messageHandler.sendTextFromPictureAsFile(file, update.getMessage().getChatId().toString()));
                    else
                        execute(messageHandler.sendTextFromPictureAsMessage(file, update.getMessage().getChatId().toString()));
                } else execute(messageHandler.checkMessage(information.getMessage()));
            } catch (UserWithNoData | TelegramApiException e) {
                LOGGER.error(e.getMessage());
            } catch (NotSuchDocumentType e) {
                try {
                    execute(new SendMessage(update.getMessage().getChatId().toString(), e.getMessage()));
                } catch (TelegramApiException ex) {
                    LOGGER.error(e.getMessage());
                }
            } finally {
                if (file != null) file.delete();
            }
        } else if (update.hasCallbackQuery()) {
            try {
                CallBackHandler callBackHandler = new CallBackHandler(databaseFunctionality.getSettingForUser(user));
                execute(callBackHandler.processCallBackQuery(information.getCallbackQuery()));
            } catch (UserWithNoData | TelegramApiException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public void setCommands() {
        SetMyCommands commands = new SetMyCommands();
        commands.setCommands(List.of(new StartCommand(), new HelpCommand(), new SettingCommand()));
        try {
            execute(commands);
        } catch (TelegramApiException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
