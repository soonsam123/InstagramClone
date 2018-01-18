package com.example.karat.instagramclone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public abstract class bottombarActivity extends Activity implements View.OnClickListener {

    ImageView homeIcon, searchIcon, plusIcon, likeIcon, userIcon;
    Intent intentHome, intentSearch, intentPlus, intentLike, intentUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeIcon = findViewById(R.id.homeIcon);
        searchIcon = findViewById(R.id.searchIcon);
        plusIcon = findViewById(R.id.plusIcon);
        likeIcon = findViewById(R.id.likeIcon);
        userIcon = findViewById(R.id.userIcon);

        homeIcon.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        plusIcon.setOnClickListener(this);
        likeIcon.setOnClickListener(this);
        userIcon.setOnClickListener(this);

        intentHome = new Intent(this, HomeActivity.class);
        intentSearch = new Intent(this, SearchActivity.class);
        intentPlus = new Intent(this, PlusActivity.class);
        intentLike = new Intent(this, LikeActivity.class);
        intentUserProfile = new Intent(this, UserProfileActivity.class);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.homeIcon){

            startActivity(intentHome);

        } else  if (view.getId() == R.id.searchIcon){

            startActivity(intentSearch);

        } else  if (view.getId() == R.id.plusIcon){

            startActivity(intentPlus);

        } else  if (view.getId() == R.id.likeIcon){

            startActivity(intentLike);

        } else  if (view.getId() == R.id.userIcon){

            startActivity(intentUserProfile);

        }

    }
}
