package com.gooproper.model;

public class ListingSementaraModel {

    String IdShareLokasi,IdAgen,Alamat,Lokasi,Latitude,Longitude,Selfie;

    public ListingSementaraModel(){}

    public ListingSementaraModel(String IdShareLokasi,String IdAgen,String Alamat,String Lokasi,String Latitude,String Longitude,String Selfie){
        this.IdShareLokasi = IdShareLokasi;
        this.IdAgen = IdAgen;
        this.Alamat = Alamat;
        this.Lokasi = Lokasi;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Selfie = Selfie;
    }

    public String getIdShareLokasi() {
        return IdShareLokasi;
    }

    public void setIdShareLokasi(String idShareLokasi) {
        IdShareLokasi = idShareLokasi;
    }

    public String getIdAgen() {
        return IdAgen;
    }

    public void setIdAgen(String idAgen) {
        IdAgen = idAgen;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getLokasi() {
        return Lokasi;
    }

    public void setLokasi(String lokasi) {
        Lokasi = lokasi;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getSelfie() {
        return Selfie;
    }

    public void setSelfie(String selfie) {
        Selfie = selfie;
    }
}
