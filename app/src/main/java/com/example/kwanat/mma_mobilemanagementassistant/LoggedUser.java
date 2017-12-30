package com.example.kwanat.mma_mobilemanagementassistant;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;

/**
 * Created by Kamil on 28.12.2017.
 */

public class LoggedUser {
    public static final String PREFERENCE_NAME="User_pref";
    public static final String USER_ID="user_id";
    public static final String USER_login="user_login";
    public static final String USER_last_login="last_login";

    private static LoggedUser instance=null;
    private Employee user;

    private LoggedUser()
    {
    }
    public static LoggedUser getInstance()
    {
        if (instance==null)
        {
            instance=new LoggedUser();
            instance.user=new Employee();
        }
        return instance;
    }


    public Employee getUser() {
        return instance.user;
    }
    public void setUser(Employee user)
    {
        this.instance.user=user;
    }



}
