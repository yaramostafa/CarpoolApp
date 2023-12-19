package com.example.driverside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.driverside.Helpers.userDataHelper;
import com.example.driverside.model.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText emailReg,idUser,passReg;
    Button BtnReg;
    FirebaseHelper firebaseDB = FirebaseHelper.getInstance();

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent1=new Intent(SignUp.this,SignIn.class);
            startActivity(intent1);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        BtnReg = findViewById(R.id.Register);
        idUser =findViewById(R.id.username);
        emailReg = findViewById(R.id.email);
        passReg = findViewById(R.id.password);

        firebaseAuth = FirebaseAuth.getInstance();

        BtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserAccount();
            }
        });
    }

    public void createUserAccount(){
        String createID = idUser.getText().toString();
        String createEmail = emailReg.getText().toString();
        String createPassword  = passReg.getText().toString();

        if(TextUtils.isEmpty(createEmail) && TextUtils.isEmpty(createPassword) && TextUtils.isEmpty(createID)||
                TextUtils.isEmpty(createEmail) && TextUtils.isEmpty(createPassword)||
                TextUtils.isEmpty(createPassword) && TextUtils.isEmpty(createID)||TextUtils.isEmpty(createEmail)&&
                TextUtils.isEmpty(createID)){
            Toast.makeText(this,"Don't leave textfields empty",Toast.LENGTH_LONG).show();
            return;
        }

        else if(TextUtils.isEmpty(createEmail)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(createPassword)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(createID)){
            Toast.makeText(this,"Please enter name", Toast.LENGTH_LONG).show();
            return;
        }
        if (!createEmail.endsWith("@eng.asu.edu.eg")) {
            Toast.makeText(this, "Invalid email domain. Please use @eng.asu.edu.eg", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(createEmail, createPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            userDataHelper helperClass=new userDataHelper(createID,createEmail, user.getUid());
                            firebaseDB.getUserReference().child(user.getUid()).setValue(helperClass);
                            Toast.makeText(SignUp.this, "USER CREATED!!!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent1=new Intent(SignUp.this,SignIn.class);
                            startActivity(intent1);

                        } else {
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}