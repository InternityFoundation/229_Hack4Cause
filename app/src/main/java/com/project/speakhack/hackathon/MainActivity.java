package com.project.speakhack.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button university_btn,student_btn;
    private TextView activation_status_txt;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        university_btn=(Button)findViewById(R.id.university_main);
        student_btn=(Button)findViewById(R.id.student_main);
        activation_status_txt=(TextView)findViewById(R.id.activation_status_txt);
        activation_status_txt.setVisibility(View.INVISIBLE);
        activation_status_txt.setEnabled(false);
        mAuth=FirebaseAuth.getInstance();
        university_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("type","university");
                startActivity(intent);
            }
        });
        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("type","student");
                startActivity(intent);
            }
        });
        activation_status_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,StatusActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            activation_status_txt.setVisibility(View.VISIBLE);
            activation_status_txt.setEnabled(true);
        }
        else {
            activation_status_txt.setVisibility(View.INVISIBLE);
            activation_status_txt.setEnabled(false);
        }
    }
}
