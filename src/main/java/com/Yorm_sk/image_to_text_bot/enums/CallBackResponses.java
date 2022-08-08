package com.Yorm_sk.image_to_text_bot.enums;

public enum CallBackResponses {
    IMG_RU("ru_img", Languages.RU), IMG_UK("uk_img", Languages.UK), IMG_EN("en_img", Languages.EN),
    IMG_ZH("zh_img", Languages.CHI_SIM),
    USER_RU("ru_user", Languages.RU), USER_UK("uk_user", Languages.UK), USER_EN("en_user", Languages.EN),
    USER_ZH("zh_user", Languages.CHI_SIM),
    TXT("txt", Languages.EN), TEL_MESSAGE("message", Languages.EN);

    private final String data;
    private final Languages language;

    CallBackResponses(String data, Languages language) {
        this.data = data;
        this.language = language;
    }

    public String getData() {
        return data;
    }

    public Languages getLanguage() {
        return language;
    }
}
