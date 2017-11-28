package com.hdh.appchatgr2.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hdh.appchatgr2.Adapters.DataAdapter;
import com.hdh.appchatgr2.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends DataAdapter implements View.OnClickListener {
    private TextInputLayout tip_email_user_login_activity, tip_password_user_login_activity;
    private Button btn_login, btn_signup_login_activity;
    private FirebaseAuth mAuth;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        initDisplay();
        initControl();
        initData();
        initEvent();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private void loginAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            goToLoggedInActivity();
                            progressDialog.dismiss();

                        } else {
                            // giao diện thất bại
                            progressDialog.dismiss();

                            Toast.makeText(LoginActivity.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initEvent() {
    }

    private void initData() {
        mAuth = FirebaseAuth.getInstance();
        mContext = getApplicationContext();
    }

    private void initControl() {
        tip_email_user_login_activity = findViewById(R.id.tip_email_user_login_activity);
        tip_email_user_login_activity.setErrorEnabled(true);
        tip_password_user_login_activity = findViewById(R.id.tip_password_user_login_activity);
        tip_password_user_login_activity.setErrorEnabled(true);
        btn_login = findViewById(R.id.btn_login_activity);
        btn_login.setOnClickListener(this);
        btn_signup_login_activity = findViewById(R.id.btn_signup_login_activity);
        btn_signup_login_activity.setOnClickListener(this);
    }

    private void initDisplay() {
        hideNotificationBar();
    }

    private void goToLoggedInActivity() {
        Intent intent = new Intent(mContext, LoggedInActivity.class);
        startActivity(intent);
        finishAffinity();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_activity:
                waitProgress("Loging...");
                getDataEdt();
                break;
            case R.id.btn_signup_login_activity:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void getDataEdt() {
        String email = tip_email_user_login_activity.getEditText().getText().toString();
        String password = tip_password_user_login_activity.getEditText().getText().toString();
        hideKeyBoard();
//        if (matcher.matches()) {
        if (email.equals("")) {
            tip_email_user_login_activity.setError("Please enter the email");
            progressDialog.dismiss();
        } else {
            tip_email_user_login_activity.setError("");
        }

        if (password.equals("")) {
            tip_password_user_login_activity.setError("Please enter the password");
            progressDialog.dismiss();
        } else {
            tip_password_user_login_activity.setError("");
        }
        if (!email.isEmpty() && !password.isEmpty()){
                        loginAccount(email, password);

        }


    }

//    private boolean validatePassword(String password) {
//        if (password.equals("")){
//            return true;
//        }else {
//            return false;
//        }
//
//    }
//
//    private boolean validateEmail(String email) {
////        String emailParttern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)"
////                + "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])\n";
////        Pattern pattern = Pattern.compile(emailParttern);
////        Matcher matcher = pattern.matches(t);
//
//        return false;
//    }

    private boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url);
        if (m.matches())
            return true;
        else
            return false;
    }
}
