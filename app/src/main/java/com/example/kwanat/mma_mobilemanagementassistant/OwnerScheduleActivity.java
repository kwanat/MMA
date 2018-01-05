package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.EmployeeReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.ScheduleReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Schedule;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Vacation;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.ScheduleWriteDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.WarehouseWriteDao;

import java.util.Calendar;
import java.util.List;

public class OwnerScheduleActivity extends BaseActivity {
    TextView dateText;
    ListView scheduleList;
    List<Schedule> userSchedules;
    List<Employee> employees;
    Schedule item;
    ArrayAdapter<Schedule> adapter;
    Dialog dialogBox;
    Intent intent;
    Context context = this;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_owner_schedule);
        setView();
        getDate();
        getSchedules();
        getEmployees();
        setAdapter();
        setOnclick();
        setDialogBox();

    }

    private void setDialogBox() {
        dialogBox = new Dialog(context);
        dialogBox.setContentView(R.layout.custom_dialog_schedules);
        dialogBox.setTitle("Choose your option");


        Button startButton = (Button) dialogBox.findViewById(R.id.changeStartTimeButton);
        Button endButton = (Button) dialogBox.findViewById(R.id.changeEndTimeButton);
        Button confirmSchedule = (Button) dialogBox.findViewById(R.id.confirmScheduleButton);
        Button cancel = (Button) dialogBox.findViewById(R.id.changeTimeCancelButton);

        final Button startSubmitButton = (Button) dialogBox.findViewById(R.id.changeStartTimeSubmitButton);
        final Button endSubmitButton = (Button) dialogBox.findViewById(R.id.changeEndTimeSubmitButton);

        final EditText startValue = (EditText) dialogBox.findViewById(R.id.changeStartTimeValue);
        final EditText endValue = (EditText) dialogBox.findViewById(R.id.changeEndTimeValue);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startSubmitButton.getVisibility()==View.GONE)
                {
                    startSubmitButton.setVisibility(View.VISIBLE);
                    startValue.setVisibility(View.VISIBLE);
                }
                else
                {
                    startSubmitButton.setVisibility(View.GONE);
                    startValue.setVisibility(View.GONE);
                }
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(endSubmitButton.getVisibility()==View.GONE)
                {
                    endSubmitButton.setVisibility(View.VISIBLE);
                    endValue.setVisibility(View.VISIBLE);
                }
                else
                {
                    endSubmitButton.setVisibility(View.GONE);
                    endValue.setVisibility(View.GONE);
                }
            }
        });

        confirmSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setApproved("Y");
                Thread update = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ScheduleWriteDao write = new ScheduleWriteDao();
                        write.update(item);
                    }
                });
                update.start();
                try {
                    update.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                startSubmitButton.setVisibility(View.GONE);
                startValue.setVisibility(View.GONE);
                endSubmitButton.setVisibility(View.GONE);
                endValue.setVisibility(View.GONE);
                dialogBox.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSubmitButton.setVisibility(View.GONE);
                startValue.setVisibility(View.GONE);
                endSubmitButton.setVisibility(View.GONE);
                endValue.setVisibility(View.GONE);
                dialogBox.dismiss();

            }


        });


        startSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(startValue.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.emptyField),Toast.LENGTH_SHORT).show();
                else
                    item.setStart(startValue.getText().toString());

                Thread update = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ScheduleWriteDao write = new ScheduleWriteDao();
                        write.update(item);
                    }
                });
                update.start();
                try {
                    update.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                startSubmitButton.setVisibility(View.GONE);
                startValue.setVisibility(View.GONE);
                endSubmitButton.setVisibility(View.GONE);
                endValue.setVisibility(View.GONE);
                dialogBox.dismiss();

            }
        });
        endSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(endValue.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.emptyField),Toast.LENGTH_SHORT).show();
                else {
                    item.setEnd(endValue.getText().toString());
                    Thread update = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ScheduleWriteDao write = new ScheduleWriteDao();
                            write.update(item);
                        }
                    });
                    update.start();
                    try {
                        update.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                    startSubmitButton.setVisibility(View.GONE);
                    startValue.setVisibility(View.GONE);
                    endSubmitButton.setVisibility(View.GONE);
                    endValue.setVisibility(View.GONE);
                }
                dialogBox.dismiss();
            }
        });

        endValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endValue.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        startValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startValue.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

    }

    private void setOnclick() {
        scheduleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item= (Schedule) parent.getAdapter().getItem(position);
                dialogBox.show();
                return false;
            }
        });
    }

    private void setAdapter() {
        adapter=new ArrayAdapter<Schedule>(context,android.R.layout.simple_list_item_2,android.R.id.text1,userSchedules)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                Schedule sched = userSchedules.get(position);

                Employee worker=findWorkerById(sched.getId_employee());

                String status=getResources().getString(R.string.status)+": ";
                if(sched.getApproved().equals("Y"))
                    status=status+getResources().getString(R.string.statusConfirmed);
                else
                    status=status+getResources().getString(R.string.statusUnset);


                text1.setText(worker.getFirst_name()+" "+worker.getLast_name());
                text2.setText(sched.getStart()+" --> "+sched.getEnd()+"     "+status);
                return view;
            }
        };
        scheduleList.setAdapter(adapter);
    }

    private void getEmployees() {
        Thread getemp = new Thread(new Runnable() {
            @Override
            public void run() {
                EmployeeReadDao empread = new EmployeeReadDao();
                employees = empread.getAll();
            }
        });
        getemp.start();
        try {
            getemp.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getSchedules() {
        Thread getsched = new Thread(new Runnable() {
            @Override
            public void run() {
                ScheduleReadDao readshed = new ScheduleReadDao();
                userSchedules = readshed.getForDay(date);
            }
        });
        getsched.start();
        try {
            getsched.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setView() {
        dateText = (TextView) findViewById(R.id.ownerScheduleDateText);
        scheduleList = (ListView) findViewById(R.id.ownerScheduleList);

    }
    private void getDate() {
        intent = getIntent();
        date=intent.getStringExtra("date");
        dateText.setText(getResources().getString(R.string.date)+": "+date);
    }

    private Employee findWorkerById(int id_employee) {
        for(Employee temp : employees)
        {
            if(temp.getId()==id_employee)
                return temp;
        }
        return null;
    }
}
