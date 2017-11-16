package com.example.user.mysql_connecting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
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

public class PeopleActivity extends AppCompatActivity {


    Bitmap downloadedBitmap;
    TextView photoText;
    String name;
    ImageView people_ImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        people_ImageView = (ImageView) findViewById(R.id.peopleImageView);
        photoText = (TextView) findViewById(R.id.photoTextView);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        setTitle(name + "'s Profile");





        downloadImage();


    }


    ///Download image when opening my profile




    public void downloadImage(){


        //.makeText(getActivity(), "Registered!!!", Toast.LENGTH_SHORT).show();
        //from Constats class
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PEOPLE_PHOTO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.contains("User has no photo!!!")) {
                            byte[] downloadByteImg = Base64.decode(response, Base64.DEFAULT);

                            downloadedBitmap = BitmapFactory.decodeByteArray(downloadByteImg, 0, downloadByteImg.length);

                            if (downloadedBitmap == null) {

                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                            } else {
                                people_ImageView.setImageBitmap(downloadedBitmap);
                                people_ImageView.refreshDrawableState();
                            }
                        }else{

                            photoText.setText(response);


                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "Timeout Error!!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "No Connection Error!!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Authentication Failure Error!!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Networl Error!!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server Error!!!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "JSON Parse Error!!!", Toast.LENGTH_SHORT).show();
                }

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("name", name);


                return params;
            }

            // and this sending to db for more security accessing
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "MyTestApp");
                return headers;
            }


        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }
}
