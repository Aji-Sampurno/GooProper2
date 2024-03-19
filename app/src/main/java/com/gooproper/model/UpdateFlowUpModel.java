package com.gooproper.model;

public class UpdateFlowUpModel {
    String IdFlowup, Tanggal, Jam, Chat, Survei, Tawar, Lokasi, Deal;

    public UpdateFlowUpModel() {
    }

    public UpdateFlowUpModel(String IdFlowup, String Tanggal, String Jam, String Chat, String Survei, String Tawar, String Lokasi, String Deal) {
        this.IdFlowup = IdFlowup;
        this.Tanggal = Tanggal;
        this.Jam = Jam;
        this.Chat = Chat;
        this.Survei = Survei;
        this.Tawar = Tawar;
        this.Lokasi = Lokasi;
        this.Deal = Deal;
    }

    public String getIdFlowup() {
        return IdFlowup;
    }

    public void setIdFlowup(String idFlowup) {
        IdFlowup = idFlowup;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getJam() {
        return Jam;
    }

    public void setJam(String jam) {
        Jam = jam;
    }

    public String getChat() {
        return Chat;
    }

    public void setChat(String chat) {
        Chat = chat;
    }

    public String getSurvei() {
        return Survei;
    }

    public void setSurvei(String survei) {
        Survei = survei;
    }

    public String getTawar() {
        return Tawar;
    }

    public void setTawar(String tawar) {
        Tawar = tawar;
    }

    public String getLokasi() {
        return Lokasi;
    }

    public void setLokasi(String lokasi) {
        Lokasi = lokasi;
    }

    public String getDeal() {
        return Deal;
    }

    public void setDeal(String deal) {
        Deal = deal;
    }
}
