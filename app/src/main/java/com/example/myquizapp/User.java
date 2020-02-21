package com.example.myquizapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String pswd;
    private String cpswd;
    private String hint;
    private boolean complete;

    public User() {
    }

    public User(String fname, String lname, String email, String phone, String pswd, String cpswd, String hint) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.pswd = pswd;
        this.cpswd = cpswd;
        this.hint = hint;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getCpswd() {
        return cpswd;
    }

    public void setCpswd(String cpswd) {
        this.cpswd = cpswd;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }


}

