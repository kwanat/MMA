package com.example.kwanat.mma_mobilemanagementassistant.DB.Tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kamil on 20.10.2017.
 */
@DatabaseTable(tableName = "Warehouse")
public class Warehouse {
    public static final String WAREHOUSE_ID = "Id_product";
    public static final String WAREHOUSE_ID_CATEGORY = "Id_category";
    public static final String WAREHOUSE_PRODUCT_NAME = "Product_name";
    public static final String WAREHOUSE_ACTUAL_QUANTITY = "Actual_quantity";
    public static final String WAREHOUSE_CRITICAL_QUANTITY= "Critical_quantity";

    @DatabaseField(generatedId = true, columnName = WAREHOUSE_ID)
    private int id;

    @DatabaseField(columnName = WAREHOUSE_ID_CATEGORY)
    private int id_category;

    @DatabaseField(columnName = WAREHOUSE_PRODUCT_NAME)
    private String name;

    @DatabaseField(columnName = WAREHOUSE_ACTUAL_QUANTITY)
    private int actual;

    @DatabaseField(columnName = WAREHOUSE_CRITICAL_QUANTITY)
    private int critical;

    public Warehouse()
    {

    }
    public Warehouse(int id, String name, int actual, int critical)
    {
        this.id_category=id;
        this.name=name;
        this.actual=actual;
        this.critical=critical;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }
}
