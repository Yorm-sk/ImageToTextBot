package com.Yorm_sk.image_to_text_bot.exceptions;

public class UserWithNoData extends Exception{
    public UserWithNoData() {
        this("User doe`snt contain any information");
    }

    public UserWithNoData(String message) {
        super(message);
    }
}
