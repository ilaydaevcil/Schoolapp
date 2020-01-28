package com.example.tez2;

import android.widget.DatePicker;

import java.util.Date;

public class EtkinlikModel {
    private String etkinlikAdi;
    private String etkinlikAciklma;
    private String baslangicTarihi;
    private String sonlanmaTarihi;


    public EtkinlikModel(){}
    public EtkinlikModel(String etkinlikAdi, String etkinlikAciklma,String baslangicTarihi,String sonlanmaTarihi) {
        this.etkinlikAdi = etkinlikAdi;
        this.etkinlikAciklma = etkinlikAciklma;
        this.baslangicTarihi=baslangicTarihi;
        this.sonlanmaTarihi=sonlanmaTarihi;

    }

    public String getSonlanmaTarihi() {
        return sonlanmaTarihi;
    }

    public String getEtkinlikAdi() {
        return etkinlikAdi;
    }

    public String getEtkinlikAciklma() {
        return etkinlikAciklma;
    }

    public String getBaslangicTarihi() {
        return baslangicTarihi;
    }
}
