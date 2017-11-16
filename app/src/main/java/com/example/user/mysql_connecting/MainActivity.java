package com.example.user.mysql_connecting;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextMessage;
    FrameLayout content;

    String retainedTag = "";
    LoginFragment loginfragment = null;
    RegisterFragment registerFragment = null;
    AccountInfoFragment infoFragment = null;
    FragmentManager fragmentManager = getSupportFragmentManager();



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_login:

                    if(fragmentManager.findFragmentByTag("loginfragment") == null) {
                        Toast.makeText(getApplicationContext(), "login is null", Toast.LENGTH_SHORT).show();

                        if(fragmentManager.findFragmentByTag("registerfragment") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("registerfragment")).commit();
                        }
                        if(fragmentManager.findFragmentByTag("infofragment") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("infofragment")).commit();
                        }

                        fragmentManager.beginTransaction().add(R.id.content, new LoginFragment(), "loginfragment").commit();

                    }else
                    {
                        if(fragmentManager.findFragmentByTag("registerfragment") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("registerfragment")).commit();
                        }
                        if(fragmentManager.findFragmentByTag("infofragment") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("infofragment")).commit();
                        }

                        Toast.makeText(getApplicationContext(), "login is  not null", Toast.LENGTH_SHORT).show();
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("loginfragment")).commit();

                    }
                    return true;
                case R.id.navigation_register:
                    retainedTag = "registerfragment";

                    if(fragmentManager.findFragmentByTag("registerfragment") == null) {
                        Toast.makeText(getApplicationContext(), "register is null", Toast.LENGTH_SHORT).show();

                        if(fragmentManager.findFragmentByTag("infofragment") != null) {
                             fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("infofragment")).commit();
                        }
                        if(fragmentManager.findFragmentByTag("loginfragment") != null) {
                             fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("loginfragment")).commit();
                        }
                        fragmentManager.beginTransaction().add(R.id.content, new RegisterFragment(), "registerfragment").commit();

                    }else
                    {
                        if(fragmentManager.findFragmentByTag("infofragment") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("infofragment")).commit();
                        }
                        if(fragmentManager.findFragmentByTag("loginfragment") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("loginfragment")).commit();
                        }
                        Toast.makeText(getApplicationContext(), "register is  not null", Toast.LENGTH_SHORT).show();
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("registerfragment")).commit();

                    }
                    return true;
                case R.id.navigation_accountInfo:
                    retainedTag = "infofragment";


                    if(fragmentManager.findFragmentByTag("infofragment") == null) {

                        Toast.makeText(getApplicationContext(), "info is null", Toast.LENGTH_SHORT).show();

                        if(fragmentManager.findFragmentByTag("registerfragment") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("registerfragment")).commit();
                        }
                        if(fragmentManager.findFragmentByTag("loginfragment") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("loginfragment")).commit();
                        }
                        fragmentManager.beginTransaction().add(R.id.content, new AccountInfoFragment(), "infofragment").commit();

                    }else
                    {
                        if(fragmentManager.findFragmentByTag("registerfragment") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("registerfragment")).commit();
                        }
                        if(fragmentManager.findFragmentByTag("loginfragment") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("loginfragment")).commit();
                        }

                        Toast.makeText(getApplicationContext(), "info is not null", Toast.LENGTH_SHORT).show();
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("infofragment")).commit();
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // show first the login fragment


if(savedInstanceState == null){
    retainedTag = "loginfragment";
    fragmentManager.beginTransaction().add(R.id.content, new LoginFragment(),retainedTag).commit();

  }



       // content = (FrameLayout) findViewById(R.id.content);
       // content.setOnClickListener(this);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onClick(View view) {

     /*   if(view.getId() == R.id.content){

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }*/

    }



}
