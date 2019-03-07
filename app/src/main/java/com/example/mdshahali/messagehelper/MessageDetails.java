package com.example.mdshahali.messagehelper;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageDetails extends AppCompatActivity {

    String message;
    String track_id;
    String favourite;
    String messageNumber;
    String catagory;
    String position_in_array;

    ArrayList<String> messageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Intent intent = getIntent();
        messageData = intent.getStringArrayListExtra("messageData");

        track_id = messageData.get(0);
        favourite = messageData.get(2);
        message = messageData.get(1);
        messageNumber = String.format("%s of %s",messageData.get(4),messageData.get(5));
        catagory = messageData.get(3);
        position_in_array = messageData.get(6);

//        add custom action bar
        addActionBar();
//        load data to view
        loadData();
    }


    private void loadData() {

        TextView message_body = (TextView) findViewById(R.id.detailMessage);
        message_body.setText(message);

        TextView message_number = (TextView) findViewById(R.id.messageNumber);
        message_number.setText(messageNumber);
    }

    private void addActionBar() {

        final Database db = MainActivity.db;

        //      adding tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView catagoryView = (TextView) findViewById(R.id.catagory);
        catagoryView.setText(catagory);

        ImageButton backButton = (ImageButton) findViewById(R.id.back);
        final ImageButton favButton = (ImageButton) findViewById(R.id.favourite);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.back){
                    try {
                        onBackPressed();
                    } finally {
                        finish();
                    }
                }
                else {
                    if(favourite.equals("1")){
                        favButton.setColorFilter(Color.WHITE);
//                        favButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.not_fav_icon));
                        db.updateFavouriteList(track_id,"0");
                        favourite = "0";
                        MessageActivity.messageList.get(Integer.parseInt(position_in_array)).favourite = 0;
                    } else {
                        favButton.setColorFilter(Color.parseColor("#FF4081"));
//                        favButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.fav_icon));
                        db.updateFavouriteList(track_id,"1");
                        favourite = "1";
                        MessageActivity.messageList.get(Integer.parseInt(position_in_array)).favourite = 1;
                    }
                }
            }
        };

        backButton.setOnClickListener(listener);
        favButton.setOnClickListener(listener);

        if(favourite.equals("1")){
            favButton.setColorFilter(Color.parseColor("#FF4081"));
//            favButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.fav_icon));
        }

    }


}
