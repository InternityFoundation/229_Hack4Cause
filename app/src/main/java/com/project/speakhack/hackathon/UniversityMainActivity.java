package com.project.speakhack.hackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UniversityMainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private CardView student_approval_cd,documentation_cd;
    private TextView studennt_text,documentation_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_main);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        student_approval_cd=(CardView)findViewById(R.id.student_approval);
        documentation_cd=(CardView)findViewById(R.id.documentation);
        studennt_text=(TextView)findViewById(R.id.student_approval_text);
        documentation_text=(TextView)findViewById(R.id.documentation_text);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.universitymain_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
            progressDialog.setMessage("Logging Out...");
            progressDialog.setCanceledOnTouchOutside(false);
            Intent intent=new Intent(UniversityMainActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            mAuth.signOut();
            progressDialog.dismiss();
        }
        return super.onOptionsItemSelected(item);
    }
}
