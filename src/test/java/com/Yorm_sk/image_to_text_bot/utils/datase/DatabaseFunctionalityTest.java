package com.Yorm_sk.image_to_text_bot.utils.datase;

import com.Yorm_sk.image_to_text_bot.exceptions.UserWithNoData;
import com.Yorm_sk.image_to_text_bot.exceptions.UserWithSuchTelegramIdExists;
import com.Yorm_sk.image_to_text_bot.models.Setting;
import com.Yorm_sk.image_to_text_bot.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseFunctionalityTest {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseFunctionalityTest.class);
    static DatabaseFunctionality databaseFunctionality;

    @BeforeAll
    static void setUp() {
        databaseFunctionality = new DatabaseFunctionality();
    }


    @Test
    void addUser() {
        User user = new User("yorm", "Dima", "Hasiuk", "en" ,1122);
        try {
            databaseFunctionality.addUser(user);
        } catch (UserWithNoData | UserWithSuchTelegramIdExists e) {
            LOGGER.error(e.getMessage());
            fail();
        }
    }

    @Test
    void addUserWrong() {
        User user = new User("yorm", "Dima", "Hasiuk", "en" ,1233);
        assertThrows(UserWithSuchTelegramIdExists.class, () -> databaseFunctionality.addUser(user));
    }


    @Test
    void updateUser() {
        User user = databaseFunctionality.getUserByTelegramId(1122);
        user.setNickname("dude");
        user.setLastName("sas");
        user.setTelegramLang("ru");
        try {
            databaseFunctionality.updateUser(user);
        } catch (UserWithNoData e) {
            LOGGER.error(e.getMessage());
            fail();
        }
    }


    @Test
    void getSettingForUser() {
        User user = databaseFunctionality.getUserByTelegramId(296300341);
        try {
            Setting setting = databaseFunctionality.getSettingForUser(user);
            assertNotNull(setting);
            LOGGER.info(setting);
        } catch (UserWithNoData e) {
            LOGGER.error(e.getMessage());
            fail();
        }
    }

    @Test
    void updateUserSetting() {
        User user = databaseFunctionality.getUserByTelegramId(1122);
        try {
            Setting setting = databaseFunctionality.getSettingForUser(user);
            assertNotNull(setting);
            setting.setImageLang("ru");
            setting.setInterfaceLang("uk");
            databaseFunctionality.updateUserSetting(setting);
        } catch (UserWithNoData e) {
            LOGGER.error(e.getMessage());
            fail();
        }
    }
}