package com.tonikamitv.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String surname = intent.getStringExtra("surname");
        String username = intent.getStringExtra("username");

        TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etSurname = (EditText) findViewById(R.id.etSurname);

        // Display user details
        String message = name + " welcome to your user area";
        tvWelcomeMsg.setText(message);
        etUsername.setText(username);
        etSurname.setText(surname);
    }
}
