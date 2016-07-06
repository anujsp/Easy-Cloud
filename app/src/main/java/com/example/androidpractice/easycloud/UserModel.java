package com.example.androidpractice.easycloud;

/**
 * Created by Vishal on 6/7/2016.
 */

public class UserModel {


    public int id;
    public String email;
    public String token;
    public String password;


    public UserModel(int id, String email, String token, String password) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.email = email;
        this.token = token;
        this.password = password;
    }
    public UserModel(){

    }

}
