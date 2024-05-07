package com.gooproper.model;

public class ReportKinerjaModel {
    String IdListing,
            NamaListing,
            Alamat,
            Location,
            Wilayah,
            JenisProperti,
            Kondisi,
            Priority,
            Img1,
            Img2,
            Template,
            TemplateBlank,
            Keterangan;

    public ReportKinerjaModel() {
    }

    public ReportKinerjaModel(String IdListing,
                              String NamaListing,
                              String Alamat,
                              String Location,
                              String Wilayah,
                              String JenisProperti,
                              String Kondisi,
                              String Priority,
                              String Img1,
                              String Img2,
                              String Template,
                              String TemplateBlank,
                              String Keterangan) {
        this.IdListing = IdListing;
        this.NamaListing = NamaListing;
        this.Alamat = Alamat;
        this.Location = Location;
        this.Wilayah = Wilayah;
        this.JenisProperti = JenisProperti;
        this.Kondisi = Kondisi;
        this.Priority = Priority;
        this.Img1 = Img1;
        this.Img2 = Img2;
        this.Keterangan = Keterangan;
        this.Template = Template;
        this.TemplateBlank =  TemplateBlank;
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

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getWilayah() {
        return Wilayah;
    }

    public void setWilayah(String wilayah) {
        Wilayah = wilayah;
    }

    public String getJenisProperti() {
        return JenisProperti;
    }

    public void setJenisProperti(String jenisProperti) {
        JenisProperti = jenisProperti;
    }

    public String getKondisi() {
        return Kondisi;
    }

    public void setKondisi(String kondisi) {
        Kondisi = kondisi;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getImg1() {
        return Img1;
    }

    public void setImg1(String img1) {
        Img1 = img1;
    }

    public String getImg2() {
        return Img2;
    }

    public void setImg2(String img2) {
        Img2 = img2;
    }

    public String getTemplate() {
        return Template;
    }

    public void setTemplate(String template) {
        Template = template;
    }

    public String getTemplateBlank() {
        return TemplateBlank;
    }

    public void setTemplateBlank(String templateBlank) {
        TemplateBlank = templateBlank;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }
}
