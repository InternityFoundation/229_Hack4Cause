package com.project.speakhack.hackathon;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAuth.getCurrentUser()!=null){
                    db.child("Users").child(mAuth.getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot!=null){
                                if(dataSnapshot.child("status").getValue().toString().equalsIgnoreCase("pending")) {
                                    Intent intent = new Intent(WelcomeActivity.this, StatusActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                else if(dataSnapshot.child("status").getValue().toString().equalsIgnoreCase("approved")){
                                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                    String type=dataSnapshot.child("type").getValue().toString();
                                    intent.putExtra("type",type);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },5000);
    }
}
