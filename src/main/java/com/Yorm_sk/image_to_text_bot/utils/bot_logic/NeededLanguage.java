package com.Yorm_sk.image_to_text_bot.utils.bot_logic;

import com.Yorm_sk.image_to_text_bot.enums.Languages;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Contain list of accessible languages from Languages enum
 * **/
public class NeededLanguage {
    private final List<String> languages = Arrays.stream(Languages.values()).map(Languages::getName).
            collect(Collectors.toList());

    /**
     * return ResourceBundle depends on language passed as parameter
     * if language is not in Language enum returns English ResourceBundle
     * **/
    public ResourceBundle getTextByLanguage(String lang) {
        ResourceBundle resourceBundle;
        if (!languages.contains(lang)) resourceBundle = ResourceBundle.getBundle("messageTexts.en");
        else resourceBundle = ResourceBundle.getBundle("messageTexts." + lang);
        return resourceBundle;
    }

    public List<String> getLanguages() {
        return languages;
    }
}
