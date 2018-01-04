package com.example.harshpandya.demo_fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class Feedback_Fragment extends android.app.Fragment {

    EditText et3;
    RatingBar ratingBar;
    Button btn1;
    DatabaseHelper dbHelper;
    String feedback,rate;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myview = inflater.inflate(R.layout.fragment_feedback_, container, false);

        dbHelper = new DatabaseHelper(getContext());
        try
        {

            et3=(EditText)myview.findViewById(R.id.et3);
            ratingBar = (RatingBar)myview.findViewById(R.id.rate);

            btn1=(Button)myview.findViewById(R.id.bt1);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        feedback = et3.getText().toString();
                        rate = String.valueOf(ratingBar.getRating());
                        Bundle bundle = getActivity().getIntent().getExtras();
                        int uniqueid = bundle.getInt("uniqueid");

                        boolean b = dbHelper.insertFeedbackData(uniqueid,feedback,rate);
                        if(b == false)
                            Toast.makeText(myview.getContext(),"Data is not inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(myview.getContext(),"Data is inserted", Toast.LENGTH_LONG).show();


                    }
                });
        }
        catch (Exception ex)
        {
            Toast.makeText(myview.getContext(),ex.toString(), Toast.LENGTH_LONG).show();
        }
        return myview;

    }
}
