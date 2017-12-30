package com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao;

import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Vacation;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil on 22.10.2017.
 */

public class VacationReadDao {


    private static final String TAG = "VacationReadDao";
    private static Dao<Vacation, Integer> vacationDao;

    public VacationReadDao() {
        try {
            vacationDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(), Vacation.class);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public Vacation getById(long id) {
        QueryBuilder<Vacation, Integer> queryBuilder = vacationDao.queryBuilder();

        try {
            queryBuilder.where().like(Vacation.VACATION_ID, id);
            return vacationDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
    /*
    public Message getByName(String name) {
        QueryBuilder<Message, Integer> queryBuilder = vacationDao.queryBuilder();

        try {
            queryBuilder.where().like(Category.CATEGORY_NAME, name);
            return vacationDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

*/
    public List<Vacation> getAll() {
        try {
            return vacationDao.queryForAll();
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

}
