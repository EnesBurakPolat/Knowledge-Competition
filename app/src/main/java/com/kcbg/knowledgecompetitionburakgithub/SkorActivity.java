package com.kcbg.knowledgecompetitionburakgithub;

import android.content.Context;  //android uygulamasıyla ilgili genel bilgilere erişmek için kullanılan sınıf
import android.content.Intent;  //diğer uygulamalara geçiş yapmak veya farklı bileşenler arasında iletişim kurmak için kullanılan sınıf
import android.content.SharedPreferences;  //küçük veri parçalarını kalıcı olarak saklamak için kullanılan sınıf
import android.media.MediaPlayer;  //ses oynatma işlemleri için kullanılan sınıf
import android.os.Bundle;  //farklı bileşenler arasında veri taşımak için kullanılan bir veri yapısıdır
import android.view.View;  //kullanıcı arayüzü elemanlarının temel sınıfıdır
import android.view.WindowManager;  //android uygulama penceresinin görüntülenme şeklini kontrol etmek için kullanılan sınıf
import android.widget.Button;  //kullanıcı arayüzünde bir düğmeyi temsil eden sınıf
import android.widget.TextView;  //kullanıcı arayüzünde metinleri temsil eden sınıf

import androidx.appcompat.app.AppCompatActivity;  // Android uygulamaları için temel etkinlik sınıfı


public class SkorActivity extends AppCompatActivity {

    private Button geriButton;

    private MediaPlayer ses_skor;//SES
    private MediaPlayer ses_tiklama;//SES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //Tam ekran modunu etkinleştir
        View decorView = getWindow().getDecorView(); //NAVİGASYON TUŞLARINI GİZLER
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_skor);

        ses_skor = MediaPlayer.create(this, R.raw.skor_ses);//SES
        ses_skor.start();//SES
        ses_skor.setVolume(0.1f, 0.1f);//SES

        ses_tiklama = MediaPlayer.create(this, R.raw.buton_ses);//SES
        ses_tiklama.setVolume(0.1f, 0.1f);//TIKLAMA SESİ SES DÜZEYİ

        TextView skorMuzik = findViewById(R.id.skorMuzik);
        TextView skorSanat = findViewById(R.id.skorSanat);
        TextView skorBilim = findViewById(R.id.skorBilim);
        TextView skorMatematik = findViewById(R.id.skorMatematik);
        TextView skorSpor = findViewById(R.id.skorSpor);
        TextView skorVideoOyunlari = findViewById(R.id.skorVideoOyunlari);
        TextView skorBiyoloji = findViewById(R.id.skorBiyoloji);
        TextView skorCografya = findViewById(R.id.skorCografya);
        TextView skorTarih = findViewById(R.id.skorTarih);
        TextView skorUlkeBulmaca = findViewById(R.id.skorUlkeBulmaca);

        //sharedPreferences nesnesini oluşturur
        SharedPreferences sharedPreferences = getSharedPreferences("Skor", Context.MODE_PRIVATE);

        //müzik skorunu alın ve gösterir
        int muzikSkor = sharedPreferences.getInt("muzik_skor", 0);
        skorMuzik.setText("Müzik: " + muzikSkor);


        int sanatSkor = sharedPreferences.getInt("sanat_skor", 0);
        skorSanat.setText("Sanat: " + sanatSkor);

        int bilimSkor = sharedPreferences.getInt("bilim_skor", 0);
        skorBilim.setText("Bilim: " + bilimSkor);

        int matematikSkor = sharedPreferences.getInt("matematik_skor", 0);
        skorMatematik.setText("Matematik: " + matematikSkor);

        int sporSkor = sharedPreferences.getInt("spor_skor", 0);
        skorSpor.setText("Spor: " + sporSkor);

        int videoOyunlariSkor = sharedPreferences.getInt("video_oyunlari_skor", 0);
        skorVideoOyunlari.setText("Video Oyunları: " + videoOyunlariSkor);

        int biyolojiSkor = sharedPreferences.getInt("biyoloji_skor", 0);
        skorBiyoloji.setText("Biyoloji: " + biyolojiSkor);

        int cografyaSkor = sharedPreferences.getInt("cografya_skor", 0);
        skorCografya.setText("Coğrafya: " + cografyaSkor);

        int tarihSkor = sharedPreferences.getInt("tarih_skor", 0);
        skorTarih.setText("Tarih: " + tarihSkor);

        int ulkeBulmacaSkor = sharedPreferences.getInt("ulke_bulmaca_skor", 0);
        skorUlkeBulmaca.setText("Ülke Bulmaca: " + ulkeBulmacaSkor);

        //Geri Butonu
        geriButton = findViewById(R.id.geriButton);
        geriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ses_tiklama.start();//SES
                ses_skor.pause();//SES
                Intent intent = new Intent(SkorActivity.this, KategoriActivity.class);//EV butonu
                finish();//KAPATIR!!!!!
                startActivity(intent); //Ev düğmesine basıldığında MainActivity'e döner
            }
        });

        //sıfırla düğmesini bulur ve tıklama olayını tanımlar
        Button sifirlaButton = findViewById(R.id.sifirlaButton);
        sifirlaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ses_tiklama.start();//SES
                //Skorları sıfırlar
                skorMuzik.setText("Müzik: 0");
                skorSanat.setText("Sanat: 0");
                skorBilim.setText("Bilim: 0");
                skorMatematik.setText("Matematik: 0");
                skorSpor.setText("Spor: 0");
                skorVideoOyunlari.setText("Video Oyunları: 0");
                skorBiyoloji.setText("Biyoloji: 0");
                skorCografya.setText("Coğrafya: 0");
                skorTarih.setText("Tarih: 0");
                skorUlkeBulmaca.setText("Ülke Bulmaca: 0");

                //SharedPreferences'e skorları kaydeder
                SharedPreferences sharedPreferences = getSharedPreferences("Skor", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("muzik_skor", 0);
                editor.putInt("sanat_skor", 0);
                editor.putInt("bilim_skor", 0);

                editor.putInt("matematik_skor", 0);
                editor.putInt("spor_skor", 0);
                editor.putInt("video_oyunlari_skor", 0);
                editor.putInt("biyoloji_skor", 0);
                editor.putInt("cografya_skor", 0);
                editor.putInt("tarih_skor", 0);
                editor.putInt("ulke_bulmaca_skor", 0);
                editor.apply();
            }
        });
    }
    @Override
    protected void onDestroy() {//SES
        super.onDestroy();
        ses_skor.release();
        ses_tiklama.release();
    }
}