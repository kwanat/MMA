package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Vacation;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.VacationWriteDao;

import java.util.Calendar;

public class AddVacationRequest extends BaseActivity {

    EditText startDateText;
    EditText endDateText;
    Button submitButton;
    private long startDate=0;
    private long endDate=0;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_vacation_request);
        setView();
        setOnClickStart();
        setOnClick2();
        setButton();
    }

    private void setButton() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((startDate>endDate)||(startDate==0)||(endDate==0))
                    Toast.makeText(getApplicationContext(),"data zakonczenia jest wczesniejsza niz data startu",Toast.LENGTH_SHORT).show();
                else
                {
                    Thread addVacation = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LoggedUser user = LoggedUser.getInstance();
                            Vacation newVacation = new Vacation(user.getUser().getId(),startDateText.getText().toString(),endDateText.getText().toString(),"u");
                            VacationWriteDao writedao = new VacationWriteDao();
                            writedao.save(newVacation);
                        }
                    });
                    addVacation.start();
                    try {
                        addVacation.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),"dodano urlop",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                    startActivity(intent);

                }
            }
        });
    }

    private void setOnClick2() {

        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialogx
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                endDateText.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                                Calendar temp= Calendar.getInstance();
                                temp.set(year,monthOfYear,dayOfMonth);
                                endDate=temp.getTimeInMillis();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void setOnClickStart() {
        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialogx
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                startDateText.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                                Calendar temp= Calendar.getInstance();
                                temp.set(year,monthOfYear,dayOfMonth);
                                startDate=temp.getTimeInMillis();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void setView() {
        startDateText = (EditText) findViewById(R.id.vacationStartDate);
        endDateText = (EditText) findViewById(R.id.vacationEndDate);
        submitButton = (Button) findViewById(R.id.vacationRequestSubmit);


     }
}
