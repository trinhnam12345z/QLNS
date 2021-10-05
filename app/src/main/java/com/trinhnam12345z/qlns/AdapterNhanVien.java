package com.trinhnam12345z.qlns;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterNhanVien extends BaseAdapter {
    Context context;
    ArrayList<NhanVien> list;
    final String DATABASE_NAME = "QLNS.db";


    public AdapterNhanVien(Context context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dong = inflater.inflate(R.layout.row, null);

        TextView txtTenNV = (TextView) dong.findViewById(R.id.txtTenNV);
        TextView txtMaNV = (TextView) dong.findViewById(R.id.txtMaNV);
        TextView txtSDT = (TextView) dong.findViewById(R.id.txtSoDT);
        ImageView imgAnh = (ImageView) dong.findViewById(R.id.imgAnh);
        Button btnSua = (Button) dong.findViewById(R.id.btnSua);
        Button btnXoa = (Button) dong.findViewById(R.id.btnXoa);

        NhanVien nhanVien = list.get(i);
        txtMaNV.setText(nhanVien.MaNV + "");
        txtTenNV.setText(nhanVien.TenNV);
        txtSDT.setText(nhanVien.SoDT);
        Bitmap bmAnh = BitmapFactory.decodeByteArray(nhanVien.Anh,0, nhanVien.Anh.length);
        imgAnh.setImageBitmap(bmAnh);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Update.class);
                intent.putExtra("MaNV",nhanVien.MaNV);
                context.startActivity(intent);
            }
        });
       btnXoa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setTitle("Xác nhận ");
               builder.setMessage("Bạn có chắc chắn xóa không ?");
               builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                        delete(nhanVien.MaNV);
                   }
               });
               builder.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                   }
               });
               builder.create().show();
           }
       });

        return dong;
    }
    private void delete(int id){
        SQLiteDatabase database = Database.initDatabase((Activity)context,DATABASE_NAME);
        database.delete("NhanVien","MaNv = ?",new String[]{id+""});

        Cursor cursor = database.rawQuery("select * from NhanVien",null);

        list.clear();

        for (int i = 0 ; i <cursor.getCount(); i++){
            cursor.moveToPosition(i);

            int manv = cursor.getInt(0);
            String tennv = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);

            list.add(new NhanVien(manv,tennv,sdt,anh));
        }
        notifyDataSetChanged();
    }
}
