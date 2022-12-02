package com.example.emercall;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {
    FirebaseUser firebaseUser;

    TextView txt_name,txt_email,txt_phone, verifyMsg, txt_verify, txt_verified;
    ImageView photo_imageView, verify_imageview, verified_imageview;
    LinearLayout btnVerifyEmail;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;

    private FirebaseUser user;
    private DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        this.setTitle("Profile");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        photo_imageView = findViewById(R.id.photo_imageView);
        verify_imageview = findViewById(R.id.verify_imageview);
        verified_imageview = findViewById(R.id.verified_imageview);
        txt_verify = findViewById(R.id.txt_verify);
        txt_verified = findViewById(R.id.txt_verified);
        btnVerifyEmail = findViewById(R.id.btnVerifyEmail);


        if (firebaseAuth.getCurrentUser() == null){

        } else {
            userID = firebaseAuth.getCurrentUser().getUid();
            final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if (!firebaseUser.isEmailVerified()) {
                verified_imageview.setVisibility(View.GONE);
                txt_verified.setVisibility(View.GONE);

                verify_imageview.setVisibility(View.VISIBLE);
                txt_verify.setVisibility(View.VISIBLE);
            } else {
                verify_imageview.setVisibility(View.GONE);
                txt_verify.setVisibility(View.GONE);

                verified_imageview.setVisibility(View.VISIBLE);
                txt_verified.setVisibility(View.VISIBLE);
                btnVerifyEmail.setOnClickListener(null);

            }

        }


    }


    public void btnLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    public void btnVerifyEmail(final View view) {
        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(view,"Verification Email has been Sent, remember to Logout and Login again after verification!!", BaseTransientBottomBar.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","onFailure: Email not sent " + e.getMessage());
            }
        });
    }

    public void btnResetPassword(final View view) {

        final EditText resetPassword = new EditText(view.getContext());
        final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset Password ?");
        passwordResetDialog.setMessage("Enter New Password greater than 6 characters long.");
        passwordResetDialog.setView(resetPassword);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String newPassword = resetPassword.getText().toString();
                firebaseUser.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(view,"Password Reset Successfully.", BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(view,"Password Reset Failed!!", BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                });
            }
        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        passwordResetDialog.create().show();
    }

    public void onBackPressed() {
        finish();
    }

}
