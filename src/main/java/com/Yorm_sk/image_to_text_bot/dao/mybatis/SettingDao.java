package com.Yorm_sk.image_to_text_bot.dao.mybatis;

import com.Yorm_sk.image_to_text_bot.dao.ISettingDao;
import com.Yorm_sk.image_to_text_bot.models.Setting;

import java.util.List;

public class SettingDao extends AbstractDao<ISettingDao> implements ISettingDao {
    @Override
    public Setting getEntityById(long id) {
        setMapper();
        Setting setting = mapper.getEntityById(id);
        closeSession();
        return setting;
    }

    @Override
    public void deleteEntityById(long id) {
        setMapper();
        mapper.deleteEntityById(id);
        session.commit();
        closeSession();
    }

    @Override
    public void updateEntity(Setting setting) {
        setMapper();
        mapper.updateEntity(setting);
        session.commit();
        closeSession();
    }

    @Override
    public void createEntity(Setting setting) {
        setMapper();
        mapper.createEntity(setting);
        session.commit();
        closeSession();
    }

    @Override
    public List<Setting> getAllEntities() {
        setMapper();
        List<Setting> settings = mapper.getAllEntities();
        closeSession();
        return settings;
    }

    @Override
    public void setMapper() {
        openSession();
        mapper  = session.getMapper(ISettingDao.class);
    }

    @Override
    public Setting getSettingByUserId(long id) {
        setMapper();
        Setting setting = mapper.getSettingByUserId(id);
        closeSession();
        return setting;
    }
}
