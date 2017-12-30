package com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao;

import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Schedule;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Created by Kamil on 20.10.2017.
 */
public class ScheduleWriteDao {


    private static final String TAG = "ScheduleWriteDao";
    private static Dao<Schedule, Integer> scheduleDao;
    private static ScheduleWriteDao instance;

    public ScheduleWriteDao() {
        try {
            scheduleDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(),Schedule.class);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public boolean save(Schedule item) {
        if(item == null) {
            return false;
        }
        try {
            scheduleDao.create(item);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    public void update(Schedule item) {
        if(item == null) {
            return;
        }
        try {
            scheduleDao.update(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public void delete(Schedule item) {
        if(item == null) {
            return;
        }
        try {
            scheduleDao.delete(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
