package com.example.karat.instagramclone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PlusActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView homeIcon, searchIcon, plusIcon, likeIcon, userIcon, imageViewCurrentPhoto;
    Intent intentHome, intentSearch, intentPlus, intentLike, intentUserProfile, intentLogIn;

    EditText editTextPhotoDescription;

    ParseUser currentUser;

    Bitmap bitmap;

    public void getPhoto(){

        Intent intentMediaStore = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentMediaStore, 1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getPhoto();

            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        /*===Assigning values===*/
        homeIcon = findViewById(R.id.homeIcon);
        searchIcon = findViewById(R.id.searchIcon);
        plusIcon = findViewById(R.id.plusIcon);
        likeIcon = findViewById(R.id.likeIcon);
        userIcon = findViewById(R.id.userIcon);
        imageViewCurrentPhoto = findViewById(R.id.imageViewCurrentPhoto);

        homeIcon.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        plusIcon.setOnClickListener(this);
        likeIcon.setOnClickListener(this);
        userIcon.setOnClickListener(this);
        imageViewCurrentPhoto.setOnClickListener(this);

        intentHome = new Intent(this, HomeActivity.class);
        intentSearch = new Intent(this, SearchActivity.class);
        intentPlus = new Intent(this, PlusActivity.class);
        intentLike = new Intent(this, LikeActivity.class);
        intentUserProfile = new Intent(this, UserProfileActivity.class);
        intentLogIn = new Intent(this, MainActivity.class);

        editTextPhotoDescription = findViewById(R.id.editTextPhotoDescription);

        /*===Item activated====*/
        plusIcon.setColorFilter(R.color.colorBlack);

        /*===Getting current user===*/
        if (ParseUser.getCurrentUser() != null) {
            currentUser = ParseUser.getCurrentUser();
        } else {
            startActivity(intentLogIn);
        }


        /*===Going to MediaStore===*/
        // Check if the API is higher the 23(Marshmallow).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {

                getPhoto();

            }
        } else {

            getPhoto();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                imageViewCurrentPhoto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void onSubmitPhoto(View view){

        if (bitmap != null) {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            // Bitmap.CompressFormat.PNG, 100 (HighQuality). JPEG, 80 is faster.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);

            byte[] byteArray = stream.toByteArray();

            final ParseFile file = new ParseFile("image.jpg", byteArray);

            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){

                        ParseObject object = new ParseObject("Image");

                        object.put("image", file);
                        object.put("username", currentUser.getUsername());
                        object.put("photoDescription", editTextPhotoDescription.getText().toString());

                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {

                                    Toast.makeText(PlusActivity.this, "Image Shared!", Toast.LENGTH_SHORT).show();

                                    startActivity(intentUserProfile);

                                } else {

                                    Toast.makeText(PlusActivity.this, "Could not shared the image. Object Error", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();

                                }
                            }
                        });

                    }else {

                        Toast.makeText(PlusActivity.this, "Could not shared the image. File Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }
                }
            });
        }

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageViewCurrentPhoto){

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }

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
