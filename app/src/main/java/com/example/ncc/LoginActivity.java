package com.example.ncc;

import static android.icu.lang.UCharacter.IndicSyllabicCategory.NUMBER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button login,signin;
    Python py;
    PyObject main;
    PyObject map;
    LinearLayout layout;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_act);

        //startActivity(new Intent(this,AchievementsAct.class));

        database = FirebaseDatabase.getInstance();

        py = Python.getInstance();
        main = py.getModule("main");
        map = main.callAttr("users");

        layout = findViewById(R.id.login_layout);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signin = findViewById(R.id.signin);

        Map<PyObject,PyObject> users = map.asMap();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = username.getText().toString().trim();
                String passwords = password.getText().toString().trim();

                if(email.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                }

                else if(passwords.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(!users.containsKey(email))
                    {
                        Toast.makeText(LoginActivity.this, "Username not found", Toast.LENGTH_SHORT).show();
                    }

                    else if(!users.get(email).equals(passwords))
                    {
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String passwords = password.getText().toString().trim();

                if(users.containsKey(user))
                {
                    Toast.makeText(LoginActivity.this, "Username already found", Toast.LENGTH_SHORT).show();
                }

                else if(user.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(passwords.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    EditText email = new EditText(LoginActivity.this);
                    email.setHint("Enter your email");

                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Login Manager")
                            .setView(email)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String mail_id = email.getText().toString();
                                    Snackbar bar = Snackbar.make(LoginActivity.this,layout,"Sending Email",Snackbar.LENGTH_INDEFINITE);
                                    bar.show();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            int code = main.callAttr("send_mail",mail_id).toInt();
                                            bar.dismiss();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if(code==-1)
                                                    {
                                                        Snackbar.make(LoginActivity.this,layout,"Something went wrong",Snackbar.LENGTH_LONG).show();
                                                    }
                                                    else
                                                    {
                                                        EditText code_edit = new EditText(LoginActivity.this);
                                                        code_edit.setHint("Enter the verification code");
                                                        code_edit.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                                                        new AlertDialog.Builder(LoginActivity.this)
                                                                .setTitle("Login Manager")
                                                                .setView(code_edit)
                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        int entered_code = -1;
                                                                        try {
                                                                            entered_code = Integer.parseInt(code_edit.getText().toString());
                                                                            if(entered_code!=code)
                                                                            {
                                                                                Snackbar.make(LoginActivity.this,layout,"Incorrect verification code",Snackbar.LENGTH_LONG).show();
                                                                            }
                                                                            else {
                                                                                database.getReference("users/"+user).setValue(passwords);
                                                                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                                                            }
                                                                        }
                                                                        catch (Exception e)
                                                                        {
                                                                            Snackbar.make(LoginActivity.this,layout,"Something went wrong",Snackbar.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                })
                                                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                    }
                                                                })
                                                                .show();
                                                    }
                                                }
                                            });
                                        }
                                    }).start();
                                }
                            })
                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
            }
        });

    }
}