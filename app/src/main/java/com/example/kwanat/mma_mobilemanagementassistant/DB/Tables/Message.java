package com.example.kwanat.mma_mobilemanagementassistant.DB.Tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kamil on 20.10.2017.
 */
@DatabaseTable(tableName = "Message")
public class Message {
    public static final String MESSAGE_ID = "Id_message";
    public static final String MESSAGE_ID_EMPLOYEE = "Id_employee";
    public static final String MESSAGE_TITLE = "Title";
    public static final String MESSAGE_DATE = "Date";
    public static final String MESSAGE_MESSAGE = "Message";

    @DatabaseField(generatedId = true, columnName = MESSAGE_ID)
    private int id;

    @DatabaseField( columnName = MESSAGE_ID_EMPLOYEE)
    private int id_employee;

    @DatabaseField( columnName = MESSAGE_TITLE)
    private String title;

    @DatabaseField( columnName = MESSAGE_DATE)
    private String date;

    @DatabaseField( columnName = MESSAGE_MESSAGE)
    private String message;



    public Message()
    {

    }


    public Message(int id, String date, String title, String message)
    {
        this.id_employee=id;
        this.date=date;
        this.title=title;
        this.message=message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return this.title + " "+this.date;
    }
}
