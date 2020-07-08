package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exam.data.User;
import com.example.exam.data.UserStorage;

public class LoginActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
    }

    public void login(View view) {
        UserStorage storage = new UserStorage();
        User user = (User)storage.getObject(this, mUsername.getText().toString(), User.class);
        if (user == null) {
            Toast.makeText(this, "User with username : " + mUsername.getText().toString() + " doesn't exist.", Toast.LENGTH_LONG).show();
            return;
        }
        if (user.getPassword().equals(mPassword.getText().toString())) {
            storage.saveLoggedUser(this, user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Bruh password is incorrect", Toast.LENGTH_LONG).show();
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
