package com.example.kwanat.mma_mobilemanagementassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kwanat.mma_mobilemanagementassistant.DB.ReadDao.CategoryReadDao;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Category;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Warehouse;
import com.example.kwanat.mma_mobilemanagementassistant.DB.Tables.Workstation;
import com.example.kwanat.mma_mobilemanagementassistant.DB.WriteDao.WarehouseWriteDao;

import java.util.List;

public class AddNewProductActivity extends BaseActivity {

    EditText productName;
    EditText productQuantity;
    EditText productCriticalQuantity;
    Spinner productCategorySpinner;
    Button submitButton;
    List<Category> categories;
    int productCategory;
    ArrayAdapter<Category> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_new_product);
        setView();
        getCategories();
        setAdapter();
        setButtons();
    }

    private void setAdapter() {
        adapter= new ArrayAdapter<Category>(getApplicationContext(),android.R.layout.simple_spinner_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategorySpinner.setAdapter(adapter);
        productCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(),"wybrano opcje"+position,Toast.LENGTH_SHORT).show();
                productCategory=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setButtons() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields())
                {
                    Thread addProduct = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            WarehouseWriteDao write = new WarehouseWriteDao();
                            int productquantity = Integer.valueOf(productQuantity.getText().toString());
                            int productcritical = Integer.valueOf(productCriticalQuantity.getText().toString());
                            Warehouse product = new Warehouse(productCategory,productName.getText().toString(),productquantity,productcritical);
                            write.save(product);
                        }
                    });
                    addProduct.start();
                    try {
                        addProduct.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getApplicationContext(),ShowWarehouseActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean checkFields() {
        if((productName.getText().toString().isEmpty())||(productQuantity.getText().toString().isEmpty())||(productCriticalQuantity.getText().toString().isEmpty()))
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.missingFields),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getCategories() {
        Thread get=new Thread(new Runnable() {
            @Override
            public void run() {
                CategoryReadDao read = new CategoryReadDao();
                categories=read.getAll();
            }
        });
        get.start();
        try {
            get.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setView() {
        productName = (EditText) findViewById(R.id.addProductName);
        productQuantity = (EditText) findViewById(R.id.addProductQuantity);
        productCriticalQuantity = (EditText) findViewById(R.id.addProductCriticalQuantity);
        productCategorySpinner = (Spinner) findViewById(R.id.adCategoryProductSpinner);
        submitButton = (Button) findViewById(R.id.addProductSubmitButton);
    }
}
