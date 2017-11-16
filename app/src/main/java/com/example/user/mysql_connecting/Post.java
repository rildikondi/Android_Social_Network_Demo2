package com.example.user.mysql_connecting;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by User on 10/21/2017.
 */

public class Post {

    public String nameUser;
    public Bitmap photoUser;


    public Post(String name, Bitmap photo) {

        this.nameUser = name;
        this.photoUser = photo;
    }

}