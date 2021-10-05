package com.trinhnam12345z.qlns;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Add extends AppCompatActivity {
    Button btnChupHinh,btnChonHinh,btnLuu,btnQuaylai;
    EditText editTenNV,editSDT,editManv;
    ImageView imgAnh;

    final String DATABASE_NAME = "QLNS.db";
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnChupHinh = (Button) findViewById(R.id.btnChupAnhh);
        btnChonHinh = (Button) findViewById(R.id.btnChonAnhh);
        btnLuu = (Button) findViewById(R.id.btnLuuu);
        btnQuaylai = (Button) findViewById(R.id.btnQuayLaii);
        editTenNV = (EditText) findViewById(R.id.editTenNVv);
        editSDT = (EditText) findViewById(R.id.editSDTt);
        imgAnh = (ImageView) findViewById(R.id.imgAnhhh);
        editManv = (EditText) findViewById(R.id.editMaNv);

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
                Intent intent = new Intent(Add.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenNV = editTenNV.getText().toString();
                String sdt = editSDT.getText().toString();
                byte[] anh = getByteArrayFromImageView(imgAnh);
                int manv = Integer.parseInt(editManv.getText().toString());

                ContentValues contentValues = new ContentValues();
                contentValues.put("TenNv",tenNV);
                contentValues.put("SDT",sdt);
                contentValues.put("Anh",anh);
                contentValues.put("MaNv",manv);

                SQLiteDatabase database = Database.initDatabase(Add.this,DATABASE_NAME);
                database.insert("NhanVien",null,contentValues);

                Intent intent = new Intent(Add.this,MainActivity.class);
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
    public byte[] getByteArrayFromImageView(ImageView imgv){
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}