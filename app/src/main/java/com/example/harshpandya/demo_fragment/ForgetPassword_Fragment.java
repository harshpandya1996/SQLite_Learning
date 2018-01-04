package com.example.harshpandya.demo_fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ForgetPassword_Fragment extends android.app.Fragment {

    EditText et3;
    Button btn1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View myview = inflater.inflate(R.layout.fragment_forget_password_, container, false);

        et3 = (EditText) myview.findViewById(R.id.et3);
        btn1 = (Button) myview.findViewById(R.id.btn1);

        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = databaseHelper.showAllData();

                if (cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {

                        String email = cursor.getString(3);

                        if(email.equals(et3.getText().toString()))
                        {
                            String password = cursor.getString(2);
                            String phoneNo = cursor.getString(4);

                            try {
                                SmsManager smsManager=SmsManager.getDefault();
                                smsManager.sendTextMessage(phoneNo,"7990625051", password, null, null);
                                Toast.makeText(getActivity(),"SMS Sent!...",Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                Toast.makeText(getActivity(),	e.toString(),Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                        }
//                        else
//                        {
//                            Toast.makeText(getActivity(),	"SMS faild",Toast.LENGTH_LONG).show();
//
//                        }
                    }
                }


            }
        });
        return myview;
    }

}