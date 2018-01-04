package com.example.harshpandya.demo_fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "DEMOFRAGMENT.DB";
        public static final String TABLE_NAME = "TB_USER";
        public static final String COL1 = "REG_ID";
        public static final String COL2 = "NAME";
        public static final String COL3 = "PASSWORD";
        public static final String COL4 = "EMAIL";
        public static final String COL5 = "PHONE";
        public static final String COL6 = "ADDRESS";
        public static final String COL7 = "DOB";

    public DatabaseHelper(Context context) {
            super(context,DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS TB_FEEDBACK (FEED_ID INTEGER PRIMARY KEY AUTOINCREMENT  , FEEDBACK TEXT , RATE TEXT, FK_REG_ID INTEGER,FOREIGN KEY(FK_REG_ID) REFERENCES TB_USER(REG_ID))");
            sqLiteDatabase.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT , %s TEXT , %s TEXT , %s TEXT , %s TEXT , %s TEXT , %s TEXT )", TABLE_NAME, COL1, COL2, COL3, COL4, COL5, COL6, COL7));
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE "+TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE TB_FEEDBACK");
            onCreate(sqLiteDatabase);
        }

        public boolean insertData(String name,String password,String email,String phone,String address,String dob)
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name",name);
            contentValues.put("password",password);
            contentValues.put("email",email);
            contentValues.put("phone",phone);
            contentValues.put("address",address);
            contentValues.put("dob",dob);
                long b = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
                if(b == -1)
                    return false;
                else
                    return true;
        }

        public Cursor showAllData()
        {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
            return cursor;
        }

    public boolean updateData(int id,String email,String phone,String address,String dob)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        contentValues.put("address",address);
        contentValues.put("dob",dob);
        long b = sqLiteDatabase.update(TABLE_NAME,contentValues,"REG_ID = ? ",new String[]{String.valueOf(id)});
        if(b == -1)
            return false;
        else
            return true;
    }

    public boolean updatePassword(int id,String password)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        long b =sqLiteDatabase.update(TABLE_NAME,contentValues,"REG_ID = ?",new String[]{String.valueOf(id)});
        if(b == -1)
            return false;
        else
            return true;
    }

    public boolean insertFeedbackData(int reg_id,String feedback,String rate)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FEEDBACK",feedback);
        contentValues.put("RATE",rate);
        contentValues.put("FK_REG_ID",reg_id);
        long b = sqLiteDatabase.insert("TB_FEEDBACK",null,contentValues);
        if(b == -1)
            return false;
        else
            return true;

    }

    public Cursor showBothData()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT TB_USER.REG_ID,TB_USER.NAME,TB_USER.EMAIL,TB_USER.PHONE,TB_FEEDBACK.FEEDBACK,TB_FEEDBACK.RATE FROM TB_USER INNER JOIN TB_FEEDBACK ON TB_USER.REG_ID=TB_FEEDBACK.FK_REG_ID ",null);
        return cursor;
    }
}
