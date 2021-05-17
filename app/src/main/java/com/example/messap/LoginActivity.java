package com.example.messap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ImageView image;
    EditText email,password;
    Button login,reg;
    Switch s;
    ProgressBar p;
    String verId;
    TextView fp;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        image =(ImageView)findViewById(R.id.imageView);
        email =(EditText)findViewById(R.id.textView5);
        password =(EditText)findViewById(R.id.textView6);
        login =(Button)findViewById(R.id.button4);
        reg =(Button)findViewById(R.id.button5);
        fp=(TextView)findViewById(R.id.textView18);

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),forgotPassword.class);
                startActivity(i);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();
                if(e.isEmpty())
                {
                    email.setError("E-mail is required");
                    email.requestFocus();
                    return;
                }
                if(p.isEmpty())
                {
                    password.setError("password is required");
                    password.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(e).matches())
                {
                    email.setError("Please Provide a valid email");
                    email.requestFocus();
                    return;
                }
                if(p.length()<6){
                    password.setError("Minimum Password Length is 6 Characters!");
                    password.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"User & Password is Invalid..",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }
}