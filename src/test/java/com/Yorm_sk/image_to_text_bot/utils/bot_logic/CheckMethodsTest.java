package com.Yorm_sk.image_to_text_bot.utils.bot_logic;

import com.Yorm_sk.image_to_text_bot.utils.datase.DatabaseFunctionality;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckMethodsTest {

    @Test
    public void isUserInDatabaseTest(){
        CheckMethods checkMethods = new CheckMethods();
        assertTrue(checkMethods.isUserTelegramIdInDatabase(new DatabaseFunctionality().getAllUsersFromDatabase(), 1122));
    }
}