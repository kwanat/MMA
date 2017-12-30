package com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao;

import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Warehouse;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.ColumnArg;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil on 22.10.2017.
 */

public class WarehouseReadDao {


    private static final String TAG = "WarehouseReadDao";
    private static Dao<Warehouse, Integer> warehouseDao;

    public WarehouseReadDao() {
        try {
            warehouseDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(), Warehouse.class);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public Warehouse getById(long id) {
        QueryBuilder<Warehouse, Integer> queryBuilder = warehouseDao.queryBuilder();

        try {
            queryBuilder.where().like(Warehouse.WAREHOUSE_ID, id);
            return warehouseDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public Warehouse getByName(String name) {
        QueryBuilder<Warehouse, Integer> queryBuilder = warehouseDao.queryBuilder();

        try {
            queryBuilder.where().like(Warehouse.WAREHOUSE_PRODUCT_NAME, name);
            return warehouseDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public Warehouse getByCategory(int category) {
        QueryBuilder<Warehouse, Integer> queryBuilder = warehouseDao.queryBuilder();

        try {
            queryBuilder.where().like(Warehouse.WAREHOUSE_ID_CATEGORY, category);
            return warehouseDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public List<Warehouse> getLowQuantity() {
        QueryBuilder<Warehouse, Integer> queryBuilder = warehouseDao.queryBuilder();

        try {
            queryBuilder.where().le(Warehouse.WAREHOUSE_ACTUAL_QUANTITY, new ColumnArg(Warehouse.WAREHOUSE_CRITICAL_QUANTITY));
            return warehouseDao.query(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


    public List<Warehouse> getAll() {
        try {
            return warehouseDao.queryForAll();
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

}
