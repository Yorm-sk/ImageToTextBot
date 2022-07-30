package com.Yorm_sk.image_to_text_bot.dao.mybatis;

import com.Yorm_sk.image_to_text_bot.dao.IUserDao;
import com.Yorm_sk.image_to_text_bot.models.User;

import java.util.List;

public class UserDao extends AbstractDao<IUserDao> implements IUserDao {

    @Override
    public User getEntityById(long id) {
        setMapper();
        User user = mapper.getEntityById(id);
        closeSession();
        return user;
    }

    @Override
    public void deleteEntityById(long id) {
        setMapper();
        mapper.deleteEntityById(id);
        session.commit();
        closeSession();
    }

    @Override
    public void updateEntity(User user) {
        setMapper();
        mapper.updateEntity(user);
        session.commit();
        closeSession();
    }

    @Override
    public void createEntity(User user) {
        setMapper();
        mapper.createEntity(user);
        session.commit();
        closeSession();
    }

    @Override
    public List<User> getAllEntities() {
        setMapper();
        List<User> users = mapper.getAllEntities();
        closeSession();
        return users;
    }

    @Override
    public User getUserByTelegramId(long id) {
        setMapper();
        User user = mapper.getUserByTelegramId(id);
        closeSession();
        return user;
    }

    @Override
    public void setMapper() {
        openSession();
        mapper = session.getMapper(IUserDao.class);
    }
}
