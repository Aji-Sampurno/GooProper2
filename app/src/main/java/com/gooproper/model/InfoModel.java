package com.gooproper.model;

public class InfoModel {

    String IdInfo,IdAgen,JenisProperty,StatusProperty,Narahubung,ImgSelfie,ImgProperty,Lokasi,Alamat,NoTelp,Latitude,Longitude,TglInput,JamInput,IsListing,LBangunan,LTanah,Harga,HargaSewa,Keterangan ,IsSpek;

    public InfoModel(){}

    public InfoModel(String IdInfo,String IdAgen,String JenisProperty,String StatusProperty,String Narahubung,String ImgSelfie,String ImgProperty,String Lokasi,String Alamat,String NoTelp,String Latitude,String Longitude,String TglInput,String JamInput,String IsListing,String LBangunan,String LTanah,String Harga,String HargaSewa,String Keterangan,String IsSpek){
        this.IdInfo = IdInfo;
        this.IdAgen = IdAgen;
        this.JenisProperty = JenisProperty;
        this.StatusProperty = StatusProperty;
        this.Narahubung = Narahubung;
        this.ImgSelfie = ImgSelfie;
        this.ImgProperty = ImgProperty;
        this.Lokasi = Lokasi;
        this.Alamat = Alamat;
        this.NoTelp = NoTelp;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.TglInput = TglInput;
        this.JamInput = JamInput;
        this.IsListing = IsListing;
        this.LBangunan = LBangunan;
        this.LTanah = LTanah;
        this.Harga = Harga;
        this.HargaSewa = HargaSewa;
        this.Keterangan = Keterangan;
        this.IsSpek = IsSpek;
    }

    public String getIdInfo() {
        return IdInfo;
    }

    public void setIdInfo(String idInfo) {
        IdInfo = idInfo;
    }

    public String getIdAgen() {
        return IdAgen;
    }

    public void setIdAgen(String idAgen) {
        IdAgen = idAgen;
    }

    public String getJenisProperty() {
        return JenisProperty;
    }

    public void setJenisProperty(String jenisProperty) {
        JenisProperty = jenisProperty;
    }

    public String getStatusProperty() {
        return StatusProperty;
    }

    public void setStatusProperty(String statusProperty) {
        StatusProperty = statusProperty;
    }

    public String getImgSelfie() {
        return ImgSelfie;
    }

    public void setImgSelfie(String imgSelfie) {
        ImgSelfie = imgSelfie;
    }

    public String getImgProperty() {
        return ImgProperty;
    }

    public void setImgProperty(String imgProperty) {
        ImgProperty = imgProperty;
    }

    public String getLokasi() {
        return Lokasi;
    }

    public void setLokasi(String lokasi) {
        Lokasi = lokasi;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getNoTelp() {
        return NoTelp;
    }

    public void setNoTelp(String noTelp) {
        NoTelp = noTelp;
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

    public String getTglInput() {
        return TglInput;
    }

    public void setTglInput(String tglInput) {
        TglInput = tglInput;
    }

    public String getJamInput() {
        return JamInput;
    }

    public void setJamInput(String jamInput) {
        JamInput = jamInput;
    }

    public String getIsListing() {
        return IsListing;
    }

    public void setIsListing(String isListing) {
        IsListing = isListing;
    }

    public String getNarahubung() {
        return Narahubung;
    }

    public void setNarahubung(String narahubung) {
        Narahubung = narahubung;
    }

    public String getLBangunan() {
        return LBangunan;
    }

    public void setLBangunan(String LBangunan) {
        this.LBangunan = LBangunan;
    }

    public String getLTanah() {
        return LTanah;
    }

    public void setLTanah(String LTanah) {
        this.LTanah = LTanah;
    }

    public String getHarga() {
        return Harga;
    }

    public void setHarga(String harga) {
        Harga = harga;
    }

    public String getHargaSewa() {
        return HargaSewa;
    }

    public void setHargaSewa(String hargaSewa) {
        HargaSewa = hargaSewa;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getIsSpek() {
        return IsSpek;
    }

    public void setIsSpek(String isSpek) {
        IsSpek = isSpek;
    }
}
