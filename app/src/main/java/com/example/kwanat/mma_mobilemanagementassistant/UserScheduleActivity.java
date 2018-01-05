package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.SubMenuBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.ScheduleReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Schedule;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.ScheduleWriteDao;

import java.util.Calendar;

public class UserScheduleActivity extends BaseActivity {
    Intent intent;
    EditText startTime;
    EditText endTime;
    Button submitButton;
    Context context = this;
    String date;
    Schedule userSched=null;
    TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_schedule);
        setView();
        getDate();
        tryGetSchedule();
        checkifapproved();
        setStartPicker();
        setStopPicker();
        setButton();
    }

    private void checkifapproved() {
        if((userSched!=null)&&(userSched.getApproved().equals("Y")))
        {
            submitButton.setVisibility(View.GONE);
            startTime.setEnabled(false);
            endTime.setEnabled(false);
        }
    }

    private void tryGetSchedule() {
        Thread get = new Thread(new Runnable() {
            @Override
            public void run() {
                LoggedUser user = LoggedUser.getInstance();
                ScheduleReadDao read = new ScheduleReadDao();
                userSched=read.getDayForWorker(user.getUser().getId(),date);
            }
        });
        get.start();
        try {
            get.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(userSched!=null)
        {
            startTime.setText(userSched.getStart());
            endTime.setText(userSched.getEnd());
        }
    }

    private void setButton() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkFields()){
                    Thread set = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            ScheduleWriteDao write = new ScheduleWriteDao();
                            if(userSched==null) {
                                LoggedUser user = LoggedUser.getInstance();
                                userSched = new Schedule(user.getUser().getId(), date, "N");
                                userSched.setStart(startTime.getText().toString());
                                userSched.setEnd(endTime.getText().toString());
                                write.save(userSched);
                            }
                            else {
                                userSched.setStart(startTime.getText().toString());
                                userSched.setEnd(endTime.getText().toString());
                                write.update(userSched);
                            }
                        }
                    });
                    set.start();
                    try {
                        set.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    intent = new Intent(getApplicationContext(),ScheduleActivity.class);
                    startActivity(intent);

                }
            }
        });
    }

    private boolean checkFields() {
        if((startTime.getText().toString().isEmpty())||(endTime.getText().toString().isEmpty())) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.missingFields), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void getDate() {
        intent = getIntent();
        date=intent.getStringExtra("date");
        dateText.setText(getResources().getString(R.string.date)+": "+date);
    }

    private void setStopPicker() {
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private void setStartPicker() {
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private void setView() {
        startTime = (EditText) findViewById(R.id.scheduleStartTime);
        endTime = (EditText) findViewById(R.id.scheduleEndTime);
        submitButton = (Button) findViewById(R.id.addScheduleSubmitButton);
        dateText = (TextView) findViewById(R.id.dateText);
    }
}
