package com.Yorm_sk.image_to_text_bot.enums;

import java.util.List;

public enum KeyBoardMessages {
    HELP(List.of("help\uD83D\uDCDA", "помощь\uD83D\uDCDA", "допомога\uD83D\uDCDA", "简介\uD83D\uDCDA")),
    SETTING(List.of("user settings\uD83D\uDD12", "настройки пользователя\uD83D\uDD12",
            "налаштування користувача\uD83D\uDD12", "用户设置\uD83D\uDD12")),
    IMG_LANG(List.of("change img language\uD83D\uDDBC", "изменить язык картинки\uD83D\uDDBC",
            "змінити мову картинки\uD83D\uDDBC", "选图片语言\uD83D\uDDBC")),
    INTERFACE_LANG(List.of("change interface language\uD83D\uDCDC", "изменить язык интерфейса\uD83D\uDCDC",
            "змінити мову інтерфейсу\uD83D\uDCDC", "选界面语言\uD83D\uDCDC"));

    private final List<String> messageTexts;

    KeyBoardMessages(List<String> messageTexts) {
        this.messageTexts = messageTexts;
    }

    public List<String> getMessageTexts() {
        return messageTexts;
    }
}
