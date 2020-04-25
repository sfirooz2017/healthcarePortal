package edu.nyit.healthcareportal;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GlobalData {
    private static GlobalData instance = new GlobalData();

    public static GlobalData getInstance() {
        return instance;
    }

    private boolean admin;
    private List<Users> users;
    private int orderNum;


    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }


    public String getDate() {
        String date = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());
        Log.d("shan", date);
        return date;

    }
    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}

