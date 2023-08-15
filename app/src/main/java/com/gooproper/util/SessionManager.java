package com.gooproper.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    public static final String PREF_NAME      = "LOGIN";
    public static final String LOGIN          = "IS_LOGIN";
    public static final String IdAdmin        = "IdAdmin";
    public static final String IdAgen         = "IdAgen";
    public static final String IdCustomer     = "IdCustomer";
    public static final String Username       = "Username";
    public static final String NamaLengkap    = "NamaLengkap";
    public static final String Nama           = "Nama";
    public static final String NoTelp         = "NoTelp";
    public static final String Alamat         = "Alamat";
    public static final String TglLahir       = "TglLahir";
    public static final String Email          = "Email";
    public static final String Photo          = "Photo";
    public static final String Password       = "Password";
    public static final String KotaKelahiran  = "KotaKelahiran";
    public static final String Pendidikan     = "Pendidikan";
    public static final String NamaSekolah    = "NamaSekolah";
    public static final String MasaKerja      = "MasaKerja";
    public static final String Jabatan        = "Jabatan";
    public static final String Status         = "Status";
    public static final String AlamatDomisili = "AlamatDomisili";
    public static final String Facebook       = "Facebook";
    public static final String Instagram      = "Instagram";
    public static final String NoKtp          = "NoKtp";
    public static final String ImgKtp         = "ImgKtp";
    public static final String ImgTtd         = "ImgTtd";
    public static final String Npwp           = "Npwp";
    public static final String Poin           = "Poin";
    public static final String IsAkses        = "IsAkses";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createsession(String IdAdmin, String IdAgen, String IdCustomer,String Username, String NamaLengkap, String Nama, String NoTelp, String Alamat, String TglLahir, String Email, String Photo, String Password, String KotaKelahiran, String Pendidikan, String NamaSekolah, String MasaKerja, String Jabatan, String Status, String AlamatDomisili, String Facebook, String Instagram, String NoKtp, String ImgKtp, String ImgTtd, String Npwp, String Poin, String IsAkses){
        editor.putBoolean(LOGIN, true);
        editor.putString(IdAdmin,IdAdmin);
        editor.putString(IdAgen,IdAgen);
        editor.putString(IdCustomer,IdCustomer);
        editor.putString(Username,Username);
        editor.putString(NamaLengkap,NamaLengkap);
        editor.putString(Nama,Nama);
        editor.putString(NoTelp,NoTelp);
        editor.putString(Alamat,Alamat);
        editor.putString(TglLahir,TglLahir);
        editor.putString(Email,Email);
        editor.putString(Photo,Photo);
        editor.putString(Password,Password);
        editor.putString(KotaKelahiran,KotaKelahiran);
        editor.putString(Pendidikan,Pendidikan);
        editor.putString(NamaSekolah,NamaSekolah);
        editor.putString(MasaKerja,MasaKerja);
        editor.putString(Jabatan,Jabatan);
        editor.putString(Status,Status);
        editor.putString(AlamatDomisili,AlamatDomisili);
        editor.putString(Facebook,Facebook);
        editor.putString(Instagram,Instagram);
        editor.putString(NoKtp,NoKtp);
        editor.putString(ImgKtp,ImgKtp);
        editor.putString(ImgTtd,ImgTtd);
        editor.putString(Npwp,Npwp);
        editor.putString(Poin,Poin);
        editor.putString(IsAkses,IsAkses);
        editor.apply();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(IdAdmin, sharedPreferences.getString(IdAdmin,null));
        user.put(IdAgen, sharedPreferences.getString(IdAgen,null));
        user.put(IdCustomer, sharedPreferences.getString(IdCustomer,null));
        user.put(Username, sharedPreferences.getString(Username,null));
        user.put(NamaLengkap, sharedPreferences.getString(NamaLengkap,null));
        user.put(Nama, sharedPreferences.getString(Nama,null));
        user.put(NoTelp, sharedPreferences.getString(NoTelp,null));
        user.put(Alamat, sharedPreferences.getString(Alamat,null));
        user.put(TglLahir, sharedPreferences.getString(TglLahir,null));
        user.put(Email, sharedPreferences.getString(Email,null));
        user.put(Photo, sharedPreferences.getString(Photo,null));
        user.put(Password, sharedPreferences.getString(Password,null));
        user.put(KotaKelahiran, sharedPreferences.getString(KotaKelahiran,null));
        user.put(Pendidikan, sharedPreferences.getString(Pendidikan,null));
        user.put(NamaSekolah, sharedPreferences.getString(NamaSekolah,null));
        user.put(MasaKerja, sharedPreferences.getString(MasaKerja,null));
        user.put(Jabatan, sharedPreferences.getString(Jabatan,null));
        user.put(Status, sharedPreferences.getString(Status,null));
        user.put(AlamatDomisili, sharedPreferences.getString(AlamatDomisili,null));
        user.put(Facebook, sharedPreferences.getString(Facebook,null));
        user.put(Instagram, sharedPreferences.getString(Instagram,null));
        user.put(NoKtp, sharedPreferences.getString(NoKtp,null));
        user.put(ImgKtp, sharedPreferences.getString(ImgKtp,null));
        user.put(ImgTtd, sharedPreferences.getString(ImgTtd,null));
        user.put(Npwp, sharedPreferences.getString(Npwp,null));
        user.put(Poin, sharedPreferences.getString(Poin,null));
        user.put(IsAkses, sharedPreferences.getString(IsAkses,null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
