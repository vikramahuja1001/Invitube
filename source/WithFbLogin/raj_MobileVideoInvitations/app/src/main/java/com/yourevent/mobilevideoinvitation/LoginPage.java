package com.yourevent.mobilevideoinvitation;

/**
 * Created by vikram on 15/10/14.
 */


import android.content.pm.Signature;
import java.util.Arrays;
import java.util.List;

import java.security.MessageDigest;
import android.util.Base64;
import android.app.Activity;
import android.content.Context;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import java.security.NoSuchAlgorithmException;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class LoginPage extends Activity {

    private Button loginButton;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        Parse.initialize(this, "D3jgbFBGfMIqdAgwORRBPfPzGRwdSl572cs1DiQt",
                "2A6ICrbaGWaEo98b4j5xZfcHxuWbJgLXOhQ0LKPt");

        // Set your Facebook App Id in strings.xml
        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClicked();
            }
        });




        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.yourevent.mobilevideoinvitation", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        // Check if there is a currently logged in user
        // and they are linked to a Facebook account.
        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
//             Go to the user info activity
            showUserDetailsActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    private void onLoginButtonClicked() {
        LoginPage.this.progressDialog = ProgressDialog.show(
                LoginPage.this, "", "Logging in...", true);
        List<String> permissions = Arrays.asList("public_profile","user_friends", "user_about_me",
                "user_relationships", "user_birthday", "user_location");
        ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                LoginPage.this.progressDialog.dismiss();
                if (user == null) {
                    Log.d(IntegratingFacebook.TAG,
                            "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d(IntegratingFacebook.TAG,
                            "User signed up and logged in through Facebook!");
                    showUserDetailsActivity();
                } else {
                    Log.d(IntegratingFacebook.TAG,
                            "User logged in through Facebook!");
                    showUserDetailsActivity();
                }
            }
        });
    }




    private void showUserDetailsActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
/*
    public static void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.example.project", PackageManager.GET_SIGNATURES); //Your package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
            //    md.update(signature.toByteArray());
           //     Log.v("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }
*/
}