package com.Yorm_sk.image_to_text_bot.bot_components.keyboards;

import com.Yorm_sk.image_to_text_bot.enums.InlineKeyBoardType;
import com.Yorm_sk.image_to_text_bot.utils.bot_logic.NeededLanguage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class KeyboardsMaker {

    public ReplyKeyboardMarkup getMainMenuKeyboard(ResourceBundle resourceBundle) {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(resourceBundle.getString("help.button")));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(resourceBundle.getString("setting.button")));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getSettingMenu(ResourceBundle resourceBundle){
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(resourceBundle.getString("img.button")));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(resourceBundle.getString("user.button")));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineMessageButtons(InlineKeyBoardType type){
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (String s: new NeededLanguage().getLanguages()){
            switch (type){
                case IMG:
                    rowList.add(List.of(getButtons(s, s + "_img")));
                    break;
                case USER:
                    rowList.add(List.of(getButtons(s, s + "_user")));
                    break;
            }
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton getButtons(String buttonName, String buttonCallBackData){
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(buttonName);
        inlineKeyboardButton.setCallbackData(buttonCallBackData);
        return inlineKeyboardButton;
    }
}
