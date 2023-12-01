package com.gooproper.model;

public class TipeModel {
    String IdTipePrimary, IdListingPrimary, NamaTipe, DeskripsiTipe, HargaTipe, GambarTipe;
    public TipeModel(){}
    public TipeModel(String IdTipePrimary, String IdListingPrimary, String NamaTipe, String DeskripsiTipe, String HargaTipe, String GambarTipe) {
        this.IdTipePrimary = IdTipePrimary;
        this.IdListingPrimary = IdListingPrimary;
        this.NamaTipe = NamaTipe;
        this.DeskripsiTipe = DeskripsiTipe;
        this.HargaTipe = HargaTipe;
        this.GambarTipe = GambarTipe;
    }

    public String getIdTipePrimary() {
        return IdTipePrimary;
    }

    public void setIdTipePrimary(String idTipePrimary) {
        IdTipePrimary = idTipePrimary;
    }

    public String getIdListingPrimary() {
        return IdListingPrimary;
    }

    public void setIdListingPrimary(String idListingPrimary) {
        IdListingPrimary = idListingPrimary;
    }

    public String getNamaTipe() {
        return NamaTipe;
    }

    public void setNamaTipe(String namaTipe) {
        NamaTipe = namaTipe;
    }

    public String getDeskripsiTipe() {
        return DeskripsiTipe;
    }

    public void setDeskripsiTipe(String deskripsiTipe) {
        DeskripsiTipe = deskripsiTipe;
    }

    public String getHargaTipe() {
        return HargaTipe;
    }

    public void setHargaTipe(String hargaTipe) {
        HargaTipe = hargaTipe;
    }

    public String getGambarTipe() {
        return GambarTipe;
    }

    public void setGambarTipe(String gambarTipe) {
        GambarTipe = gambarTipe;
    }
}
