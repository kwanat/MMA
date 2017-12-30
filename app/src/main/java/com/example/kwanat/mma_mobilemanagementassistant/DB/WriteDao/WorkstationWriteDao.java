package com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao;

import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Workstation;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Created by Kamil on 21.10.2017.
 */
public class WorkstationWriteDao {


    private static final String TAG = "WorkstationWriteDao";
    private static Dao<Workstation, Integer> workstationDao;
    private static WorkstationWriteDao instance;

    public WorkstationWriteDao() {
        try {
            workstationDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(),Workstation.class);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public boolean save(Workstation item) {
        if(item == null) {
            return false;
        }
        try {
            workstationDao.create(item);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    public void update(Workstation item) {
        if(item == null) {
            return;
        }
        try {
            workstationDao.update(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public void delete(Workstation item) {
        if(item == null) {
            return;
        }
        try {
            workstationDao.delete(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}


