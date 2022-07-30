package com.Yorm_sk.image_to_text_bot.utils.bot_logic.handlers;

import com.Yorm_sk.image_to_text_bot.bot_components.keyboards.KeyboardsMaker;
import com.Yorm_sk.image_to_text_bot.enums.InlineKeyBoardType;
import com.Yorm_sk.image_to_text_bot.enums.KeyBoardMessages;
import com.Yorm_sk.image_to_text_bot.exceptions.NotSuchDocumentType;
import com.Yorm_sk.image_to_text_bot.models.Setting;
import com.Yorm_sk.image_to_text_bot.utils.bot_logic.NeededLanguage;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handle telegram messages
 * **/
public class MessageHandler {
    private static final Logger LOGGER = LogManager.getLogger(MessageHandler.class);
    private final ResourceBundle resourceBundle;
    private final KeyboardsMaker keyboardsMaker;
    private final Setting setting;

    private final List<String> helpCommands = KeyBoardMessages.HELP.getMessageTexts();
    private final List<String> settingCommands = KeyBoardMessages.SETTING.getMessageTexts();
    private final List<String> imgCommands = KeyBoardMessages.IMG_LANG.getMessageTexts();
    private final List<String> interfaceCommands = KeyBoardMessages.INTERFACE_LANG.getMessageTexts();

    public MessageHandler(Setting setting) {
        resourceBundle = new NeededLanguage().getTextByLanguage(setting.getInterfaceLang());
        keyboardsMaker = new KeyboardsMaker();
        this.setting = setting;
    }

    /**
     * Receive telegram message, process it and return telegram method that will be executed and sent to user
     * **/
    public BotApiMethod<?> checkMessage(Message message) {
        final String chatId = message.getChatId().toString();

        if (message.hasText()) {
            String inputText = message.getText();

            if (helpCommands.contains(inputText)) inputText = "/help";
            if (settingCommands.contains(inputText)) inputText = "/setting";
            if (imgCommands.contains(inputText)) inputText = "/img_lang";
            if (interfaceCommands.contains(inputText)) inputText = "/interface_lang";

            switch (inputText) {
                case "/start":
                    return getStartMessage(chatId);
                case "/help":
                    return getHelpMessage(chatId);
                case "/setting":
                    return getSettingMessage(chatId);
                case "/img_lang":
                    return getImgSetting(chatId);
                case "/interface_lang":
                    return getInterfaceSettings(chatId);
                default:
                    return wrongInput(chatId);
            }
        } else return new SendMessage(chatId, resourceBundle.getString("wrong.update"));
    }

    private SendMessage getHelpMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, resourceBundle.getString("help.message"));
        sendMessage.setReplyMarkup(keyboardsMaker.getMainMenuKeyboard(resourceBundle));
        return sendMessage;
    }

    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(resourceBundle.getString("start.message"));
        sendMessage.setReplyMarkup(keyboardsMaker.getMainMenuKeyboard(resourceBundle));
        return sendMessage;
    }

    private SendMessage getSettingMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, messageForSetting());
        sendMessage.setReplyMarkup(keyboardsMaker.getSettingMenu(resourceBundle));
        return sendMessage;
    }

    private String messageForSetting() {
        return String.format("%s %s\n%s %s", resourceBundle.getString("current.int.language"),
                setting.getInterfaceLang(), resourceBundle.getString("current.img.language"),
                setting.getImageLang());
    }

    private SendMessage getImgSetting(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, resourceBundle.getString("language.choose"));
        sendMessage.setReplyMarkup(keyboardsMaker.getInlineMessageButtons(InlineKeyBoardType.IMG));
        return sendMessage;
    }

    private SendMessage getInterfaceSettings(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, resourceBundle.getString("language.choose"));
        sendMessage.setReplyMarkup(keyboardsMaker.getInlineMessageButtons(InlineKeyBoardType.USER));
        return sendMessage;
    }

    private SendMessage wrongInput(String chatId) {
        return new SendMessage(chatId, resourceBundle.getString("wrong.message"));
    }

    /**
     * return telegram method to upload most qualitative picture from telegram user
     * **/
    public GetFile getPhoto(List<PhotoSize> photos) throws NotSuchDocumentType {
        return new GetFile(photos.get(photos.size() - 1).getFileId());
    }

    /**
     * return telegram method that send file to telegram user
     * **/
    public SendDocument sendTextFromPicture(File file, String chatId) {
        String textFromPic = textFromPicture(file, setting.getImageLang());
        if (setting.getImageLang().equals("zh")) {
            textFromPic = textFromPic.replaceAll(" ", "");
            textFromPic = textFromPic.replaceAll("\n", "");
        }
        InputFile inputFile = getTxtFromImage(textFromPic);

        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(inputFile);
        return sendDocument;
    }

    private String textFromPicture(File file, String lang) {
        ITesseract iTesseract = new Tesseract();
        switch (lang) {
            case "en":
                iTesseract.setLanguage("eng");
                break;
            case "ru":
                iTesseract.setLanguage("rus");
                break;
            case "uk":
                iTesseract.setLanguage("ukr");
                break;
            case "zh":
                iTesseract.setLanguage("chi_sim");
                break;
            default:
                return "No such language";
        }
        iTesseract.setDatapath(String.valueOf(Paths.get(System.getProperty("user.dir"), "tessdata")));
        try {
            return iTesseract.doOCR(file);
        } catch (TesseractException e) {
            return "File is wrong";
        }
    }

    private InputFile getTxtFromImage(String text) {
        File file = new File(String.valueOf(Paths.get(System.getProperty("user.dir"), "text.txt")));
        if (setting.getImageLang().equals("zh")) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_16))) {
                writer.write(text);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(text);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return new InputFile(file, "text.txt");
    }

    /**
     * return telegram method to upload png, jpg, jpgeg, pdf files from telegram user, that less than 5 mb
     * **/
    public GetFile getDocument(Document document) throws NotSuchDocumentType {
        String filename = document.getFileName().toLowerCase();
        if ((filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg") ||
                filename.endsWith(".pdf")) && document.getFileSize() < 5e+6) {
            return new GetFile(document.getFileId());
        } else throw new NotSuchDocumentType();
    }
}
