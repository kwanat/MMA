package com.example.kwanat.mma_mobilemanagementassistant.DB.Tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kamil on 20.10.2017.
 */

@DatabaseTable(tableName = "Workstation")
public class Workstation {
    public static final String WORKSTATION_ID = "Id_workstation";
    public static final String WORKSTATION_NAME= "Workstation_name";

    @DatabaseField(generatedId = true,columnName = WORKSTATION_ID)
    private int id;

    @DatabaseField(columnName = WORKSTATION_NAME)
    private String name;

    public Workstation()
    {

    }
    public Workstation(String name)
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
    public String toString()
    {
        return this.name;
    }
}
