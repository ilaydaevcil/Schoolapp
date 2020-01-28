package com.example.tez2;

public class BolumlerModel {

    private String BolumAdi;

    private BolumlerModel(){}
    private BolumlerModel(String bolumAdi){
this.BolumAdi=bolumAdi;
    }

    public String getBolumAdi() {
        return BolumAdi;
    }

    public void setBolumAdi(String bolumAdi) {
        BolumAdi = bolumAdi;
    }


}
