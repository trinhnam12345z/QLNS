package com.trinhnam12345z.qlns;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final String DATABASE_NAME = "QLNS.db";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //goi database

        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from NhanVien",null);
        cursor.moveToFirst();
        Toast.makeText(this,cursor.getString(1),Toast.LENGTH_SHORT).show();
    }
}