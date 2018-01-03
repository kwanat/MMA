package com.example.kwanat.mma_mobilemanagementassistant;

import android.content.Intent;
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

public class ChangePasswordActivity extends BaseActivity {

    EditText oldPassword;
    EditText repeatOldPassword;
    EditText newPassword;
    Button submit;
    Employee user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_change_password);

        getUser();
        setViews();
        setButton();


    }



    private void setViews()
    {
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        repeatOldPassword = (EditText) findViewById(R.id.repeatOldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);

        submit = (Button) findViewById(R.id.changePasswordSubmit);


    }


    private void getUser()
    {
        Thread check = new Thread(new Runnable() {
            @Override
            public void run() {

                LoggedUser loguser = LoggedUser.getInstance();


                EmployeeReadDao empread = new EmployeeReadDao();
                user=empread.getById(loguser.getUser().getId());
            }
        });
        check.start();
        try {
            check.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void setButton()
    {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!oldPassword.getText().toString().equals(repeatOldPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.passwordsinequal),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String oldpass=Sha1Crypt.encryptPassword(oldPassword.getText().toString());
                    if(!(oldpass.equals(user.getPassword()))){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.passwordIncorrect),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (newPassword.getText().toString().isEmpty())
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.passwordEmpty), Toast.LENGTH_SHORT).show();
                        else {
                            user.setPassword(Sha1Crypt.encryptPassword(newPassword.getText().toString()));
                            Thread add = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    EmployeeWriteDao writedao = new EmployeeWriteDao();
                                    writedao.update(user);

                                }
                            });

                            add.start();
                            try {
                                add.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.passwordChanged), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }




            }
        });
    }

}
