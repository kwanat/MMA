package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.EmployeeReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.EmployeeWriteDao;

import java.util.List;

public class ShowEmployeeList extends BaseActivity {
    ListView emplist;
    List<Employee> employeeList;
    ArrayAdapter<Employee> adapter;
    Employee singleWorker;
    Dialog dialogBox;
    final Context context=this;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_show_employee_list);
        emplist= (ListView) findViewById(R.id.employeeList);
        getWorkers();
        buildAlert();
        setDialogBox();
        setAdapter();
        setOnClick();
    }

    private void buildAlert() {
       builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deluser);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Thread deluser = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EmployeeWriteDao empdao = new EmployeeWriteDao();
                        empdao.delete(singleWorker);
                    }
                });
                deluser.start();
                try {
                    deluser.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                employeeList.remove(singleWorker);
                adapter.notifyDataSetChanged();
                dialogBox.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        dialog = builder.create();
    }


    private void setDialogBox() {
        dialogBox = new Dialog(context);
        dialogBox.setContentView(R.layout.custom_dialog_employee);
        dialogBox.setTitle("Choose your option");


        Button change = (Button) dialogBox.findViewById(R.id.changeUser);
        Button delete = (Button) dialogBox.findViewById(R.id.deleteUser);
        Button cancel = (Button) dialogBox.findViewById(R.id.cancel);

       change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(),ChangeUserActivity.class);
                intent.putExtra("user_id",singleWorker.getId());
                dialogBox.dismiss();
                startActivity(intent);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.show();



            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBox.dismiss();
            }
        });
    }

    private void setOnClick() {
        emplist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                singleWorker = (Employee) parent.getAdapter().getItem(position);
                showDialogBox();

                return false;
            }
        });
    }

    private void showDialogBox() {
        dialogBox.show();
    }


    private void setAdapter() {

        adapter=new ArrayAdapter<Employee>(getApplicationContext(),android.R.layout.simple_list_item_1,employeeList);
        emplist.setAdapter(adapter);
    }

    private void getWorkers() {
        Thread getworkers = new Thread(new Runnable() {
            @Override
            public void run() {
                EmployeeReadDao empdao = new EmployeeReadDao();
                employeeList = empdao.getAll();
            }
        });
        getworkers.start();
        try {
            getworkers.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LoggedUser user = LoggedUser.getInstance();
        for(int i=0;i<employeeList.size();i++) {
            if((employeeList.get(i).getId()==user.getUser().getId())||(employeeList.get(i).getId()==1))
            {
                employeeList.remove(i);
                i--;
           }
        }
    }
}
