package com.project.speakhack.hackathon;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignUpUniversityActivity extends AppCompatActivity {
    private EditText name_edt,affiliated_edt,address_edt,phone_edt,email_edt;
    private Button regsiter_btn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_university);
        name_edt=(EditText)findViewById(R.id.name_university_signup);
        affiliated_edt=(EditText)findViewById(R.id.affiliated_number_signup_university);
        address_edt=(EditText)findViewById(R.id.address_signup_university);
        phone_edt=(EditText)findViewById(R.id.phone_number_signup_university);
        email_edt=(EditText)findViewById(R.id.email_signup_university);
        regsiter_btn=(Button)findViewById(R.id.register_signup_university);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering....");
        progressDialog.setCanceledOnTouchOutside(false);
        regsiter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=name_edt.getText().toString();
                String affiliatedno=affiliated_edt.getText().toString();
                String addreess=address_edt.getText().toString();
                String phone=phone_edt.getText().toString();
                String email=email_edt.getText().toString();
                register(name,affiliatedno,addreess,phone,email);
            }
        });
    }

    private void register(String name, String affiliatedno, String addreess, String phone, String email) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(affiliatedno)|| TextUtils.isEmpty(addreess)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(email)){
            Toast.makeText(SignUpUniversityActivity.this,"Please fill all the required fields",Toast.LENGTH_LONG).show();
        }
        else {
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,)
        }
    }
}
