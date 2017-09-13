package com.example.nn0lumesther.javadev.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nn0lumesther.javadev.R;
import com.example.nn0lumesther.javadev.model.Developer;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.nn0lumesther.javadev.adapter.DeveloperAdapter.DEVELOPER_KEY;


public class DetailActivity extends AppCompatActivity {
    Button button;
    private Developer developer;
    private TextView username;
    private TextView gitDetail;
    private CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();

        button = (Button) findViewById(R.id.share_btn);

        if (getIntent() != null) {
            developer = getIntent().getParcelableExtra(DEVELOPER_KEY);
            username.setText("Username: " + developer.getLogin());
            gitDetail.setText("GIT LINK: " + developer.getHtmlUrl());
            Glide.with(this)
                    .load(developer.getAvatarUrl())
                    .into(profileImage);
        }
    }

    private void initViews() {
        username = (TextView) findViewById(R.id.username_view);
        gitDetail = (TextView) findViewById(R.id.git_details);
        profileImage = (CircleImageView) findViewById(R.id.avatar_view_profile);

        gitDetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(developer.getHtmlUrl()));
                startActivity(browserIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent i = new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "Check out this awesome developer @"
                        + developer.getLogin() + ", " + developer.getHtmlUrl() + ".");
                startActivity(Intent.createChooser(i, "Share"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //b
    public void share(View view) {
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_TEXT, "Check out this awesome developer @ " + developer.getLogin() + ", " + developer.getHtmlUrl() + ".");
        startActivity(Intent.createChooser(i, "Share"));
    }

}
