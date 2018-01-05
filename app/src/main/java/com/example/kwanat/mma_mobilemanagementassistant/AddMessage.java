package com.example.kwanat.mma_mobilemanagementassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Message;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.MessageWriteDao;

public class AddMessage extends BaseActivity {

    EditText title;
    EditText message;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_message);
        setViews();
        setButtons();

    }

    private void setButtons() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
                getDate();
                Thread addmessage = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MessageWriteDao messdao = new MessageWriteDao();
                        Message mess= new Message(getUser(),getDate(),title.getText().toString(),message.getText().toString());
                        messdao.save(mess);
                    }
                });
                addmessage.start();
                try {
                    addmessage.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.messageAdded),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                startActivity(intent);
            }
        });

    }


    private String getDate() {
        DateDiff date = new DateDiff();
        return date.getDate();
    }

    private int getUser() {
        LoggedUser user = LoggedUser.getInstance();
        return user.getUser().getId();
    }

    private void setViews() {
        title = (EditText) findViewById(R.id.addMessageTitle);
        message = (EditText) findViewById(R.id.addMessageText);
        submit = (Button) findViewById(R.id.addMessageSubmit);
    }


}
