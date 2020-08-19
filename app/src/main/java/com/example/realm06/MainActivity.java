package com.example.realm06;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Model.DanhMuc;
import com.example.Model.SanPham;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    EditText edtMaSanPham, edtTenSanPham, edtDonGia;
    Spinner spDanhMuc;
    ListView lvSanPham;
    Realm realm;
    ArrayList<DanhMuc> danhMucs = new ArrayList<>();
    ArrayAdapter<DanhMuc> danhMucAdapter;
    ArrayAdapter<SanPham> sanPhamAdapter;
    DanhMuc selectedDanhMuc;
    Button btnThem, btnSua, btnXoa;
    int vt = -1;
    private TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        addData();
        //hienThiSanPham();
    }

    private void addData() {
        RealmResults<DanhMuc> d = realm.where(DanhMuc.class).findAll();
        if (d.size() > 0) {
            RealmResults<DanhMuc> results = realm.where(DanhMuc.class).findAll();
            danhMucAdapter.addAll(results);
            return;
        }

        danhMucs.add(new DanhMuc("DM001", "Hàng hóa chất"));
        danhMucs.add(new DanhMuc("DM002", "Hàng thực phẩm"));
        danhMucs.add(new DanhMuc("DM003", "Hàng điện tử"));
        realm.beginTransaction();
        for (DanhMuc dm : danhMucs) {
            DanhMuc x = realm.createObject(DanhMuc.class);
            x.setMaDanhMuc(dm.getMaDanhMuc());
            x.setTenDanhMuc(dm.getTenDanhMuc());
        }
        realm.commitTransaction();

        RealmResults<DanhMuc> results = realm.where(DanhMuc.class).findAll();
        danhMucAdapter.addAll(results);
    }

    private void addEvents() {
        spDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDanhMuc = danhMucAdapter.getItem(i);
                hienThiSanPham(selectedDanhMuc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThem();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLySua();
            }
        });

        lvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vt = i;
                if (selectedDanhMuc == null) {
                    Toast.makeText(MainActivity.this, "Mời chọn Danh mục", Toast.LENGTH_SHORT).show();
                    return;
                }

                RealmResults<DanhMuc> danhMucs = realm.where(DanhMuc.class).equalTo("maDanhMuc", selectedDanhMuc.getMaDanhMuc()).findAll();
                for (DanhMuc dm : danhMucs) {
                    edtMaSanPham.setText(dm.getSanPhams().get(i).getMaSanPham());
                    edtTenSanPham.setText(dm.getSanPhams().get(i).getTenSanPham());
                    edtDonGia.setText(dm.getSanPhams().get(i).getDonGia() + "");
                }
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXoa();
            }
        });
    }

    private void xuLyXoa() {
        if (selectedDanhMuc == null) {
            Toast.makeText(MainActivity.this, "Mời chọn Danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtMaSanPham.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Chưa nhập mã Sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!kiemTraKey(edtMaSanPham.getText().toString())) {
            edtMaSanPham.requestFocus();
            edtMaSanPham.selectAll();
            Toast.makeText(MainActivity.this, "Sản phẩm không tồn tại\nMời nhập lại mã", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vt < 0) {
            Toast.makeText(MainActivity.this, "Mời chọn Sản phẩm cần sửa", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtMaSanPham.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Chưa nhập mã Sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        realm.beginTransaction();
        RealmResults<DanhMuc> danhMucs = realm.where(DanhMuc.class).equalTo("maDanhMuc", selectedDanhMuc.getMaDanhMuc()).findAll();
        for (DanhMuc dm : danhMucs) {
            dm.getSanPhams().remove(vt);
        }
        realm.commitTransaction();

        hienThiSanPham(selectedDanhMuc);
        lamMoi();
    }

    private void xuLySua() {
        if (selectedDanhMuc == null) {
            Toast.makeText(MainActivity.this, "Mời chọn Danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtMaSanPham.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Chưa nhập mã Sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!kiemTraKey(edtMaSanPham.getText().toString())) {
            edtMaSanPham.requestFocus();
            edtMaSanPham.selectAll();
            Toast.makeText(MainActivity.this, "Sản phẩm không tồn tại\nMời nhập lại mã", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vt < 0) {
            Toast.makeText(MainActivity.this, "Mời chọn Sản phẩm cần sửa", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtMaSanPham.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Chưa nhập mã Sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!kiemTraKey(edtMaSanPham.getText().toString())) {
            edtMaSanPham.requestFocus();
            edtMaSanPham.selectAll();
            Toast.makeText(MainActivity.this, "Sản phẩm không tồn tại\nMời nhập lại mã", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtTenSanPham.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Chưa nhập tên Sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtDonGia.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Chưa nhập giá bán", Toast.LENGTH_SHORT).show();
            return;
        }

        realm.beginTransaction();
        RealmResults<DanhMuc> danhMucs = realm.where(DanhMuc.class).equalTo("maDanhMuc", selectedDanhMuc.getMaDanhMuc()).findAll();
        for (DanhMuc dm : danhMucs) {
            dm.getSanPhams().get(vt).setTenSanPham(edtTenSanPham.getText().toString());
            dm.getSanPhams().get(vt).setDonGia(Integer.parseInt(edtDonGia.getText().toString()));
        }
        realm.commitTransaction();
        hienThiSanPham(selectedDanhMuc);
        lamMoi();
    }

    private void lamMoi() {
        edtMaSanPham.setText(null);
        edtTenSanPham.setText(null);
        edtDonGia.setText(null);
        edtMaSanPham.requestFocus();
    }

    private void xuLyThem() {
        if (edtMaSanPham.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Chưa nhập mã Sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        if (kiemTraKey(edtMaSanPham.getText().toString())) {
            edtMaSanPham.requestFocus();
            edtMaSanPham.selectAll();
            Toast.makeText(MainActivity.this, "Mã Sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtTenSanPham.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Chưa nhập tên Sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtDonGia.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Chưa nhập giá bán", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDanhMuc == null) {
            Toast.makeText(MainActivity.this, "Mời chọn Danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        realm.beginTransaction();
        RealmResults<DanhMuc> danhMucs = realm.where(DanhMuc.class).equalTo("maDanhMuc", selectedDanhMuc.getMaDanhMuc()).findAll();
        for (DanhMuc dm : danhMucs) {
            dm.getSanPhams().add(new SanPham(edtMaSanPham.getText().toString(),
                    edtTenSanPham.getText().toString(), Integer.parseInt(edtDonGia.getText().toString())));
        }
        realm.commitTransaction();
        hienThiSanPham(selectedDanhMuc);
        lamMoi();
    }

    private boolean kiemTraKey(String maSanPham) {
        RealmResults<DanhMuc> danhMucs = realm.where(DanhMuc.class).findAll();
        for (DanhMuc dm : danhMucs) {
            for (SanPham sp : dm.getSanPhams()) {
                if (!sp.getMaSanPham().startsWith(maSanPham)) {
                    continue;
                }

                return true;
            }
        }
        return false;
    }

    private void hienThiSanPham(DanhMuc selectedDanhMuc) {
        RealmResults<DanhMuc> danhMucs = realm.where(DanhMuc.class).equalTo("maDanhMuc", selectedDanhMuc.getMaDanhMuc()).findAll();
        sanPhamAdapter.clear();
        for (DanhMuc dm : danhMucs) {
            sanPhamAdapter.addAll(dm.getSanPhams());
        }
    }

    private void addControls() {
        edtMaSanPham = findViewById(R.id.edtMaSanPham);
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtDonGia = findViewById(R.id.edtDonGia);
        spDanhMuc = findViewById(R.id.spDanhMuc);
        danhMucAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_checked);
        spDanhMuc.setAdapter(danhMucAdapter);
        Realm.init(MainActivity.this);
        realm = Realm.getDefaultInstance();
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        lvSanPham = findViewById(R.id.lvSanPham);
        sanPhamAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
        lvSanPham.setAdapter(sanPhamAdapter);
    }
}