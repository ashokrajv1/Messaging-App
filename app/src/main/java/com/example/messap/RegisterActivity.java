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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference reference;
    EditText name,email,password,mobile;
    Button register;
    RadioGroup rg;
    RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        name = (EditText)findViewById(R.id.textView9);
        email = (EditText)findViewById(R.id.textView10);
        password = (EditText)findViewById(R.id.textView11);
        mobile =(EditText)findViewById(R.id.textView12);
        register = (Button)findViewById(R.id.button6);
        rg = (RadioGroup)findViewById(R.id.radioGroup);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String n=name.getText().toString().trim();
                final String e=email.getText().toString().trim();
                String p=password.getText().toString().trim();
                final String m=mobile.getText().toString().trim();
                int id = rg.getCheckedRadioButtonId();
                rb=(RadioButton)findViewById(id);
                final String g = rb.getText().toString();
                if(n.isEmpty())
                {
                    name.setError("Full name is required");
                    name.requestFocus();
                    return;
                }
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
                if(m.isEmpty())
                {
                    mobile.setError("Mobile number is required");
                    mobile.requestFocus();
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
                register(n,e,p,m,g);
            }
        });
    }

    private void register(final String username, String email, final String password, final String mobile, final String gender){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("mobile", mobile);
                    hashMap.put("gender", gender);
                    hashMap.put("imageURL", "default");
                    hashMap.put("status", "offline");
                    hashMap.put("search", username.toLowerCase());

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Toast.makeText(RegisterActivity.this,"User Registered Successfully",Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this,"User Not Registered",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    //Toast.makeText(RegisterActivity.this, "Error!!!, Please try after some time..", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}