package com.Yorm_sk.image_to_text_bot.dao.mybatis;

import com.Yorm_sk.image_to_text_bot.utils.datase.MyBatisSqlFactory;
import org.apache.ibatis.session.SqlSession;

public abstract class AbstractDao <T>{
    protected SqlSession session;
    protected T mapper;

    public void openSession(){
        MyBatisSqlFactory factory = MyBatisSqlFactory.getInstance("mybatis/mb_configuration.xml");
        session = factory.getFactory().openSession();
    }

    public void closeSession(){
        if (session != null) session.close();
    }

    abstract public void setMapper();
}
