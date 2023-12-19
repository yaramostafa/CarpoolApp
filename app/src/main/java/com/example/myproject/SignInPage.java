package com.example.myproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.model.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInPage extends AppCompatActivity {
    TextView btn01;
    EditText emailIn,passIn;
    Button loginBtn;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent1=new Intent(SignInPage.this, tripsPage.class);
            startActivity(intent1);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth =FirebaseAuth.getInstance();
        FirebaseHelper firebaseDB = FirebaseHelper.getInstance();

        btn01 = findViewById(R.id.RegBtn);
        loginBtn =findViewById(R.id.loginButton);
        emailIn = findViewById(R.id.username);
        passIn = findViewById(R.id.password);


        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SignInPage.this, SignUpPage.class);
                startActivity(intent1);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = emailIn.getText().toString();
                String Password  = passIn.getText().toString();

                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(SignInPage.this,"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TextUtils.isEmpty(Password)){
                    Toast.makeText(SignInPage.this,"Please enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignInPage.this, "Succesful Login!",
                                            Toast.LENGTH_SHORT).show();
                                    firebaseDB.checkUser(mAuth);
                                    Intent intent1=new Intent(SignInPage.this, tripsPage.class);
                                    startActivity(intent1);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignInPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


}
