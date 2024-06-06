package com.gooproper.util;

public class ServerApi {

    private static final String BASE_URL = "https://app.gooproper.com";

    // Akun ==================================================================================================================================================================
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
    public static final String URL_CEK_AKTIF = BASE_URL + "/Api/CekAktif?idagen=";
    public static final String URL_CEK_OFFICER = BASE_URL + "/Api/CekOfficer?idagen=";

    // Laporan ===============================================================================================================================================================
    public static final String URL_COUNT_LISTING_JUAL = BASE_URL + "/Api/CountListingSold";
    public static final String URL_COUNT_LISTING_SEWA = BASE_URL + "/Api/CountListingRent";
    public static final String URL_COUNT_LISTING_JUALSEWA = BASE_URL + "/Api/CountListingSoldRent";
    public static final String URL_COUNT_LISTING_TERJUAL = BASE_URL + "/Api/CountListingSolded";
    public static final String URL_COUNT_LISTING_TERSEWA = BASE_URL + "/Api/CountListingRented";
    public static final String URL_COUNT_LISTING_READY = BASE_URL + "/Api/CountListingReady";
    public static final String URL_COUNT_LISTING_ALL = BASE_URL + "/Api/CountListingAll";
    public static final String URL_COUNT_LISTING_TAHUN = BASE_URL + "/Api/CountListingTahun";
    public static final String URL_COUNT_LISTING_BULAN = BASE_URL + "/Api/CountListingBulan";
    public static final String URL_GET_INFO_LAPORAN = BASE_URL + "/Api/GetLaporanInfo";
    public static final String URL_GET_LAPORAN_SUSULAN = BASE_URL + "/Api/GetLaporanSusulan";

    // Admin =================================================================================================================================================================
        // Get -------------------------------------------------------------------------------------
    public static final String URL_GET_REPORT_AGEN = BASE_URL + "/Api/GetReportAgen";
    public static final String URL_GET_AGEN_AKTIF = BASE_URL + "/Api/LastSeenAgenAktif";
    public static final String URL_GET_AGEN_TIDAK_AKTIF = BASE_URL + "/Api/LastSeenAgenTidakAktif";
        // Update ----------------------------------------------------------------------------------
    public static final String URL_APPROVE_AGEN = BASE_URL + "/Api/ApproveAgen";
    public static final String URL_REJECT_AGEN = BASE_URL + "/Api/RejectAgen";
        // Count -----------------------------------------------------------------------------------
    public static final String URL_COUNT_PELAMAR = BASE_URL + "/Api/CountPelamar";

    // Tampungan =============================================================================================================================================================
        // Add -------------------------------------------------------------------------------------
    public static final String URL_TAMBAH_LISTING_SEMENTARA = BASE_URL + "/Api/Sharelokasi";
        // Update ----------------------------------------------------------------------------------
        // Delete ----------------------------------------------------------------------------------
        // Get -------------------------------------------------------------------------------------
    public static final String URL_GET_LISTING_SEMENTARA = BASE_URL + "/Api/GetSharelokasi?idagen=";

    // Info ==================================================================================================================================================================
        // Add -------------------------------------------------------------------------------------
    public static final String URL_TAMBAH_INFO = BASE_URL + "/Api/TambahInfo";
    public static final String URL_TAMBAH_DETAIL_INFO = BASE_URL + "/Api/TambahDetailInfo";
        // Update ----------------------------------------------------------------------------------
    public static final String URL_EDIT_INFO = BASE_URL + "/Api/EditInfo";
    public static final String URL_UPDATE_INFO = BASE_URL + "/Api/UpdateInfo";
        // Delete ----------------------------------------------------------------------------------
        // Get -------------------------------------------------------------------------------------
    public static final String URL_GET_INFO = BASE_URL + "/Api/GetInfo";
    public static final String URL_GET_INFO_AGEN = BASE_URL + "/Api/GetInfoAgen?idagen=";
    public static final String URL_GET_PRAINFO = BASE_URL + "/Api/GetPraInfo";
    public static final String URL_LISTING_INFO = BASE_URL + "/Api/ListingInfo";
    public static final String URL_GET_AGEN_INFO = BASE_URL + "/Api/GetAgenInfo?id=";

    // Pra Listing ===========================================================================================================================================================
        // Add -------------------------------------------------------------------------------------
    public static final String URL_TAMBAH_PRALISTING = BASE_URL + "/Api/PraListingBaru";
    public static final String URL_TAMBAH_PRALISTING_LOKASI = BASE_URL + "/Api/PraListingLokasiBaru";
    public static final String URL_TAMBAH_PRALISTING_INFO = BASE_URL + "/Api/PraListingInfoBaru";
        // Update ----------------------------------------------------------------------------------
    public static final String URL_UPDATE_PRALISTING = BASE_URL + "/Api/UpdatePraListing";
    public static final String URL_UPDATE_PRALISTING_AGEN = BASE_URL + "/Api/UpdatePraListingAgen";
    public static final String URL_UPDATE_PRALISTING_MAPS = BASE_URL + "/Api/UpdateMapsPraListing";
    public static final String URL_UPDATE_PRALISTING_SELFIE = BASE_URL + "/Api/UpdateSelfiePraListing";
    public static final String URL_UPDATE_LISTING_SELFIE = BASE_URL + "/Api/TambahSelfie";
        // Get -------------------------------------------------------------------------------------
    public static final String URL_GET_PRALISTING_ADMIN = BASE_URL + "/Api/GetPraListingAdminBaru";
    public static final String URL_GET_PRALISTING_MANAGER = BASE_URL + "/Api/GetPraListingManagerBaru";
    public static final String URL_GET_PRALISTING_AGEN = BASE_URL + "/Api/GetPraListingAgen?idagen=";
    public static final String URL_GET_PRALISTING_TERDEKAT = BASE_URL + "/Api/GetPraListingTerdekat";
    public static final String URL_GET_PRALISTING_SURVEY = BASE_URL + "/Api/GetPraListingSurvey?id=";
        // Delete ----------------------------------------------------------------------------------
    public static final String URL_DELETE_PRALISTING = BASE_URL + "/Api/DeletePraListing";
        // Template --------------------------------------------------------------------------------
    public static final String URL_UPLOAD_TEMPLATE = BASE_URL + "/Api/UploadTemplate";
    public static final String URL_UPLOAD_UPDATE_TEMPLATE = BASE_URL + "/Api/UploadUpdateTemplate";
        // Approve ---------------------------------------------------------------------------------
    public static final String URL_APPROVE_ADMIN = BASE_URL + "/Api/ApproveAdmin";
    public static final String URL_APPROVE_MANAGER = BASE_URL + "/Api/ApproveManager";
        // Rejected --------------------------------------------------------------------------------
    public static final String URL_AJUKAN_ULANG = BASE_URL + "/Api/AjukanUlang";
    public static final String URL_REJECTED= BASE_URL + "/Api/Rejected";
    public static final String URL_GET_REJECTED_AGEN = BASE_URL + "/Api/GetRejectedAgen?id=";
    public static final String URL_GET_REJECTED = BASE_URL + "/Api/GetRejectedAdmin";
    public static final String URL_GET_KETERANGAN_REJECTED = BASE_URL + "/Api/GetKeteranganRejected?id=";
        // Count -----------------------------------------------------------------------------------
    public static final String URL_COUNT_PRALISTING_TERDEKAT = BASE_URL + "/Api/CountPraListingTerdekat";
    public static final String URL_COUNT_PRALISTING_AGEN = BASE_URL + "/Api/CountPraListingAgen?id=";
    public static final String URL_COUNT_PRALISTING_AGEN_REJECTED = BASE_URL + "/Api/CountPraListingAgenRejected?id=";
    public static final String URL_COUNT_PRALISTING_REJECTED = BASE_URL + "/Api/CountPraListingRejected";
    public static final String URL_COUNT_PRALISTING_ADMIN = BASE_URL + "/Api/CountPraListingAdmin";
    public static final String URL_COUNT_PRALISTING_MANAGER = BASE_URL + "/Api/CountPraListingManager";
    public static final String URL_COUNT_LISTING_PENDING = BASE_URL + "/Api/CountListingPending";

    // Primary ===============================================================================================================================================================
        // Add -------------------------------------------------------------------------------------
    public static final String URL_TAMBAH_LISTING_PRIMARY = BASE_URL + "/Api/ListingPrimary";
    public static final String URL_TAMBAH_PRIMARY = BASE_URL + "/Api/TambahPrimary";
    public static final String URL_TAMBAH_TIPE_PRIMARY = BASE_URL + "/Api/TambahTipePrimary";
        // Update ----------------------------------------------------------------------------------
    public static final String URL_UPDATE_PRIMARY = BASE_URL + "/Api/UpdatePrimary";
    public static final String URL_UPDATE_TIPE_PRIMARY = BASE_URL + "/Api/UpdateTipePrimary";
        // Delete ----------------------------------------------------------------------------------
    public static final String URL_DELETE_PRIMARY = BASE_URL + "/Api/DeletePrimary";
        // Get -------------------------------------------------------------------------------------
    public static final String URL_GET_LISTING_PRIMARY = BASE_URL + "/Api/GetListingPrimary";
    public static final String URL_GET_PRIMARY = BASE_URL + "/Api/GetPrimary";
    public static final String URL_GET_TIPE_PRIMARY = BASE_URL + "/Api/GetTipePrimary?id=";

    // Listing ===============================================================================================================================================================
        // Add -------------------------------------------------------------------------------------
    public static final String URL_ADD_VIEWS = BASE_URL + "/Api/AddViews";
    public static final String URL_ADD_SEEN = BASE_URL + "/Api/AddSeen";
    public static final String URL_ADD_FAVORITE = BASE_URL + "/Api/AddFavorite";
        // Update ----------------------------------------------------------------------------------
    public static final String URL_UPDATE_LISTING_MAPS = BASE_URL + "/Api/TambahMaps";
    public static final String URL_UPDATE_LISTING = BASE_URL + "/Api/UpdateListingBaru";
    public static final String URL_TAMBAH_BANNER = BASE_URL + "/Api/TambahBanner";
    public static final String URL_TAMBAH_COLIST = BASE_URL + "/Api/TambahCoList";
    public static final String URL_TAMBAH_PJP = BASE_URL + "/Api/TambahPjp";
    public static final String URL_TAMBAH_WILAYAH = BASE_URL + "/Api/TambahWilayah";
    public static final String URL_TAMBAH_NO_ARSIP = BASE_URL + "/Api/TambahNoArsip";
    public static final String URL_TAMBAH_GAMBAR_LISTING = BASE_URL + "/Api/TambahGambarListing";
    public static final String URL_APPROVE_PENDING = BASE_URL + "/Api/ApprovePending";
        // Delete ----------------------------------------------------------------------------------
    public static final String URL_LISTING_DOUBLE = BASE_URL + "/Api/ListingDouble";
    public static final String URL_LISTING_DELETE = BASE_URL + "/Api/ListingDelete";
        // Get -------------------------------------------------------------------------------------
    public static final String URL_GET_LISTING_PENDING = BASE_URL + "/Api/GetListingPending";
    public static final String URL_GET_LISTING_LAPORAN = BASE_URL + "/Api/GetLaporanListing";
    public static final String URL_GET_LISTING = BASE_URL + "/Api/GetListingFinal";
    public static final String URL_GET_LISTING_AGEN = BASE_URL + "/Api/GetListingAgenFinal?idagen=";
    public static final String URL_GET_LISTING_DEEP = BASE_URL + "/Api/GetListingDeepFinal?idlisting=";
    public static final String URL_GET_LISTING_PDF = BASE_URL + "/Api/GetListingPDF?idlisting=";
    public static final String URL_GET_LISTING_SOLD = BASE_URL + "/Api/GetListingSoldFinal";
    public static final String URL_GET_LISTING_HOT = BASE_URL + "/Api/GetListingHotFinal";
    public static final String URL_GET_LISTING_FAVORITE= BASE_URL + "/Api/GetFavorite?idcustomer=";
    public static final String URL_GET_LISTING_FAVORITE_AGEN= BASE_URL + "/Api/GetFavoriteAgen?idagen=";
    public static final String URL_GET_LISTING_SEEN= BASE_URL + "/Api/GetSeen?idcustomer=";
    public static final String URL_GET_LISTING_SEEN_AGEN= BASE_URL + "/Api/GetSeenAgen?idagen=";
    public static final String URL_GET_CO_LISTING = BASE_URL + "/Api/GetCoListing?idco=";
    public static final String URL_GET_SUSULAN = BASE_URL + "/Api/GetSusulan?id=";
    public static final String URL_CEK_ISLOKASI = BASE_URL + "/Api/GetIsCekLokasi?id=";
    public static final String URL_CEK_NO_ARSIP = BASE_URL + "/Api/GetNoArsip?id=";
        // Count -----------------------------------------------------------------------------------
    public static final String URL_COUNT_LIKE = BASE_URL + "/Api/CountLike?idlisting=";
    public static final String URL_COUNT_SEWA = BASE_URL + "/Api/CountSewa?idagen=";
    public static final String URL_COUNT_JUAL = BASE_URL + "/Api/CountJual?idagen=";
    public static final String URL_COUNT_LISTING = BASE_URL + "/Api/CountListing?idagen=";

    // Follow Up =============================================================================================================================================================
        // Add -------------------------------------------------------------------------------------
    public static final String URL_ADD_FLOWUP = BASE_URL + "/Api/AddFlowup";
    public static final String URL_ADD_FLOWUP_PRIMARY = BASE_URL + "/Api/AddFlowupPrimary";
    public static final String URL_ADD_FLOWUP_INFO = BASE_URL + "/Api/AddFlowupInfo";
        // Update ----------------------------------------------------------------------------------
    public static final String URL_UPDATE_FLOWUP = BASE_URL + "/Api/UpdateFlowup";
    public static final String URL_UPDATE_FLOWUP_PRIMARY = BASE_URL + "/Api/UpdateFlowupPrimary";
        // Delete ----------------------------------------------------------------------------------
    public static final String URL_CLOSE_FLOWUP = BASE_URL + "/Api/CloseFlowUp";
        // Get -------------------------------------------------------------------------------------
    public static final String URL_GET_FLOWUP_AGEN = BASE_URL + "/Api/GetFlowUpAgen";
    public static final String URL_GET_FLOWUP = BASE_URL + "/Api/GetFlowUp?idagen=";
    public static final String URL_GET_UPDATE_FLOWUP = BASE_URL + "/Api/GetUpdateFlowUp?id=";
    public static final String URL_GET_FLOWUP_PRIMARY_AGEN = BASE_URL + "/Api/GetFlowUpPrimaryAgen";
    public static final String URL_GET_FLOWUP_PRIMARY = BASE_URL + "/Api/GetFlowUpPrimary?idagen=";
    public static final String URL_GET_UPDATE_FLOWUP_PRIMARY = BASE_URL + "/Api/GetUpdateFlowUpPrimary?id=";
    public static final String URL_GET_FLOWUP_INFO = BASE_URL + "/Api/GetFlowUpInfoAgen";
    public static final String URL_GET_HISTORY_FOLLOW_UP = BASE_URL + "/Api/GetHistoryFollowUp";

    // Closing -------------------------------------------------------------------------------------
    public static final String URL_CLOSING = BASE_URL + "/Api/Closing";
    public static final String URL_SOLD = BASE_URL + "/Api/Sold";
    public static final String URL_RENTED = BASE_URL + "/Api/Rented";
    public static final String URL_SOLD_AGEN = BASE_URL + "/Api/SoldAgen";
    public static final String URL_RENTED_AGEN = BASE_URL + "/Api/RentedAgen";
    // User ----------------------------------------------------------------------------------------
    public static final String URL_TAMBAH_KARYAWAN = BASE_URL + "/Api/AddKaryawan";
    public static final String URL_GET_AGEN = BASE_URL + "/Api/GetAgen";
    public static final String URL_GET_AGEN_DEEP = BASE_URL + "/Api/GetAgenDeep?idagen=";
    public static final String URL_GET_DAFTAR_AGEN = BASE_URL + "/Api/GetDaftarAgen";
    public static final String URL_GET_PELAMAR_AGEN = BASE_URL + "/Api/GetPelamarAgen";
    public static final String URL_GET_PELAMAR_MITRA = BASE_URL + "/Api/GetPelamarMitra";
    public static final String URL_GET_PELAMAR_KANTORLAIN = BASE_URL + "/Api/GetPelamarKantorLain";
    public static final String URL_LAST_SEEN = BASE_URL + "/Api/LastSeen";
    public static final String URL_GET_ULTAH_AGEN = BASE_URL + "/Api/GetUltahAgen";
    public static final String URL_TAMBAH_REPORT = BASE_URL + "/Api/TambahReport";
    public static final String URL_TAMBAH_CEK_LOKASI = BASE_URL + "/Api/TambahCekLokasi";
    public static final String URL_TAMBAH_CEK_SURVEY = BASE_URL + "/Api/TambahCekSurvey";
    public static final String URL_GET_WILAYAH = BASE_URL + "/Api/GetWilayah?id=";
    public static final String URL_GET_DAERAH = BASE_URL + "/Api/GetDaerah";

    // Device ================================================================================================================================================================
        // Add -------------------------------------------------------------------------------------
    public static final String URL_ADD_DEVICE = BASE_URL + "/Api/AddDevice";
    public static final String URL_ADD_DEVICE_AGEN = BASE_URL + "/Api/AddDeviceAgen";
        // Get -------------------------------------------------------------------------------------
    public static final String URL_GET_DEVICE = BASE_URL + "/Api/GetDevice";
    public static final String URL_GET_DEVICE_AGEN = BASE_URL + "/Api/GetDeviceByAgen?idagen=";
        // Delete ----------------------------------------------------------------------------------
    public static final String URL_DELETE_DEVICE_AGEN = BASE_URL + "/Api/DeleteDeviceAgen";
    public static final String URL_DELETE_DEVICE = BASE_URL + "/Api/DeleteDevice";

    // Images ================================================================================================================================================================
    public static final String URL_DELETE_IMAGE_1 = BASE_URL + "/Api/DeleteImages1";
    public static final String URL_DELETE_IMAGE_2 = BASE_URL + "/Api/DeleteImages2";
    public static final String URL_DELETE_IMAGE_3 = BASE_URL + "/Api/DeleteImages3";
    public static final String URL_DELETE_IMAGE_4 = BASE_URL + "/Api/DeleteImages4";
    public static final String URL_DELETE_IMAGE_5 = BASE_URL + "/Api/DeleteImages5";
    public static final String URL_DELETE_IMAGE_6 = BASE_URL + "/Api/DeleteImages6";
    public static final String URL_DELETE_IMAGE_7 = BASE_URL + "/Api/DeleteImages7";
    public static final String URL_DELETE_IMAGE_8 = BASE_URL + "/Api/DeleteImages8";
    public static final String URL_DELETE_IMAGE_9 = BASE_URL + "/Api/DeleteImages9";
    public static final String URL_DELETE_IMAGE_10 = BASE_URL + "/Api/DeleteImages10";
    public static final String URL_DELETE_IMAGE_11 = BASE_URL + "/Api/DeleteImages11";
    public static final String URL_DELETE_IMAGE_12 = BASE_URL + "/Api/DeleteImages12";

    // TBO ===================================================================================================================================================================
    public static final String URL_GET_TBO = BASE_URL + "/Api/GetTbo?id=";
    public static final String URL_GET_COUNT_LISTING_AGEN_BULAN_LALU = BASE_URL + "/Api/GetCountListingAgenBulanLalu?id=";
    public static final String URL_GET_COUNT_INFO_AGEN_BULAN_LALU = BASE_URL + "/Api/GetCountInfoAgenBulanLalu?id=";
    public static final String URL_GET_COUNT_OPEN_AGEN_BULAN_LALU = BASE_URL + "/Api/GetCountOpenAgenBulanLalu?id=";
    public static final String URL_GET_COUNT_EXCLUSIVE_AGEN_BULAN_LALU = BASE_URL + "/Api/GetCountExclusiveAgenBulanLalu?id=";
    public static final String URL_GET_COUNT_BANNER_AGEN_BULAN_LALU = BASE_URL + "/Api/GetCountBannerAgenBulanLalu?id=";
    public static final String URL_GET_COUNT_LISTING_AGEN_BULAN_INI = BASE_URL + "/Api/GetCountListingAgenBulanIni?id=";
    public static final String URL_GET_COUNT_INFO_AGEN_BULAN_INI = BASE_URL + "/Api/GetCountInfoAgenBulanIni?id=";
    public static final String URL_GET_COUNT_OPEN_AGEN_BULAN_INI = BASE_URL + "/Api/GetCountOpenAgenBulanIni?id=";
    public static final String URL_GET_COUNT_EXCLUSIVE_AGEN_BULAN_INI = BASE_URL + "/Api/GetCountExclusiveAgenBulanIni?id=";
    public static final String URL_GET_COUNT_BANNER_AGEN_BULAN_INI = BASE_URL + "/Api/GetCountBannerAgenBulanIni?id=";
    public static final String URL_GET_SUM_POIN_LISTING_AGEN_BULAN_LALU = BASE_URL + "/Api/GetSumPoinBulanLalu?id=";
    public static final String URL_GET_SUM_POIN_LISTING_AGEN_BULAN_INI = BASE_URL + "/Api/GetSumPoinBulanIni?id=";
    public static final String URL_GET_SUM_POIN_INFO_AGEN_BULAN_LALU = BASE_URL + "/Api/GetSumPoinInfoBulanLalu?id=";
    public static final String URL_GET_SUM_POIN_INFO_AGEN_BULAN_INI = BASE_URL + "/Api/GetSumPoinInfoBulanIni?id=";
    public static final String URL_GET_SUM_TOTAL_POIN_AGEN_BULAN_LALU = BASE_URL + "/Api/GetSumTotalPoinBulanLalu?id=";
    public static final String URL_GET_SUM_TOTAL_POIN_AGEN_BULAN_INI = BASE_URL + "/Api/GetSumTotalPoinBulanIni?id=";

    // OFFICER ===============================================================================================================================================================
    public static final String URL_GET_REPORT_KINERJA_OFFICER = BASE_URL + "/Api/GetReportKinerjaOfficer?id=";
    public static final String URL_GET_URAIAN_KERJA_OFFICER = BASE_URL + "/Api/GetUraianKerjaOfficer?id=";
    public static final String URL_GET_CALL_OFFICER = BASE_URL + "/Api/GetCallOfficerOfficer?id=";
    public static final String URL_GET_FOLLOW_UP_INFO_OFFICER = BASE_URL + "/Api/GetFollowUpInfoOfficer?id=";
    public static final String URL_GET_FOLLOW_UP_VENDOR_OFFICER = BASE_URL + "/Api/GetFollowUpVendorOfficer?id=";
    public static final String URL_GET_FOLLOW_UP_BUYER_OFFICER = BASE_URL + "/Api/GetFollowUpBuyerOfficer?id=";
    public static final String URL_GET_TINJAU_LOKASI_OFFICER = BASE_URL + "/Api/GetTinjauLokasiOfficer?id=";
}

