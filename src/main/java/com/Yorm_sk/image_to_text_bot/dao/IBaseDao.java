package com.Yorm_sk.image_to_text_bot.dao;

import java.util.List;

public interface IBaseDao<T>{
    T getEntityById(long id);
    void deleteEntityById(long id);
    void updateEntity(T entity);
    void createEntity(T entity);
    List<T> getAllEntities();
}
