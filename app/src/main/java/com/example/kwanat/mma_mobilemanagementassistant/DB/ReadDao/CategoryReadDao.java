package com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao;


import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Category;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil on 22.10.2017.
 */

public class CategoryReadDao {


    private static final String TAG = "CategoryReadDao";
    private static Dao<Category, Integer> categoryDao;

    public CategoryReadDao() {
        try {
            categoryDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(), Category.class);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public Category getById(long id) {
        QueryBuilder<Category, Integer> queryBuilder = categoryDao.queryBuilder();

        try {
            queryBuilder.where().like(Category.CATEGORY_ID, id);
            return categoryDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
    public Category getByName(String name) {
        QueryBuilder<Category, Integer> queryBuilder = categoryDao.queryBuilder();

        try {
            queryBuilder.where().like(Category.CATEGORY_NAME, name);
            return categoryDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


    public List<Category> getAll() {
        try {
            return categoryDao.queryForAll();
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

}

