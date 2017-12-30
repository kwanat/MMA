package com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao;


import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Category;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Created by Kamil on 21.10.2017.
 */
public class CategoryWriteDao {


    private static final String TAG = "CategoryWriteDao";
    private static Dao<Category, Integer> categoryDao;
    private static CategoryWriteDao instance;

    public CategoryWriteDao() {
        try {
            categoryDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(),Category.class);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public boolean save(Category item) {
        if(item == null) {
            return false;
        }
        try {
            categoryDao.create(item);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    public void update(Category item) {
        if(item == null) {
            return;
        }
        try {
            categoryDao.update(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public void delete(Category item) {
        if(item == null) {
            return;
        }
        try {
            categoryDao.delete(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
