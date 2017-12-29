package com.prestige.kaelmok.pmix;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextInputLayout usernameWrapper = findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");
    }

    //Login & direct to Selection Menu
    public void onLoginButton(View view){

        if(view.getId() == R.id.loginButton){

            Intent intent = new Intent(MainActivity.this, Menu.class);
            startActivity(intent);
        }
    }

}
