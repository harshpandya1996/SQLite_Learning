package com.example.harshpandya.demo_fragment;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class Edit_Profile extends android.app.Fragment {

    EditText et3,et4,et5,et6;
    Button btn1;
    String newemail,newphone,newaddress,newdob;
    Calendar myCalendar = Calendar.getInstance();
    DatabaseHelper databaseHelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et6.setText(sdf.format(myCalendar.getTime()));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        et3 = (EditText)myView.findViewById(R.id.et3);
        et4 = (EditText)myView.findViewById(R.id.et4);
        et5 = (EditText)myView.findViewById(R.id.et5);
        et6 = (EditText)myView.findViewById(R.id.et6);
        btn1 = (Button)myView.findViewById(R.id.btn1);

        final int uniqueid = getActivity().getIntent().getExtras().getInt("uniqueid");
        databaseHelper = new DatabaseHelper(getContext());
        Cursor cursor = databaseHelper.showAllData();

        while (cursor.moveToNext()) {

            if(uniqueid == cursor.getInt(0))
            {
                et3.setText(cursor.getString(3));
                et4.setText(cursor.getString(4));
                et5.setText(cursor.getString(5));
                et6.setText(cursor.getString(6));
                break;
            }

        }


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        et6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        databaseHelper = new DatabaseHelper(getContext());

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    newemail = et3.getText().toString();
                    newphone = et4.getText().toString();
                    newaddress = et5.getText().toString();
                    newdob = et6.getText().toString();

                    boolean b = databaseHelper.updateData(uniqueid, newemail, newphone, newaddress, newdob);
                    if (b == false) {
                        Toast.makeText(getActivity(), "Data is not updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Data is updated", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        return myView;

    }
}
