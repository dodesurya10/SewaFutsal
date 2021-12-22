package com.dewadode.sewafutsal.activity;

import androidx.appcompat.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dewadode.sewafutsal.R;
import com.dewadode.sewafutsal.helper.DataHelper;

import java.util.List;

public class SewaLapanganActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nama, alamat, no_hp, lama;
    private SeekBar seekbarSkill;
    private String SkillFutsal;
    private TextView SkillMain;
    RadioGroup promo;
    RadioButton weekday, weekend;
    Button selesai;

    String sNama, sAlamat, sNo, sMerk, sLama;
    double dPromo;
    int iLama, iPromo, iHarga;
    double dTotal;

    private Spinner spinner;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa);

        dbHelper = new DataHelper(this);

        spinner = findViewById(R.id.spinner);
        selesai = findViewById(R.id.selesaiHitung);
        nama = findViewById(R.id.eTNama);
        alamat = findViewById(R.id.eTAlamat);
        no_hp = findViewById(R.id.eTHP);
        promo = findViewById(R.id.promoGroup);
        weekday = findViewById(R.id.rbWeekDay);
        weekend = findViewById(R.id.rbWeekEnd);
        lama = findViewById(R.id.eTLamaSewa);
        seekbarSkill = findViewById(R.id.seekBar);
        SkillMain = findViewById(R.id.skillkamu);

        spinner.setOnItemSelectedListener(this);

        loadSpinnerData();

        //seekbar
        seekbarSkill.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                SkillFutsal = String.valueOf(i);
                SkillMain.setText(SkillFutsal);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sNama = nama.getText().toString();
                sAlamat = alamat.getText().toString();
                sNo = no_hp.getText().toString();
                sLama = lama.getText().toString();
                if (sNama.isEmpty() || sAlamat.isEmpty() || sNo.isEmpty() || sLama.isEmpty()) {
                    Toast.makeText(SewaLapanganActivity.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (weekday.isChecked()) {
                    dPromo = 0.10;
                } else if (weekend.isChecked()) {
                    dPromo = 0.25;
                }

                if (sMerk.equals("SINTETIS A")) {
                    iHarga = 120000;
                } else if (sMerk.equals("SINTETIS B")) {
                    iHarga = 100000;
                } else if (sMerk.equals("VINYL")) {
                    iHarga = 125000;
                }

                iLama = Integer.parseInt(sLama);
                iPromo = (int) (dPromo * 100);
                dTotal = (iHarga * iLama) - (iHarga * iLama * dPromo);


                AlertDialog.Builder builder = new AlertDialog.Builder(SewaLapanganActivity.this);
//                builder.setIcon(R.drawable.warning);
                builder.setTitle("Submit");
                builder.setMessage(
                        "Apakah Anda Sudah Yakin Dengan Data Anda ?\n\n"+
                                "Nama : \n" + sNama + "\n\n"+
                                "Alamat : \n" + sAlamat + "\n\n"+
                                "No HP : \n" + sNo + "\n\n"
                );

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() { //method button positive desicion
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase dbH = dbHelper.getWritableDatabase();
                        dbH.execSQL("INSERT INTO penyewa (nama, alamat, no_hp) VALUES ('" +
                                sNama + "','" +
                                sAlamat + "','" +
                                sNo + "');");
                        dbH.execSQL("INSERT INTO sewa (merk, nama, promo, lama, total) VALUES ('" +
                                sMerk + "','" +
                                sNama + "','" +
                                iPromo + "','" +
                                iLama + "','" +
                                dTotal + "');");
                        PenyewaActivity.m.RefreshList();
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

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbSewaMobl);
        toolbar.setTitle("Sewa Lapangan");
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

    private void loadSpinnerData() {
        DataHelper db = new DataHelper(getApplicationContext());
        List<String> categories = db.getAllCategories();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sMerk = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}