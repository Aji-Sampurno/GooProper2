package com.gooproper.util;

public class ServerApi {

    private static final String BASE_URL = "https://app.gooproper.com";
    // Akun ----------------------------------------------------------------------------------------
    public static final String URL_LOGIN = BASE_URL + "/Api/Login";
    public static final String URL_UBAH_SANDI_ADMIN = BASE_URL + "/Api/UbahSandiAdmin";
    public static final String URL_UBAH_SANDI_AGEN = BASE_URL + "/Api/UbahSandiAgen";
    public static final String URL_UBAH_SANDI_CUSTOMER = BASE_URL + "/Api/UbahSandiCustomer";
    public static final String URL_REGISTRASI = BASE_URL + "/Api/Register";
    public static final String URL_REGISTRASI_AGEN = BASE_URL + "/Api/RegisterAgen";
    public static final String URL_REGISTRASI_MITRA = BASE_URL + "/Api/RegisterMitra";
    public static final String URL_REGISTRASI_KL = BASE_URL + "/Api/RegisterKl";
    public static final String URL_UPDATE_CUSTOMER = BASE_URL + "/Api/UpdateAkun";
    public static final String URL_UPDATE_AGEN = BASE_URL + "/Api/UpdateAgen";
    public static final String URL_UPDATE_MITRA = BASE_URL + "/Api/UpdateMitra";
    public static final String URL_UPDATE_KL = BASE_URL + "/Api/UpdateKl";
    public static final String URL_UPDATE_ADMIN = BASE_URL + "/Api/UpdateAdmin";
    // Pra Listing ---------------------------------------------------------------------------------
    public static final String URL_TAMBAH_PRALISTING = BASE_URL + "/Api/PraListing";
    public static final String URL_UPDATE_PRALISTING = BASE_URL + "/Api/UpdatePraListing";
    public static final String URL_UPDATE_PRALISTING_AGEN = BASE_URL + "/Api/UpdatePraListingAgen";
    public static final String URL_UPDATE_PRALISTING_MAPS = BASE_URL + "/Api/UpdateMapsPraListing";
    public static final String URL_DELETE_PRALISTING = BASE_URL + "/Api/DeletePraListing";
    public static final String URL_GET_PRALISTING_ADMIN = BASE_URL + "/Api/GetPraListingAdmin";
    public static final String URL_GET_PRALISTING_MANAGER = BASE_URL + "/Api/GetPraListingManager";
    public static final String URL_GET_PRALISTING_AGEN = BASE_URL + "/Api/GetPraListingAgen?idagen=";
    public static final String URL_APPROVE_ADMIN = BASE_URL + "/Api/ApproveAdmin";
    public static final String URL_APPROVE_MANAGER = BASE_URL + "/Api/ApproveManager";
    // Listing -------------------------------------------------------------------------------------
    public static final String URL_GET_LISTING = BASE_URL + "/Api/GetListing";
    public static final String URL_GET_LISTING_AGEN = BASE_URL + "/Api/GetListingAgen?idagen=";
    public static final String URL_GET_LISTING_DEEP = BASE_URL + "/Api/GetListingDeep?idlisting=";
    public static final String URL_GET_LISTING_SOLD = BASE_URL + "/Api/GetListingSold";
    public static final String URL_GET_LISTING_HOT = BASE_URL + "/Api/GetListingHot";
    public static final String URL_GET_LISTING_FAVORITE= BASE_URL + "/Api/GetFavorite?idcustomer=";
    public static final String URL_GET_LISTING_FAVORITE_AGEN= BASE_URL + "/Api/GetFavoriteAgen?idagen=";
    public static final String URL_GET_LISTING_SEEN= BASE_URL + "/Api/GetSeen?idcustomer=";
    public static final String URL_GET_LISTING_SEEN_AGEN= BASE_URL + "/Api/GetSeenAgen?idagen=";
    public static final String URL_ADD_VIEWS = BASE_URL + "/Api/AddViews";
    public static final String URL_ADD_SEEN = BASE_URL + "/Api/AddSeen";
    public static final String URL_ADD_FAVORITE = BASE_URL + "/Api/AddFavorite";
    public static final String URL_COUNT_SEWA = BASE_URL + "/Api/CountSewa?idagen=";
    public static final String URL_COUNT_JUAL = BASE_URL + "/Api/CountJual?idagen=";
    public static final String URL_COUNT_LISTING = BASE_URL + "/Api/CountListing?idagen=";
    // Follow Up -----------------------------------------------------------------------------------
    public static final String URL_ADD_FLOWUP = BASE_URL + "/Api/AddFlowup";
    public static final String URL_UPDATE_FLOWUP = BASE_URL + "/Api/UpdateFlowup";
    public static final String URL_GET_FLOWUP_AGEN = BASE_URL + "/Api/GetFlowUpAgen";
    public static final String URL_GET_FLOWUP = BASE_URL + "/Api/GetFlowUp?idagen=";
    // Closing -------------------------------------------------------------------------------------
    public static final String URL_CLOSING = BASE_URL + "/Api/Closing";
    // User ----------------------------------------------------------------------------------------
    public static final String URL_TAMBAH_KARYAWAN = BASE_URL + "/Api/AddKaryawan";
    public static final String URL_APPROVE_AGEN = BASE_URL + "/Api/ApproveAgen";
    public static final String URL_GET_AGEN = BASE_URL + "/Api/GetAgen";
    public static final String URL_GET_AGEN_DEEP = BASE_URL + "/Api/GetAgenDeep?idagen=";
    public static final String URL_GET_DAFTAR_AGEN = BASE_URL + "/Api/GetDaftarAgen";
    public static final String URL_GET_PELAMAR_AGEN = BASE_URL + "/Api/GetPelamarAgen";
    public static final String URL_GET_PELAMAR_MITRA = BASE_URL + "/Api/GetPelamarMitra";
    public static final String URL_GET_PELAMAR_KANTORLAIN = BASE_URL + "/Api/GetPelamarKantorLain";

}

