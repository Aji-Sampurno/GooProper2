package com.gooproper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    static final String KeyIdAdmin        = "IdAdmin";
    static final String KeyIdAgen         = "IdAgen";
    static final String KeyIdCustomer     = "IdCustomer";
    static final String KeyUsername       = "Username";
    static final String KeyNamaLengkap    = "NamaLengkap";
    static final String KeyNama           = "Nama";
    static final String KeyNoTelp         = "NoTelp";
    static final String KeyAlamat         = "Alamat";
    static final String KeyTglLahir       = "TglLahir";
    static final String KeyEmail          = "Email";
    static final String KeyPhoto          = "Photo";
    static final String KeyPassword       = "Password";
    static final String KeyKotaKelahiran  = "KotaKelahiran";
    static final String KeyPendidikan     = "Pendidikan";
    static final String KeyNamaSekolah    = "NamaSekolah";
    static final String KeyMasaKerja      = "MasaKerja";
    static final String KeyJabatan        = "Jabatan";
    static final String KeyStatus         = "Status";
    static final String KeyAlamatDomisili = "AlamatDomisili";
    static final String KeyFacebook       = "Facebook";
    static final String KeyInstagram      = "Instagram";
    static final String KeyNoKtp          = "NoKtp";
    static final String KeyImgKtp         = "ImgKtp";
    static final String KeyImgTtd         = "ImgTtd";
    static final String KeyNpwp           = "Npwp";
    static final String KeyPoin           = "Poin";
    static final String KeyIsAkses        = "IsAkses";

    public static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setKeyIdAdmin(Context context, String IdAdmin){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyIdAdmin, IdAdmin);
        editor.apply();
    }

    public static String getKeyIdAdmin(Context context) {
        return getSharedPreference(context).getString(KeyIdAdmin,"");
    }

    public static void setKeyIdAgen(Context context, String IdAgen){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyIdAgen, IdAgen);
        editor.apply();
    }

    public static String getKeyIdAgen(Context context) {
        return getSharedPreference(context).getString(KeyIdAgen,"");
    }

    public static void setKeyIdCustomer(Context context, String Customer){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyIdCustomer, Customer);
        editor.apply();
    }

    public static String getKeyIdCustomer(Context context) {
        return getSharedPreference(context).getString(KeyIdCustomer,"");
    }

    public static void setKeyUsername(Context context, String Username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyUsername, Username);
        editor.apply();
    }

    public static String getKeyUsername(Context context) {
        return getSharedPreference(context).getString(KeyUsername,"");
    }

    public static void setKeyNamaLengkap(Context context, String NamaLengkap){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyNamaLengkap, NamaLengkap);
        editor.apply();
    }

    public static String getKeyNamaLengkap(Context context) {
        return getSharedPreference(context).getString(KeyNamaLengkap,"");
    }

    public static void setKeyNama(Context context, String Nama){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyNama, Nama);
        editor.apply();
    }

    public static String getKeyNama(Context context) {
        return getSharedPreference(context).getString(KeyNama,"");
    }

    public static void setKeyNoTelp(Context context, String NoTelp){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyNoTelp, NoTelp);
        editor.apply();
    }

    public static String getKeyNoTelp(Context context) {
        return getSharedPreference(context).getString(KeyNoTelp,"");
    }

    public static void setKeyAlamat(Context context, String Alamat){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyAlamat, Alamat);
        editor.apply();
    }

    public static String getKeyAlamat(Context context) {
        return getSharedPreference(context).getString(KeyAlamat,"");
    }

    public static void setKeyTglLahir(Context context, String TglLAhir){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyTglLahir, TglLAhir);
        editor.apply();
    }

    public static String getKeyTglLahir(Context context) {
        return getSharedPreference(context).getString(KeyTglLahir,"");
    }

    public static void setKeyEmail(Context context, String Email){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyEmail, Email);
        editor.apply();
    }

    public static String getKeyEmail(Context context) {
        return getSharedPreference(context).getString(KeyEmail,"");
    }

    public static void setKeyPhoto(Context context, String Photo){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyPhoto, Photo);
        editor.apply();
    }

    public static String getKeyPhoto(Context context) {
        return getSharedPreference(context).getString(KeyPhoto,"");
    }

    public static void setKeyPassword(Context context, String Password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyPassword, Password);
        editor.apply();
    }

    public static String getKeyPassword(Context context) {
        return getSharedPreference(context).getString(KeyPassword,"");
    }

    public static void setKeyKotaKelahiran(Context context, String KotaKelahiran){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyKotaKelahiran, KotaKelahiran);
        editor.apply();
    }

    public static String getKeyKotaKelahiran(Context context) {
        return getSharedPreference(context).getString(KeyKotaKelahiran,"");
    }

    public static void setKeyPendidikan(Context context, String Pendidikan){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyPendidikan, Pendidikan);
        editor.apply();
    }

    public static String getKeyPendidikan(Context context) {
        return getSharedPreference(context).getString(KeyPendidikan,"");
    }

    public static void setKeyNamaSekolah(Context context, String NamaSekolah){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyNamaSekolah, NamaSekolah);
        editor.apply();
    }

    public static String getKeyNamaSekolah(Context context) {
        return getSharedPreference(context).getString(KeyNamaSekolah,"");
    }

    public static void setKeyMasaKerja(Context context, String MasaKerja){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyMasaKerja, MasaKerja);
        editor.apply();
    }

    public static String getKeyMasaKerja(Context context) {
        return getSharedPreference(context).getString(KeyMasaKerja,"");
    }

    public static void setKeyJabatan(Context context, String Jabatan){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyJabatan, Jabatan);
        editor.apply();
    }

    public static String getKeyJabatan(Context context) {
        return getSharedPreference(context).getString(KeyJabatan,"");
    }

    public static void setKeyStatus(Context context, String Status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyStatus, Status);
        editor.apply();
    }

    public static String getKeyStatus(Context context) {
        return getSharedPreference(context).getString(KeyStatus,"");
    }

    public static void setKeyAlamatDomisili(Context context, String AlamatDomisili){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyAlamatDomisili, AlamatDomisili);
        editor.apply();
    }

    public static String getKeyAlamatDomisili(Context context) {
        return getSharedPreference(context).getString(KeyAlamatDomisili,"");
    }

    public static void setKeyFacebook(Context context, String Facebook){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyFacebook, Facebook);
        editor.apply();
    }

    public static String getKeyFacebook(Context context) {
        return getSharedPreference(context).getString(KeyFacebook,"");
    }

    public static void setKeyInstagram(Context context, String Instagram){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyInstagram, Instagram);
        editor.apply();
    }

    public static String getKeyInstagram(Context context) {
        return getSharedPreference(context).getString(KeyInstagram,"");
    }

    public static void setKeyNoKtp(Context context, String NoKtp){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyNoKtp, NoKtp);
        editor.apply();
    }

    public static String getKeyNoKtp(Context context) {
        return getSharedPreference(context).getString(KeyNoKtp,"");
    }

    public static void setKeyImgKtp(Context context, String ImgKtp){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyImgKtp, ImgKtp);
        editor.apply();
    }

    public static String getKeyImgKtp(Context context) {
        return getSharedPreference(context).getString(KeyImgKtp,"");
    }

    public static void setKeyImgTtd(Context context, String ImageTtd){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyImgTtd, ImageTtd);
        editor.apply();
    }

    public static String getKeyImgTtd(Context context) {
        return getSharedPreference(context).getString(KeyImgTtd,"");
    }

    public static void setKeyNpwp(Context context, String Npwp){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyNpwp, Npwp);
        editor.apply();
    }

    public static String getKeyNpwp(Context context) {
        return getSharedPreference(context).getString(KeyNpwp,"");
    }

    public static void setKeyPoin(Context context, String Poin){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyPoin, Poin);
        editor.apply();
    }

    public static String getKeyPoin(Context context) {
        return getSharedPreference(context).getString(KeyPoin,"");
    }

    public static void setKeyIsAkses(Context context, String IsAkses){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyIsAkses, IsAkses);
        editor.apply();
    }

    public static String getKeyIsAkses(Context context) {
        return getSharedPreference(context).getString(KeyIsAkses,"");
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KeyIdAdmin);
        editor.remove(KeyIdAgen);
        editor.remove(KeyIdCustomer);
        editor.remove(KeyNamaLengkap);
        editor.remove(KeyNama);
        editor.remove(KeyNoTelp);
        editor.remove(KeyAlamat);
        editor.remove(KeyTglLahir);
        editor.remove(KeyEmail);
        editor.remove(KeyPhoto);
        editor.remove(KeyPassword);
        editor.remove(KeyKotaKelahiran);
        editor.remove(KeyPendidikan);
        editor.remove(KeyNamaSekolah);
        editor.remove(KeyMasaKerja);
        editor.remove(KeyJabatan);
        editor.remove(KeyStatus);
        editor.remove(KeyAlamatDomisili);
        editor.remove(KeyFacebook);
        editor.remove(KeyInstagram);
        editor.remove(KeyNoKtp);
        editor.remove(KeyImgKtp);
        editor.remove(KeyImgTtd);
        editor.remove(KeyNpwp);
        editor.remove(KeyPoin);
        editor.remove(KeyIsAkses);
        editor.apply();
    }


}
