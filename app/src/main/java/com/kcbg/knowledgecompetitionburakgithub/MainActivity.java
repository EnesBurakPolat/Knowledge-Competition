package com.kcbg.knowledgecompetitionburakgithub;//varsayılan

import androidx.appcompat.app.AppCompatActivity;//varsayılan

import android.media.MediaPlayer;//ses
import android.os.Bundle;//varsayılan

import android.content.Intent;  //Intent sınıfını kullanabilmek için içe aktarılır
import android.os.Handler;  //Handler sınıfını kullanabilmek için içe aktarılır
import android.view.View;  //View sınıfını kullanabilmek için içe aktarılır
import android.view.WindowManager;  //WindowManager sınıfını kullanabilmek için içe aktarılır
import android.widget.Button;  //Button sınıfını kullanabilmek için içe aktarılır
import android.widget.Toast;  //Toast sınıfını kullanabilmek için içe aktarılır

public class MainActivity extends AppCompatActivity {//varsayılan

    private Button buttonBasla; //button değişkenleri oluşturdum
    private Button buttonKapat; //button değişkenleri oluşturdum

    private MediaPlayer ses_basla;//SES
    private MediaPlayer ses_button_basla;//SES
    private MediaPlayer ses_button_kapat;//SES

    @Override//varsayılan
    protected void onCreate(Bundle savedInstanceState) {//varsayılan
        super.onCreate(savedInstanceState);//varsayılan

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//tam ekran modunu etkinleştir
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //tam ekran modunu etkinleştir
        View decorView = getWindow().getDecorView(); //NAVİGASYON TUŞLARINI GİZLER
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;//NAVİGASYON TUŞLARINI GİZLER
        decorView.setSystemUiVisibility(uiOptions);//NAVİGASYON TUŞLARINI GİZLER

        setContentView(R.layout.activity_main);//varsayılan

        buttonBasla = findViewById(R.id.basla); //buttonid tanımladım
        buttonKapat = findViewById(R.id.kapat); //buttonid tanımladım

        ses_basla = MediaPlayer.create(this, R.raw.baslama_ses); //R.raw.baslama_ses ile ses_basla ses ekledim
        ses_button_basla = MediaPlayer.create(this, R.raw.buton_ses);//R.raw.buton_basla_ses ile ses_basla ses ekledim
        ses_button_kapat = MediaPlayer.create(this, R.raw.buton_kapat_ses);//R.raw.buton_kapat_ses ile ses_basla ses ekledim

        ses_basla.start();//ses_basla sesi başlar
        ses_basla.setVolume(0.5f, 0.5f);//ses_basla ses volume ayarları 0.5 varsayılan
        ses_button_basla.setVolume(0.1f, 0.1f);//ses_button_basla sesini 0.1 yaparak azalttım
        ses_button_kapat.setVolume(0.1f, 0.1f);//ses_button_kapat sesini 0.1 yaparak azalttım

        buttonBasla.setOnClickListener(new View.OnClickListener() { //buttonBasla için setOnClickListener oluşturdum
            @Override
            public void onClick(View v) { //buttonBasla tıklanınca yapılacaklar
                ses_button_basla.start();//ses_button_basla sesi başlar
                //başla butonuna tıklandığında kuralların yazacağı yeni bir layout ekranına geçmek için bir Intent başlatır
                Intent intent = new Intent(MainActivity.this, KurallarActivity.class);
                ses_basla.pause();//ses_basla sesi durur
                startActivity(intent); //MainActivity'den KurallarActivity class'ına geçiş yapar
            }
        });

        buttonKapat.setOnClickListener(new View.OnClickListener() { //buttonKapat için setOnClickListener oluşturdum
            @Override
            public void onClick(View v) { //buttonKapat basılınca yapılacaklar
                //finish();
                ses_button_kapat.start(); // SES

                Toast.makeText(getApplicationContext(), "Uygulama Kapanıyor", Toast.LENGTH_SHORT).show();//Toast mesajı ile kullanıcıya haber verilir

                Handler handler = new Handler(); //Handler ile 2 saniye sonra yapılacak işlemler
                handler.postDelayed(new Runnable() { //delay ile kapat tuşuna bastıktan 2 saniye sonra işlemler gerçekleştirilir
                    @Override
                    public void run() {
                        moveTaskToBack(true); //uygulamayı arka plana atar
                        android.os.Process.killProcess(android.os.Process.myPid()); //uygulamayı sonlandırır
                        System.exit(1); //sistemden çıkar
                    }
                }, 2000); //2 saniye (2000 milisaniye) bekletir burada 2000 i 3000 yaparsak 3 saniye olur istediğimiz gibi arttırabiliriz
            }
        });

    }

    @Override
    protected void onDestroy() {//ses için gereken onDestroy metodu oluşturdum
        super.onDestroy();
        //sesler release edilir
        ses_basla.release();
        ses_button_basla.release();
        ses_button_kapat.release();
    }

}