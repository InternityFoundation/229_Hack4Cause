package com.project.speakhack.hackathon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatusActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private TextView status_txt;
    private Button get_started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        status_txt=(TextView)findViewById(R.id.status_txt);
        mAuth=FirebaseAuth.getInstance();
        get_started=(Button)findViewById(R.id.get_started);
        //get_started.setEnabled(false);
        get_started.setVisibility(View.INVISIBLE);
        db=FirebaseDatabase.getInstance().getReference();
        db.child("Users").child(mAuth.getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    String txt=dataSnapshot.child("status").getValue().toString();
                    status_txt.setText(txt);
                    if(txt.equalsIgnoreCase("approved")){
                        get_started.setVisibility(View.VISIBLE);
                        //get_started.setEnabled(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.child("Users").child(mAuth.getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null){
                            String type=dataSnapshot.child("type").getValue().toString();
                            Intent intent=new Intent(StatusActivity.this,LoginActivity.class);
                            mAuth.signOut();
                            intent.putExtra("type",type);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
