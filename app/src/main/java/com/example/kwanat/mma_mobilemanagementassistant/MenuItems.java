package com.example.kwanat.mma_mobilemanagementassistant;

import java.util.ArrayList;

/**
 * Created by Kamil on 03.01.2018.
 */

public class MenuItems {
    private static MenuItems instance=null;
    private static ArrayList<String> ownerMenu;
    private static ArrayList<String> managerMenu;
    private static ArrayList<String> employeeMenu;

    private MenuItems(){}

    public static MenuItems getInstance()
    {
        if(instance==null)
        {
            instance = new MenuItems();
        }
        return instance;
    }


    public static ArrayList<String> getOwnerMenu() {
        return ownerMenu;
    }

    public static void setOwnerMenu(ArrayList<String> ownerMenu) {
        MenuItems.ownerMenu = ownerMenu;
    }

    public static ArrayList<String> getManagerMenu() {
        return managerMenu;
    }

    public static void setManagerMenu(ArrayList<String> managerMenu) {
        MenuItems.managerMenu = managerMenu;
    }

    public static ArrayList<String> getEmployeeMenu() {
        return employeeMenu;
    }

    public static void setEmployeeMenu(ArrayList<String> employeeMenu) {
        MenuItems.employeeMenu = employeeMenu;
    }

    public ArrayList<String> getmenu(int id){

        switch (id)
        {
            case 1:
                return ownerMenu;
            case 2:
                return managerMenu;
            default:
                return employeeMenu;
        }
    }
}
