package com.example.mdshahali.messagehelper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    Cursor c;
    static int id;

    ArrayList<String> messageData = new ArrayList<String>();
    static ArrayList<Helper> messageList = new ArrayList<>();

    ListView listView;
    ImageButton fav_show;
    String catergory;
    int categoryID;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        fav_show = (ImageButton) findViewById(R.id.favShow);
        fav_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MessageActivity.this, MessageActivity.class);
                    intent.putExtra("key", -5);
                    startActivity(intent);
                } finally {
                    finish();
                }
            }
        });

//        get data from main activity
        Intent intent = getIntent();
        id = intent.getIntExtra("key", -1);

//        adding custom action bar
        addActionBar();

        getMessages(id);

        setMessageToList();
    }


    private void addActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.catagory);
        if (id == -5)
            textView.setText("Favourite List");
        else
            textView.setText(MainActivity.catagoryData.get(id - 1));

        ImageButton backButton = (ImageButton) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onBackPressed();
                } finally {
                    finish();
                }
            }
        });
    }


    private void getMessages(int id) {

        Database db = MainActivity.db;
        int track_id, fav;
        String body;

        messageList.clear();

        if (id == -1) {
            return;
        }

        if (id == -5)
            c = db.getFavouriteList();
        else
            c = db.getAllMessage(id);


        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                categoryID = c.getInt(c.getColumnIndex("id"));
                body = c.getString(c.getColumnIndex("body"));
                track_id = c.getInt(c.getColumnIndex("track_id"));
                fav = c.getInt(c.getColumnIndex("favourite"));

                messageList.add(new Helper(categoryID,track_id, body, fav));

            } while (c.moveToNext());
        }

        c.close();
    }


    private void setMessageToList() {

        listView = (ListView) findViewById(R.id.messageList);
        listView.setAdapter(new ListAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                make new intent with whole message

//                adding track_id, message_body, favourite, category name, message_number, total number, position in this array
                messageData.clear();
                messageData.add(String.valueOf(messageList.get(position).track_id));
                messageData.add(messageList.get(position).message_body);
                messageData.add(String.valueOf(messageList.get(position).favourite));
                messageData.add(String.valueOf(messageList.get(position).categoryID));
                messageData.add(String.valueOf(position + 1));
                messageData.add(String.valueOf(messageList.size()));
                messageData.add(String.valueOf(position));

                Intent intent = new Intent(MessageActivity.this, MessageDetails.class);
//                intent.putExtra("id",(int)(id+1));
                intent.putStringArrayListExtra("messageData", messageData);
                startActivity(intent);

            }
        });
    }


    class Helper {
        int categoryID;
        int track_id;
        String message_body;
        int favourite;

        Helper(int cat,int track_id, String message_body, int favourite) {
            this.categoryID = cat;
            this.track_id = track_id;
            this.favourite = favourite;
            this.message_body = message_body;
        }
    }

    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        TextView message;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.message_list, null);
            message = (TextView) view.findViewById(R.id.messages);
            message.setText(messageList.get(position).message_body);
            return view;
        }
    }

}
