package com.hdh.appchatgr2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hdh.appchatgr2.R;
import com.hdh.appchatgr2.Setup;

public class ChatActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        textView = (TextView) findViewById(R.id.tv);
        textView.setText(Setup.currentUser.getmName());
    }
}
