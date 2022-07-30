package com.Yorm_sk.image_to_text_bot.utils.datase;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisSqlFactory {
    private final static Logger LOGGER = LogManager.getLogger(MyBatisSqlFactory.class);
    private static MyBatisSqlFactory instance;
    private SqlSessionFactory factory;

    private MyBatisSqlFactory(String resources){
        try {
            InputStream inputStream = Resources.getResourceAsStream(resources);
            factory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static MyBatisSqlFactory getInstance(String resources){
        if (instance == null) instance = new MyBatisSqlFactory(resources);
        return instance;
    }

    public SqlSessionFactory getFactory() {
        return factory;
    }
}
