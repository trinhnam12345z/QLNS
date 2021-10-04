package com.trinhnam12345z.qlns;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Update extends AppCompatActivity {

    Button btnChupHinh,btnChonHinh,btnLuu,btnQuaylai;
    EditText editTenNV,editSDT;
    ImageView imgAnh;
    int manv;

    final String DATABASE_NAME = "QLNS.db";
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        addControl();

        Intent intent = getIntent();
        manv = intent.getIntExtra("MaNV",-1);
        loadData(manv);

        addEvent();
    }

    private void addEvent() {
        btnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Update.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent , REQUEST_TAKE_PHOTO);
    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        startActivityForResult(intent , REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgAnh.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if(requestCode == REQUEST_TAKE_PHOTO){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAnh.setImageBitmap(bitmap);
            }
        }
    }

    private void loadData(int manv) {
        SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from NhanVien where MaNv = ?",new String[]{manv +""});
        cursor.moveToFirst();
        String tennv = cursor.getString(1);
        String sdt = cursor.getString(2);
        byte[] anh = cursor.getBlob(3);
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
        imgAnh.setImageBitmap(bitmap);
        editTenNV.setText(tennv);
        editSDT.setText(sdt);
    }

    private void addControl() {
        btnChupHinh = (Button) findViewById(R.id.btnChupAnh);
        btnChonHinh = (Button) findViewById(R.id.btnChonAnh);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnQuaylai = (Button) findViewById(R.id.btnQuayLai);
        editTenNV = (EditText) findViewById(R.id.editTenNV);
        editSDT = (EditText) findViewById(R.id.editSDT);
        imgAnh = (ImageView) findViewById(R.id.imgAnhh);
    }
}