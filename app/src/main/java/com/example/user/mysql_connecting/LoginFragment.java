package com.example.user.mysql_connecting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener,View.OnKeyListener {

    View v;
    TextView btn_go_to_register;
    Button btn_login;
    EditText et_login_email,et_login_password;



    public LoginFragment() {
        // Required empty public constructor
    }

    public void login(FragmentManager fragmentManager){

        final FragmentManager fragmentManager2 = fragmentManager;

        final String email = et_login_email.getText().toString().trim();
        final String password = et_login_password.getText().toString().trim();



        if(email.equals("") || password.equals("")){

            Toast.makeText(getActivity(), "Please fill all the fields!", Toast.LENGTH_SHORT).show();

        }
        else if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false)
        {

            Toast.makeText(getActivity(), "E-mail address is not valid!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            //.makeText(getActivity(), "Registered!!!", Toast.LENGTH_SHORT).show();
            //from Constats class
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.contains("Welcome!!!")){


                                AccountInfoFragment accountInfoFragment = new AccountInfoFragment();
                                accountInfoFragment.setLogin_info(email);
                                fragmentManager2.beginTransaction().replace(R.id.content, accountInfoFragment).commit();

                            }

                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error instanceof TimeoutError){
                        Toast.makeText(getActivity(), "Timeout Error!!!", Toast.LENGTH_SHORT).show();
                    }else if(error instanceof NoConnectionError){
                        Toast.makeText(getActivity(), "No Connection Error!!!", Toast.LENGTH_SHORT).show();
                    }else if(error instanceof AuthFailureError){
                        Toast.makeText(getActivity(), "Authentication Failure Error!!!", Toast.LENGTH_SHORT).show();
                    }else if(error instanceof NetworkError){
                        Toast.makeText(getActivity(), "Networl Error!!!", Toast.LENGTH_SHORT).show();
                    }else if(error instanceof ServerError){
                        Toast.makeText(getActivity(), "Server Error!!!", Toast.LENGTH_SHORT).show();
                    }else if(error instanceof ParseError){
                        Toast.makeText(getActivity(), "JSON Parse Error!!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }){
                // whats sending to database
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(Constants.KEY_EMAIL, email);
                    params.put(Constants.KEY_PASSWORD, password);
                    return params;
                }
                // and this sending to db for more security accessing
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("User-Agent", "MyTestApp");
                    return headers;
                }
            };

            MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_login, container, false);
        btn_go_to_register = (TextView) v.findViewById(R.id.btn_goto_register);
        btn_login = (Button) v.findViewById(R.id.btn_login);


        et_login_email = (EditText) v.findViewById(R.id.login_email);
        et_login_password = (EditText) v.findViewById(R.id.login_password);

        btn_go_to_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        et_login_password.setOnKeyListener(this);

        setRetainInstance(true);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View view) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch(view.getId()){

            case R.id.btn_login:
                login(fragmentManager);

                break;
            case R.id.btn_goto_register:

                fragmentManager.beginTransaction().replace(R.id.content, new RegisterFragment()).commit();

                break;
            default:
                break;

        }



    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

       /* if(i == keyEvent.KEYCODE_ENTER){

            login();

        }*/

        return false;
    }
}
