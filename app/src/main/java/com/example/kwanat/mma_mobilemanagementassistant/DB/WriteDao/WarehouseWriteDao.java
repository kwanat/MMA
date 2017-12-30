package com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao;


import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Warehouse;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Created by Kamil on 21.10.2017.
 */
public class WarehouseWriteDao {


    private static final String TAG = "WarehouseWriteDao";
    private static Dao<Warehouse, Integer> warehouseDao;
    private static WarehouseWriteDao instance;

    public WarehouseWriteDao() {
        try {
            warehouseDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(),Warehouse.class);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public boolean save(Warehouse item) {
        if(item == null) {
            return false;
        }
        try {
            warehouseDao.create(item);
        }
        catch(java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    public void update(Warehouse item) {
        if(item == null) {
            return;
        }
        try {
            warehouseDao.update(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public void delete(Warehouse item) {
        if(item == null) {
            return;
        }
        try {
            warehouseDao.delete(item);
        } catch (java.sql.SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

