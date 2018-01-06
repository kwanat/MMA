package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity

{
    public DrawerLayout drawerLayout;
    public ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    ArrayList<String> items;

    protected void onCreate(Bundle savedInstanceState, int resLayoutID)
    {
        super.onCreate(savedInstanceState);
        setContentView(resLayoutID);
        MenuItems menu =MenuItems.getInstance();
        final LoggedUser user = LoggedUser.getInstance();
        items=menu.getmenu(user.getUser().getId_workstation());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle((Activity) this, drawerLayout, 0, 0)
        {
            public void onDrawerClosed(View view)
            {
                getSupportActionBar().setTitle(R.string.app_name);
            }

            public void onDrawerOpened(View drawerView)
            {
                getSupportActionBar().setTitle(R.string.menu);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,
                items));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long arg3) {
                String item = (String) parent.getAdapter().getItem(pos);
                Intent intent;
                if(item.equals(getResources().getString(R.string.homeMenu)))
                {
                    intent=new Intent(getApplicationContext(),StartActivity.class);
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.addMessageMenu)))
                {
                    intent=new Intent(getApplicationContext(),AddMessage.class);
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.addEmployeeMenu)))
                {
                    intent=new Intent(getApplicationContext(),AddUserActivity.class);
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.addVacationMenu)))
                {
                    intent=new Intent(getApplicationContext(),AddVacationRequest.class);
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.showEmployeesMenu)))
                {
                    intent=new Intent(getApplicationContext(),ShowEmployeeList.class);
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.showVacationMenu)))
                {
                    intent=new Intent(getApplicationContext(),ShowVacationRequests.class);
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.passwordMenu)))
                {
                    intent=new Intent(getApplicationContext(),ChangePasswordActivity.class);
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.myVacationMenu)))
                {
                    intent=new Intent(getApplicationContext(),ShowUserVacationActivity.class);
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.viewWarehouseMenu)))
                {
                    intent=new Intent(getApplicationContext(),ShowWarehouseActivity.class);
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.scheduleMenu)))
                {
                    intent=new Intent(getApplicationContext(),ScheduleActivity.class);
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.changeDataMenu)))
                {
                    intent=new Intent(getApplicationContext(),ChangeUserActivity.class);
                    intent.putExtra("user_id",user.getUser().getId());
                    startActivity(intent);
                }
                if(item.equals(getResources().getString(R.string.logoutMenu)))
                {
                    SharedPreferences preferences = getSharedPreferences(LoggedUser.PREFERENCE_NAME, Activity.MODE_PRIVATE);
                    SharedPreferences.Editor preferencesEditor = preferences.edit();
                    preferencesEditor.putInt(LoggedUser.USER_ID, 0);
                    preferencesEditor.commit();

                    intent=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }



            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}