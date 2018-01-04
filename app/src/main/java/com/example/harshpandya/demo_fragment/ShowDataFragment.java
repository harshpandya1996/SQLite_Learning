package com.example.harshpandya.demo_fragment;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class ShowDataFragment extends android.app.Fragment {

    DatabaseHelper databaseHelper;
    GridView gridView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_show_data, container, false);

        gridView = (GridView)myView.findViewById(R.id.grid_view);

        ArrayList<String> arrayList = new ArrayList<String>();
        databaseHelper = new DatabaseHelper(getContext());
        Cursor cursor = databaseHelper.showBothData();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {

                int uniqueid = cursor.getInt(0);
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String phone = cursor.getString(3);
                String feedback = cursor.getString(4);
                String rate = cursor.getString(5);

                arrayList.add(String.valueOf(uniqueid));
                arrayList.add(String.valueOf(name));
                arrayList.add(String.valueOf(email));
                arrayList.add(String.valueOf(phone));
                arrayList.add(String.valueOf(feedback));
                arrayList.add(String.valueOf(rate));


                ArrayAdapter listAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
                gridView.setAdapter(listAdapter);
            }
        }
        return myView;
    }


}
