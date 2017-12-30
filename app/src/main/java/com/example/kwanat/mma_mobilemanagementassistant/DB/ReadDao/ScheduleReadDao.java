package com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao;


import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Schedule;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil on 22.10.2017.
 */

public class ScheduleReadDao {

    private static final String TAG = "ScheduleReadDao";
    private static Dao<Schedule, Integer> scheduleDao;

    public ScheduleReadDao() {
        try {
            scheduleDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(), Schedule.class);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public Schedule getById(long id) {
        QueryBuilder<Schedule, Integer> queryBuilder = scheduleDao.queryBuilder();

        try {
            queryBuilder.where().like(Schedule.SCHEDULE_ID_SCHEDULE, id);
            return scheduleDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
    public Schedule getDayForWorker(int id, String day) {
        QueryBuilder<Schedule, Integer> queryBuilder = scheduleDao.queryBuilder();

        try {
            queryBuilder.where().like(Schedule.SCHEDULE_ID_EMPLOYEE, id)
                    .and()
                    .like(Schedule.SCHEDULE_DATE,day);
            return scheduleDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public List<Schedule> getForWorker(int id) {
        QueryBuilder<Schedule, Integer> queryBuilder = scheduleDao.queryBuilder();

        try {
            queryBuilder.where().eq(Schedule.SCHEDULE_ID_EMPLOYEE,id);
            return scheduleDao.query(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


    public List<Schedule> getAll() {
        try {
            return scheduleDao.queryForAll();
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


}