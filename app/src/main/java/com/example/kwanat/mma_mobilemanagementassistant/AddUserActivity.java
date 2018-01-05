package com.example.kwanat.mma_mobilemanagementassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.EmployeeReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.WorkstationReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Workstation;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.EmployeeWriteDao;

import java.util.List;

public class AddUserActivity extends BaseActivity {
    private EditText userFirstNameText;
    private EditText userNameText;
    private EditText userLoginText;
    private EditText userPasswordText;

    private Button submitButton;

    private Spinner worksSpinner;
    private ArrayAdapter<Workstation> adapter;

    private Employee newuser;
    private List<Workstation> workstations;

    private String userFirstName;
    private String userName;
    private String userLogin;
    private String userPassword;

    private int userWorkstation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_user);

        setView();
        getWorkstations();
        setnewAdapter();
        setButton();



    }

    private void setView() {
        userFirstNameText = (EditText) findViewById(R.id.addUserName);
        userNameText = (EditText) findViewById(R.id.addUserLastName);
        userLoginText = (EditText) findViewById(R.id.addUserLogin);
        userPasswordText = (EditText) findViewById(R.id.addUserPassword);
        submitButton = (Button) findViewById(R.id.addUserSubmit);
        worksSpinner= (Spinner) findViewById(R.id.addUserSpinner);
    }

    private void getWorkstations()
    {

        Thread get = new Thread(new Runnable() {
            @Override
            public void run() {
                WorkstationReadDao getdao = new WorkstationReadDao();
                workstations=getdao.getAll();

            }
        });

        get.start();
        try {
            get.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkUser()
    {
        Thread check = new Thread(new Runnable() {
            @Override
            public void run() {
                EmployeeReadDao empread = new EmployeeReadDao();
                newuser=empread.getByLogin(userLogin);
            }
        });
        check.start();
        try {
            check.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addUser()
    {
        Thread add = new Thread(new Runnable() {
            @Override
            public void run() {
                EmployeeWriteDao writedao = new EmployeeWriteDao();
                newuser=new Employee(userFirstName,userName,userLogin,userPassword);
                newuser.setId_workstation(userWorkstation);
                writedao.save(newuser);

            }
        });

        add.start();
        try {
            add.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setnewAdapter()
    {
        adapter= new ArrayAdapter<Workstation>(getApplicationContext(),android.R.layout.simple_spinner_item,workstations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        worksSpinner.setAdapter(adapter);
        worksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(),"wybrano opcje"+position,Toast.LENGTH_SHORT).show();
                userWorkstation=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setButton()
    {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userFirstName=userFirstNameText.getText().toString();
                userName=userNameText.getText().toString();
                userLogin=userLoginText.getText().toString();
                userPassword=Sha1Crypt.encryptPassword(userPasswordText.getText().toString());
                if(checkfields()) {
                    checkUser();
                    if (newuser != null) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginTaken), Toast.LENGTH_LONG).show();
                    } else {

                        addUser();
                        checkUser();
                        if (newuser != null) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.userAdded), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.databaseConnectionFailed), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.missingFields), Toast.LENGTH_LONG).show();



            }
        });
    }

    private boolean checkfields() {
        if(userFirstName.isEmpty())
            return false;
        if(userName.isEmpty())
            return false;
        if(userLogin.isEmpty())
            return false;
        if(userPassword.isEmpty())
            return false;
        return true;
    }
}
