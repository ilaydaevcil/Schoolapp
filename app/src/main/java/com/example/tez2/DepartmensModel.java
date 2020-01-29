package com.example.tez2;

public class DepartmensModel {
    //i watched a video. He said "you have to add a module. in module, you have to crate somethink like in your database."

    private String BolumAdi;
    //BolumAdi: Departmens Name

    private DepartmensModel(){}
    private DepartmensModel(String bolumAdi){
this.BolumAdi=bolumAdi;
    }

    public String getBolumAdi() {
        return BolumAdi;
    }

    public void setBolumAdi(String bolumAdi) {
        BolumAdi = bolumAdi;
    }


}
