package com.hdh.appchatgr2.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hdh.appchatgr2.Adapters.DataAdapter;
import com.hdh.appchatgr2.Beans.User;
import com.hdh.appchatgr2.R;
import com.hdh.appchatgr2.Setup;

import java.util.HashMap;

public class RegisterActivity extends DataAdapter implements View.OnClickListener, FirebaseAuth.AuthStateListener {
    private TextView tv_have_account_register_activity;
    private TextInputLayout tip_name_user_register_activity, tip_email_user_register_activity, tip_password_user_register_activity;
    private Button btn_create_acc_register_activity, btn_haveaccount_login_activity;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initControl();
        initData();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }

    private void initEvent() {

    }

    private void createAccount(final String email, final String password, final String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "thành công:", Toast.LENGTH_SHORT).show();
                            writeNewUser(email,name,password);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Lỗi: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }



    private void writeNewUser(final String email, String name, final String password) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        User user = new User(uid,name,User.DEFAULT_AVATAR,false,User.NOT_UP_DATE_YET,User.NOT_UP_DATE_YET,User.NOT_UP_DATE_YET);

        databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mAuth.addAuthStateListener(RegisterActivity.this);
                }else {
                    Toast.makeText(RegisterActivity.this, "Lỗi: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initData() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void initControl() {
        btn_haveaccount_login_activity = findViewById(R.id.btn_haveaccount_login_activity);
        tip_name_user_register_activity = findViewById(R.id.tip_name_user_register_activity);
        tip_email_user_register_activity = findViewById(R.id.tip_email_user_register_activity);
        tip_password_user_register_activity = findViewById(R.id.tip_password_user_register_activity);
        btn_create_acc_register_activity = findViewById(R.id.btn_create_acc_register_activity);

        btn_haveaccount_login_activity.setOnClickListener(this);
        btn_create_acc_register_activity.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_acc_register_activity:
                waitProgress("Creating account...");
                getDataEdt();
                break;
            case R.id.btn_haveaccount_login_activity:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
    private void goToLoggedInActivity(){
        Intent intent = new Intent(this,LoggedInActivity.class);
        startActivity(intent);
        finishAffinity();
    }
    private void getDataEdt(){
        name = tip_name_user_register_activity.getEditText().getText().toString();
        email = tip_email_user_register_activity.getEditText().getText().toString();
        password = tip_password_user_register_activity.getEditText().getText().toString();
        createAccount(email, password, name);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        boolean hasUser =firebaseUser!=null;
        Log.e("Register,hasUser","changed Auth "+hasUser);
        if (firebaseUser!=null){
            goToLoggedInActivity();
        }
    }
}
