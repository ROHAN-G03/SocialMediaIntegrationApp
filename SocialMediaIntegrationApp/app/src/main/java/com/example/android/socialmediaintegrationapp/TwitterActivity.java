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
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;

public class TwitterActivity extends AppCompatActivity {


    TwitterLoginButton loginButton;
    Button logOut;
    TextView tv,tv1;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_twitter);
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        logOut=(Button)findViewById(R.id.out);
        tv1=(TextView)findViewById(R.id.profile_email_twitter);
        tv=(TextView)findViewById(R.id.profile_name_twitter);
        img=(ImageView)findViewById(R.id.profile_pic_twitter);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result)
            {
                loginButton.setVisibility(View.INVISIBLE);
                logOut.setVisibility(View.VISIBLE);
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                login(session);
            }

            @Override
            public void failure(TwitterException exception)
            {
                Toast.makeText(TwitterActivity.this,"Twitter Login Fail",Toast.LENGTH_LONG).show();

                // Do something on failure
            }
        });
    }

    public void Clicked3(View v)
    {
        Intent intn=new Intent(this,ShowActivity.class);
        Toast.makeText(this,"Logged Out",Toast.LENGTH_LONG).show();
        startActivity(intn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
    public void login(TwitterSession session)
    {
        String username=session.getUserName();
        tv1.setText(username);
    /*    TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                // Do something with the result, which provides the email address
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });*/
        Call<User> user = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(true, true, true);
        user.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> userResult) {
                String name = userResult.data.name;
                String email = userResult.data.email;
                tv.setText(name);
                tv1.setText(email);
                // _normal (48x48px) | _bigger (73x73px) | _mini (24x24px)
                String photoUrlNormalSize   = userResult.data.profileImageUrl;
                String photoUrlBiggerSize   = userResult.data.profileImageUrl.replace("_normal", "_bigger");
                String photoUrlMiniSize     = userResult.data.profileImageUrl.replace("_normal", "_mini");
                String photoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");

                Glide.with(TwitterActivity.this).load(photoUrlNormalSize).into(img);

            }

            @Override
            public void failure(TwitterException exc)
            {
                Toast.makeText(TwitterActivity.this,"FAILED",Toast.LENGTH_LONG).show();
           //     Log.d("TwitterKit", "Verify Credentials Failure", exc);
            }
        });
    }
}
