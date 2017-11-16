package com.example.user.mysql_connecting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by User on 10/21/2017.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    Context mContext;
    int mLayoutResourceId;
    ArrayList<Post> mData = null;


    // resource -> row
    public PostAdapter(Context context, int resource, ArrayList<Post> data){
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId =  resource;
        this.mData = data;
    }

    // overrride methods of ArrayAdapter
    @Override
    public Post getItem(int position){
        return  super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View row = convertView;
        PostHolder holder = null;

        // if we currently dont have a row View to reuse ...
        if(row == null) {
            //Create a new View
            // inflate the layout for a single row
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new PostHolder();

            //get a reference to the different view elements we wish to update
            holder.nameView = (TextView) row.findViewById(R.id.rowTextView);
                       holder.imageView = (ImageView) row.findViewById(R.id.rowImageView);

            row.setTag(holder);
        }else{
            // otherwise use an existing one  -> brings reuseability of code more eficensy on memory
            holder = (PostHolder) row.getTag();
        }

        // get the data from the data arraay
        Post post = mData.get(position);


        // setting the view to reflect the data we need to display
        // nameView.setText(place.mNameOfPlace);
        // zipView.setText(String.valueOf(place.mZipCode)); // always pay attention yo your data type

        // setup and reuse the same listener for each row
        holder.imageView.setOnClickListener(PopupListener);
        Integer rowPosition = position;
        holder.imageView.setTag(rowPosition);

        // setting the  view to reflect the data we need to display
        holder.nameView.setText(post.nameUser);



        //imageView.setImageResource(resId);

        holder.imageView.setImageBitmap(post.photoUser);

        // returning the row view (because this is called getView after all)
        return row;



    }

    View.OnClickListener PopupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer viewPosition = (Integer) v.getTag();
            Post p = mData.get(viewPosition);
            Toast.makeText(getContext(), p.nameUser, Toast.LENGTH_SHORT).show();


        }
    };

    private static class PostHolder {
        TextView nameView;
        ImageView imageView;


    }


}

