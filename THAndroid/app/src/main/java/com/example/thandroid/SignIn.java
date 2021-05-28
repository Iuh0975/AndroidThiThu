package com.example.thandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {


    private EditText edtmail,edtpassword;
    private Button btnSignin;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase instance;
    private DatabaseReference db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        edtmail = findViewById(R.id.plainEmail);
        edtpassword = findViewById(R.id.plainPassword);
       btnSignin =findViewById(R.id.BtnDangKy);
       firebaseAuth = FirebaseAuth.getInstance();


       btnSignin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                instance =FirebaseDatabase.getInstance();
                db =instance.getReference("User");
                String email =edtmail.getText().toString();
                String password = edtpassword.getText().toString();
                if(TextUtils.isEmpty(password)){
                    edtpassword.setError("Password required !");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    edtmail.setError("Email required");
                    return;
                }
                if(password.length()<6){
                    edtpassword.setError("Password must be >= 6 characters");
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignIn.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplication(),SignIn.class));
                            }else {
                                Toast.makeText(SignIn.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                    }
                });
           }
       });

    }
}
