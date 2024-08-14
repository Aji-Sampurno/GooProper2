package com.gooproper.model;

public class PasangBannerModel {
    String IdListing, NamaListing, Alamat, Priority, Kondisi, Size, Img1;

    public PasangBannerModel() {
    }

    public PasangBannerModel(String IdListing, String NamaListing, String Alamat, String Priority, String Kondisi, String Size, String Img1) {
        this.IdListing = IdListing;
        this.NamaListing = NamaListing;
        this.Alamat = Alamat;
        this.Kondisi = Kondisi;
        this.Priority = Priority;
        this.Size = Size;
        this.Img1 = Img1;
    }

    public String getIdListing() {
        return IdListing;
    }

    public void setIdListing(String idListing) {
        IdListing = idListing;
    }

    public String getNamaListing() {
        return NamaListing;
    }

    public void setNamaListing(String namaListing) {
        NamaListing = namaListing;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getKondisi() {
        return Kondisi;
    }

    public void setKondisi(String kondisi) {
        Kondisi = kondisi;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getImg1() {
        return Img1;
    }

    public void setImg1(String img1) {
        Img1 = img1;
    }
}
