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
    // Info Property -------------------------------------------------------------------------------

    // Pra Listing ---------------------------------------------------------------------------------
    public static final String URL_TAMBAH_PRALISTING = BASE_URL + "/Api/PraListingFinal";
    public static final String URL_AJUKAN_ULANG = BASE_URL + "/Api/AjukanUlang";
    public static final String URL_TAMBAH_PRALISTING_LOKASI = BASE_URL + "/Api/PraListingFinalLokasi";
    public static final String URL_UPDATE_PRALISTING = BASE_URL + "/Api/UpdatePraListing";
    public static final String URL_UPDATE_PRALISTING_AGEN = BASE_URL + "/Api/UpdatePraListingAgenFinal";
    public static final String URL_UPDATE_PRALISTING_MAPS = BASE_URL + "/Api/UpdateMapsPraListing";
    public static final String URL_DELETE_PRALISTING = BASE_URL + "/Api/DeletePraListing";
    public static final String URL_GET_PRALISTING_ADMIN = BASE_URL + "/Api/GetPraListingAdmin";
    public static final String URL_GET_PRALISTING_MANAGER = BASE_URL + "/Api/GetPraListingManager";
    public static final String URL_GET_PRALISTING_AGEN = BASE_URL + "/Api/GetPraListingAgen?idagen=";
    public static final String URL_APPROVE_ADMIN = BASE_URL + "/Api/ApproveAdmin";
    public static final String URL_APPROVE_MANAGER = BASE_URL + "/Api/ApproveManager";
    public static final String URL_REJECTED= BASE_URL + "/Api/Rejected";
    public static final String URL_GET_REJECTED_AGEN = BASE_URL + "/Api/GetRejectedAgen?id=";
    public static final String URL_GET_REJECTED = BASE_URL + "/Api/GetRejectedAdmin";
    public static final String URL_GET_KETERANGAN_REJECTED = BASE_URL + "/Api/GetKeteranganRejected?id=";
    // Listing -------------------------------------------------------------------------------------
    public static final String URL_UPDATE_LISTING_MAPS = BASE_URL + "/Api/UpdateMapsListing";
    public static final String URL_TAMBAH_LISTING_PRIMARY = BASE_URL + "/Api/ListingPrimary";
    public static final String URL_TAMBAH_LISTING_SEMENTARA = BASE_URL + "/Api/Sharelokasi";
    public static final String URL_GET_LISTING_SEMENTARA = BASE_URL + "/Api/GetSharelokasi?idagen=";
    public static final String URL_GET_LISTING_PRIMARY = BASE_URL + "/Api/GetListingPrimary";
    public static final String URL_GET_LISTING_LAPORAN = BASE_URL + "/Api/GetLaporanListing";
    public static final String URL_GET_LISTING = BASE_URL + "/Api/GetListing";
    public static final String URL_UPDATE_LISTING = BASE_URL + "/Api/UpdateListing";
    public static final String URL_GET_LISTING_AGEN = BASE_URL + "/Api/GetListingAgen?idagen=";
    public static final String URL_GET_LISTING_DEEP = BASE_URL + "/Api/GetListingDeep?idlisting=";
    public static final String URL_GET_LISTING_SOLD = BASE_URL + "/Api/GetListingSold";
    public static final String URL_GET_LISTING_HOT = BASE_URL + "/Api/GetListingHot";
    public static final String URL_GET_LISTING_FAVORITE= BASE_URL + "/Api/GetFavorite?idcustomer=";
    public static final String URL_GET_LISTING_FAVORITE_AGEN= BASE_URL + "/Api/GetFavoriteAgen?idagen=";
    public static final String URL_GET_LISTING_SEEN= BASE_URL + "/Api/GetSeen?idcustomer=";
    public static final String URL_GET_LISTING_SEEN_AGEN= BASE_URL + "/Api/GetSeenAgen?idagen=";
    public static final String URL_GET_CO_LISTING = BASE_URL + "/Api/GetCoListing?idco=";
    public static final String URL_ADD_VIEWS = BASE_URL + "/Api/AddViews";
    public static final String URL_ADD_SEEN = BASE_URL + "/Api/AddSeen";
    public static final String URL_ADD_FAVORITE = BASE_URL + "/Api/AddFavorite";
    public static final String URL_COUNT_LIKE = BASE_URL + "/Api/CountLike?idlisting=";
    public static final String URL_COUNT_SEWA = BASE_URL + "/Api/CountSewa?idagen=";
    public static final String URL_COUNT_JUAL = BASE_URL + "/Api/CountJual?idagen=";
    public static final String URL_COUNT_LISTING = BASE_URL + "/Api/CountListing?idagen=";
    // Follow Up -----------------------------------------------------------------------------------
    public static final String URL_ADD_FLOWUP = BASE_URL + "/Api/AddFlowup";
    public static final String URL_UPDATE_FLOWUP = BASE_URL + "/Api/UpdateFlowup";
    public static final String URL_GET_FLOWUP_AGEN = BASE_URL + "/Api/GetFlowUpAgen";
    public static final String URL_GET_FLOWUP = BASE_URL + "/Api/GetFlowUp?idagen=";
    public static final String URL_ADD_FLOWUP_PRIMARY = BASE_URL + "/Api/AddFlowupPrimary";
    public static final String URL_UPDATE_FLOWUP_PRIMARY = BASE_URL + "/Api/UpdateFlowupPrimary";
    public static final String URL_GET_FLOWUP_PRIMARY_AGEN = BASE_URL + "/Api/GetFlowUpPrimaryAgen";
    public static final String URL_GET_FLOWUP_PRIMARY = BASE_URL + "/Api/GetFlowUpPrimary?idagen=";
    // Closing -------------------------------------------------------------------------------------
    public static final String URL_CLOSING = BASE_URL + "/Api/Closing";
    public static final String URL_SOLD = BASE_URL + "/Api/Sold";
    public static final String URL_RENTED = BASE_URL + "/Api/Rented";
    public static final String URL_SOLD_AGEN = BASE_URL + "/Api/SoldAgen";
    public static final String URL_RENTED_AGEN = BASE_URL + "/Api/RentedAgen";
    // User ----------------------------------------------------------------------------------------
    public static final String URL_TAMBAH_KARYAWAN = BASE_URL + "/Api/AddKaryawan";
    public static final String URL_APPROVE_AGEN = BASE_URL + "/Api/ApproveAgen";
    public static final String URL_GET_AGEN = BASE_URL + "/Api/GetAgen";
    public static final String URL_GET_AGEN_DEEP = BASE_URL + "/Api/GetAgenDeep?idagen=";
    public static final String URL_GET_DAFTAR_AGEN = BASE_URL + "/Api/GetDaftarAgen";
    public static final String URL_GET_PELAMAR_AGEN = BASE_URL + "/Api/GetPelamarAgen";
    public static final String URL_GET_PELAMAR_MITRA = BASE_URL + "/Api/GetPelamarMitra";
    public static final String URL_GET_PELAMAR_KANTORLAIN = BASE_URL + "/Api/GetPelamarKantorLain";
    public static final String URL_ADD_DEVICE = BASE_URL + "/Api/AddDevice";
    public static final String URL_GET_DEVICE = BASE_URL + "/Api/GetDevice";
    public static final String URL_ADD_DEVICE_AGEN = BASE_URL + "/Api/AddDeviceAgen";
    public static final String URL_GET_DEVICE_AGEN = BASE_URL + "/Api/GetDeviceByAgen?idagen=";
    public static final String URL_DELETE_DEVICE_AGEN = BASE_URL + "/Api/DeleteDeviceAgen";
    public static final String URL_DELETE_IMAGE_1 = BASE_URL + "/Api/DeleteImages1";
    public static final String URL_DELETE_IMAGE_2 = BASE_URL + "/Api/DeleteImages2";
    public static final String URL_DELETE_IMAGE_3 = BASE_URL + "/Api/DeleteImages3";
    public static final String URL_DELETE_IMAGE_4 = BASE_URL + "/Api/DeleteImages4";
    public static final String URL_DELETE_IMAGE_5 = BASE_URL + "/Api/DeleteImages5";
    public static final String URL_DELETE_IMAGE_6 = BASE_URL + "/Api/DeleteImages6";
    public static final String URL_DELETE_IMAGE_7 = BASE_URL + "/Api/DeleteImages7";
    public static final String URL_DELETE_IMAGE_8 = BASE_URL + "/Api/DeleteImages8";
    // Primary -------------------------------------------------------------------------------------
    public static final String URL_TAMBAH_PRIMARY = BASE_URL + "/Api/TambahPrimary";
    public static final String URL_TAMBAH_TIPE_PRIMARY = BASE_URL + "/Api/TambahTipePrimary";
    public static final String URL_GET_PRIMARY = BASE_URL + "/Api/GetPrimary";
    public static final String URL_GET_TIPE_PRIMARY = BASE_URL + "/Api/GetTipePrimary?id=";
    public static final String URL_DELETE_PRIMARY = BASE_URL + "/Api/DeletePrimary";
    // Info ----------------------------------------------------------------------------------------
    public static final String URL_TAMBAH_INFO = BASE_URL + "/Api/TambahInfo";
    public static final String URL_UPDATE_INFO = BASE_URL + "/Api/UpdateInfo";
    public static final String URL_GET_INFO = BASE_URL + "/Api/GetInfo";
    public static final String URL_GET_INFO_AGEN = BASE_URL + "/Api/GetInfoAgen";
    public static final String URL_GET_PRAINFO = BASE_URL + "/Api/GetPraInfo";
    public static final String URL_LISTING_INFO = BASE_URL + "/Api/ListingInfo";
    public static final String URL_GET_AGEN_INFO = BASE_URL + "/Api/GetAgenInfo?id=";

}

