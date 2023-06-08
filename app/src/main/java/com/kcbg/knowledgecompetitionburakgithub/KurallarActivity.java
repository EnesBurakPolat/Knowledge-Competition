package com.kcbg.knowledgecompetitionburakgithub;//varsayılan

import android.media.MediaPlayer;//ses
import android.os.Bundle;//varsayılan

import androidx.appcompat.app.AppCompatActivity;//varsayılan

import android.content.Intent;  //diğer uygulamalara geçiş yapmak veya farklı bileşenler arasında iletişim kurmak için kullanılan sınıf
import android.view.View;  //kullanıcı arayüzü elemanlarının temel sınıfıdır
import android.view.WindowManager;  //android uygulama penceresinin görüntülenme şeklini kontrol etmek için kullanılan sınıf
import android.widget.Button;  //kullanıcı arayüzünde bir düğmeyi temsil eden sınıf

public class KurallarActivity extends AppCompatActivity {//varsayılan

    private Button buttonHazirim; //button değişkenleri oluşturdum
    private Button buttonHayir; //button değişkenleri oluşturdum

    private MediaPlayer ses_kurallar;//SES
    private MediaPlayer ses_tiklama;//SES

    @Override//varsayılan
    protected void onCreate(Bundle savedInstanceState) {//varsayılan
        super.onCreate(savedInstanceState);//varsayılan

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //tam ekran modunu etkinleştir
        View decorView = getWindow().getDecorView(); //NAVİGASYON TUŞLARINI GİZLER
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_kurallar);//varsayılan

        buttonHazirim = findViewById(R.id.hazir); //buttonid tanımladım
        buttonHayir = findViewById(R.id.hazir_degilim); //buttonid tanımladım

        ses_kurallar = MediaPlayer.create(this, R.raw.kurallar_ses);//SES
        ses_kurallar.start();//SES
        ses_kurallar.setVolume(0.1f, 0.1f);//SES

        ses_tiklama = MediaPlayer.create(this, R.raw.buton_ses);//SES
        ses_tiklama.setVolume(0.1f, 0.1f);//TIKLAMA SESİ SES DÜZEYİ

        buttonHazirim.setOnClickListener(new View.OnClickListener() { //button onclicklistener oluşturdum
            @Override
            public void onClick(View v) {
                ses_tiklama.start();//SES
                //"Hazırım" butonuna tıklandığında KategoriActivity'ye geçmek için bir Intent başlatır
                Intent intent = new Intent(KurallarActivity.this, KategoriActivity.class);
                ses_kurallar.pause();//SES
                finish();//KAPATIR!!!!!
                startActivity(intent);
            }
        });

        buttonHayir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ses_tiklama.start();//SES
                //"Hayır" butonuna tıklandığında geri gitmek için finish() metodunu çağırır
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {//SES
        super.onDestroy();
        ses_kurallar.release();
        ses_tiklama.release();
    }
}