package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.EmployeeReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.VacationReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Employee;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Vacation;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.VacationWriteDao;

import java.util.ArrayList;
import java.util.List;

public class ShowVacationRequests extends BaseActivity {

    ListView employeeList;
    ArrayAdapter<Vacation> adapter;
    List<Vacation> vacationList;
    List<Vacation> unsetList = new ArrayList<Vacation>();
    List<Vacation> confirmedList= new ArrayList<Vacation>();
    List<Vacation> rejectedList= new ArrayList<Vacation>();
    List<Employee> workers;
    Vacation item;
    Dialog dialogBox;
    CheckBox unsetBox;
    CheckBox confirmedBox;
    CheckBox rejectedBox;
    final Context context = this;

    private class CheckBoxSomethingChanger implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            updateList();

        }
    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_show_vacation_requests);

        setView();
        getWorkers();
        getVacationRequests();
        prepareVacationList();
        setDialogBox();
        setAdapter();
        setListener();

    }

    private void prepareVacationList() {

    }

    private void setView() {

        employeeList = (ListView)findViewById(R.id.showVacationList);
        unsetBox = (CheckBox) findViewById(R.id.unsetBox);
        confirmedBox = (CheckBox) findViewById(R.id.confirmedBox);
        rejectedBox = (CheckBox) findViewById(R.id.rejectedBox);

        unsetBox.setChecked(true);
        confirmedBox.setChecked(true);
        rejectedBox.setChecked(true);

        unsetBox.setOnCheckedChangeListener(new CheckBoxSomethingChanger());
        confirmedBox.setOnCheckedChangeListener(new CheckBoxSomethingChanger());
        rejectedBox.setOnCheckedChangeListener(new CheckBoxSomethingChanger());
    }

    private void setDialogBox() {
            dialogBox = new Dialog(context);
            dialogBox.setContentView(R.layout.custom_dialog_employee);
            dialogBox.setTitle("Choose your option");


            Button confirm = (Button) dialogBox.findViewById(R.id.changeUser);
            Button reject = (Button) dialogBox.findViewById(R.id.deleteUser);
            Button cancel = (Button) dialogBox.findViewById(R.id.cancel);

            confirm.setText("confirm");
            reject.setText("reject");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread setConfirm = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        VacationWriteDao writedao = new VacationWriteDao();
                        if(item.getStatus().equals("u"))
                            unsetList.remove(item);
                        if(item.getStatus().equals("r"))
                            rejectedList.remove(item);
                        if(!item.getStatus().equals("c")) {
                            item.setStatus("c");
                            writedao.update(item);
                            confirmedList.add(item);
                        }
                    }
                });
                setConfirm.start();
                try {
                    setConfirm.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateList();
                dialogBox.dismiss();

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread setRejected = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        VacationWriteDao writedao = new VacationWriteDao();
                        if(item.getStatus().equals("u"))
                            unsetList.remove(item);
                        if(item.getStatus().equals("c"))
                            confirmedList.remove(item);
                        if(!item.getStatus().equals("r")) {
                            item.setStatus("r");
                            writedao.update(item);
                            rejectedList.add(item);
                        }
                    }
                });
                setRejected.start();
                try {
                    setRejected.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateList();
                dialogBox.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBox.dismiss();
            }
        });




        }

    private void updateList() {
        vacationList.clear();
        if(unsetBox.isChecked())
            vacationList.addAll(unsetList);
        if(confirmedBox.isChecked())
            vacationList.addAll(confirmedList);
        if(rejectedBox.isChecked())
            vacationList.addAll(rejectedList);
        adapter.notifyDataSetChanged();
    }

    private void setListener() {
        employeeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = (Vacation) parent.getAdapter().getItem(position);
                showDialogBox();
                return false;
            }
        });
    }
    private void showDialogBox() {
        dialogBox.show();
    }

    private void setAdapter() {
        adapter=new ArrayAdapter<Vacation>(context,android.R.layout.simple_list_item_2,android.R.id.text1,vacationList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);


                Employee worker=findWorkerById(vacationList.get(position).getId_employee());

                text1.setText(worker.getFirst_name()+" "+worker.getLast_name());
                text2.setText(vacationList.get(position).getStart()+" --> "+vacationList.get(position).getEnd());
                return view;
            }
        };
        employeeList.setAdapter(adapter);
    }

    private void getVacationRequests() {
        Thread getVacationList = new Thread(new Runnable() {
            @Override
            public void run() {
                VacationReadDao readDao = new VacationReadDao();
                vacationList = readDao.getAll();
            }
        });
        getVacationList.start();
        try {
            getVacationList.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        unsetList.clear();
        confirmedList.clear();
        unsetList.clear();
        confirmedList.clear();
        rejectedList.clear();
        for(Vacation tmp : vacationList)
        {
            switch(tmp.getStatus()){
                case "u":
                    unsetList.add(tmp);
                    break;
                case "c":
                    confirmedList.add(tmp);
                    break;
                case "r":
                    rejectedList.add(tmp);
                    break;
                default:
                    unsetList.add(tmp);
                    break;

            }
        }
    }

    private Employee findWorkerById(int id_employee) {
        for(Employee temp : workers)
        {
            if(temp.getId()==id_employee)
                return temp;
        }
        return null;
    }

    private void getWorkers() {

        Thread getAllWorkers = new Thread(new Runnable() {
            @Override
            public void run() {
                EmployeeReadDao empdao= new EmployeeReadDao();
                workers=empdao.getAll();
            }
        });
        getAllWorkers.start();
        try {
            getAllWorkers.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
