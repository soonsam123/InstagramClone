package com.example.karat.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView homeIcon, searchIcon, plusIcon, likeIcon, userIcon;
    Intent intentHome, intentSearch, intentPlus, intentLike, intentUserProfile, intentLogIn;
    ListView listView;
    CustomList adapter;

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> subTitles = new ArrayList<>();
    ArrayList<Integer> imagesId = new ArrayList<>();

    ParseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
        intentLogIn = new Intent(this, MainActivity.class);

        /*===Item activated====*/
        searchIcon.setColorFilter(R.color.colorBlack);

        /*===ListView and Adapter====*/
        listView = findViewById(R.id.listView);

        adapter = new CustomList(SearchActivity.this, titles, subTitles, imagesId);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(SearchActivity.this, Integer.toString(i), Toast.LENGTH_SHORT).show();

            }
        });

        if (ParseUser.getCurrentUser() != null) {
            currentUser = ParseUser.getCurrentUser();
        }else {
            startActivity(intentLogIn);
        }


        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.addDescendingOrder("nickName");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0) {
                        for (ParseUser user : objects) {

                            titles.add(user.getString("nickName"));
                            subTitles.add(user.getUsername());
                            imagesId.add(R.drawable.avatar);

                        }

                        listView.setAdapter(adapter);

                    }

                } else {

                    e.printStackTrace();

                }

            }
        });
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
