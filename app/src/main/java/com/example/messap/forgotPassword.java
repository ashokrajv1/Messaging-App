package com.example.messap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {


    EditText email;
    Button reset;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email=(EditText)findViewById(R.id.textView21);
        reset=(Button)findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = email.getText().toString().trim();
                if(e.isEmpty())
                {
                    email.setError("E-mail is required");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(e).matches())
                {
                    email.setError("Please Provide a valid email");
                    email.requestFocus();
                    return;
                }
                mAuth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(forgotPassword.this,"please check your email",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(forgotPassword.this,"Try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}