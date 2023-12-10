package com.example.driverside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
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
            Intent intent1=new Intent(SignIn.this,MainActivity.class);
            startActivity(intent1);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth =FirebaseAuth.getInstance();

        btn01 = findViewById(R.id.SignBtn);
        loginBtn =findViewById(R.id.loginButton);
        emailIn = findViewById(R.id.username);
        passIn = findViewById(R.id.password);


        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SignIn.this,SignUp.class);
                startActivity(intent1);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = emailIn.getText().toString();
                String Password  = passIn.getText().toString();

                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(SignIn.this,"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TextUtils.isEmpty(Password)){
                    Toast.makeText(SignIn.this,"Please enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    checkUser();
                                    Toast.makeText(SignIn.this, "Succesful Login!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent1=new Intent(SignIn.this,MainActivity.class);
                                    startActivity(intent1);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignIn.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    public void checkUser(){
        FirebaseUser user = mAuth.getCurrentUser();
        final String[] uniID = new String[1];
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase= reference.orderByChild("uid").equalTo(user.getUid());
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("drivers");
        Query checkDriverDatabase  = reference2.orderByChild("uid").equalTo(user.getUid());
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    uniID[0] =  snapshot.child(user.getUid()).child("uniID").getValue(String.class);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        checkDriverDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    userDataHelper helperClass=new userDataHelper(uniID[0],user.getEmail(),user.getUid());
                    reference2.child(user.getUid()).setValue(helperClass);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
