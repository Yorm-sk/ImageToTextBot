package com.Yorm_sk.image_to_text_bot.utils.bot_logic.handlers;

import com.Yorm_sk.image_to_text_bot.bot_components.keyboards.KeyboardsMaker;
import com.Yorm_sk.image_to_text_bot.enums.CallBackResponses;
import com.Yorm_sk.image_to_text_bot.models.Setting;
import com.Yorm_sk.image_to_text_bot.utils.bot_logic.NeededLanguage;
import com.Yorm_sk.image_to_text_bot.utils.datase.DatabaseFunctionality;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Handle telegram call backs
 * **/
public class CallBackHandler {
    private final DatabaseFunctionality databaseFunctionality;
    private final Setting setting;
    private ResourceBundle resourceBundle;

    public CallBackHandler(Setting setting) {
        databaseFunctionality = new DatabaseFunctionality();
        this.setting = setting;
        resourceBundle = new NeededLanguage().getTextByLanguage(setting.getInterfaceLang());
    }

    /**
     * Receive telegram call back from keyboard, process it and return telegram method that will be executed and sent to user
     * **/
    public BotApiMethod<?> processCallBackQuery(CallbackQuery callbackQuery){
        final String chatId = callbackQuery.getMessage().getChatId().toString();

        String data = callbackQuery.getData();

        List<String> correctData = Arrays.stream(CallBackResponses.values()).map(CallBackResponses::getData)
                .collect(Collectors.toList());

        if (correctData.contains(data)) return languageChanged(data, chatId);
        else return wrongInput(chatId);
    }

    private SendMessage languageChanged(String data, String chatId){
        CallBackResponses response;
        switch (data){
            case "ru_img":
                response = CallBackResponses.IMG_RU;
                break;
            case "uk_img":
                response = CallBackResponses.IMG_UK;
                break;
            case "en_img":
                response = CallBackResponses.IMG_EN;
                break;
            case "ru_user":
                response = CallBackResponses.USER_RU;
                break;
            case "uk_user":
                response =CallBackResponses.USER_UK;
                break;
            case "zh_img":
                response = CallBackResponses.IMG_ZH;
                break;
            case "zh_user":
                response = CallBackResponses.USER_ZH;
                break;
            case "txt":
                response = CallBackResponses.TXT;
                break;
            case "message":
                response = CallBackResponses.TEL_MESSAGE;
                break;
            default:
                response = CallBackResponses.USER_EN;
                break;
        }
        SendMessage sendMessage;
        switch (response){
            case IMG_EN:
            case IMG_RU:
            case IMG_UK:
            case IMG_ZH:
                setting.setImageLang(response.getLanguage().getName());
                databaseFunctionality.updateUserSetting(setting);
                resourceBundle = new NeededLanguage().getTextByLanguage(setting.getInterfaceLang());
                sendMessage = new SendMessage(chatId, resourceBundle.getString("img.changed"));
                sendMessage.setReplyMarkup(new KeyboardsMaker().getMainMenuKeyboard(resourceBundle));
                return sendMessage;
            case USER_EN:
            case USER_RU:
            case USER_UK:
            case USER_ZH:
                setting.setInterfaceLang(response.getLanguage().getName());
                databaseFunctionality.updateUserSetting(setting);
                resourceBundle = new NeededLanguage().getTextByLanguage(setting.getInterfaceLang());
                sendMessage = new SendMessage(chatId, resourceBundle.getString("interface.changed"));
                sendMessage.setReplyMarkup(new KeyboardsMaker().getMainMenuKeyboard(resourceBundle));
                return sendMessage;
            case TXT:
                setting.setSendByFile(true);
                databaseFunctionality.updateUserSetting(setting);
                sendMessage = new SendMessage(chatId, "\uD83D\uDC4D");
                sendMessage.setReplyMarkup(new KeyboardsMaker().getMainMenuKeyboard(resourceBundle));
                return sendMessage;
            case TEL_MESSAGE:
                setting.setSendByFile(false);
                databaseFunctionality.updateUserSetting(setting);
                sendMessage = new SendMessage(chatId, "\uD83D\uDC4D");
                sendMessage.setReplyMarkup(new KeyboardsMaker().getMainMenuKeyboard(resourceBundle));
                return sendMessage;
        }
        return new SendMessage(chatId, "Something went wrong");
    }

    private SendMessage wrongInput(String chatId){
        return new SendMessage(chatId, "You enter something wrong");
    }
}
