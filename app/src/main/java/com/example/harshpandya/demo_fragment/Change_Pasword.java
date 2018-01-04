package com.example.harshpandya.demo_fragment;

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

public class Change_Pasword extends android.app.Fragment {

    EditText et3,et4;
    Button btn1;
    DatabaseHelper databaseHelper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_change_pasword, container, false);

        et3 = (EditText)myView.findViewById(R.id.et3);
        et4 = (EditText)myView.findViewById(R.id.et4);
        btn1 = (Button) myView.findViewById(R.id.btn1);

        Bundle bundle = getActivity().getIntent().getExtras();
        final int uniqueid = bundle.getInt("uniqueid");

        databaseHelper = new DatabaseHelper(getContext());
        final Cursor cursor = databaseHelper.showAllData();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                while (cursor.moveToNext())
                {
                    int id = cursor.getInt(0);

                    if(id == uniqueid)
                    {

                        String newpassword = et3.getText().toString();
                        String newpassword2 = et4.getText().toString();

                        if(newpassword.equals(newpassword2))
                        {
                            boolean b = databaseHelper.updatePassword(id,newpassword);
                            if(b == false)
                                Toast.makeText(getActivity(),"Re-enter Password",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getActivity(),"Password is updated",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getActivity(),"Password is updated",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return myView;
    }
}
