package com.hdh.appchatgr2.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hdh.appchatgr2.Activities.StartActivity;
import com.hdh.appchatgr2.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;


import java.util.Calendar;
import java.util.Random;

import dmax.dialog.SpotsDialog;


public class FragmentProfile extends Fragment implements View.OnClickListener {
    private TextView tv_working_user_profile_fragment, tv_provine_user_profile_fragment, tv_name_user_profile_fragment, tv_birth_user_profile_fragment, tv_aboutus_fragment_profile;
    private Button btn_logout_profile_fragment;
    private View view;
    private FirebaseAuth mAuth;
    private Context mContext;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private ImageView iv_edit_working_profile_fragment, iv_edit_name_profile_fragment, iv_edit_province_profile_fragment, iv_edit_birth_profile_fragment, iv_user_fragment_profile;
    private String name, province, occupation, birth, image, b;
    private AlertDialog progressDialog;
    private int day, month, year;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    public static final int GALLERY_PICK = 1;
    public static final int MAX_LENGTH = 9;
    private StorageReference storageIMG;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initControl();
        initData();
        readDataProfile();
        return view;

    }

    private void readDataProfile() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("mName").getValue().toString();
                province = dataSnapshot.child("mProvince").getValue().toString();
                occupation = dataSnapshot.child("mOccupation").getValue().toString();
                birth = dataSnapshot.child("mBirth").getValue().toString();
                image = dataSnapshot.child("mLinkImage").getValue().toString();

                tv_name_user_profile_fragment.setText(name);
                tv_working_user_profile_fragment.setText(occupation);
                tv_provine_user_profile_fragment.setText(province);
                tv_birth_user_profile_fragment.setText(birth);
                Picasso.with(mContext).load(image).into(iv_user_fragment_profile);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showEditDialog(String titleEditDialog, final String nameDB, final String result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View diaglogView = layoutInflater.inflate(R.layout.dialog_edt_profile_fragment, null);
        builder.setView(diaglogView);

        final EditText editText = diaglogView.findViewById(R.id.edt_edit_name_profile_fragment);

        builder.setTitle(titleEditDialog);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                waitProgress("Please waiting...");
                String editName = editText.getText().toString();
                databaseReference.child(nameDB).setValue(editName).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorDone));
        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorCancel));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout_profile_fragment:
                mAuth.getInstance().signOut();
                Intent intent = new Intent(mContext, StartActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_edit_name_profile_fragment:
                showEditDialog("Edit your name", "mName", "Edit your name success");
                break;
            case R.id.iv_edit_birth_profile_fragment:
                calendarBirth();
                Toast.makeText(mContext, "Checked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_edit_province_profile_fragment:
                showEditDialog("Edit your province", "mProvince", "Edit your province success");
                break;
            case R.id.iv_edit_working_profile_fragment:
                showEditDialog("Edit your occpaciton", "mOccupation", "Edit your occupation success");
                break;
            case R.id.tv_aboutus_fragment_profile:
                infoAboutUs();
                break;
            case R.id.iv_user_fragment_profile:
                selectGallery();
                break;
            default:
                break;
        }
    }

    private void initData() {
        storageIMG = FirebaseStorage.getInstance().getReference();
        calendar = Calendar.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mContext = getContext().getApplicationContext();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

    }

    private void initControl() {
        tv_working_user_profile_fragment = view.findViewById(R.id.tv_working_user_profile_fragment);
        tv_provine_user_profile_fragment = view.findViewById(R.id.tv_provine_user_profile_fragment);
        tv_name_user_profile_fragment = view.findViewById(R.id.tv_name_user_profile_fragment);
        tv_aboutus_fragment_profile = view.findViewById(R.id.tv_aboutus_fragment_profile);
        tv_birth_user_profile_fragment = view.findViewById(R.id.tv_birth_user_profile_fragment);
        btn_logout_profile_fragment = view.findViewById(R.id.btn_logout_profile_fragment);
        iv_edit_working_profile_fragment = view.findViewById(R.id.iv_edit_working_profile_fragment);
        iv_edit_name_profile_fragment = view.findViewById(R.id.iv_edit_name_profile_fragment);
        iv_edit_province_profile_fragment = view.findViewById(R.id.iv_edit_province_profile_fragment);
        iv_edit_birth_profile_fragment = view.findViewById(R.id.iv_edit_birth_profile_fragment);
        iv_user_fragment_profile = view.findViewById(R.id.iv_user_fragment_profile);


        btn_logout_profile_fragment.setOnClickListener(this);
        iv_edit_working_profile_fragment.setOnClickListener(this);
        iv_edit_name_profile_fragment.setOnClickListener(this);
        iv_edit_province_profile_fragment.setOnClickListener(this);
        iv_edit_birth_profile_fragment.setOnClickListener(this);
        iv_user_fragment_profile.setOnClickListener(this);
        tv_aboutus_fragment_profile.setOnClickListener(this);
    }

    private void waitProgress(String titleProgress) {
        progressDialog = new SpotsDialog(getActivity(), R.style.customProgress);
        progressDialog.setTitle(titleProgress);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    private void calendarBirth() {
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                String s = dayOfMonth + " - " + month + " - " + year;


                databaseReference.child("mBirth").setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        tv_birth_user_profile_fragment.setText(dayOfMonth + " - " + month + " - " + year);
//
                    }
                });
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void infoAboutUs() {

    }

    private void selectGallery() {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, GALLERY_PICK);

//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(getActivity());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == Activity.RESULT_OK) {
            waitProgress("Waiting...");
            Uri uri = data.getData();
            String currentID = firebaseUser.getUid();
            StorageReference path = storageIMG.child("Photos").child(currentID + ".jpg");

            path.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        String downloadIMG = task.getResult().getDownloadUrl().toString();
                        databaseReference.child("mLinkImage").setValue(downloadIMG).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, "fail", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

////        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_PICK){
////            Uri imgUri = data.getData();
////            CropImage.activity(imgUri)
////                    .start(getActivity());
////
////        }
////
////        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
////            CropImage.ActivityResult result = CropImage.getActivityResult(data);
////            if (resultCode == Activity.RESULT_OK) {
////                Uri resultUri = result.getUri();
////
////                String currentID = firebaseUser.getUid();
////                StorageReference path = storageIMG.child("Photos").child(currentID + ".jpg");
////
////                path.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
////                    @Override
////                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
////                        if (task.isSuccessful()) {
////                            String downloadIMG = task.getResult().getDownloadUrl().toString();
////                            databaseReference.child("mLinkImage").setValue(downloadIMG).addOnCompleteListener(new OnCompleteListener<Void>() {
////                                @Override
////                                public void onComplete(@NonNull Task<Void> task) {
////                                    if (task.isSuccessful()) {
////                                        progressDialog.dismiss();
////                                        Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
////                                    }
////
////                                }
////                            });
////
////
////
////                        } else {
////                            progressDialog.dismiss();
////                            Toast.makeText(mContext, "fail", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////
////
////                });
//
//
//
//
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
    }


}
