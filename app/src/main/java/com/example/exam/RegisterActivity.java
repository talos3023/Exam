package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exam.data.Storage;
import com.example.exam.data.User;
import com.example.exam.data.UserStorage;

public class RegisterActivity extends AppCompatActivity {

    EditText mFirstName;
    EditText mLastName;
    EditText mUsername;
    EditText mPassword;
    EditText mEmail;
    EditText mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mEmail = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);

    }

    public void register (View view) {
        if (validate()) {
            User user = new User();
            user.setFirstName(mFirstName.getText().toString());
            user.setLastName(mLastName.getText().toString());
            user.setUsername(mUsername.getText().toString());
            user.setPassword(mPassword.getText().toString());
            user.setEmail(mEmail.getText().toString());
            user.setPhone(mPhone.getText().toString());
            Storage storage = new UserStorage();
            storage.add(this, user.getUsername(), user);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private boolean validate() {
        StringBuilder emptyFields = new StringBuilder();
        if (mFirstName.getText().toString().isEmpty()) {
            emptyFields.append("First Name, ");
        }
        if (mUsername.getText().toString().isEmpty()) {
            emptyFields.append("Username, ");
        }
        if (mPassword.getText().toString().isEmpty()) {
            emptyFields.append("Password");
        }
        if (emptyFields.toString().isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Please fill the necessary fields : " + emptyFields.toString(), Toast.LENGTH_LONG).show();
        return false;
    }
}
