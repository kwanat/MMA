package com.example.kwanat.mma_mobilemanagementassistant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

public class ScheduleActivity extends BaseActivity {
    CalendarView calendar;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_schedule);
        calendar = (CalendarView) findViewById(R.id.addScheduleCalendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Toast.makeText(getApplicationContext(),year+"-"+month+"-"+dayOfMonth,Toast.LENGTH_SHORT).show();
                LoggedUser user = LoggedUser.getInstance();
                month=month+1;
                String custommonth;
                String customday;
                if(month<10)
                    custommonth="0"+month;
                else
                    custommonth=String.valueOf(month);
                if(dayOfMonth<10)
                    customday="0"+dayOfMonth;
                else
                    customday=String.valueOf(dayOfMonth);
                switch (user.getUser().getId_workstation()){
                    case 1:
                        intent = new Intent(getApplicationContext(),OwnerScheduleActivity.class);


                        intent.putExtra("date",year+"-"+custommonth+"-"+customday);
                        startActivity(intent);
                        break;
                    default:
                        intent = new Intent(getApplicationContext(),UserScheduleActivity.class);
                        intent.putExtra("date",year+"-"+custommonth+"-"+customday);
                        startActivity(intent);
                }

            }
        });

    }
}
