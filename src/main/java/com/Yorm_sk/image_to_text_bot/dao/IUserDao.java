package com.Yorm_sk.image_to_text_bot.dao;

import com.Yorm_sk.image_to_text_bot.models.User;

public interface IUserDao extends IBaseDao <User>{

    User getUserByTelegramId(long id);
}
