package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.EmployeeReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        prepareMenuLists();
        user=LoggedUser.getInstance();
        preferences = getSharedPreferences(LoggedUser.PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        if(isNetworkAvailable()) {

            id = preferences.getInt(LoggedUser.USER_ID, 0);
            if (id != 0) {
                String lastlogin = preferences.getString(LoggedUser.USER_last_login, null);
                DateDiff instance = new DateDiff();
                long daysDiff = instance.getDaysFromNow(lastlogin);
                if (daysDiff < 14) // zalogować
                {
                    String curdate = instance.getDate();
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

                } else {
                    intent = new Intent(this, LoginActivity.class);
                }
            } else {
                intent = new Intent(this, LoginActivity.class);
            }
            //intent = new Intent(this, ShowVacationRequests.class); //Do debugowania
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.internetNotConnected),Toast.LENGTH_LONG).show();



    }

    private void prepareMenuLists() {
        MenuItems lists = MenuItems.getInstance();
        ArrayList<String> temp=new ArrayList<>();
        ArrayList<String> temp1=new ArrayList<>();
        ArrayList<String> temp2=new ArrayList<>();
        temp.add(getResources().getString(R.string.homeMenu));
        temp.add(getResources().getString(R.string.addMessageMenu));
        temp.add(getResources().getString(R.string.viewWarehouseMenu));
        temp.add(getResources().getString(R.string.changeDataMenu));
        temp.add(getResources().getString(R.string.addVacationMenu));
        temp.add(getResources().getString(R.string.myVacationMenu));
        temp.add(getResources().getString(R.string.scheduleMenu));
        temp.add(getResources().getString(R.string.passwordMenu));
        temp.add(getResources().getString(R.string.logoutMenu));
        lists.setEmployeeMenu(temp);

        temp1.add(getResources().getString(R.string.homeMenu));
        temp1.add(getResources().getString(R.string.addMessageMenu));
        temp1.add(getResources().getString(R.string.viewWarehouseMenu));
        temp1.add(getResources().getString(R.string.changeDataMenu));
        temp1.add(getResources().getString(R.string.scheduleMenu));
        temp1.add(getResources().getString(R.string.showVacationMenu));
        temp1.add(getResources().getString(R.string.passwordMenu));
        temp1.add(getResources().getString(R.string.logoutMenu));
        lists.setManagerMenu(temp1);

        temp2.add(getResources().getString(R.string.homeMenu));
        temp2.add(getResources().getString(R.string.addMessageMenu));
        temp2.add(getResources().getString(R.string.viewWarehouseMenu));
        temp2.add(getResources().getString(R.string.changeDataMenu));
        temp2.add(getResources().getString(R.string.showVacationMenu));
        temp2.add(getResources().getString(R.string.scheduleMenu));
        temp2.add(getResources().getString(R.string.showEmployeesMenu));
        temp2.add(getResources().getString(R.string.addEmployeeMenu));
        temp2.add(getResources().getString(R.string.passwordMenu));
        temp2.add(getResources().getString(R.string.logoutMenu));
        lists.setOwnerMenu(temp2);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
