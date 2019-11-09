package com.project.speakhack.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button university_btn,student_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        university_btn=(Button)findViewById(R.id.university_main);
        student_btn=(Button)findViewById(R.id.student_main);
        university_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUpUniversityActivity.class);
                startActivity(intent);
            }
        });
        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUpStudentActivity.class);
                startActivity(intent);
            }
        });
    }
}
