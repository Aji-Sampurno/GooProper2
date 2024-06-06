package com.gooproper.model;

public class PraListingTerdekatModel {

    String IdPraListing, NamaListing, Alamat, Wilayah, Img1;
    double Latitude, Longitude;

    public PraListingTerdekatModel() {
    }

    public PraListingTerdekatModel(double Latitude, Double Longitude, String IdPraListing, String NamaListing, String Alamat, String Img1) {
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.IdPraListing = IdPraListing;
        this.NamaListing = NamaListing;
        this.Alamat = Alamat;
        this.Img1 = Img1;
    }

    public PraListingTerdekatModel(String IdPraListing, String NamaListing, String Alamat, String Wilayah, String Img1, double Latitude, double Longitude) {
        this.IdPraListing = IdPraListing;
        this.NamaListing = NamaListing;
        this.Alamat = Alamat;
        this.Wilayah = Wilayah;
        this.Img1 = Img1;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public String getIdPraListing() {
        return IdPraListing;
    }

    public void setIdPraListing(String idPraListing) {
        IdPraListing = idPraListing;
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

    public String getWilayah() {
        return Wilayah;
    }

    public void setWilayah(String wilayah) {
        Wilayah = wilayah;
    }

    public String getImg1() {
        return Img1;
    }

    public void setImg1(String img1) {
        Img1 = img1;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
