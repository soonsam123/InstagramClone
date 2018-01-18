package com.example.karat.instagramclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nameTextView, postsTextView, followersTextView, followingTextView, descriptionTextView;
    ImageView homeIcon, searchIcon, plusIcon, likeIcon, userIcon;
    Intent intentHome, intentSearch, intentPlus, intentLike, intentUserProfile, intentEditDescription, intentLogIn;
    ParseUser currentUser;

    GridLayout gridLayout;

    int c = 0, r = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        /*===Assigning values===*/
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
        intentEditDescription = new Intent(this, EditDescriptionActivity.class);
        intentLogIn = new Intent(this, MainActivity.class);

        postsTextView = findViewById(R.id.postsTextView);
        followersTextView = findViewById(R.id.followersTextView);
        followingTextView = findViewById(R.id.followingTextView);
        nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        gridLayout = findViewById(R.id.thirdSection);


        /*===Item activated====*/
        userIcon.setColorFilter(R.color.colorBlack);

        /*===Showing the username of the user that is logged in====*/
        if (ParseUser.getCurrentUser() != null) {
            currentUser = ParseUser.getCurrentUser();
        }else {
            startActivity(intentLogIn);
        }


        /*===Assign the values to be displayed in the screen===*/
        postsTextView.setText((currentUser.getNumber("posts").toString()));
        followersTextView.setText(currentUser.getNumber("followers").toString());
        followingTextView.setText(currentUser.getNumber("following").toString());
        nameTextView.setText(currentUser.getString("nickName"));
        descriptionTextView.setText(currentUser.getString("description"));


        /*===Displaying the images===*/
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");

        query.whereEqualTo("username", currentUser.getUsername());

        query.addAscendingOrder("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0){

                        c = 0; r = 0;
                        for (ParseObject object : objects){

                            ParseFile file = (ParseFile) object.get("image");

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if (e == null && data != null){

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                                        ImageView imageView = new ImageView(getApplicationContext());

                                        imageView.setImageBitmap(bitmap);

                                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                                        params.height = 360;
                                        params.width = 360;
                                        params.setGravity(Gravity.CENTER);
                                        params.columnSpec = GridLayout.spec(c);
                                        params.rowSpec = GridLayout.spec(r);

                                        imageView.setLayoutParams(params);
                                        gridLayout.addView(imageView);

                                        if (c == 2){

                                            r = r + 1;
                                            c = 0;
                                        }

                                        c = c + 1;


                                    }

                                }
                            });

                        }

                    }

                }

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.userprofile_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.logout_menu){

            ParseUser.logOut();

            startActivity(intentLogIn);

            return true;

        }

        if (item.getItemId() == R.id.editDescription_menu){

            startActivity(intentEditDescription);

        }

        return false;

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
