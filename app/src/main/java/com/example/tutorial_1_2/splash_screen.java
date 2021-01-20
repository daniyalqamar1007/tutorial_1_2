package com.example.tutorial_1_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class splash_screen extends AppCompatActivity {

    private final static int LOGIN_REQUEST_CODE = 1007; //any value you want
    private List<AuthUI.IdpConfig> providers;
    private FirebaseAuth firebaseauth;
    private FirebaseAuth.AuthStateslistner listner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    private void init() {
        providers = Arrays.asList(
                new AurthUI.IdpConfig.PhoneBuilder()build(),
                new AurthUI.IdpConfig.GoogleBuilder()build();

                firebaseauth = FirebaseAuth.getInstance();
                listener = myfirebaseAuth -> {
                    Firebaseuser user = myfirebaseAuth.getCurrentUser();
                    if(user != null)
                        delaySplashScreen();
                    else showLoginLayout();
                }
        )
    }

    private void showLoginLayout() {

        AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout
                .Builder(R.layout.lay)
                .setPhoneButtonid(R.id.phonesignin)
                .setGoogleButtonId(R.id.googlesignin)
                .build();

                startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAuthMethodPickerLayout(authMethodPickerLayout)
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                    .build().LOGIN_REQUEST_CODE;



    }

    private void delaySplashScreen() {
        Completable.timer(5, TimeUnit.SECONDS,
                AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception{
                        Toast.makeText(splash_screen.this,"Welcome",Toast.LENGTH_SHORT.show());
                    }
                })
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == LOGIN_REQUEST_CODE)
        {
            IdpResponse responce = IdpResponse.fromresultIntent(data);
            if (resultCode == RESULT_OK)
            {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
            else
            {
                Toast.makeText(this, "[ERROR]" +responce.getError().getMessage(), Toast.LENGTH_SHORT ).show();
            }
        }
    }

}