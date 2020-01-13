package com.example.android.aide;

public class Users {
    private String name;
    private String contactno;
    private String uname;
    private String user_role;

    public Users(){}

    public Users(String name, String contactno, String uname, String user_role){
        this.name = name;
        this.contactno = contactno;
        this.uname = uname;
        this.user_role = user_role;
    }

    public String getName(){
        return name;
    }
    public String getContactno(){
        return contactno;
    }
    public String getUname(){
        return uname;
    }
    public String getUserRole(){
        return user_role;
    }
}
