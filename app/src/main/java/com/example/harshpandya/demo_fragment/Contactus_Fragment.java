package com.example.harshpandya.demo_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class Contactus_Fragment extends android.app.Fragment {

    EditText et1,et2,et3;
    Button btn1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View myview = inflater.inflate(R.layout.fragment_contactus_, container, false);

        et1=(EditText)myview.findViewById(R.id.et1);
        et2=(EditText)myview.findViewById(R.id.et2);
        et3=(EditText)myview.findViewById(R.id.et3);

        btn1=(Button)myview.findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        Toast.makeText(myview.getContext(),""+et1.getText().toString()+" "+et2.getText().toString()+" "+et3.getText().toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("text/plain");
                intent.putExtra(Intent.ACTION_SENDTO,et1.getText().toString());
                intent.putExtra(Intent.EXTRA_SUBJECT,et2.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,et3.getText().toString());

                startActivity(Intent.createChooser(intent,""));

            }
        });

        return myview;


    }

}
