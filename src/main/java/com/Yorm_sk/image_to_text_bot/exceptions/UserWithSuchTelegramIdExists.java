package com.Yorm_sk.image_to_text_bot.exceptions;

public class UserWithSuchTelegramIdExists extends Exception{
    public UserWithSuchTelegramIdExists() {
        this("Such telegram user is already in database");
    }

    public UserWithSuchTelegramIdExists(String message) {
        super(message);
    }
}
