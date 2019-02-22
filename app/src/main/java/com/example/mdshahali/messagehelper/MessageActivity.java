package com.example.mdshahali.messagehelper;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    Cursor c;
    ArrayList<String> messageList = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

//        get data from main activity
        Intent intent = getIntent();
        int id = intent.getIntExtra("key",-1);

        messageList.add("Nothing found");

        getMessages(id);

        setMessageToList();
    }

    private void getMessages(int id) {
        if(id == -1){
            messageList.clear();
            return;
        }

//        messageList.clear();

        Database db = MainActivity.db;
        c = db.getMessage(id);

        if(c.getCount()>0){
            c.moveToFirst();
            do {
                messageList.add(c.getString(c.getColumnIndex("body")));
            } while (c.moveToNext());
        }

        c.close();
    }

    private void setMessageToList() {
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.message_list,R.id.messages,messageList);
        listView = (ListView) findViewById(R.id.messageList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                do what you need
//                make new intent with whole message

            }
        });
    }

}
