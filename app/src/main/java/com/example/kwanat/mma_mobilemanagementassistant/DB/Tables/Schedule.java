package com.example.kwanat.mma_mobilemanagementassistant.DB.Tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kamil on 20.10.2017.
 */

@DatabaseTable(tableName = "Schedule")
public class Schedule {
    public static final String SCHEDULE_ID_SCHEDULE = "Id_schedule";
    public static final String SCHEDULE_ID_EMPLOYEE = "Id_employee";
    public static final String SCHEDULE_DATE = "Date";
    public static final String SCHEDULE_START = "Start_time";
    public static final String SCHEDULE_END = "End_time";
    public static final String SCHEDULE_APPROVED = "Approved";

    @DatabaseField(generatedId = true, columnName = SCHEDULE_ID_SCHEDULE)
    private int id;

    @DatabaseField(columnName = SCHEDULE_ID_EMPLOYEE)
    private int id_employee;

    @DatabaseField(columnName = SCHEDULE_DATE)
    private String date;

    @DatabaseField(columnName = SCHEDULE_START)
    private String start;

    @DatabaseField(columnName = SCHEDULE_END)
    private String end;

    @DatabaseField(columnName = SCHEDULE_APPROVED)
    private String approved;


    public Schedule()
    {

    }

    public Schedule(int id_employee, String day, String start, String closing, String approved)
    {
        this.id_employee=id_employee;
        this.date=day;
        this.start=start;
        this.end=closing;
        this.approved=approved;
    }

    public Schedule(int id_employee, String day, String approved)
    {
        this.id_employee=id_employee;
        this.date=day;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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