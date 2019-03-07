package com.example.mdshahali.messagehelper;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = "love_message.db";
    private static final int DB_VERSION = 1;

    private Context context;
    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.context = context;

        try {
            createDataBase();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    static public synchronized DatabaseHelper getInstance(Context context){
        if(instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    private void createDataBase() {
        if(!checkDataBase()) {
            copyDataBase();
            this.close();
        }
    }

    private boolean checkDataBase() {
//        getReadableDatabase("fdksfasd").rawq
//        getWritableDatabase("fasdafdsa").execSQL("");
        return true;
    }

    private void copyDataBase() {
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
