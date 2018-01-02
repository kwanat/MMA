package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.EmployeeReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private Intent intent;
    LoggedUser user;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=LoggedUser.getInstance();
        preferences = getSharedPreferences(LoggedUser.PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();


        id=preferences.getInt(LoggedUser.USER_ID,0);
        if(id!=0)
        {
            String lastlogin=preferences.getString(LoggedUser.USER_last_login,null);
           DateDiff instance=new DateDiff();
            long daysDiff= instance.getDaysFromNow(lastlogin);
            if(daysDiff<14) // zalogować
            {
                String curdate=instance.getDate();
                preferencesEditor.putString(LoggedUser.USER_last_login, curdate);
                preferencesEditor.commit();
                intent = new Intent(this, StartActivity.class);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EmployeeReadDao read = new EmployeeReadDao();
                        Employee newuser = read.getById(id);
                        user.setUser(newuser);
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                intent = new Intent(this, LoginActivity.class);
            }
        }
        else
        {
            intent = new Intent(this, LoginActivity.class);
        }
        intent = new Intent(this, ShowVacationRequests.class); //Do debugowania
        startActivity(intent);
        finish();



    }
}
