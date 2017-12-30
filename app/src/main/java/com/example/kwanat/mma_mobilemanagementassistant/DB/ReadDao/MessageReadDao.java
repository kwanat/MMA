package com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao;


import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Message;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil on 22.10.2017.
 */

public class MessageReadDao {


    private static final String TAG = "MessageReadDao";
    private static Dao<Message, Integer> messageDao;

    public MessageReadDao() {
        try {
            messageDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(), Message.class);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public Message getById(long id) {
        QueryBuilder<Message, Integer> queryBuilder = messageDao.queryBuilder();

        try {
            queryBuilder.where().like(Message.MESSAGE_ID, id);
            return messageDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
    /*
    public Message getByName(String name) {
        QueryBuilder<Message, Integer> queryBuilder = messageDao.queryBuilder();

        try {
            queryBuilder.where().like(Category.CATEGORY_NAME, name);
            return messageDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

*/
    public List<Message> getAll() {
        try {
            return messageDao.queryForAll();
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

}
