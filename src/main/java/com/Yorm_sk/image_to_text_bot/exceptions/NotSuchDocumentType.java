package com.Yorm_sk.image_to_text_bot.exceptions;

public class NotSuchDocumentType extends Exception{
    public NotSuchDocumentType() {
        this("Please send png, jpeg, pdf or jpg file, it must be less then 5 mb");
    }

    public NotSuchDocumentType(String message) {
        super(message);
    }
}
