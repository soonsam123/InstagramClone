package com.example.karat.instagramclone;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    ActionBar actionBar;
    EditText username, password;
    Intent intentUserProfile, intentSignUp;
    ParseUser currentUser;
    ConstraintLayout background;
    TextView logoTextView;

    /*============LOGIN ACTIVITY=============*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        background = findViewById(R.id.background);
        logoTextView = findViewById(R.id.logo);

        /*=====Get the intents=====*/
        intentUserProfile = new Intent(this, UserProfileActivity.class);
        intentSignUp = new Intent(this, SignUpActivity.class);

        /*=====Go to user's profile if it is already signed in=====*/
        currentUser = ParseUser.getCurrentUser();

        if (currentUser != null){

            startActivity(intentUserProfile);

        }

        /*=====Set the Listeners=====*/
        password.setOnKeyListener(this);
        background.setOnClickListener(this);
        logoTextView.setOnClickListener(this);

        /*=====Show Analytics=====*/
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }

    public void loginButton(View view){

        if (!username.getText().toString().equals("") && !password.getText().toString().equals("")) {
            ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (user != null) {

                        startActivity(intentUserProfile);

                    } else {

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            });
        } else {

            if (username.getText().toString().equals("")){
                Toast.makeText(this, "Fill in the username field", Toast.LENGTH_SHORT).show();
            } else if (password.getText().toString().equals("")){
                Toast.makeText(this, "Fill in the password field", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void textViewSignUp(View view){

        startActivity(intentSignUp);

    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){

            loginButton(view);

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
