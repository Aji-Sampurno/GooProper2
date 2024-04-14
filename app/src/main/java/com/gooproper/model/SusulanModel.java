package com.gooproper.model;

public class SusulanModel {
    String Keterangan, TglInput, PoinTambahan, PoinBerkurang;

    public SusulanModel() {
    }

    public SusulanModel(String Keterangan, String TglInput, String PoinTambahan, String PoinBerkurang) {
        this.Keterangan = Keterangan;
        this.TglInput = TglInput;
        this.PoinTambahan = PoinTambahan;
        this.PoinBerkurang = PoinBerkurang;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getTglInput() {
        return TglInput;
    }

    public void setTglInput(String tglInput) {
        TglInput = tglInput;
    }

    public String getPoinTambahan() {
        return PoinTambahan;
    }

    public void setPoinTambahan(String poinTambahan) {
        PoinTambahan = poinTambahan;
    }

    public String getPoinBerkurang() {
        return PoinBerkurang;
    }

    public void setPoinBerkurang(String poinBerkurang) {
        PoinBerkurang = poinBerkurang;
    }
}
