package com.example.kwanat.mma_mobilemanagementassistant.DB.Tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kamil on 28.12.2017.
 */
@DatabaseTable(tableName = "Vacation")
public class Vacation {
    public static final String VACATION_ID = "Id_vacation";
    public static final String VACATION_ID_EMPLOYEE = "Id_employee";
    public static final String VACATION_START = "Start_date";
    public static final String VACATION_END = "End_date";
    public static final String VACATION_APPROVED = "Approved";

    @DatabaseField(generatedId = true, columnName = VACATION_ID)
    private int id;

    @DatabaseField(columnName = VACATION_ID_EMPLOYEE)
    private int id_employee;

    @DatabaseField(columnName = VACATION_START)
    private String start;

    @DatabaseField(columnName = VACATION_END)
    private String end;

    @DatabaseField(columnName = VACATION_APPROVED)
    private String approved;

    public Vacation()
    {}

    public Vacation(int id, String start, String stop, String approved)
    {
        this.id_employee=id;
        this.start=start;
        this.end=stop;
        this.approved=approved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }
}
