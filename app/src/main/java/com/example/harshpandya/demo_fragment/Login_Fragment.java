package com.example.harshpandya.demo_fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Fragment extends android.app.Fragment {

    EditText et1,et2;
    Button btn1;
    DatabaseHelper databaseHelper;
    String username,password;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_login_, container, false);

        et1 = (EditText)myView.findViewById(R.id.et1);
        et2 = (EditText)myView.findViewById(R.id.et2);
        btn1 = (Button)myView.findViewById(R.id.btn1);

        databaseHelper = new DatabaseHelper(getContext());

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = et1.getText().toString();
                password = et2.getText().toString();

                if(username.equals("root") && password.equals("root"))
                {
                    Intent intent = new Intent(getActivity(),AdminActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Cursor cursor = databaseHelper.showAllData();
                    if (cursor.getCount() != 0) {
                        while (cursor.moveToNext()) {

                            int uniqueid = cursor.getInt(0);
                            String us = cursor.getString(1);
                            String pwd = cursor.getString(2);

                            if (username.equals(us) && password.equals(pwd)) {
                                Toast.makeText(getActivity(), "welcome " + username, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(), Main2Activity.class);
                                intent.putExtra("uniqueid", uniqueid);
                                startActivity(intent);

                            }

                        }
                    }
                }

            }
        });

        return myView;
    }

}
