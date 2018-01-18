package com.example.karat.instagramclone;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditDescriptionActivity extends AppCompatActivity {


    EditText editTextEditDescription;
    ActionBar actionBar;
    ParseUser currentUser;
    Intent intentUserProfile, intentLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_description);

        /*===Action Bar===*/
        try {

            actionBar = getSupportActionBar();
            actionBar.setTitle("Edit Description");

        }catch (Exception e){e.printStackTrace();}


        /*===Assigning values===*/
        editTextEditDescription = findViewById(R.id.editTextEditDescription);

        intentUserProfile = new Intent(this, UserProfileActivity.class);
        intentLogIn = new Intent(this, MainActivity.class);

        /*===Getting current user===*/
        if (ParseUser.getCurrentUser() != null) {
            currentUser = ParseUser.getCurrentUser();
        } else {
            startActivity(intentLogIn);
        }
    }


    public void onSubmitDescription(View view){

        if (!editTextEditDescription.getText().toString().equals("")){

            currentUser.put("description", editTextEditDescription.getText().toString());
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null){
                        Toast.makeText(EditDescriptionActivity.this, "Description updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditDescriptionActivity.this, "Failed to edit description", Toast.LENGTH_SHORT).show();
                    }

                    startActivity(intentUserProfile);

                }
            });

        }

    }


}
