package com.kcbg.knowledgecompetitionburakgithub;

import android.content.Context;  //android uygulamasıyla ilgili genel bilgilere erişmek için kullanılan sınıf
import android.content.DialogInterface;  //kullanıcıyla etkileşimli diyalog kutuları oluşturmak için kullanılan sınıf
import android.content.Intent;  //diğer uygulamalara geçiş yapmak veya farklı bileşenler arasında iletişim kurmak için kullanılan sınıf
import android.content.SharedPreferences;  //küçük veri parçalarını kalıcı olarak saklamak için kullanılan sınıf
import android.media.MediaPlayer;  //ses oynatma işlemleri için kullanılan sınıf
import android.os.Bundle;  //farklı bileşenler arasında veri taşımak için kullanılan bir veri yapısıdır
import android.os.Handler;  //belirli bir süre gecikmeyle çalışacak kod blokları oluşturmak için kullanılan sınıf
import android.view.View;  //kullanıcı arayüzü elemanlarının temel sınıfıdır
import android.view.Window;  //uygulama penceresinin davranışını kontrol etmek için kullanılan sınıf
import android.view.WindowManager;  //android uygulama penceresinin görüntülenme şeklini kontrol etmek için kullanılan sınıf
import android.widget.Button;  //kullanıcı arayüzünde bir düğmeyi temsil eden sınıf
import android.widget.ImageView;  //kullanıcı arayüzünde bir resmi temsil eden sınıf
import android.widget.TextView;  //kullanıcı arayüzünde metinleri temsil eden sınıf

import androidx.appcompat.app.AlertDialog;  //basit bir diyalog kutusu oluşturmak için kullanılan sınıf
import androidx.appcompat.app.AppCompatActivity;  //android uygulamaları için temel etkinlik sınıfı

import android.os.CountDownTimer;  //geri sayım için kullanılan sınıf

public class SoruActivity extends AppCompatActivity {//varsayılan

    private String kategori;
    private int soruIndex;
    private int skor;

    private TextView textViewKategori;
    private TextView textViewSoru;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    private Button buttonBitir;
    private Button buttonGeriDon;

    private String[] sorular;
    private String[][] cevaplar;
    private String[] dogruCevaplar;

    private ImageView resimImageView;//RESİMLİ SORULAR

    private int[] resimler; //resim kaynaklarını tutacak diziyi oluşturdum

    private int sure;//SÜRE
    private CountDownTimer countdownTimer;//SÜRE
    private TextView textViewSure;

    private MediaPlayer soru_arkaplan_1;//SES

    private MediaPlayer soru_isaretleme_sesi;//SES

    private MediaPlayer soru_bitti_sesi;//SES

    private MediaPlayer soru_bitti_basarili_sesi;//SES


    @Override
    protected void onCreate(Bundle savedInstanceState) {//varsayılan
        super.onCreate(savedInstanceState);//varsayılan

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //Tam ekran modunu etkinleştir
        View decorView = getWindow().getDecorView(); //NAVİGASYON TUŞLARINI GİZLER
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_soru);//varsayılan

        soru_arkaplan_1 = MediaPlayer.create(this, R.raw.soru_arka_plan_ses_1);//SES
        soru_arkaplan_1.start();//SES
        soru_arkaplan_1.setVolume(0.1f, 0.1f);//SES

        soru_isaretleme_sesi = MediaPlayer.create(this, R.raw.soru_isaretleme_ses);//SES
        soru_isaretleme_sesi.setVolume(0.1f, 0.1f);//SES

        soru_bitti_sesi = MediaPlayer.create(this, R.raw.soru_bitti_ses);//SES
        soru_bitti_sesi.setVolume(0.1f, 0.1f);//SES

        soru_bitti_basarili_sesi = MediaPlayer.create(this, R.raw.soru_basarili_ses);//SES
        soru_bitti_basarili_sesi.setVolume(0.1f, 0.1f);//SES

        textViewKategori = findViewById(R.id.kategoriBaslik); //butonlar ve textViewler koda tanıtılıyor
        textViewSoru = findViewById(R.id.soruMetni);
        buttonA = findViewById(R.id.cevapAButton);
        buttonB = findViewById(R.id.cevapBButton);
        buttonC = findViewById(R.id.cevapCButton);
        buttonD = findViewById(R.id.cevapDButton);
        buttonBitir = findViewById(R.id.bitirButton);
        buttonGeriDon = findViewById(R.id.geriButton);

        resimImageView = findViewById(R.id.resimImageView);//RESİMLİ SORULAR

        textViewSure = findViewById(R.id.sureText);//SÜRE

        sure = 90; //SÜRE

        //Intent'ten seçilen kategoriyi alır
        Intent intent = getIntent();
        kategori = intent.getStringExtra("kategori");
        textViewKategori.setText(kategori);

        //soruları ve cevapları kategoriye göre ayarladım
        if (kategori.equals("Müzik")) {

            sorular = new String[]{"Soru 1: Türk halk müziği türünde önemli eserlere" +
                    "imza atmış olan sanatçı hangisidir?",
                    "Soru 2: Hangi enstrüman genellikle bir orkestranın kalbi olarak kabul edilir?",
                    "Soru 3: Türk halk müziği enstrümanlarından biri olan \"saz\" hangi" +
                            "tür bir çalgıdır?",
                    "Soru 4: Yukarıdaki müzik aletinin adı nedir ?", "Soru 5: Yukarıdaki gitarda" +
                    "kaç tane tel vardır ?"};//sorular

            cevaplar = new String[sorular.length][4];

            resimler = new int[]{R.drawable.resim_yok,
                    R.drawable.resim_yok,
                    R.drawable.resim_yok,
                    R.drawable.muzik_resim_4,
                    R.drawable.muzik_resim_5};//resimli sorular

            cevaplar[0][0] = "A: Barış Manço";
            cevaplar[0][1] = "B: Sezen Aksu";
            cevaplar[0][2] = "C: Neşet Ertaş";
            cevaplar[0][3] = "D: Zeki Müren";

            cevaplar[1][0] = "A: Piyano";
            cevaplar[1][1] = "B: Keman";
            cevaplar[1][2] = "C: Davul";
            cevaplar[1][3] = "D: Flüt";

            cevaplar[2][0] = "A: Üflemeli çalgı";
            cevaplar[2][1] = "B: Vurmalı çalgı";
            cevaplar[2][2] = "C: Yaylı çalgı";
            cevaplar[2][3] = "D: Telli çalgı";

            cevaplar[3][0] = "A: Kornet";
            cevaplar[3][1] = "B: Melodeon";
            cevaplar[3][2] = "C: Klarnet";
            cevaplar[3][3] = "D: Bendir";

            cevaplar[4][0] = "A: 4";
            cevaplar[4][1] = "B: 5";
            cevaplar[4][2] = "C: 6";
            cevaplar[4][3] = "D: 7";

            dogruCevaplar = new String[]{"C: Neşet Ertaş", "A: Piyano", "D: Telli çalgı",
                    "B: Melodeon", "C: 6"};

        }
        else if (kategori.equals("Sanat")) {

            sorular = new String[]{"Soru 1: Leonardo da Vinci'nin ünlü tablosu \"Mona Lisa\" hangi"+
                    "müzede sergilenmektedir?",
                    "Soru 2: \"Pietà\" ve \"David\" gibi heykelleriyle tanınan ünlü İtalyan" +
                            "sanatçı kimdir?",
                    "Soru 3: Hangi ressam," +
                    "\"Gece Yıldızlı\" ve \"Ay'a Yolculuk\" gibi ünlü eserlere imza atmıştır?",
                    "Soru 4: Yukarıdaki resim hangi ünlü ressamın eseridir?",
                    "Soru 5: Yukarıdaki resimde hangi sanat akımına ait bir tablo görülmektedir?"};

            cevaplar = new String[sorular.length][4];

            resimler = new int[]{R.drawable.resim_yok,
                    R.drawable.resim_yok, R.drawable.resim_yok,
                    R.drawable.sanat_resim_4, R.drawable.sanat_resim_5};//resimli sorular

            cevaplar[0][0] = "A: Louvre Müzesi";
            cevaplar[0][1] = "B: British Museum";
            cevaplar[0][2] = "C: Metropolitan Sanat Müzesi";
            cevaplar[0][3] = "D: Prado Müzesi";

            cevaplar[1][0] = "A: Salvador Dalí";
            cevaplar[1][1] = "B: Pablo Picasso";
            cevaplar[1][2] = "C: Vincent van Gogh";
            cevaplar[1][3] = "D: Michelangelo";

            cevaplar[2][0] = "A: Vincent van Gogh";
            cevaplar[2][1] = "B: Claude Monet";
            cevaplar[2][2] = "C: Leonardo da Vinci";
            cevaplar[2][3] = "D: Pablo Picasso";

            cevaplar[3][0] = "A: Salvador Dalí";
            cevaplar[3][1] = "B: Frida Kahlo";
            cevaplar[3][2] = "C: Jackson Pollock";
            cevaplar[3][3] = "D: Pablo Picasso";

            cevaplar[4][0] = "A: Sürrealizm";
            cevaplar[4][1] = "B: Rönesans";
            cevaplar[4][2] = "C: Kübizm";
            cevaplar[4][3] = "D: İmpresyonizm";

            dogruCevaplar = new String[]{"A: Louvre Müzesi", "D: Michelangelo",
                    "A: Vincent van Gogh",
                    "A: Salvador Dalí", "B: Rönesans"};
        }
        else if (kategori.equals("Bilim")) {

            sorular = new String[]{"Soru 1: Yukarıdaki resim daha çok hangi bilim dalı ile" +
                    "ilgilidir?",
                    "Soru 2: Yukarıdaki resimde hangi bilim insanı görülmektedir?",
                    "Soru 3: Hücrelerin enerji üretimiyle ilgilenen bilim dalı hangisidir?",
                    "Soru 4: Elektrik yüklerinin hareketi ve davranışını inceleyen bilim dalı" +
                            "hangisidir?",
                    "Soru 5: İnsanların tarih öncesine ait kültürel kalıntıları ve eserleri" +
                            "inceleyen bilim" +
                            "dalı hangisidir?"};

            cevaplar = new String[sorular.length][4];

            resimler = new int[]{R.drawable.bilim_resim_1,
                    R.drawable.bilim_resim_2, R.drawable.resim_yok,
                    R.drawable.resim_yok, R.drawable.resim_yok};//resimli sorular

            cevaplar[0][0] = "A: Biyoloji";
            cevaplar[0][1] = "B: Fizik";
            cevaplar[0][2] = "C: Kimya";
            cevaplar[0][3] = "D: Matematik";

            cevaplar[1][0] = "A: Albert Einstein";
            cevaplar[1][1] = "B: Marie Curie";
            cevaplar[1][2] = "C: Isaac Newton";
            cevaplar[1][3] = "D: Charles Darwin";

            cevaplar[2][0] = "A: Nöroloji";
            cevaplar[2][1] = "B: Kriptoloji";
            cevaplar[2][2] = "C: Nükleer fizik";
            cevaplar[2][3] = "D: Biyokimya";

            cevaplar[3][0] = "A: Astrofizik";
            cevaplar[3][1] = "B: Biyoloji";
            cevaplar[3][2] = "C: Elektronik";
            cevaplar[3][3] = "D: Psikoloji";

            cevaplar[4][0] = "A: Arkeoloji";
            cevaplar[4][1] = "B: Jeoloji";
            cevaplar[4][2] = "C: Astronomi";
            cevaplar[4][3] = "D: Ekoloji";

            dogruCevaplar = new String[]{"B: Fizik", "C: Isaac Newton", "D: Biyokimya",
                    "C: Elektronik", "A: Arkeoloji"};
        }
        else if (kategori.equals("Matematik")) {

            sorular = new String[]{"Soru 1: Bir dairedeki merkez açının ölçüsü 60 derecedir." +
                    "Dairenin tam çevresi kaç derecedir?",
                    "Soru 2: Bir üçgenin iç açıları toplamı kaç derecedir?",
                    "Soru 3: Yukarıdaki resimde hangi geometrik şekil görülmektedir?",
                    "Soru 4: Yukarıdaki resimde kaç tane paralel çizgi vardır?",
                    "Soru 5: Bir dik üçgenin iki dik kenarının uzunlukları sırasıyla" +
                            "3 cm ve 4 cm ise, hipotenüsün uzunluğu kaç cm'dir?"};

            cevaplar = new String[sorular.length][4];

            resimler = new int[]{R.drawable.resim_yok,
                    R.drawable.resim_yok,
                    R.drawable.mat_resim_3 ,
                    R.drawable.mat_resim_4 ,
                    R.drawable.resim_yok};//resimli sorular

            cevaplar[0][0] = "A: 60π";
            cevaplar[0][1] = "B: 120π";
            cevaplar[0][2] = "C: 180π";
            cevaplar[0][3] = "D: 360π";

            cevaplar[1][0] = "A: 90";
            cevaplar[1][1] = "B: 180";
            cevaplar[1][2] = "C: 270";
            cevaplar[1][3] = "D: 360";

            cevaplar[2][0] = "A: Kare";
            cevaplar[2][1] = "B: Dikdörtgen";
            cevaplar[2][2] = "C: Üçgen";
            cevaplar[2][3] = "D: Daire";

            cevaplar[3][0] = "A: 1";
            cevaplar[3][1] = "B: 2";
            cevaplar[3][2] = "C: 3";
            cevaplar[3][3] = "D: 4";

            cevaplar[4][0] = "A: 5";
            cevaplar[4][1] = "B: 7";
            cevaplar[4][2] = "C: 8";
            cevaplar[4][3] = "D: 10";

            dogruCevaplar = new String[]{"D: 360π", "B: 180", "A: Kare", "B: 2", "A: 5"};
        }
        else if (kategori.equals("Spor")) {

            sorular = new String[]{"Soru 1: Yukarıdaki resimde hangi spor dalının sahası" +
                    "görülmektedir?", "Soru 2: Hangi spor dalında, maçlar iki takım arasında" +
                    "11'er oyuncu ile oynanır?", "Soru 3: Olimpiyat Oyunları kaç yılda bir" +
                    "düzenlenir?", "Soru 4: Yukarıdaki resimde hangi spor dalının ekipmanı" +
                    "görülmektedir?", "Soru 5: Hangi spor dalında, maçlar setler halinde" +
                    "oynanır ve raket kullanılır?"};

            cevaplar = new String[sorular.length][4];

            resimler = new int[]{R.drawable.spor_resim_1,
                    R.drawable.resim_yok,R.drawable.resim_yok ,
                    R.drawable.spor_resim_4 , R.drawable.resim_yok};//resimli sorular

            cevaplar[0][0] = "A: Futbol";
            cevaplar[0][1] = "B: Basketbol";
            cevaplar[0][2] = "C: Tenis";
            cevaplar[0][3] = "D: Voleybol";

            cevaplar[1][0] = "A: Voleybol";
            cevaplar[1][1] = "B: Tenis";
            cevaplar[1][2] = "C: Basketbol";
            cevaplar[1][3] = "D: Futbol";

            cevaplar[2][0] = "A: 2 yılda bir";
            cevaplar[2][1] = "B: 4 yılda bir";
            cevaplar[2][2] = "C: 6 yılda bir";
            cevaplar[2][3] = "D: 8 yılda bir";

            cevaplar[3][0] = "A: Boks";
            cevaplar[3][1] = "B: Yüzme";
            cevaplar[3][2] = "C: Kayak";
            cevaplar[3][3] = "D: Bisiklet";

            cevaplar[4][0] = "A: Tenis";
            cevaplar[4][1] = "B: Basketbol";
            cevaplar[4][2] = "C: Voleybol";
            cevaplar[4][3] = "D: Futbol";

            dogruCevaplar = new String[]{"C: Tenis", "D: Futbol",
                    "B: 4 yılda bir", "D: Bisiklet", "A: Tenis"};
        }
        else if (kategori.equals("Video Oyunları")) {

            sorular = new String[]{"Soru 1: Hangi video oyun konsolu," +
                    "\"PlayStation\" markasıyla bilinir?", "Soru 2: Hangi video" +
                    "oyun serisi, \"The Legend of Zelda\" adlı oyunuyla ünlüdür?",
                    "Soru 3: Hangi video oyunu, \"Minecraft\" adlı popüler yapım" +
                            "oyunudur?", "Soru 4: Yukarıdaki resimde hangi video" +
                    "oyun karakteri görülmektedir?", "Soru 5: Yukarıdaki resimde" +
                    "hangi video oyunun logosu görülmektedir?"};

            cevaplar = new String[sorular.length][4];

            resimler = new int[]{R.drawable.resim_yok, R.drawable.resim_yok,
                    R.drawable.resim_yok , R.drawable.video_oyun_resim_4 ,
                    R.drawable.video_oyun_resim_5};//resimli sorular

            cevaplar[0][0] = "A: Nintendo Switch";
            cevaplar[0][1] = "B: Xbox";
            cevaplar[0][2] = "C: PlayStation";
            cevaplar[0][3] = "D: PC (Personal Computer)";

            cevaplar[1][0] = "A: Final Fantasy";
            cevaplar[1][1] = "B: Grand Theft Auto";
            cevaplar[1][2] = "C: The Legend of Zelda";
            cevaplar[1][3] = "D: Assassin's Creed";

            cevaplar[2][0] = "A: Fortnite";
            cevaplar[2][1] = "B: Minecraft";
            cevaplar[2][2] = "C: Call of Duty";
            cevaplar[2][3] = "D: League of Legends";

            cevaplar[3][0] = "A: Mario (Super Mario)";
            cevaplar[3][1] = "B: Kratos (God of War)";
            cevaplar[3][2] = "C: Master Chief (Halo)";
            cevaplar[3][3] = "D: Lara Croft (Tomb Raider)";

            cevaplar[4][0] = "A: The Elder Scrolls V: Skyrim";
            cevaplar[4][1] = "B: Overwatch";
            cevaplar[4][2] = "C: Fortnite";
            cevaplar[4][3] = "D: The Witcher 3: Wild Hunt";

            dogruCevaplar = new String[]{"C: PlayStation", "C: The Legend of Zelda",
                    "B: Minecraft", "A: Mario (Super Mario)", "C: Fortnite"};
        }
        else if (kategori.equals("Biyoloji")) {

            sorular = new String[]{"Soru 1: Hangi organel hücrenin \"enerji santrali\"" +
                    "olarak bilinir?", "Soru 2: Yukarıdaki resimde hangi hücre yapısı" +
                    "görülmektedir?", "Soru 3: DNA molekülü hangi molekülün yapı taşıdır?",
                    "Soru 4: Yukarıdaki resimde hangi organ sistemine ait bir" +
                            "organ görülmektedir?", "Soru 5: Fotosentez olayı hangi" +
                    "hücre yapısı tarafından gerçekleştirilir?"};

            cevaplar = new String[sorular.length][4];

            resimler = new int[]{R.drawable.resim_yok, R.drawable.biyoloji_resim_2,
                    R.drawable.resim_yok , R.drawable.biyoloji_resim_4 ,
                    R.drawable.resim_yok};//resimli sorular

            cevaplar[0][0] = "A: Golgi aygıtı";
            cevaplar[0][1] = "B: Mitokondri";
            cevaplar[0][2] = "C: Endoplazmik retikulum";
            cevaplar[0][3] = "D: Lizozom";

            cevaplar[1][0] = "A: Ribozom";
            cevaplar[1][1] = "B: Nükleus";
            cevaplar[1][2] = "C: Kloroplast";
            cevaplar[1][3] = "D: Hücre zarı";

            cevaplar[2][0] = "A: Karbonhidrat";
            cevaplar[2][1] = "B: Protein";
            cevaplar[2][2] = "C: Lipid";
            cevaplar[2][3] = "D: Nükleotid";

            cevaplar[3][0] = "A: Solunum sistemi";
            cevaplar[3][1] = "B: Sinir sistemi";
            cevaplar[3][2] = "C: Sindirim sistemi";
            cevaplar[3][3] = "D: Dolaşım sistemi";

            cevaplar[4][0] = "A: Kloroplast";
            cevaplar[4][1] = "B: Golgi aygıtı";
            cevaplar[4][2] = "C: Endoplazmik retikulum";
            cevaplar[4][3] = "D: Lizozom";

            dogruCevaplar = new String[]{"B: Mitokondri", "C: Kloroplast",
                    "D: Nükleotid", "A: Solunum sistemi", "A: Kloroplast"};
        }
        else if (kategori.equals("Coğrafya")) {

            sorular = new String[]{"Soru 1: Dünya'nın en geniş kıtası hangisidir?",
                    "Soru 2: Bir ülkenin kara sınırlarının tamamına yakını hangi" +
                            "ülkeyle komşu ise bu ülkenin coğrafi şekli aşağıdaki" +
                            "terimle tanımlanır?", "Soru 3: Aşağıdaki ülkelerden" +
                    "hangisi Pasifik Okyanusu'nda bulunan bir adadır?",
                    "Soru 4: Yukarıdaki resimde hangi coğrafi olgu görülmektedir?",
                    "Soru 5: Yukarıdaki resimde hangi coğrafi olgu görülmektedir?"};

            cevaplar = new String[sorular.length][4];

            resimler = new int[]{R.drawable.resim_yok, R.drawable.resim_yok,
                    R.drawable.resim_yok , R.drawable.cografya_resim_4 ,
                    R.drawable.cografya_resim_5};//resimli sorular

            cevaplar[0][0] = "A: Asya";
            cevaplar[0][1] = "B: Afrika";
            cevaplar[0][2] = "C: Avrupa";
            cevaplar[0][3] = "D: Kuzey Amerika";

            cevaplar[1][0] = "A: Enklav";
            cevaplar[1][1] = "B: Eksklav";
            cevaplar[1][2] = "C: Adalar ülkesi";
            cevaplar[1][3] = "D: Yarımadalar ülkesi";

            cevaplar[2][0] = "A: Japonya";
            cevaplar[2][1] = "B: Hindistan";
            cevaplar[2][2] = "C: Kanada";
            cevaplar[2][3] = "D: Mısır";

            cevaplar[3][0] = "A: Dağ";
            cevaplar[3][1] = "B: Ova";
            cevaplar[3][2] = "C: Plato";
            cevaplar[3][3] = "D: Vadi";

            cevaplar[4][0] = "A: Kanyon";
            cevaplar[4][1] = "B: Delta";
            cevaplar[4][2] = "C: Yarımadalar";
            cevaplar[4][3] = "D: Set gölü";

            dogruCevaplar = new String[]{"A: Asya", "A: Enklav", "A: Japonya",
                    "B: Ova", "B: Delta"};
        }
        else if (kategori.equals("Tarih")) {

            sorular = new String[]{"Soru 1: Hangi tarih aralığı \"Orta Çağ\"" +
                    "dönemini kapsar?", "Soru 2: Yukarıdaki resimde hangi tarihi" +
                    "olayın sonucunu gösteren bir sembol görülmektedir?",
                    "Soru 3: Yukarıdaki resimde hangi tarihî kişi veya olayın" +
                            "bir görseli görülmektedir?", "Soru 4: Hangi tarihî" +
                    "olay, \"1914-1918\" yılları arasında gerçekleşmiştir?",
                    "Soru 5 :Hangi tarihî dönemde \"İskenderiye Kütüphanesi\"" +
                            "büyük bir yangın sonucu yok olmuştur?"};

            cevaplar = new String[sorular.length][4];

            resimler = new int[]{R.drawable.resim_yok, R.drawable.tarih_resim_2,
                    R.drawable.tarih_resim_3 , R.drawable.resim_yok ,
                    R.drawable.resim_yok};//resimli sorular

            cevaplar[0][0] = "A: M.Ö. 476 - M.S. 1453";
            cevaplar[0][1] = "B: M.S. 476 - M.S. 1453";
            cevaplar[0][2] = "C: M.Ö. 476 - M.S. 476";
            cevaplar[0][3] = "D: M.S. 476 - M.S. 476";

            cevaplar[1][0] = "A: Amerikan Bağımsızlık Savaşı";
            cevaplar[1][1] = "B: Fransız Devrimi";
            cevaplar[1][2] = "C: Amerikan İç Savaşı";
            cevaplar[1][3] = "D: İngiliz İç Savaşı";

            cevaplar[2][0] = "A: Napolyon Bonapart";
            cevaplar[2][1] = "B: Leonardo da Vinci";
            cevaplar[2][2] = "C: Martin Luther King Jr.";
            cevaplar[2][3] = "D: Galileo Galilei";

            cevaplar[3][0] = "A: İkinci Dünya Savaşı";
            cevaplar[3][1] = "B: İspanyol İç Savaşı";
            cevaplar[3][2] = "C: I. Dünya Savaşı";
            cevaplar[3][3] = "D: Kore Savaşı";

            cevaplar[4][0] = "A: Antik Mısır";
            cevaplar[4][1] = "B: Antik Yunan";
            cevaplar[4][2] = "C: Roma İmparatorluğu";
            cevaplar[4][3] = "D: Orta Çağ";

            dogruCevaplar = new String[]{"B: M.S. 476 - M.S. 1453",
                    "A: Amerikan Bağımsızlık Savaşı", "D: Galileo Galilei",
                    "C: I. Dünya Savaşı", "D: Orta Çağ"};
        }
        else if (kategori.equals("Ülke Bulmaca")) {

            sorular = new String[]{"Soru 1: Yukarıdaki resim hangi ülkeye aittir ?",
                    "Soru 2: Yukarıdaki resim hangi ülkeye aittir ?",
                    "Soru 3: Yukarıdaki resim hangi ülkeye aittir ?",
                    "Soru 4: Yukarıdaki resim hangi ülkeye aittir ?",
                    "Soru 5: Yukarıdaki resim hangi ülkeye aittir ?"};

            cevaplar = new String[sorular.length][4];

            resimler = new int[]{R.drawable.ulke_bul_1, R.drawable.ulke_bul_2,
                    R.drawable.ulke_bul_3 , R.drawable.ulke_bul_4 ,
                    R.drawable.ulke_bul_5};//resimli sorular

            cevaplar[0][0] = "A: İtalya";
            cevaplar[0][1] = "B: Fransa";
            cevaplar[0][2] = "C: Türkiye";
            cevaplar[0][3] = "D: Finlandiya";

            cevaplar[1][0] = "A: Amerika";
            cevaplar[1][1] = "B: Fransa";
            cevaplar[1][2] = "C: İngiltere";
            cevaplar[1][3] = "D: Almanya";

            cevaplar[2][0] = "A: İngiltere";
            cevaplar[2][1] = "B: Finlandiya";
            cevaplar[2][2] = "C: İngiltere";
            cevaplar[2][3] = "D: Danimarka";

            cevaplar[3][0] = "A: İtalya";
            cevaplar[3][1] = "B: Romanya";
            cevaplar[3][2] = "C: Belçika";
            cevaplar[3][3] = "D: Litvanya";

            cevaplar[4][0] = "A: Japonya";
            cevaplar[4][1] = "B: Çin";
            cevaplar[4][2] = "C: Güney Kore";
            cevaplar[4][3] = "D: Tayland";

            dogruCevaplar = new String[]{"C: Türkiye", "B: Fransa",
                    "D: Danimarka", "C: Belçika", "A: Japonya"};
        }

        skor = 0; //skor başlangıç için 0 olarak ayarladım
        soruIndex = 0; //soru indexleri soru 1 için 0 olduğundan dolayı ilk 0 olarak ayarladım
        soruGoster(); //soruGoster fonksiyonu çağırdım

        buttonA.setOnClickListener(new View.OnClickListener() { //A butonu için setOnClickListener oluşturdum
            @Override
            public void onClick(View v) {//onClick
                soru_isaretleme_sesi.start();//Soru işaretleme sesi
                cevapKontrol(buttonA.getText().toString());//cevabın doğru olup olmadığını kontrol ediyor
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {  //B butonu için setOnClickListener oluşturdum
            @Override
            public void onClick(View v) {
                soru_isaretleme_sesi.start();//Soru işaretleme sesi
                cevapKontrol(buttonB.getText().toString());//cevabın doğru olup olmadığını kontrol ediyor
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() { //C butonu için setOnClickListener oluşturdum
            @Override
            public void onClick(View v) {
                soru_isaretleme_sesi.start();//Soru işaretleme sesi
                cevapKontrol(buttonC.getText().toString());//cevabın doğru olup olmadığını kontrol ediyor
            }
        });

        buttonD.setOnClickListener(new View.OnClickListener() { //D butonu için setOnClickListener oluşturdum
            @Override
            public void onClick(View v) {
                soru_isaretleme_sesi.start();//Soru işaretleme sesi
                cevapKontrol(buttonD.getText().toString());//cevabın doğru olup olmadığını kontrol ediyor
            }
        });

        buttonBitir.setOnClickListener(new View.OnClickListener() { //Bitir butonu için setOnClickListener oluşturdum
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SoruActivity.this);
                alertDialogBuilder
                        .setMessage("Testi bitirmek istediğinize emin misiniz?")
                        .setCancelable(false)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                soru_bitti_sesi.start();//SES
                                sonucGoster();
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                //TAM EKRAN ALERT
                //tam ekran modunu etkinleştirir
                Window window = alertDialog.getWindow();
                if (window != null) {
                    View decorView = window.getDecorView();
                    int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                    decorView.setSystemUiVisibility(flags);
                }
            }
        });

        buttonGeriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SoruActivity.this);
                alertDialogBuilder
                        .setMessage("Testi tamamen sonlandırmak istediğinize emin misiniz?")
                        .setCancelable(false)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                soru_bitti_sesi.start();//SES
                                //testi sonlandırınca gideceği class
                                Intent intent = new Intent(getApplicationContext(), KategoriActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                Window window = alertDialog.getWindow();//TAM EKRAN ALERT //tam ekran modunu etkinleştir
                if (window != null) {
                    View decorView = window.getDecorView();
                    int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                    decorView.setSystemUiVisibility(flags);
                }
            }
        });
    }

    private void soruGoster() {

        textViewSoru.setText(sorular[soruIndex]);
        buttonA.setText(cevaplar[soruIndex][0]);
        buttonB.setText(cevaplar[soruIndex][1]);
        buttonC.setText(cevaplar[soruIndex][2]);
        buttonD.setText(cevaplar[soruIndex][3]);

        //resim varsa gösterir
        if (resimler != null && resimler.length > soruIndex) {
            resimImageView.setImageResource(resimler[soruIndex]);
            resimImageView.setVisibility(View.VISIBLE);
        } else {
            resimImageView.setVisibility(View.GONE);
        }

        if (countdownTimer != null) { //SÜRE
            countdownTimer.cancel();//SÜRE
        }
        countdownTimer = new CountDownTimer(sure * 1000, 1000) { //SÜRE

            @Override
            public void onTick(long millisUntilFinished) {//SÜRE
                if (sure > -1) {//SÜRE
                    sure--;//SÜRE
                }
                textViewSure.setText("Süre: " + sure);//SÜRE

                if (sure == 0) {
                    countdownTimer.cancel(); //sayaç saymayı durdurur
                    sonucGoster(); //sonucGoster() fonksiyonunu çalıştırır
                }
            }

            @Override
            public void onFinish() { //SÜRE
                sonucGoster(); //SÜRE
            }
        }.start(); //SÜRE
    }

    private void cevapKontrol(String cevap) {
        if (cevap.equals(dogruCevaplar[soruIndex])) {
            skor = skor + 20;
            SharedPreferences sharedPreferences = getSharedPreferences("Skor", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (kategori.equals("Müzik")) {
                editor.putInt("muzik_skor", skor);
            } else if (kategori.equals("Sanat")) {
                editor.putInt("sanat_skor", skor);
            } else if (kategori.equals("Bilim")) {
                editor.putInt("bilim_skor", skor);
            } else if (kategori.equals("Matematik")) {
                editor.putInt("matematik_skor", skor);
            } else if (kategori.equals("Spor")) {
                editor.putInt("spor_skor", skor);
            } else if (kategori.equals("Video Oyunları")) {
                editor.putInt("video_oyunlari_skor", skor);
            } else if (kategori.equals("Biyoloji")) {
                editor.putInt("biyoloji_skor", skor);
            } else if (kategori.equals("Coğrafya")) {
                editor.putInt("cografya_skor", skor);
            } else if (kategori.equals("Tarih")) {
                editor.putInt("tarih_skor", skor);
            } else if (kategori.equals("Ülke Bulmaca")) {
                editor.putInt("ulke_bulmaca_skor", skor);
            }
            editor.apply();
        }

        //RENK
        String dogruCevap = dogruCevaplar[soruIndex];
        if (cevap.equals(dogruCevap)) {
            //doğru cevabı renklendirir
            renklendir(true, cevap);
        } else {
            //yanlış cevabı renklendirir
            renklendir(false, cevap);
        }


        //SONRAKİ SORUYA GEÇER
        soruIndex++;

        if (soruIndex < sorular.length) {
            //sonraki soru çıkmadan önce gecikme olması için
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetRenklendirme(); //KIRMIZI VEYA YEŞİL RENK RESETLENİR
                    soruGoster();
                }
            }, 1000); //1 saniye gecikme
        } else if (soruIndex == sorular.length) {
            soru_bitti_basarili_sesi.start();
            sonucGoster();
        } else {
            sonucGoster();
        }
    }
    //onCreate metodu bitti
    //Buradakiler fonksiyonlar: sonucGoster, onDestroy, onResume, onPause, renklendir ve resetRenklendirme fonkiyonları vardır.
    private void sonucGoster() {
        countdownTimer.cancel();//countdownTimer durdurulur
        soru_arkaplan_1.pause();//soru_arkaplan_1 sesi durdurulur
        int dogru_sayisi = skor / 20; //doğru sayısı hesaplanır
        int yanlis_sayisi = soruIndex - dogru_sayisi; //yanlış sayısı hesaplanır

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SoruActivity.this);
        alertDialogBuilder
                .setMessage("Test bitti!\nSkorunuz: " + skor + "\nDoğru Sayısı: " + dogru_sayisi + "\nYanlış Sayısı: " + yanlis_sayisi)
                .setCancelable(false)
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), KategoriActivity.class);//nereye döneceğini söylüyor
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        //TAM EKRAN ALERT
        //tam ekran modunu etkinleştirir
        Window window = alertDialog.getWindow();
        if (window != null) {
            View decorView = window.getDecorView();
            int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(flags);
        }
    }

    @Override
    protected void onDestroy() {//SES
        super.onDestroy();
        soru_arkaplan_1.release();
    }
    @Override
    protected void onResume() {
        super.onResume();
        soru_arkaplan_1.start(); //KategoriActivity'e dönüldüğünde müziği başlatır
    }

    @Override
    protected void onPause() {
        super.onPause();
        soru_arkaplan_1.pause(); //KategoriActivity'den ayrılırken müziği durdurur
    }

    private void renklendir(boolean dogru, String cevap) {
        if (dogru) {
            //doğru cevap renklendirme (yeşil)
            if (buttonA.getText().toString().equals(dogruCevaplar[soruIndex])) {
                buttonA.setBackgroundColor(getResources().getColor(R.color.yesil));
            } else if (buttonB.getText().toString().equals(dogruCevaplar[soruIndex])) {
                buttonB.setBackgroundColor(getResources().getColor(R.color.yesil));
            } else if (buttonC.getText().toString().equals(dogruCevaplar[soruIndex])) {
                buttonC.setBackgroundColor(getResources().getColor(R.color.yesil));
            } else if (buttonD.getText().toString().equals(dogruCevaplar[soruIndex])) {
                buttonD.setBackgroundColor(getResources().getColor(R.color.yesil));
            }
        } else {
            //yanlış cevap renklendirme (kırmızı)
            if (buttonA.getText().toString().equals(cevap)) {
                buttonA.setBackgroundColor(getResources().getColor(R.color.kirmizi));
            } else if (buttonB.getText().toString().equals(cevap)) {
                buttonB.setBackgroundColor(getResources().getColor(R.color.kirmizi));
            } else if (buttonC.getText().toString().equals(cevap)) {
                buttonC.setBackgroundColor(getResources().getColor(R.color.kirmizi));
            } else if (buttonD.getText().toString().equals(cevap)) {
                buttonD.setBackgroundColor(getResources().getColor(R.color.kirmizi));
            }

            //doğru cevap renklendirme (yeşil)
            if (buttonA.getText().toString().equals(dogruCevaplar[soruIndex])) {
                buttonA.setBackgroundColor(getResources().getColor(R.color.yesil));
            } else if (buttonB.getText().toString().equals(dogruCevaplar[soruIndex])) {
                buttonB.setBackgroundColor(getResources().getColor(R.color.yesil));
            } else if (buttonC.getText().toString().equals(dogruCevaplar[soruIndex])) {
                buttonC.setBackgroundColor(getResources().getColor(R.color.yesil));
            } else if (buttonD.getText().toString().equals(dogruCevaplar[soruIndex])) {
                buttonD.setBackgroundColor(getResources().getColor(R.color.yesil));
            }
        }
    }

    private void resetRenklendirme() {
        //şıkların orijinal arka plan renklerini kullanarak renklendirmeyi sıfırla
        buttonA.setBackgroundResource(R.drawable.button_cevap); //button_a, buttonA'nın orijinal arka plan tasarımını temsil eder
        buttonB.setBackgroundResource(R.drawable.button_cevap); //button_b, buttonB'nin orijinal arka plan tasarımını temsil eder
        buttonC.setBackgroundResource(R.drawable.button_cevap); //button_c, buttonC'nin orijinal arka plan tasarımını temsil eder
        buttonD.setBackgroundResource(R.drawable.button_cevap); //button_d, buttonD'nin orijinal arka plan tasarımını temsil eder
    }
}