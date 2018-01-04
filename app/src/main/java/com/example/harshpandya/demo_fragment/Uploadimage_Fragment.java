package com.example.harshpandya.demo_fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;


public class Uploadimage_Fragment extends android.app.Fragment {

    ImageView imageView;
    Button button;
    private String imagepath = null;
    private ProgressDialog dialog = null;
    private int serverResponseCode = 0;
    private String upLoadServerUri = null;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_uploadimage, container, false);

        imageView = (ImageView) myview.findViewById(R.id.myimg);
        button = (Button) myview.findViewById(R.id.uploadpic);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"hi1",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
                Toast.makeText(getActivity(),"Uploading started..",Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {

                    public void run() {
                        // uploadFile(imagepath);
                    }
                }).start();

            }
        });

        return myview;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getActivity(),"hi2",Toast.LENGTH_LONG).show();

            try{
                if(requestCode == 1 && resultCode == RESULT_OK) {

                    Toast.makeText(getActivity(),"hi3",Toast.LENGTH_LONG).show();

                    Uri selectedImageUri = data.getData();
                    imagepath = getPath(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
                    imageView.setImageBitmap(bitmap);
                    //messageText.setText("Uploading file path:" + imagepath);
                    Toast.makeText(getActivity(),"Execute",Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception ex){
                Toast.makeText(getActivity(),ex.toString(),Toast.LENGTH_LONG).show();
            }
        }

        public String getPath(Uri uri) {

            Toast.makeText(getActivity(),"hi4",Toast.LENGTH_LONG).show();

            Cursor cursor = null;
            int column_index = 0;
            try {

                String[] projection = {MediaStore.Images.Media.DATA};
                cursor = getActivity().managedQuery(uri,projection,null,null,null);
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
            }
            catch (Exception ex)
            {
                Toast.makeText(getActivity(),ex.toString(),Toast.LENGTH_LONG).show();

            }

            return cursor.getString(column_index);
        }

    public int uploadFile(String sourceFileUri) {

        int day, month, year;
        int second, minute, hour;
        GregorianCalendar date = new GregorianCalendar();

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);

        String name = (hour + "" + minute + "" + second + "" + day + "" + (month + 1) + "" + year);
        String tag = name + ".jpg";
        String fileName = sourceFileUri.replace(sourceFileUri, tag);

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            try {
                dialog.dismiss();
                Log.e("uploadFile", "Source File not exist :" + imagepath);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("Source File not exist :" + imagepath);
                        Toast.makeText(getActivity(), "Source File not exist :" + imagepath, Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e)
            {
                Toast.makeText(getActivity(), e.toString() + imagepath, Toast.LENGTH_SHORT).show();
            }
            return 0;
        } else {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                Toast.makeText(getActivity(), "Before Whilte.", Toast.LENGTH_SHORT).show();

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n" + " D:/Android/uploads";
                            //messageText.setText(msg);
                            Toast.makeText(getActivity(), msg+"File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                fileInputStream.close();
                dos.flush();
                dos.close();

            }
            catch (Exception e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            return serverResponseCode;
        }
    }
}

