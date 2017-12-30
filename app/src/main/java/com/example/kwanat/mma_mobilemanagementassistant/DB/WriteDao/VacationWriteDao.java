package com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao;


import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Vacation;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Created by Kamil on 21.10.2017.
 */
public class VacationWriteDao {


    private static final String TAG = "VacationWriteDao";
    private static Dao<Vacation, Integer> vacationDao;
    private static VacationWriteDao instance;

    public VacationWriteDao() {
        try {
            vacationDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(),Vacation.class);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public boolean save(Vacation item) {
        if(item == null) {
            return false;
        }
        try {
            vacationDao.create(item);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    public void update(Vacation item) {
        if(item == null) {
            return;
        }
        try {
            vacationDao.update(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public void delete(Vacation item) {
        if(item == null) {
            return;
        }
        try {
            vacationDao.delete(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}


