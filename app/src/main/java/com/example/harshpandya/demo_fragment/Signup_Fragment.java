package com.example.harshpandya.demo_fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
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

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class Signup_Fragment extends android.app.Fragment {

    EditText et1,et2,et3,et4,et5,et6;
    Button btn1;
    DatabaseHelper databaseHelper;
    Calendar myCalendar = Calendar.getInstance();
    String name,password,email,address,dob;
    long phone;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et6.setText(sdf.format(myCalendar.getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View myView = inflater.inflate(R.layout.fragment_signup_, container, false);

        et1 = (EditText) myView.findViewById(R.id.et1);
        et2 = (EditText) myView.findViewById(R.id.et2);
        et3 = (EditText) myView.findViewById(R.id.et3);
        et4 = (EditText) myView.findViewById(R.id.et4);
        et5 = (EditText) myView.findViewById(R.id.et5);
        et6 = (EditText) myView.findViewById(R.id.et6);
        btn1 = (Button)myView.findViewById(R.id.btn1);

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
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                try {
                    name = et1.getText().toString();
                    password = et2.getText().toString();
                    email = et3.getText().toString();
                    phone = Long.valueOf(et4.getText().toString());
                    address = et5.getText().toString();
                    dob = et6.getText().toString();



                    boolean b = databaseHelper.insertData(name, password, email, String.valueOf(phone), address, dob);
                    if (b == false) {
                        Toast.makeText(getActivity(), "Data is not inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), String.valueOf(phone), Toast.LENGTH_LONG).show();

                        Toast.makeText(getActivity(), "Data is inserted", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex)
                {
                    Toast.makeText(getActivity(),ex.toString(),Toast.LENGTH_LONG).show();
                }

                Fragment fragment = new Signup_Fragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.content_main, new Login_Fragment()).commit();

            }
        });



        return myView;
    }

}
