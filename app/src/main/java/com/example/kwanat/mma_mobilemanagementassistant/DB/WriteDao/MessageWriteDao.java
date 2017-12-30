package com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao;

import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Message;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Created by Kamil on 21.10.2017.
 */
public class MessageWriteDao {


    private static final String TAG = "MessageWriteDao";
    private static Dao<Message, Integer> messageDao;
    private static MessageWriteDao instance;

    public MessageWriteDao() {
        try {
            messageDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(),Message.class);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public boolean save(Message item) {
        if(item == null) {
            return false;
        }
        try {
            messageDao.create(item);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    public void update(Message item) {
        if(item == null) {
            return;
        }
        try {
            messageDao.update(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public void delete(Message item) {
        if(item == null) {
            return;
        }
        try {
            messageDao.delete(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

