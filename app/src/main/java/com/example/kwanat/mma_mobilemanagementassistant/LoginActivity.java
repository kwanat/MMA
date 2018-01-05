package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.EmployeeReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;

public class LoginActivity extends AppCompatActivity {

    Employee user;
    LoggedUser luser;
    SharedPreferences preferences;

    String login;
    String password;

    EditText logintext;
    EditText passwordtext;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit = (Button) findViewById(R.id.login_submit);
        logintext = (EditText) findViewById(R.id.login);
        passwordtext = (EditText) findViewById(R.id.password);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login=logintext.getText().toString();
                password=Sha1Crypt.encryptPassword(passwordtext.getText().toString());

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EmployeeReadDao read = new EmployeeReadDao();
                        user = read.getByLoginAndPassword(login,password);

                    }
                });

                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(user==null)
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.incorrectCredencials),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    luser=LoggedUser.getInstance();
                    preferences = getSharedPreferences(LoggedUser.PREFERENCE_NAME, Activity.MODE_PRIVATE);
                    SharedPreferences.Editor preferencesEditor = preferences.edit();
                    DateDiff instance=new DateDiff();
                    String curdate=instance.getDate();

                    preferencesEditor.putString(LoggedUser.USER_last_login, curdate);
                    preferencesEditor.putInt(LoggedUser.USER_ID, user.getId());
                    preferencesEditor.putString(LoggedUser.USER_login, user.getLogin());
                    preferencesEditor.commit();

                    luser.setUser(user);
                    Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(intent);

                }

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
