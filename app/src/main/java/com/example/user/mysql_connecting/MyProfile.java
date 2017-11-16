package com.example.user.mysql_connecting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class MyProfile extends AppCompatActivity {


    byte[] choosenBitmap = null;
    Bitmap downloadedBitmap;
    String stringImage;
    String email;
    ImageView imageView;


    public void uploadImage(){


        if(choosenBitmap != null) {


            //.makeText(getActivity(), "Registered!!!", Toast.LENGTH_SHORT).show();
            //from Constats class
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                            if (response.contains("Photo Uploaded!!!")) {

                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
                    params.put("image", stringImage);
                    params.put("email", email);


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


        }else{

            Toast.makeText(getApplicationContext(), "Please select a photo!!!", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.profile_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.addPhoto){
            if(Build.VERSION.SDK_INT >22) {

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {
                    getPhoto();
                }
            }else{

                 getPhoto();
            }

        }
        if(item.getItemId() == R.id.share){
            uploadImage();
        }

        if(item.getItemId() == R.id.log_out){


           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }




        return super.onOptionsItemSelected(item);
    }

    public void getPhoto(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    // sdk >22
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getPhoto();

            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");



        imageView = (ImageView) findViewById(R.id.ImageView);

        downloadImage();




        if(Build.VERSION.SDK_INT >22) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {
                getPhoto();
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);



                imageView.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);

                choosenBitmap = stream.toByteArray();
                stringImage = Base64.encodeToString(choosenBitmap, Base64.DEFAULT);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

            ///Download image when opening my profile




    public void downloadImage(){


        //.makeText(getActivity(), "Registered!!!", Toast.LENGTH_SHORT).show();
        //from Constats class
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DOWNLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        byte[] downloadByteImg = Base64.decode(response,Base64.DEFAULT);

                        downloadedBitmap = BitmapFactory.decodeByteArray(downloadByteImg, 0, downloadByteImg.length);

                        if(downloadedBitmap == null){

                            Toast.makeText(getApplicationContext(), "downloadedBitmap is null in Onresponse!!!", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            imageView.setImageBitmap(downloadedBitmap);
                            imageView.refreshDrawableState();
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

                params.put("email", email);


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
