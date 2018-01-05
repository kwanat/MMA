package com.example.kwanat.mma_mobilemanagementassistant;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.WarehouseReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Vacation;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Warehouse;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.WarehouseWriteDao;

import java.util.ArrayList;
import java.util.List;

public class ShowWarehouseActivity extends BaseActivity {
    ListView viewList;
    Button addProduct;
    Button showCritical;
    List<Warehouse> actualList;
    List<Warehouse> allItems;
    List<Warehouse> criticalItems;
    ArrayAdapter<Warehouse> adapter;
    final Context context = this;
    Dialog dialogBox;
    Warehouse item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_show_warehouse);
        actualList = new ArrayList<>();
        setView();
        resolvePriviliges();
        setButtons();
        getList();
        setAdapter();
        setDialog();
        setOnClick();

    }

    private void setOnClick() {
        viewList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item= (Warehouse) parent.getAdapter().getItem(position);
                dialogBox.show();
                return false;
            }
        });
    }

    private void setDialog() {
        dialogBox = new Dialog(context);
        dialogBox.setContentView(R.layout.custom_dialog_warehouse);
        dialogBox.setTitle("Choose your option");


        Button addButton = (Button) dialogBox.findViewById(R.id.addQuantityButton);
        Button getButton = (Button) dialogBox.findViewById(R.id.getQuantityButton);
        Button cancel = (Button) dialogBox.findViewById(R.id.WarehouseDialogCancelButton);

        final Button addSubmitButton = (Button) dialogBox.findViewById(R.id.addQuantitySubmitButton);
        final Button getSubmitButton = (Button) dialogBox.findViewById(R.id.getQuantitySubmitButton);

        final EditText addValue = (EditText) dialogBox.findViewById(R.id.addQuantityValue);
        final EditText getValue = (EditText) dialogBox.findViewById(R.id.getQuantityValue);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addSubmitButton.getVisibility()==View.GONE)
                {
                    addSubmitButton.setVisibility(View.VISIBLE);
                    addValue.setVisibility(View.VISIBLE);
                }
                else
                {
                    addSubmitButton.setVisibility(View.GONE);
                    addValue.setVisibility(View.GONE);
                }
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSubmitButton.getVisibility()==View.GONE)
                {
                    getSubmitButton.setVisibility(View.VISIBLE);
                    getValue.setVisibility(View.VISIBLE);
                }
                else
                {
                    getSubmitButton.setVisibility(View.GONE);
                    getValue.setVisibility(View.GONE);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubmitButton.setVisibility(View.GONE);
                addValue.setVisibility(View.GONE);
                getSubmitButton.setVisibility(View.GONE);
                getValue.setVisibility(View.GONE);
                dialogBox.dismiss();

            }


        });


        addSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addValue.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.emptyField),Toast.LENGTH_SHORT).show();
                else
                item.setActual(item.getActual()+Integer.valueOf(addValue.getText().toString()));
                Thread update = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        WarehouseWriteDao write = new WarehouseWriteDao();
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
                addSubmitButton.setVisibility(View.GONE);
                addValue.setVisibility(View.GONE);
                getSubmitButton.setVisibility(View.GONE);
                getValue.setVisibility(View.GONE);
                dialogBox.dismiss();

            }
        });
        getSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getValue.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.emptyField),Toast.LENGTH_SHORT).show();
                else if(item.getActual()<Integer.valueOf(getValue.getText().toString()))
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.valueTooHigh),Toast.LENGTH_SHORT).show();
                    else
                    item.setActual(item.getActual()-Integer.valueOf(getValue.getText().toString()));
                Thread update = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        WarehouseWriteDao write = new WarehouseWriteDao();
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
                addSubmitButton.setVisibility(View.GONE);
                addValue.setVisibility(View.GONE);
                getSubmitButton.setVisibility(View.GONE);
                getValue.setVisibility(View.GONE);
                dialogBox.dismiss();
            }
        });
    }



    private void setAdapter() {
        adapter=new ArrayAdapter<Warehouse>(context,android.R.layout.simple_list_item_2,android.R.id.text1,actualList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(actualList.get(position).getName());
                text2.setText(getResources().getString(R.string.actual)+": "+actualList.get(position).getActual());
                return view;
            }
        };
        viewList.setAdapter(adapter);
    }


    private void getList() {
        Thread getitems = new Thread(new Runnable() {
            @Override
            public void run() {
                WarehouseReadDao readDao = new WarehouseReadDao();
                LoggedUser user= LoggedUser.getInstance();
                switch(user.getUser().getId_workstation()) {
                    case 1:
                        allItems = readDao.getAll();
                        criticalItems = readDao.getLowQuantity();
                        break;
                    case 2:
                        allItems = readDao.getAll();
                        criticalItems = readDao.getLowQuantity();
                        break;
                    case 3:
                        allItems = readDao.getAllTechnical();
                        criticalItems = readDao.getTechnicalLowQuantity();
                        break;
                    default:
                        allItems = readDao.getAllNotTechnical();
                        criticalItems = readDao.getNotTechnicalLowQuantity();
                }

            }
        });
        getitems.start();
        try {
            getitems.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actualList.addAll(allItems);
    }

    private void resolvePriviliges() {
        LoggedUser user= LoggedUser.getInstance();
        if((user.getUser().getId_workstation()==1)||(user.getUser().getId_workstation()==2))
        {
            addProduct.setVisibility(View.VISIBLE);
            showCritical.setVisibility(View.VISIBLE);
        }
        else{
            addProduct.setVisibility(View.GONE);
            showCritical.setVisibility(View.GONE);
        }
    }

    private void setButtons() {
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddNewProductActivity.class);
                startActivity(intent);
            }
        });

        showCritical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO podmienic liste i wywolac refresh
                if(actualList.equals(allItems)) {
                    actualList.clear();
                    actualList.addAll(criticalItems);
                    showCritical.setText(getResources().getString(R.string.showAllItemsButton));
                }
                else {
                    actualList.clear();
                    actualList.addAll(allItems);
                    showCritical.setText(getResources().getString(R.string.showCriticalConditionButton));
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setView() {
        viewList=(ListView) findViewById(R.id.showWarehouseList);
        addProduct = (Button) findViewById(R.id.addItemToWarehouseButton);
        showCritical = (Button) findViewById(R.id.showCriticalCondition);
    }
}
