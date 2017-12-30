package com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao;


import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Workstation;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil on 22.10.2017.
 */

public class WorkstationReadDao {


    private static final String TAG = "WorkstationReadDao";
    private static Dao<Workstation, Integer> workstationDao;

    public WorkstationReadDao() {
        try {
            workstationDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(), Workstation.class);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public Workstation getById(long id) {
        QueryBuilder<Workstation, Integer> queryBuilder = workstationDao.queryBuilder();

        try {
            queryBuilder.where().like(Workstation.WORKSTATION_ID, id);
            return workstationDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public Workstation getByName(String name) {
        QueryBuilder<Workstation, Integer> queryBuilder = workstationDao.queryBuilder();

        try {
            queryBuilder.where().like(Workstation.WORKSTATION_NAME, name);
            return workstationDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


    public List<Workstation> getAll() {
        try {
            return workstationDao.queryForAll();
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

}
