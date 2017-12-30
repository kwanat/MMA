package com.example.kwanat.mma_mobilemanagementassistant.DB.Tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Kamil on 20.10.2017.
 */

@DatabaseTable(tableName = "Employee")
public class Employee {
    public static final String EMPLOYEE_ID = "Id_employee";
    public static final String EMPLOYEE_LOGIN = "Login";
    public static final String EMPLOYEE_PASSWORD = "Password";
    public static final String EMPLOYEE_FIRST_NAME = "First_name";
    public static final String EMPLOYEE_LAST_NAME = "Last_name";
    public static final String EMPLOYEE_PESEL = "Pesel";
    public static final String EMPLOYEE_ADDRESS = "Address";
    public static final String EMPLOYEE_PHONE_NUMBER = "Phone_number";
    public static final String EMPLOYEE_WORKSTATION = "Id_workstation";

    @DatabaseField(generatedId = true,columnName = EMPLOYEE_ID)
    private int id;

    @DatabaseField(columnName = EMPLOYEE_LOGIN)
    private String login;

    @DatabaseField(columnName = EMPLOYEE_PASSWORD)
    private String password;

    @DatabaseField(columnName = EMPLOYEE_FIRST_NAME)
    private String first_name;

    @DatabaseField(columnName = EMPLOYEE_LAST_NAME)
    private String last_name;

    @DatabaseField(columnName = EMPLOYEE_PESEL)
    private String pesel;

    @DatabaseField(columnName = EMPLOYEE_ADDRESS)
    private String address;

    @DatabaseField(columnName = EMPLOYEE_PHONE_NUMBER)
    private int phone_number;

    @DatabaseField(columnName = EMPLOYEE_WORKSTATION)
    private int id_workstation;

    public Employee() {

    }
    public Employee(String first_name,String last_name, String pesel, String address,int phone_number,int id_workstation)
    {
        this.first_name=first_name;
        this.last_name=last_name;
        this.pesel=pesel;
        this.address=address;
        this.phone_number=phone_number;
        this.id_workstation=id_workstation;
    }
    public Employee(String first_name,String last_name, String login, String password)
    {
        this.first_name=first_name;
        this.last_name=last_name;
        this.login=login;
        this.password=password;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public int getId_workstation() {
        return id_workstation;
    }

    public void setId_workstation(int id_workstation) {
        this.id_workstation = id_workstation;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
