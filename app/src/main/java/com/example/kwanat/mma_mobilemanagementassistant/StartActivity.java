package com.example.kwanat.mma_mobilemanagementassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.MessageReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StartActivity extends BaseActivity {
    public static final String TITLE = "TITLE";
    public static final String DATE = "DATE";
    public static final String AUTHOR = "AUTHOR";
    public static final String MESSAGE = "MESSAGE";

    ListView list;
    List<Message> messages;
    ArrayAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_start);
        list = (ListView) findViewById(R.id.messagelist);
        getMessages();
        Collections.reverse(messages);
        setnewAdapter();
    }

    private void setnewAdapter() {
        adapter=new ArrayAdapter<Message>(getApplicationContext(),android.R.layout.simple_list_item_1,messages);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO przejsc do nastepnej aktywnosci z wyswietleniem wiadomosci
                Message single = (Message) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(),MessageDetailsActivity.class);
                intent.putExtra(TITLE,single.getTitle());
                intent.putExtra(DATE,single.getDate());
                intent.putExtra(AUTHOR,single.getId_employee());
                intent.putExtra(MESSAGE,single.getMessage());
                startActivity(intent);
            }
        });

    }

    private void getMessages() {
        Thread getmes = new Thread(new Runnable() {
            @Override
            public void run() {
                MessageReadDao getdao = new MessageReadDao();
                messages=getdao.getAll();
            }
        });
        getmes.start();
        try {
            getmes.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
