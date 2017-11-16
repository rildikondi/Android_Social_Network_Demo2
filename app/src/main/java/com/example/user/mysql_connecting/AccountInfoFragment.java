package com.example.user.mysql_connecting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInfoFragment extends Fragment {
    View v;
    TextView login_info;

    Button go_to_friends;
    Button myProfile;
    Button home;
    Button search;

    String info = "";
    String email = "";



    public AccountInfoFragment() {
        // Required empty public constructor
    }

    public void setLogin_info(String aEmail){
        info = "Welcome to the Test App!!!";
        email = aEmail;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       // getActivity().getSupportFragmentManager()
        v = inflater.inflate(R.layout.fragment_account_info, container, false);
        if (info == "Welcome to the Test App!!!"){

            go_to_friends = (Button) v.findViewById(R.id.go_toUF);
            go_to_friends.setVisibility(View.VISIBLE);

            go_to_friends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), UserActivity.class);
                   startActivity(i);


                }
            });


            home = (Button) v.findViewById(R.id.go_to_Home);
            home.setVisibility(View.VISIBLE);

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), Home.class);
                    startActivity(i);

                }
            });


            myProfile = (Button) v.findViewById(R.id.myProfileButton);
            myProfile.setVisibility(View.VISIBLE);

            myProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), MyProfile.class);
                    i.putExtra("email", email);
                    startActivity(i);

                }
            });

            search = (Button) v.findViewById(R.id.searchButton);
            search.setVisibility(View.VISIBLE);








            login_info = (TextView) v.findViewById(R.id.infoTextView);
            login_info.setText("Welcome to the Test App!!!");
         }

        setRetainInstance(true);
        return v;


    }


}
