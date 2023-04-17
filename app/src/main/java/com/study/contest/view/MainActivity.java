package com.study.contest.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.study.contest.R;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Intent firstPage;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);

        Button loginButton = findViewById(R.id.logInButton);
        etEmail = findViewById(R.id.emailTextEdit);
        etPassword = findViewById(R.id.editTextPassword);

        firstPage = new Intent(this, MainPage.class);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(view -> loginUser());
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(firstPage);
        }
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.login_can_not_be_empty));
            etEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.password_can_not_be_empty));
            etPassword.requestFocus();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(firstPage);
                        } else if (task.isComplete()) {
                            etPassword.setError(getString(R.string.login_or_password_error));
                        }
                    }
            );
        }


    }


}