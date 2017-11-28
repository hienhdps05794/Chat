package com.hdh.appchatgr2.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hdh.appchatgr2.Adapters.ViewPagerAdapter;
import com.hdh.appchatgr2.Beans.User;
import com.hdh.appchatgr2.R;
import com.hdh.appchatgr2.Setup;


public class LoggedInActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private ImageView ivRecent, ivFindFriend, ivContact, ivProfile;
    private LinearLayout sectionRecent, sectionFindFriend, sectionContact, sectionProfile;
    private FirebaseAuth mAuth ;
    private boolean doubleBackpressedtoExit = false;
    private final static int TIMEBACKPRESSED = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loggedin_activity);
        initControls();
        initData();
        initDisplay();
        initEvents();
        Log.e("loggedin", "create");
    }

    private void initData() {
        mAuth = FirebaseAuth.getInstance();
        Setup.currentUser = new User();
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Setup.currentUser.setmId(uid);
        updateOnline(true);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Setup.currentUser = dataSnapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoggedInActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateOnline(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateOnline(false);
        Log.e("loggedin", "destroy");
        Setup.currentUser = new User();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackpressedtoExit) {
            super.onBackPressed();
            finishAffinity();
            return;
        } else {
//            this.doubleBackpressedtoExit = true;
//            AlertDialog.Builder builder = new AlertDialog.Builder(LoggedInActivity.this);
//                    builder.setTitle("Log-out")
//                    .setMessage("Are you sure you want Logout?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            mAuth.signOut();
//                            Intent intent = new Intent(LoggedInActivity.this, StartActivity.class);
//                            startActivity(intent);
//                        }
//                    })
//                    .setNegativeButton("No", null);
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();



                if (getFragmentManager().getBackStackEntryCount() == 0 ) {
                    getFragmentManager().popBackStack();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("Log-Out")
                            .setMessage("Do you want Log-out?")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mAuth.getInstance().signOut();
                                    Intent intent = new Intent(LoggedInActivity.this, StartActivity.class);
                            startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorDone));
                    alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorCancel));

                } else {
                    super.onBackPressed();

                }
            }


//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackpressedtoExit = false;
//            }
//        }, TIMEBACKPRESSED);
    }

    private void initEvents() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ivRecent.setImageResource(R.drawable.recent_normal);
                ivFindFriend.setImageResource(R.drawable.find_friend_normal);
                ivContact.setImageResource(R.drawable.contact_normal);
                ivProfile.setImageResource(R.drawable.profile_normal);

                switch (position) {
                    case 0:
                        ivRecent.setImageResource(R.drawable.recent_active2);
                        break;
                    case 1:
                        ivFindFriend.setImageResource(R.drawable.find_friend_active2);
                        break;
                    case 2:
                        ivContact.setImageResource(R.drawable.contact_active2);
                        break;
                    case 3:
                        ivProfile.setImageResource(R.drawable.profile_active2);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        sectionRecent.setOnClickListener(this);
        sectionFindFriend.setOnClickListener(this);
        sectionContact.setOnClickListener(this);
        sectionProfile.setOnClickListener(this);
    }

    private void initDisplay() {
        initViewPager();
        initTopBar();
    }

    private void initTopBar() {
        ivRecent.setImageResource(R.drawable.recent_active2);
    }

    private void initViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
    }

    private void initControls() {
        viewPager = findViewById(R.id.vp_loggedin_activity);
        ivRecent = findViewById(R.id.iv_recent_loggedin_activity);
        ivFindFriend = findViewById(R.id.iv_findfriend_loggedin_activity);
        ivContact = findViewById(R.id.iv_contact_loggedin_activity);
        ivProfile = findViewById(R.id.iv_profile_loggedin_activity);
        sectionRecent = findViewById(R.id.section_recent_loggedin_activity);
        sectionFindFriend = findViewById(R.id.section_findfriend_loggedin_activity);
        sectionContact = findViewById(R.id.section_contact_loggedin_activity);
        sectionProfile = findViewById(R.id.section_profile_loggedin_activity);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.section_recent_loggedin_activity:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.section_findfriend_loggedin_activity:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.section_contact_loggedin_activity:
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.section_profile_loggedin_activity:
                viewPager.setCurrentItem(3, true);
                break;
        }
    }

    private void updateOnline(boolean isOnline) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(Setup.currentUser.getmId()).child("mIsOnline");
        databaseReference.onDisconnect().setValue(false);
        databaseReference.setValue(isOnline)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoggedInActivity.this, "lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}
