package com.trinhnam12345z.qlns;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterNhanVien extends BaseAdapter {
    Context context;
    ArrayList<NhanVien> list;

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

        return dong;
    }
}
