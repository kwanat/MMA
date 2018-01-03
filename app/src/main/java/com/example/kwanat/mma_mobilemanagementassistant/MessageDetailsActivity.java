package com.example.kwanat.mma_mobilemanagementassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.EmployeeReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;

import org.w3c.dom.Text;

public class MessageDetailsActivity extends BaseActivity {
    TextView title;
    TextView author;
    TextView pubDate;
    TextView message;
    String auth;
    int authorid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message_details);
        title = (TextView) findViewById(R.id.messageTitle);
        author = (TextView) findViewById(R.id.messageAuthor);
        pubDate = (TextView) findViewById(R.id.messagePubDate);
        message = (TextView) findViewById(R.id.messageText);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra(StartActivity.TITLE));
        pubDate.setText(intent.getStringExtra(StartActivity.DATE));
        message.setText(intent.getStringExtra(StartActivity.MESSAGE));
        authorid= intent.getIntExtra(StartActivity.AUTHOR,0);
        getUser();
        author.setText(auth);

    }

    private void getUser() {
        Thread getuser = new Thread(new Runnable() {
            @Override
            public void run() {
                EmployeeReadDao dao = new EmployeeReadDao();
                Employee user= dao.getById(authorid);
                auth=user.getFirst_name()+" "+user.getLast_name();
            }
        });
        getuser.start();
        try {
            getuser.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
