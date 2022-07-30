package com.Yorm_sk.image_to_text_bot.dao;

import com.Yorm_sk.image_to_text_bot.models.Setting;

public interface ISettingDao extends IBaseDao<Setting> {
    Setting getSettingByUserId(long id);
}
