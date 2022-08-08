package com.Yorm_sk.image_to_text_bot.utils.datase;

import com.Yorm_sk.image_to_text_bot.dao.mybatis.SettingDao;
import com.Yorm_sk.image_to_text_bot.dao.mybatis.UserDao;
import com.Yorm_sk.image_to_text_bot.exceptions.UserWithNoData;
import com.Yorm_sk.image_to_text_bot.exceptions.UserWithSuchTelegramIdExists;
import com.Yorm_sk.image_to_text_bot.models.Setting;
import com.Yorm_sk.image_to_text_bot.models.User;
import com.Yorm_sk.image_to_text_bot.utils.bot_logic.NeededLanguage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contain DAOs to work with user and their settings
 * When created read information about users and their settings and write it own lists
 * Information about database is in resources db.properties
 * Use Mybatis to work with MysqlTables
 * **/
public class DatabaseFunctionality {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseFunctionality.class);

    private final UserDao userDao;
    private final SettingDao settingDao;

    private List<User> users;
    private List<Setting> settings;

    public DatabaseFunctionality() {
        userDao = new UserDao();
        settingDao = new SettingDao();
        users = userDao.getAllEntities();
        settings = settingDao.getAllEntities();
    }

    public void addUser(User user) throws UserWithNoData, UserWithSuchTelegramIdExists {
        if (user == null) throw new UserWithNoData();
        if (getUsersTelegramIds().contains(user.getTelegramId())) throw new UserWithSuchTelegramIdExists();
        userDao.createEntity(user);
        String standardLanguage = user.getTelegramLang();
        if (!(new NeededLanguage().getLanguages().contains(standardLanguage))) standardLanguage = "en";
        Setting setting = new Setting(standardLanguage, standardLanguage, user, true);
        settingDao.createEntity(setting);
        users = getAllUsersFromDatabase();
        settings = settingDao.getAllEntities();
        LOGGER.info("User " + user.getFirstName() + " was created in database");
    }

    public void updateUser(User user) throws UserWithNoData {
        if (user == null) throw new UserWithNoData();
        userDao.updateEntity(user);
        users = userDao.getAllEntities();
        LOGGER.info("User " + user.getFirstName() + " was updated in database");
    }

    public List<User> getAllUsersFromDatabase() {
        LOGGER.info("Users in database was got");
        return userDao.getAllEntities();
    }

    public Setting getSettingForUser(User user) throws UserWithNoData {
        if (user == null) throw new UserWithNoData();
        List<Long> ids = users.stream().map(User::getId).collect(Collectors.toList());
        if (ids.contains(user.getId())) {
            LOGGER.info("Setting for user " + user.getFirstName() + " was got");
            return settings.stream().filter(setting -> setting.getId() == user.getId()).
                    collect(Collectors.toList()).get(0);
        } else {
            LOGGER.info("Setting for user was got from database");
            return settingDao.getSettingByUserId(user.getId());
        }
    }

    public void updateUserSetting(Setting setting) {
        settingDao.updateEntity(setting);
        settings = settingDao.getAllEntities();
        LOGGER.info("Setting for" + setting.getUser().getFirstName() + " was updated in database");
    }

    public User getUserById(long id) {
        LOGGER.info("User is read");
        LOGGER.info("Setting for user was got from database");
        return userDao.getEntityById(id);

    }

    public User getUserByTelegramId(long id) {
        List<Long> ids = users.stream().map(User::getTelegramId).collect(Collectors.toList());
        if (ids.contains(id)) {
            LOGGER.info("User with telegram id "+ id + " was got");
            return users.stream().filter(user -> user.getTelegramId() == id).collect(Collectors.toList()).get(0);
        } else {
            LOGGER.info("User with telegram id "+ id + " was got from database");
            return userDao.getUserByTelegramId(id);
        }
    }

    private List<Long> getUsersTelegramIds() {
        return userDao.getAllEntities().stream().map(User::getTelegramId).collect(Collectors.toList());
    }

    public List<User> getUsers() {
        return users;
    }
}
