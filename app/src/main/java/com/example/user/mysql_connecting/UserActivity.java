package com.example.user.mysql_connecting;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {



    ListView listView = null;
    ArrayAdapter adapter;
    ArrayList<String> usernames;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        usernames = new ArrayList<>();


        listView  = (ListView) findViewById(R.id.userListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        listView.setAdapter(adapter);
        showFriends();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), PeopleActivity.class);

                intent.putExtra("name", usernames.get(position));

                startActivity(intent);

            }
        });

    }



    public void showFriends(){

            //.makeText(getActivity(), "Registered!!!", Toast.LENGTH_SHORT).show();
            //from Constats class
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LIST_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(!response.contains("This db has not any accounts!!!")){



                                try {

                                    JSONArray jsonArray = new JSONArray(response);

                                    int counter = 0;
                                    for (int i = 0; i<jsonArray.length(); i++) {
                                        JSONArray arr = jsonArray.getJSONArray(i);
                                        String name = arr.getString(0);
                                        usernames.add(name);
                                        counter++;
                                        adapter.notifyDataSetChanged();
                                       // Toast.makeText(getApplicationContext(), "data arrived" + name, Toast.LENGTH_SHORT).show();
                                    }

                                }catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "data not arrived", Toast.LENGTH_SHORT).show();
                                     e.printStackTrace();

                                }
                            }else
                            {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error instanceof TimeoutError){
                        Toast.makeText(getApplicationContext(), "Timeout Error!!!", Toast.LENGTH_SHORT).show();
                    }else if(error instanceof NoConnectionError){
                        Toast.makeText(getApplicationContext(), "No Connection Error!!!", Toast.LENGTH_SHORT).show();
                    }else if(error instanceof AuthFailureError){
                        Toast.makeText(getApplicationContext(), "Authentication Failure Error!!!", Toast.LENGTH_SHORT).show();
                    }else if(error instanceof NetworkError){
                        Toast.makeText(getApplicationContext(), "Networl Error!!!", Toast.LENGTH_SHORT).show();
                    }else if(error instanceof ServerError){
                        Toast.makeText(getApplicationContext(), "Server Error!!!", Toast.LENGTH_SHORT).show();
                    }else if(error instanceof ParseError){
                        Toast.makeText(getApplicationContext(), "JSON Parse Error!!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }){

                // and this sending to db for more security accessing
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("User-Agent", "MyTestApp");
                    return headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);




        }


}
