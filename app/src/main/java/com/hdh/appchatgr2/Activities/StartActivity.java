package com.hdh.appchatgr2.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.hdh.appchatgr2.R;
import com.hdh.appchatgr2.Setup;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_already_account_start_activity, btn_create_account_start_activity;
    private FirebaseAuth mAuth;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initControl();
        initData();
    }

    private void initData() {
        mContext = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initControl() {
        btn_create_account_start_activity = findViewById(R.id.btn_already_account_start_activity);
        btn_create_account_start_activity.setOnClickListener(this);
        btn_already_account_start_activity = findViewById(R.id.btn_create_account_start_activity);
        btn_already_account_start_activity.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_already_account_start_activity:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_create_account_start_activity:
                Intent intent2 = new Intent(mContext, RegisterActivity.class);
                startActivity(intent2);
                break;

            default:
                break;

        }
    }


}
