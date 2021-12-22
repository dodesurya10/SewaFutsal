package com.dewadode.sewafutsal.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.dewadode.sewafutsal.R;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tvToday, tvMainSalam;
    String hariIni;
    Animation animTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button informasi = findViewById(R.id.btn_info_mobil);
        Button sewa = findViewById(R.id.btn_sewa);
        Button about = findViewById(R.id.btn_about);


        informasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DaftarLapanganActivity.class);
                startActivity(i);
            }
        });

        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(MainActivity.this, PenyewaActivity.class);
                startActivity(p);
            }
        });

            about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("About Sewa Boking Futsal");
                builder.setMessage(
                        "Sewa Booking Futsal adalah aplikasi untuk membantu player" +
                                " untuk melakukan booking sewa lapangan futsal" +
                                " disimpan ke dalam database\n\n" +
                                "Nama  : I Dewa Gede Suryadiantha Wedagama\n" +
                                "NIM     : 1905551138"
                );
                builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        tvToday = findViewById(R.id.tvDate);
        tvMainSalam = findViewById(R.id.tvMainSalam);
        animTv = AnimationUtils.loadAnimation(this, R.anim.anim_tv);
        tvMainSalam.startAnimation(animTv);

        Date dateNow = Calendar.getInstance().getTime();
        hariIni = (String) DateFormat.format("EEEE", dateNow);
        if (hariIni.equalsIgnoreCase("sunday")) {
            hariIni = "Minggu";
        } else if (hariIni.equalsIgnoreCase("monday")) {
            hariIni = "Senin";
        } else if (hariIni.equalsIgnoreCase("tuesday")) {
            hariIni = "Selasa";
        } else if (hariIni.equalsIgnoreCase("wednesday")) {
            hariIni = "Rabu";
        } else if (hariIni.equalsIgnoreCase("thursday")) {
            hariIni = "Kamis";
        } else if (hariIni.equalsIgnoreCase("friday")) {
            hariIni = "Jumat";
        } else if (hariIni.equalsIgnoreCase("saturday")) {
            hariIni = "Sabtu";
        }

        getToday();
        setSalam();
    }

    private void setSalam() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            tvMainSalam.setText("Selamat Pagi" + " " + "Player!");
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            tvMainSalam.setText("Selamat Siang" + " " + "Player!");
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            tvMainSalam.setText("Selamat Sore" + " " + "Player!");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            tvMainSalam.setText("Selamat Malam" + " " + "Player!");
        }
    }

    private void getToday() {
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) DateFormat.format("d", date);
        String monthNumber = (String) DateFormat.format("M", date);
        String year = (String) DateFormat.format("yyyy", date);

        int month = Integer.parseInt(monthNumber);
        String bulan = null;
        if (month == 1) {
            bulan = "Januari";
        } else if (month == 2) {
            bulan = "Februari";
        } else if (month == 3) {
            bulan = "Maret";
        } else if (month == 4) {
            bulan = "April";
        } else if (month == 5) {
            bulan = "Mei";
        } else if (month == 6) {
            bulan = "Juni";
        } else if (month == 7) {
            bulan = "Juli";
        } else if (month == 8) {
            bulan = "Agustus";
        } else if (month == 9) {
            bulan = "September";
        } else if (month == 10) {
            bulan = "Oktober";
        } else if (month == 11) {
            bulan = "November";
        } else if (month == 12) {
            bulan = "Desember";
        }
        String formatFix = hariIni + ", " + tanggal + " " + bulan + " " + year;
        tvToday.setText(formatFix);
    }

}
