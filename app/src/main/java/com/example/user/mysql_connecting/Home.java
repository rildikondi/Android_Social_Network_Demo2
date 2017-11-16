package com.example.user.mysql_connecting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    Bitmap downloadedBitmap;
    TextView photosStatus;

    ListView photoListView;
    PostAdapter adapter;

    ArrayList<Post> postsArraylist = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        photosStatus = (TextView) findViewById(R.id.statusTextView);

        photoListView = (ListView) findViewById(R.id.homeListView);







        downloadImages();




    }


    ///Download image when opening my profile




    public void downloadImages(){


        //.makeText(getActivity(), "Registered!!!", Toast.LENGTH_SHORT).show();
        //from Constats class
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ALL_PEOPLE_PHOTO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.contains("No friends have posted photos!!!")) {




                            try {

                                JSONArray jsonArray = new JSONArray(response);

                                Toast.makeText(getApplicationContext(),  jsonArray.length()+"", Toast.LENGTH_SHORT).show();



                                for (int i = 0; i<jsonArray.length(); i++) {

                                    JSONArray arr = jsonArray.getJSONArray(i);
                                    String person =  arr.getString(0);
                                    String photoarray = arr.getString(1);

                                    byte[] downloadByteImg = Base64.decode(photoarray, Base64.DEFAULT);

                                    downloadedBitmap = BitmapFactory.decodeByteArray(downloadByteImg, 0, downloadByteImg.length);

                                    if (downloadedBitmap == null) {

                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                    } else {



                                        postsArraylist.add(new Post(person,downloadedBitmap));



                                    }



                                    // Toast.makeText(getApplicationContext(), "data arrived" + name, Toast.LENGTH_SHORT).show();
                                }




                            }catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "data not arrived", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();

                            }

                            adapter = new PostAdapter(getApplicationContext(), R.layout.row, postsArraylist);

                            if(photoListView != null){
                                // mListView.setAdapter(mArrayAdapter);
                                photoListView.setAdapter(adapter);
                            }




                        }else{

                            photosStatus.setText(response);
                            photosStatus.setVisibility(View.VISIBLE);


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
