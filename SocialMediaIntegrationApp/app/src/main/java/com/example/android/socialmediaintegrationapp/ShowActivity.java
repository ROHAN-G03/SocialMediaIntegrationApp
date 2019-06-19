package com.example.android.socialmediaintegrationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.widget.LoginButton;

public class ShowActivity extends AppCompatActivity {

    Button b;
    Button b1;
    Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        b=findViewById(R.id.fb_login);
        b1=findViewById(R.id.twitter_login);
        b2=findViewById(R.id.google_login);
    }
    public void clicked(View v)
    {
        Intent intn=new Intent(this,MainActivity.class);
        startActivity(intn);
    }
    public void clicked2(View v)
    {
        Intent intn=new Intent(this,TwitterActivity.class);
        startActivity(intn);
    }
    public void clicked4(View v)
    {
        Intent intn=new Intent(this,GoogleActivity.class);
        startActivity(intn);
    }
}
