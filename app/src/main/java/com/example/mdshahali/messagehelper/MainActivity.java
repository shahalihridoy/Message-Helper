package com.example.mdshahali.messagehelper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView catagoryList;
    static Database db; //static is for accessing from other class without creating object again

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showCatagoryList();
    }


//    this code will show the catagory list

    public void showCatagoryList() {

        catagoryList = (ListView) findViewById(R.id.catagoryList);
        ArrayList<String> catagoryData = new ArrayList<String>();

        db = new Database(this);

        Cursor c = db.getCatagoryList();

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                catagoryData.add(c.getString(c.getColumnIndex("catagory")));
            } while (c.moveToNext());
        }

        c.close();

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.catagory_list, R.id.catagory, catagoryData);

        catagoryList.setAdapter(adapter);

        catagoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                here position would be the primary key for searching in database
//                opening new window to show message of corresponding category

                Intent intent = new Intent(MainActivity.this,MessageActivity.class);
                intent.putExtra("key",(int)(id+1));
                startActivity(intent);

            }
        });

    }

}