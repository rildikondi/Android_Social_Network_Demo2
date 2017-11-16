package com.example.user.mysql_connecting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ConfigurationHelper;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
public class RegisterFragment extends Fragment implements View.OnClickListener{

    View v;
    TextView btn_go_to_login;
    Button btn_register;
    EditText et_username, et_email, et_email2, et_password, et_password2;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      v = inflater.inflate(R.layout.fragment_register, container, false);
        btn_go_to_login = (TextView) v.findViewById(R.id.btn_go_to_login);
        btn_register = (Button) v.findViewById(R.id.btn_register);

        et_username = (EditText) v.findViewById(R.id.name);
        et_email = (EditText) v.findViewById(R.id.email);
        et_email2 = (EditText) v.findViewById(R.id.email2);
        et_password = (EditText) v.findViewById(R.id.password);
        et_password2 = (EditText) v.findViewById(R.id.password2);




        btn_go_to_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        // Inflate the layout for this fragment
        setRetainInstance(true);
        return v;
    }

    @Override
    public void onClick(View view) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


        switch(view.getId()){
            case R.id.registerRelativeLayout:


                break;

            case R.id.btn_register:

                final String name = et_username.getText().toString().trim();
                final String email = et_email.getText().toString().trim();
                final String email2 = et_email2.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                final String password2 = et_password2.getText().toString().trim();
                final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@#!%*?&])[A-Za-z\\d$@#!%*?&]{8,15}";

                if(name.equals("") || email.equals("") || email2.equals("") || password.equals("") || password2.equals("") ){

                    Toast.makeText(getActivity(), "Please fill all the fields!", Toast.LENGTH_SHORT).show();

                }
                else if (!email.equals(email2) && !password.equals(password2))
                {

                    Toast.makeText(getActivity(), "Email and password do not match!", Toast.LENGTH_SHORT).show();

                }
                else if (!password.equals(password2))
                {

                    Toast.makeText(getActivity(), "Password do not match!", Toast.LENGTH_SHORT).show();

                }
                else if (!email.equals(email2))
                {

                    Toast.makeText(getActivity(), "Email do not match!", Toast.LENGTH_SHORT).show();

                }
                else if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false)
                {

                    Toast.makeText(getActivity(), "E-mail address is not valid!", Toast.LENGTH_SHORT).show();

                }
                else if (!password.matches(regex))
                {
                    Toast.makeText(getActivity(), "Password is invalid ! Please read the Criteres!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //.makeText(getActivity(), "Registered!!!", Toast.LENGTH_SHORT).show();
                                                                                            //from Constats class
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.contains("Please check your e-mail!!!") || response.contains("E-mail already exists!!!")){

                                        fragmentManager.beginTransaction().replace(R.id.content, new LoginFragment()).commit();

                                    }

                                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

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
                            params.put(Constants.KEY_NAME, name);
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


                break;
            case R.id.btn_go_to_login:
                fragmentManager.beginTransaction().replace(R.id.content, new LoginFragment()).commit();

                break;
            default:
                break;

        }

    }
}
