package com.dewadode.sewafutsal.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dewadode.sewafutsal.R;
import com.dewadode.sewafutsal.helper.DataHelper;

public class DetailPenyewaActivity extends AppCompatActivity {

    String sNama, sAlamat, sHP, sMerk, sHarga, getNama;
    int iLama, iPromo, iTotal;
    double dTotal;
    Button btnEdit;

    protected Cursor cursor;
    DataHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyewa);

        getNama = getIntent().getStringExtra("nama");

        Toast.makeText(DetailPenyewaActivity.this, getNama.toString(), Toast.LENGTH_SHORT).show();

        dbHelper = new DataHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from penyewa, lapangan, sewa where penyewa.nama = sewa.nama AND lapangan.merk = sewa.merk AND penyewa.nama = '" + getNama.toString() + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            sNama = cursor.getString(0);
            sAlamat = cursor.getString(1);
            sHP = cursor.getString(2);
            sMerk = cursor.getString(3);
            sHarga = cursor.getString(4);
            iPromo = cursor.getInt(7);
            iLama = cursor.getInt(8);
            dTotal = cursor.getDouble(9);
        }

        final EditText etNama = findViewById(R.id.editNama);
        final EditText etAlamat = findViewById(R.id.editAlamat);
        final EditText etHP = findViewById(R.id.editTelp);

        TextView tvMerk = findViewById(R.id.HMerk);
        TextView tvHarga = findViewById(R.id.HHarga);

        TextView tvLama = findViewById(R.id.HLamaSewa);
        TextView tvPromo = findViewById(R.id.HPromo);
        TextView tvTotal = findViewById(R.id.HTotal);

        btnEdit = findViewById(R.id.btnEdit);

        etNama.setText("     " + sNama);
        etAlamat.setText("     " + sAlamat);
        etHP.setText("     " + sHP);

        tvMerk.setText("     " + sMerk);
        tvHarga.setText("     Rp. " + sHarga);

        tvLama.setText("     " + iLama + " jam");
        tvPromo.setText("     " + iPromo + "%");
        iTotal = (int) dTotal;
        tvTotal.setText("     Rp. " + iTotal);

        setupToolbar();

        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sNama = etNama.getText().toString();
                sAlamat = etAlamat.getText().toString();
                sHP = etHP.getText().toString();
                if (sNama.isEmpty() || sAlamat.isEmpty() || sHP.isEmpty()) {
                    Toast.makeText(DetailPenyewaActivity.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPenyewaActivity.this);
//                builder.setIcon(R.drawable.warning);
                builder.setTitle("Edit");
                builder.setMessage(
                        "Apakah Anda Sudah Yakin Dengan Perubahan Data Anda ?\n\n"+
                                "Nama : \n" + sNama + "\n\n"+
                                "Alamat : \n" + sAlamat + "\n\n"+
                                "No HP : \n" + sHP + "\n\n"
                );

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() { //method button positive desicion
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(DetailPenyewaActivity.this, "Berhasil diubah", Toast.LENGTH_SHORT).show();
                        SQLiteDatabase dbH = dbHelper.getWritableDatabase();
                        dbH.execSQL("UPDATE penyewa SET nama = '" +  sNama + "' , alamat = '" + sAlamat + "' , no_hp = '" + sHP + "' WHERE nama = '" + getIntent().getStringExtra("nama") + "'");
                        dbH.execSQL("UPDATE sewa SET nama = " +  sNama + " WHERE nama = " + getIntent().getStringExtra("nama"));
//                        DetailPenyewaActivity.m.RefreshList();
                        finish();
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() { //method button negative desicion
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create(); //method get alert dan create alert
                alertDialog.show(); //to show alert
            }
        });

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbDetailPenyewa);
        toolbar.setTitle("Detail PenyewaActivity");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}