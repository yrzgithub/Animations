package com.example.ncc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginAct extends AppCompatActivity{

    EditText username,password;
    Button login,signin;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_act);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signin = findViewById(R.id.signin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login.getText().toString();
                String passwords = password.getText().toString();
                if(email.isEmpty())
                {
                    Toast.makeText(LoginAct.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(passwords.isEmpty())
                {
                    Toast.makeText(LoginAct.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(LoginAct.this, "cl", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
