package com.example.karat.instagramclone;

import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    ActionBar actionBar;
    EditText username, password, email;
    Intent intentLogIn, intentUserProfile;
    ConstraintLayout background;
    TextView logoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /*=====Hide action bar=====*/
        try {
            actionBar = getSupportActionBar();
        } catch (Exception e){e.printStackTrace();}
        if (actionBar != null) {
            actionBar.hide();
        }

        /*=====Get the ids=====*/
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        email = findViewById(R.id.editTextEmail);
        background = findViewById(R.id.background);
        logoTextView = findViewById(R.id.logo);

        /*=====Get the intents=====*/
        intentUserProfile = new Intent(this, UserProfileActivity.class);
        intentLogIn = new Intent(this, MainActivity.class);

        /*=====Set the Listeners=====*/
        email.setOnKeyListener(this);
        background.setOnClickListener(this);
        logoTextView.setOnClickListener(this);


    }



    public void signUpButton(View view){

        ParseUser user = new ParseUser();
        if (!username.getText().toString().equals("") && !password.getText().toString().equals("") && !email.getText().toString().equals("")) {

            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            user.setEmail(email.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){

                        startActivity(intentUserProfile);


                    } else {

                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });

        } else {

            if (username.getText().toString().equals("")){
                Toast.makeText(this, "Fill in the username", Toast.LENGTH_SHORT).show();
            } else if (password.getText().toString().equals("")){
                Toast.makeText(this, "Fill in the password", Toast.LENGTH_SHORT).show();
            } else if (email.getText().toString().equals("")){
                Toast.makeText(this, "Fill in the email", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void textViewLogIn(View view){

        startActivity(intentLogIn);

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){

            signUpButton(view);

        }

        return false;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.background || view.getId() == R.id.logo){

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }

    }
}
