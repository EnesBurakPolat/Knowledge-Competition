package com.kcbg.knowledgecompetitionburakgithub;

import android.content.Intent;  //diğer uygulamalara geçiş yapmak veya farklı bileşenler arasında iletişim kurmak için kullanılan sınıf
import android.media.MediaPlayer;  //ses oynatma işlemleri için kullanılan sınıf
import android.os.Bundle;  //farklı bileşenler arasında veri taşımak için kullanılan bir veri yapısıdır
import android.view.View;  //kullanıcı arayüzü elemanlarının temel sınıfıdır
import android.view.ViewGroup;  //kullanıcı arayüzündeki düzen öğelerini gruplamak için kullanılan sınıf
import android.view.WindowManager;  //android uygulama penceresinin görüntülenme şeklini kontrol etmek için kullanılan sınıf
import android.widget.AdapterView;  //liste gibi bir görüntü öğesine yapılan seçimleri dinlemek için kullanılan sınıf
import android.widget.ArrayAdapter;  //dizi veya veritabanı kaynaklı verileri liste halinde görüntülemek için kullanılan sınıf
import android.widget.Button;  //kullanıcı arayüzünde bir düğmeyi temsil eden sınıf
import android.widget.ImageView;  //kullanıcı arayüzünde bir resmi temsil eden sınıf
import android.widget.LinearLayout;  //diğer görüntü öğelerini yatay veya dikey bir düzende gruplamak için kullanılan sınıf
import android.widget.ListView;  //liste görüntüsünü sağlayan ve kaydırılabilir öğeleri içeren bir kullanıcı arayüzü sınıfı
import android.widget.TextView;  //kullanıcı arayüzünde metinleri temsil eden sınıf


import androidx.appcompat.app.AppCompatActivity;

public class KategoriActivity extends AppCompatActivity {

     private String[] kategoriListesi = {"Müzik", //kategoriListesi adında String tipinde bir array oluşturdum
             "Sanat", "Bilim",
             "Matematik", "Spor",
             "Video Oyunları", "Biyoloji",
             "Coğrafya", "Tarih", "Ülke Bulmaca"};
     private int[] kategoriResimleri = {R.drawable.resim1, //kategoriResimleri adında integer tipinde bir array oluşturdum
             R.drawable.resim2, R.drawable.resim3,
             R.drawable.resim4, R.drawable.resim5,
             R.drawable.resim6, R.drawable.resim7,
             R.drawable.resim8, R.drawable.resim9, R.drawable.resim10};

     private int[] kategoriSkorlari = new int[kategoriListesi.length]; //her kategori için skor tutacak diziyi oluştrudum
     private Button skorButton; //skorButton'u değişkeni oluşturdum

     private Button homeButton;//homeButton'u değişkenini oluştrudum

     private MediaPlayer ses_kategori;//ses_kategori mediaplayer değişkeni
     private MediaPlayer ses_tiklama;//ses_tiklama mediaplayer değişkeni

     @Override
     protected void onCreate(Bundle savedInstanceState) {//varsayılan
          super.onCreate(savedInstanceState);//varsayılan

          getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                  WindowManager.LayoutParams.FLAG_FULLSCREEN); //tam ekran modunu etkinleştir
          View decorView = getWindow().getDecorView(); //NAVİGASYON TUŞLARINI GİZLER
          int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
          decorView.setSystemUiVisibility(uiOptions);

          setContentView(R.layout.activity_kategori);//varsayılan

          homeButton = findViewById(R.id.ev_butonu);//homeButton koda tanıtılıyor

          ses_kategori = MediaPlayer.create(this, R.raw.kategori_ses);//kategori sesi ayarlandı
          ses_kategori.start();//ses_kategori başlatılıyor
          ses_kategori.setVolume(0.1f, 0.1f);//ses_kategori ses düzeyi belirlendi

          ses_tiklama = MediaPlayer.create(this, R.raw.buton_ses);//tıklama sesi ayarlandı
          ses_tiklama.setVolume(0.1f, 0.1f);//TIKLAMA SESİ SES DÜZEYİ

          homeButton.setOnClickListener(new View.OnClickListener() {//EV butonuna basınca MainActivity'e geri dönecek ve KategoriActivity kapatılacak
               @Override
               public void onClick(View v) {//EV butonu
                    ses_tiklama.start();//tıklama sesi
                    Intent intent = new Intent(KategoriActivity.this, MainActivity.class);//EV butonu
                    ses_kategori.pause();//kategori sesi durduruluyor
                    finish();//KAPATIR!!!!!
                    startActivity(intent); //ev düğmesine basıldığında MainActivity'e döner

               }
          });

          ListView listView = findViewById(R.id.listView);
          KategoriAdapter adapter = new KategoriAdapter();
          listView.setAdapter(adapter);
          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String secilenKategori = kategoriListesi[position];
                    Intent intent = new Intent(KategoriActivity.this, SoruActivity.class);
                    intent.putExtra("kategori", secilenKategori);
                    ses_tiklama.start();//SES
                    finish();//KAPATIR!!!!!
                    startActivity(intent);
               }
          });

          skorButton = findViewById(R.id.skorButton); //skor butonu koda tanıtıldı
          skorButton.setOnClickListener(new View.OnClickListener() { //skor butonu setOnClickListener'ı ayarlandı
               @Override
               public void onClick(View v) { //skor butonuna basınca tıklama sesi ve showSkorlarPenceresi fonksiyonu çalışacak
                    ses_tiklama.start();//SES
                    showSkorlarPenceresi();
               }
          });

          //başlangıçta tüm kategori skorlarını 0 olarak ayarlandı
          for (int i = 0; i < kategoriSkorlari.length; i++) {
               kategoriSkorlari[i] = 0;
          }
     }

     private void showSkorlarPenceresi() { //skorlar penceresini göster
          Intent intent = new Intent(KategoriActivity.this, SkorActivity.class); //kategoriden skor class'ına gider
          ses_kategori.pause();//kategori için çalan arka plan sesi durur
          intent.putExtra("kategoriSkorlari", kategoriSkorlari); //bu kod ile aktiviteden bir diğerine veri iletir
          finish();//KAPATIR!!!!!
          startActivity(intent); //skor aktivitesi başlar
     }

     private class KategoriAdapter extends ArrayAdapter<String> { //bu kod kategori listesini ekranda göstermek için özel bir yapı oluşturur
                                                                 //her bir öğe için bir düzen oluşturulur ve resim ile metin görüntülenir
                                                                 //liste öğeleri arasında boşluk bırakılır

          KategoriAdapter() {
               //kategoriAdapter sınıfının kurucu metodudur
               //üst sınıfın kurucu metodunu çağırarak ArrayAdapter'ı başlatır
               super(KategoriActivity.this, R.layout.activity_kategori_list_item, kategoriListesi);
          }

          @Override
          public View getView(int position, View convertView, ViewGroup parent) {
               if (convertView == null) {
                    //convertView yeniden kullanılabilir bir görünüm yoksa inflate edilir
                    convertView = getLayoutInflater().inflate(R.layout.activity_kategori_list_item, parent, false);
               }

               LinearLayout itemLayout = convertView.findViewById(R.id.itemLayout);//itemLayout, liste öğesi düzeninin ana layout'u olarak belirlenir
               ImageView imageView = convertView.findViewById(R.id.imageView);//imageView, kategori resminin görüntüsünü temsil eder
               TextView textView = convertView.findViewById(R.id.textView);//textView, kategori adının metin görüntüsünü temsil eder

               imageView.setImageResource(kategoriResimleri[position]);//imageView kategoriResimleri dizisindeki ilgili resim kaynağıyla ayarlanır
               textView.setText(kategoriListesi[position]);//textView kategoriListesi dizisindeki ilgili metinle ayarlanır

               //itemLayout'un düzen parametreleri belirlenir
               LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

               layoutParams.setMargins(0, 10, 0, 10);//itemLayout'un sol ve sağ kenarlarına 10 birim dolgu eklenir
               itemLayout.setLayoutParams(layoutParams);//itemLayout'un düzen parametreleri layoutParams değişkeniyle ayarlanır

               return convertView;
          }
     }

     @Override
     protected void onDestroy() {//SES
          super.onDestroy();
          ses_kategori.release();
          ses_tiklama.release();
     }
     @Override
     protected void onResume() {
          super.onResume();
          ses_kategori.start(); //kategoriActivity'e dönüldüğünde müziği başlatır
     }

     @Override
     protected void onPause() {
          super.onPause();
          ses_kategori.pause(); //kategoriActivity'den ayrılırken müziği durdurur
     }
}