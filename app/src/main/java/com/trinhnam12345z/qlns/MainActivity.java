package com.trinhnam12345z.qlns;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String DATABASE_NAME = "QLNS.db";
    SQLiteDatabase database;

    ListView listDSNV;
    ArrayList<NhanVien> arrNhanVien;
    AdapterNhanVien adapterNhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
        readData();

    }

    private void readData() {
        //goi database
        database = Database.initDatabase(this,DATABASE_NAME);

        Cursor cursor = database.rawQuery("select * from NhanVien",null);

        arrNhanVien.clear();

        for (int i = 0 ; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);

            int manv = cursor.getInt(0);
            String tennv = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);

            arrNhanVien.add(new NhanVien(manv,tennv,sdt,anh));
        }
        adapterNhanVien.notifyDataSetChanged();//cap nhat adapter

//        cursor.moveToFirst();
//        Toast.makeText(this,cursor.getString(1),Toast.LENGTH_SHORT).show();
    }

    private void addControl(){
        listDSNV = (ListView) findViewById(R.id.listDSNV);
        arrNhanVien = new ArrayList<>();
        adapterNhanVien = new AdapterNhanVien(this,arrNhanVien);
        listDSNV.setAdapter(adapterNhanVien);
    }
}