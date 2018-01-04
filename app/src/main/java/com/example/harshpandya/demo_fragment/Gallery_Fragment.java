package com.example.harshpandya.demo_fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Gallery_Fragment extends android.app.Fragment {

    ImageView myimage;
    int[] myInts = {R.drawable.photo1,R.drawable.photo2,R.drawable.photo3,R.drawable.photo4,R.drawable.photo5,R.drawable.photo6};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView =  inflater.inflate(R.layout.fragment_gallery_, container, false);

        myimage = (ImageView) myView.findViewById(R.id.photos);

        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            int i = 0;
            @Override
            public void run() {

                    myimage.setImageResource(myInts[i]);
                    i++;

                    if(i>(myInts.length-1)) {
                        i = 0;
                    }

                    handler.postDelayed(this, 2500);
                }

            };




        handler.postDelayed(runnable,2500);

        return myView;
    }

}
