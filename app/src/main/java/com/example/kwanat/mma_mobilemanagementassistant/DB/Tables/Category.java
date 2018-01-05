package com.example.kwanat.mma_mobilemanagementassistant.DB.Tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kamil on 20.10.2017.
 */

@DatabaseTable(tableName = "Category")
public class Category {
    public static final String CATEGORY_ID="Id_category";
    public static final String CATEGORY_NAME="Category_name";

    @DatabaseField(generatedId = true, columnName = CATEGORY_ID)
    private int id;

    @DatabaseField(columnName = CATEGORY_NAME)
    private String name;

    public Category()
    {

    }
    public Category(String name)
    {
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
