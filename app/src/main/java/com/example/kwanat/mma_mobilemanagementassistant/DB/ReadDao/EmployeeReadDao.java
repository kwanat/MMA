package com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao;

import android.util.Log;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Connection;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil on 22.10.2017.
 */

public class EmployeeReadDao {


    private static final String TAG = "EmployeeReadDao";
    private static Dao<Employee, Integer> employeeDao;

    public EmployeeReadDao() {
        try {
            employeeDao = DaoManager.createDao(Connection.getInstance().getConnectionSource(), Employee.class);
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public Employee getById(long id) {
        QueryBuilder<Employee, Integer> queryBuilder = employeeDao.queryBuilder();

        try {
            queryBuilder.where().like(Employee.EMPLOYEE_ID, id);
            return employeeDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
    public Employee getByPesel(String pesel) {
        QueryBuilder<Employee, Integer> queryBuilder = employeeDao.queryBuilder();

        try {
            queryBuilder.where().like(Employee.EMPLOYEE_PESEL, pesel);
            return employeeDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
    public Employee getByLogin(String login) {
        QueryBuilder<Employee, Integer> queryBuilder = employeeDao.queryBuilder();

        try {
            queryBuilder.where().like(Employee.EMPLOYEE_LOGIN, login);
            return employeeDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
    public Employee getByLoginAndPassword(String login,String password) {
        QueryBuilder<Employee, Integer> queryBuilder = employeeDao.queryBuilder();

        try {
            queryBuilder.where().like(Employee.EMPLOYEE_LOGIN, login)
                    .and()
            .like(Employee.EMPLOYEE_PASSWORD, password);
            return employeeDao.queryForFirst(queryBuilder.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


    public List<Employee> getAll() {
        try {
            return employeeDao.queryForAll();
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

}
