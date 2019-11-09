package com.project.speakhack.hackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpUniversityActivity extends AppCompatActivity {
    private EditText name_edt,affiliated_edt,address_edt,phone_edt,email_edt,password_edt,retype_edt;
    private Button regsiter_btn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference db;
    private TextView login_txt_university;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_university);
        name_edt=(EditText)findViewById(R.id.name_university_signup);
        affiliated_edt=(EditText)findViewById(R.id.affiliated_number_signup_university);
        address_edt=(EditText)findViewById(R.id.address_signup_university);
        phone_edt=(EditText)findViewById(R.id.phone_number_signup_university);
        email_edt=(EditText)findViewById(R.id.email_signup_university);
        password_edt=(EditText)findViewById(R.id.password_signup_university);
        retype_edt=(EditText)findViewById(R.id.retype_signup_university);
        regsiter_btn=(Button)findViewById(R.id.register_signup_university);
        login_txt_university=(TextView)findViewById(R.id.login_txt_university);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference();
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
                String passowrd=password_edt.getText().toString();
                String retype=retype_edt.getText().toString();
                register(name,affiliatedno,addreess,phone,email,passowrd,retype);
            }
        });
        login_txt_university.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpUniversityActivity.this,LoginActivity.class);
                intent.putExtra("type","university");
                startActivity(intent);
                finish();
            }
        });
    }

    private void register(final String name, final String affiliatedno, final String addreess, final String phone, final String email, String password, String retype) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(affiliatedno)|| TextUtils.isEmpty(addreess)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(retype)){
            Toast.makeText(SignUpUniversityActivity.this,"Please fill all the required fields",Toast.LENGTH_LONG).show();
        }
        else if(!password.equalsIgnoreCase(retype)){
            Toast.makeText(SignUpUniversityActivity.this,"Passwords does not match",Toast.LENGTH_LONG).show();
        }
        else {
            if(mAuth.getCurrentUser()!=null){
                mAuth.signOut();
            }
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpUniversityActivity.this,"aaa",Toast.LENGTH_LONG).show();
                        Map<String,String> map=new HashMap<>();
                        map.put("name",name);
                        map.put("affiliated number",affiliatedno);
                        map.put("address",addreess);
                        map.put("phone number",phone);
                        map.put("email",email);
                        map.put("status","pending");
                        map.put("type","university");
                        db.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Intent intent=new Intent(SignUpUniversityActivity.this,StatusActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                    else{
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }
}
