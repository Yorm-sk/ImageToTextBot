package com.Yorm_sk.image_to_text_bot.enums;

public enum Languages {
    RU("ru"), UK("uk"), EN("en"), CHI_SIM("zh");

    private final String name;

    Languages(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
