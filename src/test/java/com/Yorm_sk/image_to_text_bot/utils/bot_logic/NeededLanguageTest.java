package com.Yorm_sk.image_to_text_bot.utils.bot_logic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeededLanguageTest {
    private static NeededLanguage neededLanguage;

    @BeforeAll
    static void getSet(){
        neededLanguage = new NeededLanguage();
    }

    @Test
    void getTextByLanguageText() {
        assertEquals("help", neededLanguage.getTextByLanguage("en").getString("help.button"));
        assertEquals("This bot can convert your text on (if it clears) to txt file.\n" +
                        "To do this select language which image will have in menu, and then send image in png or jpeg format.",
                neededLanguage.getTextByLanguage("en").getString("help.message"));
        assertEquals("настройки пользователя", neededLanguage.getTextByLanguage("ru").getString("setting.button"));
        assertEquals("Этот бот может преобразовывать картинки (если текст четкий) в txt файл.\n" +
                "Чтобы сделать это выберите язык картнки в настройках а после отправте файл в  формате png или jpg.",
                neededLanguage.getTextByLanguage("ru").getString("help.message"));
        assertEquals("мова інтерфейсу", neededLanguage.getTextByLanguage("uk").getString("user.button"));
        assertEquals("Бот конвертує текст на картинці (якщо він чіткий) в txt файл.\n" +
                "Для цього потрібно вибрати мову на картинці в меню, а потім наділсати картинку png або jpeg формату.",
                neededLanguage.getTextByLanguage("uk").getString("help.message"));
    }
}