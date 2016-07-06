package com.example.androidpractice.easycloud.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.androidpractice.easycloud.UserModel;


public class CloudDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 4;

    static final String DATABASE_NAME = "cloud.db";

    public CloudDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //put your sql create table statements here
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + Contract.CloudUserEntry.TABLE_NAME + " (" +
                Contract.CloudUserEntry._ID + " INTEGER PRIMARY KEY," +
                Contract.CloudUserEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL, " +
                Contract.CloudUserEntry.COLUMN_USER_PASSWORD + " TEXT NOT NULL, " +
                Contract.CloudUserEntry.COLUMN_USER_TOKEN + " TEXT NOT NULL " +
                " );";

        Log.v("create table", SQL_CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.CloudUserEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public long addUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(Contract.CloudUserEntry.COLUMN_USER_EMAIL, user.email);
        values.put(Contract.CloudUserEntry.COLUMN_USER_PASSWORD, user.password);
        values.put(Contract.CloudUserEntry.COLUMN_USER_TOKEN, user.token);

        // insert row in students table

        long insert = db.insert(Contract.CloudUserEntry.TABLE_NAME, null, values);


        Log.v("Inserted value", "" + insert);
        return insert;
    }

    public UserModel getUser(String user_email) {
        SQLiteDatabase db = this.getReadableDatabase();


                Cursor c = db.rawQuery(
                "select * from " + Contract.CloudUserEntry.TABLE_NAME + " where email = ? ", new String[]{user_email});

        UserModel fakeUser = new UserModel();
        fakeUser.email = "error";
        fakeUser.password = "error";


        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            Log.v("Cursor value", c.toString());
        } else
            return fakeUser;


        UserModel user = new UserModel();
        user.email = c.getString(c.getColumnIndex(Contract.CloudUserEntry.COLUMN_USER_EMAIL));
        user.password = c.getString(c.getColumnIndex(Contract.CloudUserEntry.COLUMN_USER_PASSWORD));
        user.token = c.getString(c.getColumnIndex(Contract.CloudUserEntry.COLUMN_USER_TOKEN));

//        Log.v("user email", user.email);
//        Log.v("user password", user.password);
//        Log.v("user token", user.token);

        return user;
    }

    public int UpdateUserPassword(String user_email,String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = { user_email };
        String query =
                "UPDATE " + Contract.CloudUserEntry.TABLE_NAME
                        + " SET "   + Contract.CloudUserEntry.COLUMN_USER_PASSWORD + "=" + password
                        + " WHERE " + Contract.CloudUserEntry.COLUMN_USER_EMAIL +"=?";
        Log.i("TAG", query);
        Cursor c = db.rawQuery(query, args);

        /*if ( cu.getCount() > 0) {
            //c.moveToFirst();
            Log.v("Cursor value", String.valueOf(cu.getCount()));
        } else
            return 0;*/

     /*   UserModel user = new UserModel();
        user.email = c.getString(c.getColumnIndex(Contract.CloudUserEntry.COLUMN_USER_EMAIL));
        user.password = c.getString(c.getColumnIndex(Contract.CloudUserEntry.COLUMN_USER_PASSWORD));
        user.token = c.getString(c.getColumnIndex(Contract.CloudUserEntry.COLUMN_USER_TOKEN));

        Log.v("updated password",user.password);*/

        c.moveToFirst();
        c.close();
        return 1;
    }
}
