package com.hdh.appchatgr2.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hdh.appchatgr2.R;

public class SplashScreen extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth.getInstance().addAuthStateListener(SplashScreen.this);

            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    void goToLoggedInActivity() {
        Intent intent = new Intent(getApplicationContext(), LoggedInActivity.class);
        startActivity(intent);
        finish();
    }

    void goToStartActivity() {
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Log.e("Check session Splash", String.valueOf(user!=null));
        if (user != null) {
            //đã từng đăng nhập

            goToLoggedInActivity();
        } else {
            //chưa từng có phiên đăng nhập trước hoặc đã logout
            goToStartActivity();
        }
    }
}
