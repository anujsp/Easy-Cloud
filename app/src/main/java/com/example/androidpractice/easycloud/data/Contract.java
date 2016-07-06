package com.example.androidpractice.easycloud.data;

import android.provider.BaseColumns;


public class Contract {

    //display_title, summary_short, and publish_date.
    public static final class CloudUserEntry implements BaseColumns {


        // Table name
        public static final String TABLE_NAME = "users";
        //Columns
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_USER_PASSWORD = "password";
        public static final String COLUMN_USER_TOKEN = "token";

    }


}
