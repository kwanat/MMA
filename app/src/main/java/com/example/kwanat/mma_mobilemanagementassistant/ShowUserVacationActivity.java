package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.EmployeeReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.VacationReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Vacation;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.VacationWriteDao;

import java.util.ArrayList;
import java.util.List;

public class ShowUserVacationActivity extends BaseActivity {

    ListView employeeList;
    ArrayAdapter<Vacation> adapter;
    List<Vacation> vacationList;
    LoggedUser user;

    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_show_user_vacation);
        setView();
        getUser();
        getVacationRequests();
        setAdapter();

    }

    private void getUser() {
        user =LoggedUser.getInstance();
    }


    private void setView() {

        employeeList = (ListView)findViewById(R.id.showUserVacationList);
    }


    private void setAdapter() {
        adapter=new ArrayAdapter<Vacation>(context,android.R.layout.simple_list_item_2,android.R.id.text1,vacationList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);




                String status;
                switch(vacationList.get(position).getStatus()) {
                    case "u":
                        status=getResources().getString(R.string.statusUnset);
                        break;
                    case "c":
                        status=getResources().getString(R.string.statusConfirmed);
                        break;
                    case "r":
                        status=getResources().getString(R.string.statusRejected);
                        break;
                    default:
                        status=getResources().getString(R.string.statusUnset);
                        break;
                }
                text1.setText(vacationList.get(position).getStart()+" --> "+vacationList.get(position).getEnd());
                text2.setText(getResources().getString(R.string.status)+": "+status);
                return view;
            }
        };
        employeeList.setAdapter(adapter);
    }

    private void getVacationRequests() {
        Thread getVacationList = new Thread(new Runnable() {
            @Override
            public void run() {

                VacationReadDao readDao = new VacationReadDao();
                vacationList = readDao.getForUser(user.getUser().getId());
            }
        });
        getVacationList.start();
        try {
            getVacationList.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




}

