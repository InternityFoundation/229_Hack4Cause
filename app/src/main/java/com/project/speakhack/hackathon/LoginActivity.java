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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText email_login,password_login;
    private Button login_btn;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private ProgressDialog progressDialog;
    private String type;
    private TextView register_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_login=(EditText)findViewById(R.id.email_login);
        password_login=(EditText)findViewById(R.id.password_login);
        login_btn=(Button)findViewById(R.id.login_btn);
        register_txt=(TextView)findViewById(R.id.register_txt);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference();
        type=getIntent().getStringExtra("type");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=email_login.getText().toString();
                String password=password_login.getText().toString();
                login(email,password);
            }
        });
        register_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equalsIgnoreCase("university")){
                    Intent intent=new Intent(LoginActivity.this,SignUpUniversityActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(type.equalsIgnoreCase("student")){
                    Intent intent=new Intent(LoginActivity.this,SignUpStudentActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*if (mAuth.getCurrentUser()!=null){
            if(type.equalsIgnoreCase("university welcome")){
                db.child("Universities").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot!=null){
                            if (dataSnapshot.hasChild(mAuth.getCurrentUser().getUid().toString())){
                                Intent intent=new Intent(LoginActivity.this,UniversityMainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Intent intent = new Intent(LoginActivity.this, StatusActivity.class);
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
            else if(type.equalsIgnoreCase("student welcome")){
                Intent intent=new Intent(LoginActivity.this,StudentMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }*/
    }

    private void login(String email, String password) {
        if(TextUtils.isEmpty(email)|TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Please fill all the fields",Toast.LENGTH_LONG).show();
        }
        else {
            if(mAuth.getCurrentUser()!=null){
            mAuth.signOut();
            }
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        db.child("Users").child(mAuth.getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot!=null){
                                    String txt=dataSnapshot.child("type").getValue().toString();
                                    if(txt.equalsIgnoreCase("university")){
                                        Intent intent=new Intent(LoginActivity.this,UniversityMainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Map<String,String> map=new HashMap<>();
                                        map.put("name",dataSnapshot.child("name").getValue().toString());
                                        map.put("address",dataSnapshot.child("address").getValue().toString());
                                        map.put("affiliated number",dataSnapshot.child("affiliated number").getValue().toString());
                                        map.put("email",dataSnapshot.child("email").getValue().toString());
                                        map.put("phone number",dataSnapshot.child("phone number").getValue().toString());
                                        db.child("Universities").child(mAuth.getCurrentUser().getUid().toString())
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                    }
                                    else if(txt.equalsIgnoreCase("student")){
                                        Intent intent=new Intent(LoginActivity.this,StudentMainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

}
