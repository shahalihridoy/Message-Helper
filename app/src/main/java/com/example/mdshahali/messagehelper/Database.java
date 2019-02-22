package com.example.mdshahali.messagehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class Database extends SQLiteOpenHelper {

    //[bug_: there is issue creating your own database]
    //[Solution1_: http://stackoverflow.com/a/29281714/4754141]
    //[Prerequisite : android_metadata table required]
    //[Solution2_: better edit DB_CATEGORY.sqlite in assets folder, if you want multiple database to copy]

    private static String DB_PATH = "";
    private static String DB_NAME = "love_message.db";
    private static final int DB_VERSION = 1;

    Cursor temp;

    private SQLiteDatabase mDataBase;
    private final Context ctx;

    Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        this.ctx = context;

        try {
            createDataBase();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void createDataBase() throws IOException {
        if(!checkDataBase()) {
            this.getReadableDatabase();
            copyDataBase();
            this.close();
        }
    }

    public boolean checkDataBase() {
        try {
//            when there is no exception, it means database exists
            temp = getCatagoryList();
            temp.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }


    public synchronized void close(){
        if(mDataBase != null)
            mDataBase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void copyDataBase() throws IOException {

        System.out.println("\n-----------------------------\ncurrentlly I am copying\n---------------\n");
        InputStream mInput =  ctx.getAssets().open("database/"+DB_NAME);
        String outfileName = DB_PATH;
        OutputStream mOutput = new FileOutputStream(outfileName);
        byte[] buffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(buffer))>0) {
            mOutput.write(buffer, 0, mLength);
        }
        mOutput.flush();
        mInput.close();
        mOutput.close();
    }


    public Cursor getCatagoryList() {
        SQLiteDatabase db = super.getReadableDatabase();
        Cursor c = db.rawQuery("select * from catagory", null);
        return c;
    }

    public Cursor getMessage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from message where id = "+id, null);
        return c;
    }

    public Cursor getFavouriteList(){
//        here true means 1 and false = 0
        return this.getReadableDatabase().rawQuery("select * from message where favourite=1",null);
    }

    public Cursor runCustomQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        return c;
    }

}
