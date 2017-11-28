package com.hdh.appchatgr2.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.hdh.appchatgr2.R;

import dmax.dialog.SpotsDialog;

/**
 * Created by ASUS on 11/26/2017.
 */

public class DataAdapter extends AppCompatActivity {
    public AlertDialog progressDialog;
    public Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
    }

    protected void waitProgress(String title){
        progressDialog = new SpotsDialog(this,title,R.style.customProgress);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    protected void hideNotificationBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    protected void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
