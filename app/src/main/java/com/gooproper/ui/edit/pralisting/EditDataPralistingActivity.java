package com.gooproper.ui.edit.pralisting;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.adapter.DaerahManager;
import com.gooproper.adapter.WilayahManager;
import com.gooproper.ui.LocationActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.SendMessageToFCM;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditDataPralistingActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    final int CODE_GALLERY_REQUEST1 = 1;
    final int CODE_GALLERY_REQUEST2 = 2;
    final int CODE_GALLERY_REQUEST3 = 3;
    final int CODE_GALLERY_REQUEST4 = 4;
    final int CODE_GALLERY_REQUEST5 = 5;
    final int CODE_GALLERY_REQUEST6 = 6;
    final int CODE_GALLERY_REQUEST7 = 7;
    final int CODE_GALLERY_REQUEST8 = 8;
    final int CODE_GALLERY_REQUEST9 = 9;
    final int CODE_GALLERY_REQUEST10 = 10;
    final int CODE_GALLERY_REQUEST11 = 11;
    final int CODE_GALLERY_REQUEST12 = 12;
    final int CODE_CAMERA_REQUEST1 = 13;
    final int CODE_CAMERA_REQUEST2 = 14;
    final int CODE_CAMERA_REQUEST3 = 15;
    final int CODE_CAMERA_REQUEST4 = 16;
    final int CODE_CAMERA_REQUEST5 = 17;
    final int CODE_CAMERA_REQUEST6 = 18;
    final int CODE_CAMERA_REQUEST7 = 19;
    final int CODE_CAMERA_REQUEST8 = 20;
    final int CODE_CAMERA_REQUEST9 = 21;
    final int CODE_CAMERA_REQUEST10 = 22;
    final int CODE_CAMERA_REQUEST11 = 23;
    final int CODE_CAMERA_REQUEST12 = 24;
    final int KODE_REQUEST_KAMERA1 = 25;
    final int KODE_REQUEST_KAMERA2 = 26;
    final int KODE_REQUEST_KAMERA3 = 27;
    final int KODE_REQUEST_KAMERA4 = 28;
    final int KODE_REQUEST_KAMERA5 = 29;
    final int KODE_REQUEST_KAMERA6 = 30;
    final int KODE_REQUEST_KAMERA7 = 31;
    final int KODE_REQUEST_KAMERA8 = 32;
    final int KODE_REQUEST_KAMERA9 = 33;
    final int KODE_REQUEST_KAMERA10 = 34;
    final int KODE_REQUEST_KAMERA11 = 35;
    final int KODE_REQUEST_KAMERA12 = 36;
    final int CODE_GALLERY_REQUEST_SHM = 37;
    final int CODE_CAMERA_REQUEST_SHM = 38;
    final int KODE_REQUEST_KAMERA_SHM = 39;
    final int CODE_GALLERY_REQUEST_HGB = 40;
    final int CODE_CAMERA_REQUEST_HGB = 41;
    final int KODE_REQUEST_KAMERA_HGB = 42;
    final int CODE_GALLERY_REQUEST_HSHP = 43;
    final int CODE_CAMERA_REQUEST_HSHP = 44;
    final int KODE_REQUEST_KAMERA_HSHP = 45;
    final int CODE_GALLERY_REQUEST_PPJB = 46;
    final int CODE_CAMERA_REQUEST_PPJB = 47;
    final int KODE_REQUEST_KAMERA_PPJB = 48;
    final int CODE_GALLERY_REQUEST_STRA = 49;
    final int CODE_CAMERA_REQUEST_STRA = 50;
    final int KODE_REQUEST_KAMERA_STRA = 51;
    final int CODE_GALLERY_REQUEST_AJB = 52;
    final int CODE_CAMERA_REQUEST_AJB = 53;
    final int KODE_REQUEST_KAMERA_AJB = 54;
    final int CODE_GALLERY_REQUEST_PetokD = 55;
    final int CODE_CAMERA_REQUEST_PetokD = 56;
    final int KODE_REQUEST_KAMERA_PetokD = 57;
    final int CODE_GALLERY_REQUEST_Selfie = 58;
    final int CODE_CAMERA_REQUEST_Selfie = 59;
    final int KODE_REQUEST_KAMERA_Selfie = 60;
    final int CODE_GALLERY_REQUEST_KTP = 61;
    final int CODE_CAMERA_REQUEST_KTP = 62;
    final int KODE_REQUEST_KAMERA_KTP = 63;
    final int CODE_GALLERY_REQUEST_PJP = 64;
    final int CODE_CAMERA_REQUEST_PJP = 65;
    final int KODE_REQUEST_KAMERA_PJP = 66;
    final int CODE_GALLERY_REQUEST_PJP1 = 67;
    final int CODE_CAMERA_REQUEST_PJP1 = 68;
    final int KODE_REQUEST_KAMERA_PJP1 = 69;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1 = 70;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES1 = 71;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2 = 72;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES2 = 73;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3 = 74;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES3 = 75;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4 = 76;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES4 = 77;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5 = 78;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES5 = 79;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6 = 80;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES6 = 81;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7 = 82;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES7 = 83;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8 = 84;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES8 = 85;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9 = 86;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES9 = 87;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10 = 88;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES10 = 89;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE11 = 90;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES11 = 91;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE12 = 92;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES12 = 93;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SHM = 94;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SHM = 95;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HGB = 96;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HGB = 97;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HSHP = 98;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HSHP = 99;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PPJB = 100;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PPJB = 101;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_STRA = 102;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_STRA = 103;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_AJB = 104;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_AJB = 105;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PetokD = 106;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PetokD = 107;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP = 108;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP = 109;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP1 = 110;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP1 = 111;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SELFIE = 112;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SELFIE = 113;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_KTP = 114;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_KTP = 115;
    private static final int MAPS_ACTIVITY_REQUEST_CODE = 116;
    private static final int STORAGE_PERMISSION_CODE = 117;
    private static final int PICK_PDF_SHM = 118;
    private static final int PICK_PDF_HGB = 119;
    private static final int PICK_PDF_HSHP = 120;
    private static final int PICK_PDF_PPJB = 121;
    private static final int PICK_PDF_Stratatitle = 122;
    private static final int PICK_PDF_AJB = 123;
    private static final int PICK_PDF_PetokD = 124;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 125;
    Uri Uri1, Uri2, Uri3, Uri4, Uri5, Uri6, Uri7, Uri8, Uri9, Uri10, Uri11, Uri12;
    Uri UriSHM, UriHGB, UriHSHP, UriPPJB, UriSTRA, UriAJB, UriPetokD;
    Uri UriSHMPdf, UriHGBPdf, UriHSHPPdf, UriPPJBPdf, UriSTRAPdf, UriAJBPdf, UriPetokDPdf;
    Uri UriSHMF, UriHGBF, UriHSHPF, UriPPJBF, UriSTRAF, UriAJBF, UriPetokDF;
    Uri UriPJP, UriPJP1;
    Uri UriSelfie, UriKTP;
    LinearLayout lyt1, lyt2, lyt3, lyt4, lyt5, lyt6, lyt7, lyt8, lyt9, lyt10, lyt11, lyt12;
    LinearLayout LytSHM, LytHGB, LytHSHP, LytPPJB, LytStratatitle, LytAJB, LytPetokD;
    LinearLayout LytPjp, LytPjp1;
    LinearLayout LytBtnShm, LytBtnHGB, LytBtnHSHP, LytBtnPPJB, LytBtnStra, LytBtnAJB, LytBtnPetokD;
    LinearLayout LytSelfie;
    ImageView back;
    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12;
    ImageView IVShm, IVHgb, IVHshp, IVPpjb, IVStratatitle, IVAJB, IVPetokD;
    ImageView IVPjp, IVPjp1;
    ImageView IVSelfie;
    Button batal, submit;
    Button select, select1, select2, select3, select4, select5, select6, select7, select9, select10, select11, select12;
    Button maps;
    Button BtnSHM, BtnHGB, BtnHSHP, BtnPPJB, BtnSTRA, BtnAJB, BtnPetokD;
    Button BtnSHMPdf, BtnHGBPdf, BtnHSHPPdf, BtnPPJBPdf, BtnSTRAPdf, BtnAJBPdf, BtnPetokDPdf;
    Button BtnPjp, BtnPjp1;
    Button BtnSelfie;
    ImageView hps1, hps2, hps3, hps4, hps5, hps6, hps7, hps8, hps9, hps10, hps11, hps12;
    ImageView HpsSHM, HpsHGB, HpsHSHP, HpsPPJB, HpsStratatitle, HpsAJB, HpsPetokD;
    ImageView HpsPjp, HpsPjp1;
    ImageView HpsSelfie;
    CheckBox CBSHM, CBHGB, CBHSHP, CBPPJB, CBSTRA, CBAJB, CBPetokD, CBMarketable, CBHarga, CBSelfie, CBLokasi;
    TextInputEditText EtPjp, Etjenisproperti, Etnamaproperti, Etalamatproperti, Etalamatpropertitemplate, Etwilayahproperti, Etluas, Etland, Etdimensi, Etlantai, Etbed, Etbath, Etbedart, Etbathart, Etgarasi, Etcarpot, Etlistrik, Etair, Etperabot, Etketperabot, Etbanner, Etstatus, Etharga, Ethargasewa, Etketerangan, Ethadap, Etsize, EtFee;
    TextInputLayout LytSize, LytHargaJual, LytHargaSewa;
    RadioButton open, exclusive;
    RadioGroup rgpriority;
    TextView TVSHM, TVHGB, TVHSHP, TVPPJB, TVSTRA, TVAJB, TVPetokD;
    String latitudeStr, longitudeStr, addressStr, lokasiStr, Lat, Lng, token, StrLokasi;
    String isDelete1, isDelete2, isDelete3, isDelete4, isDelete5, isDelete6, isDelete7, isDelete8, isDelete9, isDelete10, isDelete11, isDelete12;
    String isimage1, isimage2, isimage3, isimage4, isimage5, isimage6, isimage7, isimage8, isimage9, isimage10, isimage11, isimage12;
    String isSHM, isHGB, isHSHP, isPPJB, isSTRA, isAJB, isPetokD;
    String isPJP1, isPJP2;
    String isSelfie;
    String image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12;
    String SHM, HGB, HSHP, PPJB, STRA, AJB, PetokD;
    String PJPHal1, PJPHal2;
    String ImgSelfie;
    String idnull, idpralisting, priority, HargaString, HargaSewaString, StringIdAgen;
    String timeStamp;
    String fileListing1,fileListing2,fileListing3,fileListing4,fileListing5,fileListing6,fileListing7,fileListing8,fileListing9,fileListing10,fileListing11,fileListing12;
    String fileSertifikatshm,fileSertifikathgb,fileSertifikathshp,fileSertifikatppjb,fileSertifikatstra,fileSertifikatajb,fileSertifikatpetokd;
    String fileSertifikatshmpdf,fileSertifikathgbpdf,fileSertifikathshppdf,fileSertifikatppjbpdf,fileSertifikatstrapdf,fileSertifikatajbpdf,fileSertifikatpetokdpdf;
    String filePjp1,filePjp2;
    String fileSelfie,fileKTP;
    private StorageReference mStorageRef;
    StorageReference storageRef;
    StorageReference ImgListing1,ImgListing2,ImgListing3,ImgListing4,ImgListing5,ImgListing6,ImgListing7,ImgListing8,ImgListing9,ImgListing10,ImgListing11,ImgListing12;
    StorageReference ImgSertifikatshm,ImgSertifikathgb,ImgSertifikathshp,ImgSertifikatppjb,ImgSertifikatstra,ImgSertifikatajb,ImgSertifikatpetokd;
    StorageReference ImgSertifikatshmpdf,ImgSertifikathgbpdf,ImgSertifikathshppdf,ImgSertifikatppjbpdf,ImgSertifikatstrapdf,ImgSertifikatajbpdf,ImgSertifikatpetokdpdf;
    StorageReference ImgPjp,ImgPjp1;
    StorageReference ImageSelfie,ImageKTP;
    private WilayahManager wilayahManager;
    private DaerahManager daerahManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_pralisting);

        pDialog = new ProgressDialog(EditDataPralistingActivity.this);

        rgpriority = findViewById(R.id.rgstatus);

        open = findViewById(R.id.rbopen);
        exclusive = findViewById(R.id.rbexclusive);

        iv1 = findViewById(R.id.ivs1);
        iv2 = findViewById(R.id.ivs2);
        iv3 = findViewById(R.id.ivs3);
        iv4 = findViewById(R.id.ivs4);
        iv5 = findViewById(R.id.ivs5);
        iv6 = findViewById(R.id.ivs6);
        iv7 = findViewById(R.id.ivs7);
        iv8 = findViewById(R.id.ivs8);
        iv9 = findViewById(R.id.ivs9);
        iv10 = findViewById(R.id.ivs10);
        iv11 = findViewById(R.id.ivs11);
        iv12 = findViewById(R.id.ivs12);
        IVShm = findViewById(R.id.IVSHM);
        IVHgb = findViewById(R.id.IVHGB);
        IVHshp = findViewById(R.id.IVHSHP);
        IVPpjb = findViewById(R.id.IVPPJB);
        IVStratatitle = findViewById(R.id.IVStratatitle);
        IVAJB = findViewById(R.id.IVAJB);
        IVPetokD = findViewById(R.id.IVPetokD);
        IVPjp = findViewById(R.id.IVPjp);
        IVPjp1 = findViewById(R.id.IVPjp1);

        lyt1 = findViewById(R.id.lyts1);
        lyt2 = findViewById(R.id.lyts2);
        lyt3 = findViewById(R.id.lyts3);
        lyt4 = findViewById(R.id.lyts4);
        lyt5 = findViewById(R.id.lyts5);
        lyt6 = findViewById(R.id.lyts6);
        lyt7 = findViewById(R.id.lyts7);
        lyt8 = findViewById(R.id.lyts8);
        lyt9 = findViewById(R.id.lyts9);
        lyt10 = findViewById(R.id.lyts10);
        lyt11 = findViewById(R.id.lyts11);
        lyt12 = findViewById(R.id.lyts12);
        LytSize = findViewById(R.id.lytUkuranBanner);
        LytHargaJual = findViewById(R.id.lytharga);
        LytHargaSewa = findViewById(R.id.lythargasewa);
        LytSHM = findViewById(R.id.LytSHM);
        LytHGB = findViewById(R.id.LytHGB);
        LytHSHP = findViewById(R.id.LytHSHP);
        LytPPJB = findViewById(R.id.LytPPJB);
        LytStratatitle = findViewById(R.id.LytStratatitle);
        LytAJB = findViewById(R.id.LytAJB);
        LytPetokD = findViewById(R.id.LytPetokD);
        LytPjp = findViewById(R.id.LytPjp);
        LytPjp1 = findViewById(R.id.LytPjp1);
        LytBtnShm = findViewById(R.id.LytBtnSHM);
        LytBtnHGB = findViewById(R.id.LytBtnHGB);
        LytBtnHSHP = findViewById(R.id.LytBtnHSHP);
        LytBtnPPJB = findViewById(R.id.LytBtnPPJB);
        LytBtnStra = findViewById(R.id.LytBtnStratatitle);
        LytBtnAJB = findViewById(R.id.LytBtnAJB);
        LytBtnPetokD = findViewById(R.id.LytBtnPetokD);

        back = findViewById(R.id.backFormBtn);

        batal = findViewById(R.id.btnbatal);
        submit = findViewById(R.id.btnsubmit);
        select = findViewById(R.id.btnSelectImage);
        select1 = findViewById(R.id.btnSelectImage1);
        select2 = findViewById(R.id.btnSelectImage2);
        select3 = findViewById(R.id.btnSelectImage3);
        select4 = findViewById(R.id.btnSelectImage4);
        select5 = findViewById(R.id.btnSelectImage5);
        select6 = findViewById(R.id.btnSelectImage6);
        select7 = findViewById(R.id.btnSelectImage7);
        select9 = findViewById(R.id.btnSelectImage9);
        select10 = findViewById(R.id.btnSelectImage10);
        select11 = findViewById(R.id.btnSelectImage11);
        select12 = findViewById(R.id.btnSelectImage12);

        hps1 = findViewById(R.id.IVDelete1);
        hps2 = findViewById(R.id.IVDelete2);
        hps3 = findViewById(R.id.IVDelete3);
        hps4 = findViewById(R.id.IVDelete4);
        hps5 = findViewById(R.id.IVDelete5);
        hps6 = findViewById(R.id.IVDelete6);
        hps7 = findViewById(R.id.IVDelete7);
        hps8 = findViewById(R.id.IVDelete8);
        hps9 = findViewById(R.id.IVDelete9);
        hps10 = findViewById(R.id.IVDelete10);
        hps11 = findViewById(R.id.IVDelete11);
        hps12 = findViewById(R.id.IVDelete12);
        HpsSHM = findViewById(R.id.IVDeleteSHM);
        HpsHGB = findViewById(R.id.IVDeleteHGB);
        HpsHSHP = findViewById(R.id.IVDeleteHSHP);
        HpsPPJB = findViewById(R.id.IVDeletePPJB);
        HpsStratatitle = findViewById(R.id.IVDeleteStratatitle);
        HpsAJB = findViewById(R.id.IVDeleteAJB);
        HpsPetokD = findViewById(R.id.IVDeletePetokD);
        HpsPjp = findViewById(R.id.IVDeletePjp);
        HpsPjp1 = findViewById(R.id.IVDeletePjp1);

        CBSHM = findViewById(R.id.CBSHM);
        CBHGB = findViewById(R.id.CBHGB);
        CBHSHP = findViewById(R.id.CBHSHP);
        CBPPJB = findViewById(R.id.CBPPJB);
        CBSTRA = findViewById(R.id.CBStratatitle);
        CBAJB = findViewById(R.id.CBAJB);
        CBPetokD = findViewById(R.id.CBPetokD);
        CBMarketable = findViewById(R.id.CBMarketable);
        CBHarga = findViewById(R.id.CBHarga);
        CBSelfie = findViewById(R.id.CBSelfie);
        CBLokasi = findViewById(R.id.CBLokasi);

        BtnSHM = findViewById(R.id.BtnSHM);
        BtnSHMPdf = findViewById(R.id.BtnSHMPDF);
        BtnHGB = findViewById(R.id.BtnHGB);
        BtnHGBPdf = findViewById(R.id.BtnHGBPDF);
        BtnHSHP = findViewById(R.id.BtnHSHP);
        BtnHSHPPdf = findViewById(R.id.BtnHSHPPDF);
        BtnPPJB = findViewById(R.id.BtnPPJB);
        BtnPPJBPdf = findViewById(R.id.BtnPPJBPDF);
        BtnSTRA = findViewById(R.id.BtnStratatitle);
        BtnSTRAPdf = findViewById(R.id.BtnStratatitlePDF);
        BtnAJB = findViewById(R.id.BtnAJB);
        BtnAJBPdf = findViewById(R.id.BtnAJBPDF);
        BtnPetokD = findViewById(R.id.BtnPetokD);
        BtnPetokDPdf = findViewById(R.id.BtnPetokDPDF);
        BtnPjp = findViewById(R.id.BtnPjp);
        BtnPjp1 = findViewById(R.id.BtnPjp1);

        TVSHM = findViewById(R.id.TVSHM);
        TVHGB = findViewById(R.id.TVHGB);
        TVHSHP = findViewById(R.id.TVHSHP);
        TVPPJB = findViewById(R.id.TVPPJB);
        TVSTRA = findViewById(R.id.TVSTRA);
        TVAJB = findViewById(R.id.TVAJB);
        TVPetokD = findViewById(R.id.TVPetokD);

        Etjenisproperti = findViewById(R.id.etjenisproperti);
        Etnamaproperti = findViewById(R.id.etnamaproperti);
        Etalamatproperti = findViewById(R.id.etalamatproperti);
        Etalamatpropertitemplate = findViewById(R.id.etalamatpropertitemplate);
        Etwilayahproperti = findViewById(R.id.etwilayahlisting);
        EtPjp = findViewById(R.id.etkonfirmasipjp);
        Etluas = findViewById(R.id.etluastanah);
        Etland = findViewById(R.id.etluasbangunan);
        Etdimensi = findViewById(R.id.etdimensi);
        Etlantai = findViewById(R.id.etjumlahlantai);
        Etbed = findViewById(R.id.etkamartidur);
        Etbath = findViewById(R.id.etkamarmandi);
        Etbedart = findViewById(R.id.etkamartidurart);
        Etbathart = findViewById(R.id.etkamarmandiart);
        Etgarasi = findViewById(R.id.etgarasi);
        Etcarpot = findViewById(R.id.etcarpot);
        Etlistrik = findViewById(R.id.etdayalistrik);
        Etair = findViewById(R.id.etsumberair);
        Etperabot = findViewById(R.id.etperabot);
        Etketperabot = findViewById(R.id.etketperabot);
        Etbanner = findViewById(R.id.etbanner);
        Etstatus = findViewById(R.id.etstatusproperti);
        Etharga = findViewById(R.id.etharga);
        Ethargasewa = findViewById(R.id.ethargasewa);
        Etketerangan = findViewById(R.id.etketerangan);
        Ethadap = findViewById(R.id.ethadap);
        Etsize = findViewById(R.id.etukuranbanner);
        EtFee = findViewById(R.id.etfee);

        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        fileListing1 = "Listing1_" + timeStamp + ".jpg";
        fileListing2 = "Listing2_" + timeStamp + ".jpg";
        fileListing3 = "Listing3_" + timeStamp + ".jpg";
        fileListing4 = "Listing4_" + timeStamp + ".jpg";
        fileListing5 = "Listing5_" + timeStamp + ".jpg";
        fileListing6 = "Listing6_" + timeStamp + ".jpg";
        fileListing7 = "Listing7_" + timeStamp + ".jpg";
        fileListing8 = "Listing8_" + timeStamp + ".jpg";
        fileListing9 = "Listing9_" + timeStamp + ".jpg";
        fileListing10 = "Listing10_" + timeStamp + ".jpg";
        fileListing11 = "Listing11_" + timeStamp + ".jpg";
        fileListing12 = "Listing12_" + timeStamp + ".jpg";
        fileSertifikatshm = "SHM_" + timeStamp + ".jpg";
        fileSertifikatshmpdf = "SHM_" + timeStamp + ".pdf";
        fileSertifikathgb = "HGB_" + timeStamp + ".jpg";
        fileSertifikathgbpdf = "HGB_" + timeStamp + ".pdf";
        fileSertifikathshp = "HSHP_" + timeStamp + ".jpg";
        fileSertifikathshppdf = "HSHP_" + timeStamp + ".pdf";
        fileSertifikatppjb = "PPJB_" + timeStamp + ".jpg";
        fileSertifikatppjbpdf = "PPJB_" + timeStamp + ".pdf";
        fileSertifikatstra = "Stratatitle_" + timeStamp + ".jpg";
        fileSertifikatstrapdf = "Stratatitle_" + timeStamp + ".pdf";
        fileSertifikatajb = "Ajb_" + timeStamp + ".jpg";
        fileSertifikatajbpdf = "Ajb_" + timeStamp + ".pdf";
        fileSertifikatpetokd = "PetokD_" + timeStamp + ".jpg";
        fileSertifikatpetokdpdf = "PetokD_" + timeStamp + ".pdf";
        filePjp1 = "PJP1_" + timeStamp + ".jpg";
        filePjp2 = "PJP2_" + timeStamp + ".jpg";
        fileSelfie = "Selfie_" + timeStamp + ".jpg";
        fileKTP = "KTP_" + timeStamp + ".jpg";

        storageRef = FirebaseStorage.getInstance().getReference();
        ImgListing1 = storageRef.child("listing/" + fileListing1);
        ImgListing2 = storageRef.child("listing/" + fileListing2);
        ImgListing3 = storageRef.child("listing/" + fileListing3);
        ImgListing4 = storageRef.child("listing/" + fileListing4);
        ImgListing5 = storageRef.child("listing/" + fileListing5);
        ImgListing6 = storageRef.child("listing/" + fileListing6);
        ImgListing7 = storageRef.child("listing/" + fileListing7);
        ImgListing8 = storageRef.child("listing/" + fileListing8);
        ImgListing9 = storageRef.child("listing/" + fileListing9);
        ImgListing10 = storageRef.child("listing/" + fileListing10);
        ImgListing11 = storageRef.child("listing/" + fileListing11);
        ImgListing12 = storageRef.child("listing/" + fileListing12);
        ImgSertifikatshm = storageRef.child("sertifikat/" + fileSertifikatshm);
        ImgSertifikathgb = storageRef.child("sertifikat/" + fileSertifikathgb);
        ImgSertifikathshp = storageRef.child("sertifikat/" + fileSertifikathshp);
        ImgSertifikatppjb = storageRef.child("sertifikat/" + fileSertifikatppjb);
        ImgSertifikatstra = storageRef.child("sertifikat/" + fileSertifikatstra);
        ImgSertifikatajb = storageRef.child("sertifikat/" + fileSertifikatajb);
        ImgSertifikatpetokd = storageRef.child("sertifikat/" + fileSertifikatpetokd);
        ImgSertifikatshmpdf = storageRef.child("sertifikat/" + fileSertifikatshmpdf);
        ImgSertifikathgbpdf = storageRef.child("sertifikat/" + fileSertifikathgbpdf);
        ImgSertifikathshppdf = storageRef.child("sertifikat/" + fileSertifikathshppdf);
        ImgSertifikatppjbpdf = storageRef.child("sertifikat/" + fileSertifikatppjbpdf);
        ImgSertifikatstrapdf = storageRef.child("sertifikat/" + fileSertifikatstrapdf);
        ImgSertifikatajbpdf = storageRef.child("sertifikat/" + fileSertifikatajbpdf);
        ImgSertifikatpetokdpdf = storageRef.child("sertifikat/" + fileSertifikatpetokdpdf);
        ImgPjp = storageRef.child("pjp/" + filePjp1);
        ImgPjp1 = storageRef.child("pjp/" + filePjp2);
        ImageSelfie = storageRef.child("selfie/" + fileSelfie);
        ImageKTP = storageRef.child("ktp/" + fileKTP);

        Intent data = getIntent();
        String intentIdPraListing = data.getStringExtra("IdPraListing");
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentIdAgenCo = data.getStringExtra("IdAgenCo");
        String intentIdInput = data.getStringExtra("IdInput");
        String intentNamaListing = data.getStringExtra("NamaListing");
        String intentAlamat = data.getStringExtra("Alamat");
        String intentAlamatTemplate = data.getStringExtra("AlamatTemplate");
        String intentLatitude = data.getStringExtra("Latitude");
        String intentLongitude = data.getStringExtra("Longitude");
        String intentLocation = data.getStringExtra("Location");
        String intentSelfie = data.getStringExtra("Selfie");
        String intentWide = data.getStringExtra("Wide");
        String intentLand = data.getStringExtra("Land");
        String intentDimensi = data.getStringExtra("Dimensi");
        String intentListrik = data.getStringExtra("Listrik");
        String intentLevel = data.getStringExtra("Level");
        String intentBed = data.getStringExtra("Bed");
        String intentBedArt = data.getStringExtra("BedArt");
        String intentBath = data.getStringExtra("Bath");
        String intentBathArt = data.getStringExtra("BathArt");
        String intentGarage = data.getStringExtra("Garage");
        String intentCarpot = data.getStringExtra("Carpot");
        String intentHadap = data.getStringExtra("Hadap");
        String intentSHM = data.getStringExtra("SHM");
        String intentHGB = data.getStringExtra("HGB");
        String intentHSHP = data.getStringExtra("HSHP");
        String intentPPJB = data.getStringExtra("PPJB");
        String intentStratatitle = data.getStringExtra("Stratatitle");
        String intentAJB = data.getStringExtra("AJB");
        String intentPetokD = data.getStringExtra("PetokD");
        String intentPjp = data.getStringExtra("Pjp");
        String intentImgSHM = data.getStringExtra("ImgSHM");
        String intentImgHGB = data.getStringExtra("ImgHGB");
        String intentImgHSHP = data.getStringExtra("ImgHSHP");
        String intentImgPPJB = data.getStringExtra("ImgPPJB");
        String intentImgStratatitle = data.getStringExtra("ImgStratatitle");
        String intentImgAJB = data.getStringExtra("ImgAJB");
        String intentImgPetokD = data.getStringExtra("ImgPetokD");
        String intentImgPjp = data.getStringExtra("ImgPjp");
        String intentImgPjp1 = data.getStringExtra("ImgPjp1");
        String intentNoCertificate = data.getStringExtra("NoCertificate");
        String intentPbb = data.getStringExtra("Pbb");
        String intentJenisProperti = data.getStringExtra("JenisProperti");
        String intentJenisCertificate = data.getStringExtra("JenisCertificate");
        String intentSumberAir = data.getStringExtra("SumberAir");
        String intentKondisi = data.getStringExtra("Kondisi");
        String intentDeskripsi = data.getStringExtra("Deskripsi");
        String intentPrabot = data.getStringExtra("Prabot");
        String intentKetPrabot = data.getStringExtra("KetPrabot");
        String intentPriority = data.getStringExtra("Priority");
        String intentTtd = data.getStringExtra("Ttd");
        String intentBanner = data.getStringExtra("Banner");
        String intentSize = data.getStringExtra("Size");
        String intentHarga = data.getStringExtra("Harga");
        String intentHargaSewa = data.getStringExtra("HargaSewa");
        String intentTglInput = data.getStringExtra("TglInput");
        String intentImg1 = data.getStringExtra("Img1");
        String intentImg2 = data.getStringExtra("Img2");
        String intentImg3 = data.getStringExtra("Img3");
        String intentImg4 = data.getStringExtra("Img4");
        String intentImg5 = data.getStringExtra("Img5");
        String intentImg6 = data.getStringExtra("Img6");
        String intentImg7 = data.getStringExtra("Img7");
        String intentImg8 = data.getStringExtra("Img8");
        String intentImg9 = data.getStringExtra("Img9");
        String intentImg10 = data.getStringExtra("Img10");
        String intentImg11 = data.getStringExtra("Img11");
        String intentImg12 = data.getStringExtra("Img12");
        String intentVideo = data.getStringExtra("Video");
        String intentLinkFacebook = data.getStringExtra("LinkFacebook");
        String intentLinkTiktok = data.getStringExtra("LinkTiktok");
        String intentLinkInstagram = data.getStringExtra("LinkInstagram");
        String intentLinkYoutube = data.getStringExtra("LinkYoutube");
        String intentIsAdmin = data.getStringExtra("IsAdmin");
        String intentIsManager = data.getStringExtra("IsManager");
        String intentIsRejected = data.getStringExtra("IsRejected");
        String intentSold = data.getStringExtra("Sold");
        String intentRented = data.getStringExtra("Rented");
        String intentView = data.getStringExtra("View");
        String intentMarketable = data.getStringExtra("Marketable");
        String intentStatusHarga = data.getStringExtra("StatusHarga");
        String intentNama = data.getStringExtra("Nama");
        String intentNoTelp = data.getStringExtra("NoTelp");
        String intentInstagram = data.getStringExtra("Instagram");
        String intentFee = data.getStringExtra("Fee");
        String intentNamaVendor = data.getStringExtra("NamaVendor");
        String intentNoTelpVendor = data.getStringExtra("NoTelpVendor");
        String intentIsSelfie = data.getStringExtra("IsSelfie");
        String intentIsLokasi = data.getStringExtra("IsLokasi");
        String intentKeterangan = data.getStringExtra("Keterangan");

        wilayahManager = new WilayahManager();
        daerahManager = new DaerahManager();

        StringIdAgen = intentIdAgen;
        isimage1 = intentImg1;
        isimage2 = intentImg2;
        isimage3 = intentImg3;
        isimage4 = intentImg4;
        isimage5 = intentImg5;
        isimage6 = intentImg6;
        isimage7 = intentImg7;
        isimage8 = intentImg8;
        isimage9 = intentImg9;
        isimage10 = intentImg10;
        isimage11 = intentImg11;
        isimage12 = intentImg12;
        isSHM = intentImgSHM;
        isHGB = intentImgHGB;
        isPPJB = intentImgPPJB;
        isHSHP = intentImgHSHP;
        isSTRA = intentImgStratatitle;
        isAJB = intentImgAJB;
        isPetokD = intentImgPetokD;
        isPJP1 = intentImgPjp;
        isPJP2 = intentImgPjp1;
        isSelfie = intentSelfie;
        idnull = "0";

        BtnSHM.setOnClickListener(view -> showPhotoSHM());
        BtnSHMPdf.setOnClickListener(view -> pilihFileSHM(view));
        BtnHGB.setOnClickListener(view -> showPhotoHGB());
        BtnHGBPdf.setOnClickListener(view -> pilihFileHGB(view));
        BtnHSHP.setOnClickListener(view -> showPhotoHSHP());
        BtnHSHPPdf.setOnClickListener(view -> pilihFileHSHP(view));
        BtnPPJB.setOnClickListener(view -> showPhotoPPJB());
        BtnPPJBPdf.setOnClickListener(view -> pilihFilePPJB(view));
        BtnSTRA.setOnClickListener(view -> showPhotoSTRA());
        BtnSTRAPdf.setOnClickListener(view -> pilihFileSTRA(view));
        BtnAJB.setOnClickListener(view -> showPhotoAJB());
        BtnAJBPdf.setOnClickListener(view -> pilihFileAJB(view));
        BtnPetokD.setOnClickListener(view -> showPhotoPetokD());
        BtnPetokDPdf.setOnClickListener(view -> pilihFilePetokD(view));
        BtnPjp.setOnClickListener(view -> showPhotoPJP());
        BtnPjp1.setOnClickListener(view -> showPhotoPJP1());
        back.setOnClickListener(view -> finish());
        batal.setOnClickListener(view -> finish());
        hps1.setOnClickListener(view -> clearBitmap1());
        hps2.setOnClickListener(view -> clearBitmap2());
        hps3.setOnClickListener(view -> clearBitmap3());
        hps4.setOnClickListener(view -> clearBitmap4());
        hps5.setOnClickListener(view -> clearBitmap5());
        hps6.setOnClickListener(view -> clearBitmap6());
        hps7.setOnClickListener(view -> clearBitmap7());
        hps8.setOnClickListener(view -> clearBitmap8());
        hps9.setOnClickListener(view -> clearBitmap9());
        hps10.setOnClickListener(view -> clearBitmap10());
        hps11.setOnClickListener(view -> clearBitmap11());
        hps12.setOnClickListener(view -> clearBitmap12());
        HpsSHM.setOnClickListener(view -> clearBitmapSHM());
        HpsHGB.setOnClickListener(view -> clearBitmapHGB());
        HpsHSHP.setOnClickListener(view -> clearBitmapHSHP());
        HpsPPJB.setOnClickListener(view -> clearBitmapPPJB());
        HpsStratatitle.setOnClickListener(view -> clearBitmapSTRA());
        HpsAJB.setOnClickListener(view -> clearBitmapAJB());
        HpsPetokD.setOnClickListener(view -> clearBitmapPetokD());
        HpsPjp.setOnClickListener(view -> clearBitmapPJP());
        HpsPjp1.setOnClickListener(view -> clearBitmapPJP1());
        Etjenisproperti.setOnClickListener(view -> ShowJenisProperti(view));
        Etwilayahproperti.setOnClickListener(v -> fetchDataDaerah());
        EtPjp.setOnClickListener(view -> ShowPjp(view));
        Etair.setOnClickListener(view -> ShowSumberAir(view));
        Etperabot.setOnClickListener(view -> ShowPerabot(view));
        Etbanner.setOnClickListener(view -> ShowBanner(view));
        Etsize.setOnClickListener(view -> ShowSize(view));
        Etstatus.setOnClickListener(view -> ShowStatus(view));
        EtPjp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Ya")) {
                    if (UriPJP == null && intentImgPjp.equals("0")) {
                        BtnPjp.setVisibility(View.VISIBLE);
                        BtnPjp.setText("Upload Pjp Halaman 1");
                    } else {
                        BtnPjp.setVisibility(View.GONE);
                    }
                    if (UriPJP1 == null) {
                        BtnPjp1.setVisibility(View.VISIBLE);
                    } else {
                        BtnPjp1.setVisibility(View.GONE);
                    }
                } else if (editable.toString().equalsIgnoreCase("Tidak")) {
                    if (UriPJP == null && intentImgPjp.equals("0")) {
                        BtnPjp.setVisibility(View.VISIBLE);
                        BtnPjp.setText("Upload Bukti Chat");
                        BtnPjp1.setVisibility(View.GONE);
                    } else {
                        BtnPjp.setVisibility(View.GONE);
                        BtnPjp1.setVisibility(View.GONE);
                    }
                } else {
                    BtnPjp.setVisibility(View.GONE);
                    BtnPjp1.setVisibility(View.GONE);
                }
            }
        });
        Etbanner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Ya")) {
                    LytSize.setVisibility(View.VISIBLE);
                } else {
                    LytSize.setVisibility(View.GONE);
                }
            }
        });
        Etstatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Jual")) {
                    LytHargaJual.setVisibility(View.VISIBLE);
                    LytHargaSewa.setVisibility(View.GONE);
                } else if (editable.toString().equalsIgnoreCase("Sewa")) {
                    LytHargaSewa.setVisibility(View.VISIBLE);
                    LytHargaJual.setVisibility(View.GONE);
                } else if (editable.toString().equalsIgnoreCase("Jual/Sewa")) {
                    LytHargaJual.setVisibility(View.VISIBLE);
                    LytHargaSewa.setVisibility(View.VISIBLE);
                } else {
                    LytHargaJual.setVisibility(View.GONE);
                    LytHargaSewa.setVisibility(View.GONE);
                }
            }
        });
        Etharga.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    Etharga.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[Rp,.\\s]", "");
                    if (!cleanString.isEmpty()) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = String.format(Locale.US, "%,.0f", parsed);
                        HargaString = cleanString;
                        current = formatted;
                        Etharga.setText(formatted);
                        Etharga.setSelection(formatted.length());
                    } else {
                        Etharga.setText("");
                        HargaString = "";
                    }

                    Etharga.addTextChangedListener(this);
                }
            }
        });
        Ethargasewa.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    Ethargasewa.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[Rp,.\\s]", "");
                    if (!cleanString.isEmpty()) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = String.format(Locale.US, "%,.0f", parsed);
                        HargaSewaString = cleanString;
                        current = formatted;
                        Ethargasewa.setText(formatted);
                        Ethargasewa.setSelection(formatted.length());
                    } else {
                        Ethargasewa.setText("");
                        HargaSewaString = "";
                    }

                    Ethargasewa.addTextChangedListener(this);
                }
            }
        });
        CBSHM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    LytBtnShm.setVisibility(View.VISIBLE);
                } else {
                    UriSHM = null;
                    UriSHMPdf = null;
                    LytSHM.setVisibility(View.GONE);
                    LytBtnShm.setVisibility(View.GONE);
                }
            }
        });
        CBHGB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    LytBtnHGB.setVisibility(View.VISIBLE);
                } else {
                    UriHGB = null;
                    UriHGBPdf = null;
                    LytHGB.setVisibility(View.GONE);
                    LytBtnHGB.setVisibility(View.GONE);
                }
            }
        });
        CBHSHP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    LytBtnHSHP.setVisibility(View.VISIBLE);
                } else {
                    UriHSHP = null;
                    UriHSHPPdf = null;
                    LytHSHP.setVisibility(View.GONE);
                    LytBtnHSHP.setVisibility(View.GONE);
                }
            }
        });
        CBPPJB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    LytBtnPPJB.setVisibility(View.VISIBLE);
                } else {
                    UriPPJB = null;
                    UriPPJBPdf = null;
                    LytPPJB.setVisibility(View.GONE);
                    LytBtnPPJB.setVisibility(View.GONE);
                }
            }
        });
        CBSTRA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    LytBtnStra.setVisibility(View.VISIBLE);
                } else {
                    UriSTRA = null;
                    UriSTRAPdf = null;
                    LytStratatitle.setVisibility(View.GONE);
                    LytBtnStra.setVisibility(View.GONE);
                }
            }
        });
        CBAJB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    LytBtnAJB.setVisibility(View.VISIBLE);
                } else {
                    UriAJB = null;
                    UriAJBPdf = null;
                    LytAJB.setVisibility(View.GONE);
                    LytBtnAJB.setVisibility(View.GONE);
                }
            }
        });
        CBPetokD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    LytBtnPetokD.setVisibility(View.VISIBLE);
                } else {
                    UriPetokD = null;
                    UriPetokDPdf = null;
                    LytPetokD.setVisibility(View.GONE);
                    LytBtnPetokD.setVisibility(View.GONE);
                }
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog8();
            }
        });
        select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog1();
            }
        });
        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog2();
            }
        });
        select3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog3();
            }
        });
        select4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog4();
            }
        });
        select5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog5();
            }
        });
        select6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog6();
            }
        });
        select7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog7();
            }
        });
        select9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog9();
            }
        });
        select10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog10();
            }
        });
        select11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog11();
            }
        });
        select12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog12();
            }
        });

        if (intentPriority.equals("open")){
            open.setChecked(true);
            priority = intentPriority;
        } else if (intentPriority.equals("exclusive")) {
            exclusive.setChecked(true);
            priority = intentPriority;
        } else {
            open.setChecked(false);
            exclusive.setChecked(false);
        }
        if (intentSHM.equals("1")){
            CBSHM.setChecked(true);
            LytSHM.setVisibility(View.VISIBLE);
            TVSHM.setVisibility(View.VISIBLE);
            LytBtnShm.setVisibility(View.VISIBLE);
        }
        if (intentHGB.equals("1")){
            CBHGB.setChecked(true);
            LytHGB.setVisibility(View.VISIBLE);
            TVHGB.setVisibility(View.VISIBLE);
            LytBtnHGB.setVisibility(View.VISIBLE);
        }
        if (intentHSHP.equals("1")){
            CBHSHP.setChecked(true);
            LytHSHP.setVisibility(View.VISIBLE);
            TVHSHP.setVisibility(View.VISIBLE);
            LytBtnHSHP.setVisibility(View.VISIBLE);
        }
        if (intentPPJB.equals("1")){
            CBPPJB.setChecked(true);
            LytPPJB.setVisibility(View.VISIBLE);
            TVPPJB.setVisibility(View.VISIBLE);
            LytBtnPPJB.setVisibility(View.VISIBLE);
        }
        if (intentStratatitle.equals("1")){
            CBSTRA.setChecked(true);
            LytStratatitle.setVisibility(View.VISIBLE);
            TVSTRA.setVisibility(View.VISIBLE);
            LytBtnStra.setVisibility(View.VISIBLE);
        }
        if (intentAJB.equals("1")){
            CBAJB.setChecked(true);
            LytAJB.setVisibility(View.VISIBLE);
            TVAJB.setVisibility(View.VISIBLE);
            LytBtnAJB.setVisibility(View.VISIBLE);
        }
        if (intentPetokD.equals("1")){
            CBPetokD.setChecked(true);
            LytPetokD.setVisibility(View.VISIBLE);
            TVPetokD.setVisibility(View.VISIBLE);
            LytBtnPetokD.setVisibility(View.VISIBLE);
        }
        if (!intentImgPjp.equals("0")){
            LytPjp.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImgPjp)
                    .into(IVPjp);
        }
        if (!intentImgPjp1.equals("0")){
            LytPjp1.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImgPjp1)
                    .into(IVPjp1);
        }
        if (!intentImgSHM.equals("0")){
            int indexPercent2 = intentImgSHM.indexOf("%2");
            String title = intentImgSHM.substring(indexPercent2 + 3);
            int indexQuestionMark = title.indexOf("?");
            if (indexQuestionMark != -1) {
                title = title.substring(0, indexQuestionMark);
            }
            TVSHM.setText(title);
        }
        if (!intentImgHGB.equals("0")){
            int indexPercent2 = intentImgHGB.indexOf("%2");
            String title = intentImgHGB.substring(indexPercent2 + 3);
            int indexQuestionMark = title.indexOf("?");
            if (indexQuestionMark != -1) {
                title = title.substring(0, indexQuestionMark);
            }
            TVHGB.setText(title);
        }
        if (!intentImgHSHP.equals("0")){
            int indexPercent2 = intentImgHSHP.indexOf("%2");
            String title = intentImgHSHP.substring(indexPercent2 + 3);
            int indexQuestionMark = title.indexOf("?");
            if (indexQuestionMark != -1) {
                title = title.substring(0, indexQuestionMark);
            }
            TVHSHP.setText(title);
        }
        if (!intentImgPPJB.equals("0")){
            int indexPercent2 = intentImgPPJB.indexOf("%2");
            String title = intentImgPPJB.substring(indexPercent2 + 3);
            int indexQuestionMark = title.indexOf("?");
            if (indexQuestionMark != -1) {
                title = title.substring(0, indexQuestionMark);
            }
            TVPPJB.setText(title);
        }
        if (!intentImgStratatitle.equals("0")){
            int indexPercent2 = intentImgStratatitle.indexOf("%2");
            String title = intentImgStratatitle.substring(indexPercent2 + 3);
            int indexQuestionMark = title.indexOf("?");
            if (indexQuestionMark != -1) {
                title = title.substring(0, indexQuestionMark);
            }
            TVSTRA.setText(title);
        }
        if (!intentImgAJB.equals("0")){
            int indexPercent2 = intentImgAJB.indexOf("%2");
            String title = intentImgAJB.substring(indexPercent2 + 3);
            int indexQuestionMark = title.indexOf("?");
            if (indexQuestionMark != -1) {
                title = title.substring(0, indexQuestionMark);
            }
            TVAJB.setText(title);
        }
        if (!intentImgPetokD.equals("0")){
            int indexPercent2 = intentImgPetokD.indexOf("%2");
            String title = intentImgPetokD.substring(indexPercent2 + 3);
            int indexQuestionMark = title.indexOf("?");
            if (indexQuestionMark != -1) {
                title = title.substring(0, indexQuestionMark);
            }
            TVPetokD.setText(title);
        }
        if (!intentImg1.equals("0")){
            lyt1.setVisibility(View.VISIBLE);
            select1.setVisibility(View.VISIBLE);
            select1.setText("Ganti Gambar");
            select2.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg1)
                    .into(iv1);
        }
        if (!intentImg2.equals("0")){
            lyt2.setVisibility(View.VISIBLE);
            select2.setVisibility(View.VISIBLE);
            select2.setText("Ganti Gambar");
            select3.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg2)
                    .into(iv2);
        }
        if (!intentImg3.equals("0")){
            lyt3.setVisibility(View.VISIBLE);
            select3.setVisibility(View.VISIBLE);
            select3.setText("Ganti Gambar");
            select4.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg3)
                    .into(iv3);
        }
        if (!intentImg4.equals("0")){
            lyt4.setVisibility(View.VISIBLE);
            select4.setVisibility(View.VISIBLE);
            select4.setText("Ganti Gambar");
            select5.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg4)
                    .into(iv4);
        }
        if (!intentImg5.equals("0")){
            lyt5.setVisibility(View.VISIBLE);
            select5.setVisibility(View.VISIBLE);
            select5.setText("Ganti Gambar");
            select6.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg5)
                    .into(iv5);
        }
        if (!intentImg6.equals("0")){
            lyt6.setVisibility(View.VISIBLE);
            select6.setVisibility(View.VISIBLE);
            select6.setText("Ganti Gambar");
            select7.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg6)
                    .into(iv6);
        }
        if (!intentImg7.equals("0")){
            lyt7.setVisibility(View.VISIBLE);
            select7.setVisibility(View.VISIBLE);
            select7.setText("Ganti Gambar");
            select.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg7)
                    .into(iv7);
        }
        if (!intentImg8.equals("0")){
            lyt8.setVisibility(View.VISIBLE);
            select.setVisibility(View.VISIBLE);
            select.setText("Ganti Gambar");
            select9.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg8)
                    .into(iv8);
        }
        if (!intentImg9.equals("0")){
            lyt9.setVisibility(View.VISIBLE);
            select9.setVisibility(View.VISIBLE);
            select9.setText("Ganti Gambar");
            select10.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg9)
                    .into(iv9);
        }
        if (!intentImg10.equals("0")){
            lyt10.setVisibility(View.VISIBLE);
            select10.setVisibility(View.VISIBLE);
            select10.setText("Ganti Gambar");
            select11.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg10)
                    .into(iv10);
        }
        if (!intentImg11.equals("0")){
            lyt11.setVisibility(View.VISIBLE);
            select11.setVisibility(View.VISIBLE);
            select11.setText("Ganti Gambar");
            select12.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(intentImg11)
                    .into(iv11);
        }
        if (!intentImg12.equals("0")){
            lyt12.setVisibility(View.VISIBLE);
            select12.setVisibility(View.VISIBLE);
            select12.setText("Ganti Gambar");
            Picasso.get()
                    .load(intentImg12)
                    .into(iv12);
        }
//        if (!intentSelfie.equals("0")){
//            LytSelfie.setVisibility(View.VISIBLE);
//            Picasso.get()
//                    .load(intentSelfie)
//                    .into(IVSelfie);
//        }
        if (intentLatitude != null && !intentLatitude.isEmpty()) {
            latitudeStr = intentLatitude;
        }
        if (intentLongitude != null && !intentLongitude.isEmpty()) {
            longitudeStr = intentLongitude;
        }
        if (intentLocation != null && !intentLocation.isEmpty()) {
            addressStr = intentLocation;
        }
        if (intentIsSelfie.equals("1")){
            CBSelfie.setChecked(true);
//            BtnSelfie.setText("Ganti Selfie");
        }
        if (intentIsLokasi.equals("1")){
            CBLokasi.setChecked(true);
//            maps.setVisibility(View.GONE);
        }
        if (intentMarketable.equals("1")){
            CBMarketable.setChecked(true);
        }
        if (intentStatusHarga.equals("1")){
            CBHarga.setChecked(true);
        }
        idpralisting = intentIdPraListing;
        Etjenisproperti.setText(intentJenisProperti);
        Etnamaproperti.setText(intentNamaListing);
        Etalamatproperti.setText(intentAlamat);
        Etluas.setText(intentWide);
        Etland.setText(intentLand);
        Etdimensi.setText(intentDimensi);
        Ethadap.setText(intentHadap);
        Etlantai.setText(intentLevel);
        Etbed.setText(intentBed);
        Etbedart.setText(intentBedArt);
        Etbath.setText(intentBath);
        Etbathart.setText(intentBathArt);
        Etgarasi.setText(intentGarage);
        Etcarpot.setText(intentCarpot);
        Etlistrik.setText(intentListrik);
        Etair.setText(intentSumberAir);
        Etperabot.setText(intentPrabot);
        Etketperabot.setText(intentKetPrabot);
        Etbanner.setText(intentBanner);
        Etsize.setText(intentSize);
        Etstatus.setText(intentKondisi);
        Etharga.setText(intentHarga);
        Ethargasewa.setText(intentHargaSewa);
        Etketerangan.setText(intentDeskripsi);
        EtFee.setText(intentFee);

        submit.setOnClickListener(view -> {
            int checkedRadioButtonId = rgpriority.getCheckedRadioButtonId();

            if (checkedRadioButtonId == -1) {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap pilih Open atau Exclusive pada bagian atas form");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            } else {
                if (Validate()) {
                    if (Uri1 == null && isimage1.equals("0")) {
                        Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_eror_input);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                        TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                        tv.setText("Harap Tambahkan Gambar");

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                        Glide.with(EditDataPralistingActivity.this)
                                .load(R.drawable.alert)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    } else {
                        handleImage1Success();
                    }
                }
            }
        });
        rgpriority.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbopen) {
                priority = "open";
            } else if (checkedId == R.id.rbexclusive) {
                priority = "exclusive";
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    public void startMapsActivityForResult() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivityForResult(intent, MAPS_ACTIVITY_REQUEST_CODE);
    }
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    private void showPhotoSelectionDialog1() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST1);
                                break;
                            case 1:
                                requestPermissions1();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog2() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST2);
                                break;
                            case 1:
                                requestPermissions2();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog3() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST3);
                                break;
                            case 1:
                                requestPermissions3();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog4() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST4);
                                break;
                            case 1:
                                requestPermissions4();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog5() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST5);
                                break;
                            case 1:
                                requestPermissions5();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog6() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST6);
                                break;
                            case 1:
                                requestPermissions6();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog7() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST7);
                                break;
                            case 1:
                                requestPermissions7();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog8() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST8);
                                break;
                            case 1:
                                requestPermissions8();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog9() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST9);
                                break;
                            case 1:
                                requestPermissions9();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog10() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST10);
                                break;
                            case 1:
                                requestPermissions10();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog11() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST11);
                                break;
                            case 1:
                                requestPermissions11();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog12() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST12);
                                break;
                            case 1:
                                requestPermissions12();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSHM() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_SHM);
                                break;
                            case 1:
                                requestPermissionsSHM();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoHGB() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_HGB);
                                break;
                            case 1:
                                requestPermissionsHGB();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoHSHP() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_HSHP);
                                break;
                            case 1:
                                requestPermissionsHSHP();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoPPJB() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_PPJB);
                                break;
                            case 1:
                                requestPermissionsPPJB();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSTRA() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_STRA);
                                break;
                            case 1:
                                requestPermissionsSTRA();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoAJB() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_AJB);
                                break;
                            case 1:
                                requestPermissionsAJB();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoPetokD() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_PetokD);
                                break;
                            case 1:
                                requestPermissionsPetokD();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoPJP() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_PJP);
                                break;
                            case 1:
                                requestPermissionsPjp();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoPJP1() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_PJP1);
                                break;
                            case 1:
                                requestPermissionsPjp1();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelfie() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditDataPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_Selfie);
                                break;
                            case 1:
                                requestPermissionsSelfie();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void requestPermissions1() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES1);
        }
    }
    private void requestPermissions2() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES2);
        }
    }
    private void requestPermissions3() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES3);
        }
    }
    private void requestPermissions4() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES4);
        }
    }
    private void requestPermissions5() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES5);
        }
    }
    private void requestPermissions6() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES6);
        }
    }
    private void requestPermissions7() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES7);
        }
    }
    private void requestPermissions8() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES8);
        }
    }
    private void requestPermissions9() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES9);
        }
    }
    private void requestPermissions10() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES10);
        }
    }
    private void requestPermissions11() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE11);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES11);
        }
    }
    private void requestPermissions12() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE12);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES12);
        }
    }
    private void requestPermissionsSHM() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SHM);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SHM);
        }
    }
    private void requestPermissionsHGB() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HGB);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HGB);
        }
    }
    private void requestPermissionsHSHP() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HSHP);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HSHP);
        }
    }
    private void requestPermissionsPPJB() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PPJB);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PPJB);
        }
    }
    private void requestPermissionsSTRA() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_STRA);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_STRA);
        }
    }
    private void requestPermissionsAJB() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_AJB);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_AJB);
        }
    }
    private void requestPermissionsPetokD() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PetokD);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PetokD);
        }
    }
    private void requestPermissionsPjp() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP);
        }
    }
    private void requestPermissionsPjp1() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP1);
        }
    }
    private void requestPermissionsSelfie() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SELFIE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SELFIE);
        }
    }
    private void bukaKamera1() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri1 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri1);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA1);
            }
        }
    }
    private void bukaKamera2() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri2 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri2);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA2);
            }
        }
    }
    private void bukaKamera3() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri3 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri3);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA3);
            }
        }
    }
    private void bukaKamera4() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri4 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri4);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA4);
            }
        }
    }
    private void bukaKamera5() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri5 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri5);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA5);
            }
        }
    }
    private void bukaKamera6() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri6 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri6);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA6);
            }
        }
    }
    private void bukaKamera7() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri7 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri7);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA7);
            }
        }
    }
    private void bukaKamera8() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri8 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri8);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA8);
            }
        }
    }
    private void bukaKamera9() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri9 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri9);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA9);
            }
        }
    }
    private void bukaKamera10() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri10 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri10);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA10);
            }
        }
    }
    private void bukaKamera11() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri11 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri11);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA11);
            }
        }
    }
    private void bukaKamera12() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri12 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri12);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA12);
            }
        }
    }
    private void bukaKameraSHM() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriSHM = FileProvider.getUriForFile(this, "com.gooproper", photoFile); // Gantilah "com.example.android.fileprovider" dengan authority Anda
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriSHM);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_SHM);
            }
        }
    }
    private void bukaKameraHGB() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriHGB = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriHGB);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_HGB);
            }
        }
    }
    private void bukaKameraHSHP() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriHSHP = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriHSHP);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_HSHP);
            }
        }
    }
    private void bukaKameraPPJB() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriPPJB = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriPPJB);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_PPJB);
            }
        }
    }
    private void bukaKameraSTRA() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriSTRA = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriSTRA);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_STRA);
            }
        }
    }
    private void bukaKameraAJB() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriAJB = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriAJB);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_AJB);
            }
        }
    }
    private void bukaKameraPetokD() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriPetokD = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriPetokD);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_PetokD);
            }
        }
    }
    private void bukaKameraPjp() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriPJP = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriPJP);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_PJP);
            }
        }
    }
    private void bukaKameraPjp1() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriPJP1 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriPJP1);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_PJP1);
            }
        }
    }
    private void bukaKameraSelfie() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriSelfie = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriSelfie);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_Selfie);
            }
        }
    }
    public void pilihFileSHM(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih File PDF"), PICK_PDF_SHM);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");

            startActivityForResult(intent, PICK_PDF_SHM);
        }
    }
    public void pilihFileHGB(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih File PDF"), PICK_PDF_HGB);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");

            startActivityForResult(intent, PICK_PDF_HGB);
        }
    }
    public void pilihFileHSHP(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih File PDF"), PICK_PDF_HSHP);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");

            startActivityForResult(intent, PICK_PDF_HSHP);
        }
    }
    public void pilihFilePPJB(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih File PDF"), PICK_PDF_PPJB);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");

            startActivityForResult(intent, PICK_PDF_PPJB);
        }
    }
    public void pilihFileSTRA(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih File PDF"), PICK_PDF_Stratatitle);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");

            startActivityForResult(intent, PICK_PDF_Stratatitle);
        }
    }
    public void pilihFileAJB(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih File PDF"), PICK_PDF_AJB);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");

            startActivityForResult(intent, PICK_PDF_AJB);
        }
    }
    public void pilihFilePetokD(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih File PDF"), PICK_PDF_PetokD);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");

            startActivityForResult(intent, PICK_PDF_PetokD);
        }
    }
    private File createImageFile() {
        String imageFileName = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST2);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST3);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST4);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST5);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST6);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST7);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST8);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST11);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE12) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST12);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SHM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_SHM);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HGB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HGB);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HSHP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HSHP);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PPJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PPJB);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_STRA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_STRA);
            }
        }  else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_AJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_AJB);
            }
        }  else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PetokD) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PetokD);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP1);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SELFIE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_Selfie);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST2);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST3);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST4);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST5);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES6) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST6);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES7) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST7);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST8);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST11);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES12) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST12);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SHM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_SHM);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HGB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HGB);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HSHP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HSHP);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PPJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PPJB);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_STRA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_STRA);
            }
        }  else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_AJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_AJB);
            }
        }  else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PetokD) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PetokD);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP1);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SELFIE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_Selfie);
            }
        } else if (requestCode == CODE_GALLERY_REQUEST1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST2);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST3);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST4);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST5);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST6) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST6);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST7) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST7);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST8);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST11);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST12) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST12);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_SHM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_SHM);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_HGB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HGB);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_HSHP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HSHP);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_PPJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PPJB);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_STRA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_STRA);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        }  else if (requestCode == CODE_GALLERY_REQUEST_AJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_AJB);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        }  else if (requestCode == CODE_GALLERY_REQUEST_PetokD) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PetokD);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_PJP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_PJP1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP1);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_Selfie) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_Selfie);
            } else {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera1();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera2();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera3();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera4();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera5();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST6) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera6();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST7) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera7();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera8();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera9();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera10();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera11();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST12) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera12();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_SHM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraSHM();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_HGB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraHGB();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_HSHP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraHSHP();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_PPJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraPPJB();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_STRA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraSTRA();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        }  else if (requestCode == CODE_CAMERA_REQUEST_AJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraAJB();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        }  else if (requestCode == CODE_CAMERA_REQUEST_PetokD) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraPetokD();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_PJP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraPjp();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_PJP1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraPjp1();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_Selfie) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraSelfie();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDataPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAPS_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {

                latitudeStr = data.getStringExtra("latitude");
                longitudeStr = data.getStringExtra("longitude");
                addressStr = data.getStringExtra("address");
                lokasiStr = data.getStringExtra("lokasi");
                if (latitudeStr == null && longitudeStr == null){
                    CBLokasi.setChecked(false);
                } else {
                    CBLokasi.setChecked(true);
                    maps.setVisibility(View.GONE);
                }
            }
        }

        if (requestCode == CODE_GALLERY_REQUEST1 && resultCode == RESULT_OK && data != null) {
            Uri1 = data.getData();
            iv1.setImageURI(Uri1);
            lyt1.setVisibility(View.VISIBLE);
            select1.setVisibility(View.GONE);
            select2.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST2 && resultCode == RESULT_OK && data != null) {
            Uri2 = data.getData();
            iv2.setImageURI(Uri2);
            lyt2.setVisibility(View.VISIBLE);
            select2.setVisibility(View.GONE);
            select3.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST3 && resultCode == RESULT_OK && data != null) {
            Uri3 = data.getData();
            iv3.setImageURI(Uri3);
            lyt3.setVisibility(View.VISIBLE);
            select3.setVisibility(View.GONE);
            select4.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST4 && resultCode == RESULT_OK && data != null) {
            Uri4 = data.getData();
            iv4.setImageURI(Uri4);
            lyt4.setVisibility(View.VISIBLE);
            select4.setVisibility(View.GONE);
            select5.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST5 && resultCode == RESULT_OK && data != null) {
            Uri5 = data.getData();
            iv5.setImageURI(Uri5);
            lyt5.setVisibility(View.VISIBLE);
            select5.setVisibility(View.GONE);
            select6.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST6 && resultCode == RESULT_OK && data != null) {
            Uri6 = data.getData();
            iv6.setImageURI(Uri6);
            lyt6.setVisibility(View.VISIBLE);
            select6.setVisibility(View.GONE);
            select7.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST7 && resultCode == RESULT_OK && data != null) {
            Uri7 = data.getData();
            iv7.setImageURI(Uri7);
            lyt7.setVisibility(View.VISIBLE);
            select7.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST8 && resultCode == RESULT_OK && data != null) {
            Uri8 = data.getData();
            iv8.setImageURI(Uri8);
            lyt8.setVisibility(View.VISIBLE);
            select.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST9 && resultCode == RESULT_OK && data != null) {
            Uri9 = data.getData();
            iv9.setImageURI(Uri9);
            lyt9.setVisibility(View.VISIBLE);
            select9.setVisibility(View.GONE);
            select10.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST10 && resultCode == RESULT_OK && data != null) {
            Uri10 = data.getData();
            iv10.setImageURI(Uri10);
            lyt10.setVisibility(View.VISIBLE);
            select10.setVisibility(View.GONE);
            select11.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST11 && resultCode == RESULT_OK && data != null) {
            Uri11 = data.getData();
            iv11.setImageURI(Uri11);
            lyt11.setVisibility(View.VISIBLE);
            select11.setVisibility(View.GONE);
            select12.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST12 && resultCode == RESULT_OK && data != null) {
            Uri12 = data.getData();
            iv12.setImageURI(Uri12);
            lyt12.setVisibility(View.VISIBLE);
            select12.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_SHM && resultCode == RESULT_OK && data != null) {
            UriSHM = data.getData();
            IVShm.setImageURI(UriSHM);
            IVShm.setVisibility(View.VISIBLE);
            TVSHM.setVisibility(View.GONE);
            LytSHM.setVisibility(View.VISIBLE);
            LytBtnShm.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_HGB && resultCode == RESULT_OK && data != null) {
            UriHGB = data.getData();
            IVHgb.setImageURI(UriHGB);
            IVHgb.setVisibility(View.VISIBLE);
            TVHGB.setVisibility(View.GONE);
            LytHGB.setVisibility(View.VISIBLE);
            LytBtnHGB.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_HSHP && resultCode == RESULT_OK && data != null) {
            UriHSHP = data.getData();
            IVHshp.setImageURI(UriHSHP);
            IVHshp.setVisibility(View.VISIBLE);
            TVHSHP.setVisibility(View.GONE);
            LytHSHP.setVisibility(View.VISIBLE);
            LytBtnHSHP.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_PPJB && resultCode == RESULT_OK && data != null) {
            UriPPJB = data.getData();
            IVPpjb.setImageURI(UriPPJB);
            IVPpjb.setVisibility(View.VISIBLE);
            TVPPJB.setVisibility(View.GONE);
            LytPPJB.setVisibility(View.VISIBLE);
            LytBtnPPJB.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_STRA && resultCode == RESULT_OK && data != null) {
            UriSTRA = data.getData();
            IVStratatitle.setImageURI(UriSTRA);
            IVStratatitle.setVisibility(View.VISIBLE);
            TVSTRA.setVisibility(View.GONE);
            LytStratatitle.setVisibility(View.VISIBLE);
            LytBtnStra.setVisibility(View.GONE);
        }  else if (requestCode == CODE_GALLERY_REQUEST_AJB && resultCode == RESULT_OK && data != null) {
            UriAJB = data.getData();
            IVAJB.setImageURI(UriSTRA);
            IVAJB.setVisibility(View.VISIBLE);
            TVAJB.setVisibility(View.GONE);
            LytAJB.setVisibility(View.VISIBLE);
            LytBtnAJB.setVisibility(View.GONE);
        }  else if (requestCode == CODE_GALLERY_REQUEST_PetokD && resultCode == RESULT_OK && data != null) {
            UriPetokD = data.getData();
            IVPetokD.setImageURI(UriSTRA);
            IVPetokD.setVisibility(View.VISIBLE);
            TVPetokD.setVisibility(View.GONE);
            LytPetokD.setVisibility(View.VISIBLE);
            LytBtnPetokD.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_PJP && resultCode == RESULT_OK && data != null) {
            UriPJP = data.getData();
            IVPjp.setImageURI(UriPJP);
            LytPjp.setVisibility(View.VISIBLE);
            BtnPjp.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_PJP1 && resultCode == RESULT_OK && data != null) {
            UriPJP1 = data.getData();
            IVPjp1.setImageURI(UriPJP1);
            LytPjp1.setVisibility(View.VISIBLE);
            BtnPjp1.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_Selfie && resultCode == RESULT_OK && data != null) {
            UriSelfie = data.getData();
            IVSelfie.setImageURI(UriSelfie);
//            LytSelfie.setVisibility(View.VISIBLE);
//            BtnSelfie.setVisibility(View.GONE);
            CBSelfie.setChecked(true);
        } else if (requestCode == KODE_REQUEST_KAMERA1 && resultCode == RESULT_OK) {
            iv1.setImageURI(Uri1);
            lyt1.setVisibility(View.VISIBLE);
            select1.setVisibility(View.GONE);
            select2.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA2 && resultCode == RESULT_OK) {
            iv2.setImageURI(Uri2);
            lyt2.setVisibility(View.VISIBLE);
            select2.setVisibility(View.GONE);
            select3.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA3 && resultCode == RESULT_OK) {
            iv3.setImageURI(Uri3);
            lyt3.setVisibility(View.VISIBLE);
            select3.setVisibility(View.GONE);
            select4.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA4 && resultCode == RESULT_OK) {
            iv4.setImageURI(Uri4);
            lyt4.setVisibility(View.VISIBLE);
            select4.setVisibility(View.GONE);
            select5.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA5 && resultCode == RESULT_OK) {
            iv5.setImageURI(Uri5);
            lyt5.setVisibility(View.VISIBLE);
            select5.setVisibility(View.GONE);
            select6.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA6 && resultCode == RESULT_OK) {
            iv6.setImageURI(Uri6);
            lyt6.setVisibility(View.VISIBLE);
            select6.setVisibility(View.GONE);
            select7.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA7 && resultCode == RESULT_OK) {
            iv7.setImageURI(Uri7);
            lyt7.setVisibility(View.VISIBLE);
            select7.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA8 && resultCode == RESULT_OK) {
            iv8.setImageURI(Uri8);
            lyt8.setVisibility(View.VISIBLE);
            select.setVisibility(View.GONE);
            select9.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA9 && resultCode == RESULT_OK) {
            iv9.setImageURI(Uri9);
            lyt9.setVisibility(View.VISIBLE);
            select9.setVisibility(View.GONE);
            select10.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA10 && resultCode == RESULT_OK) {
            iv10.setImageURI(Uri10);
            lyt10.setVisibility(View.VISIBLE);
            select10.setVisibility(View.GONE);
            select11.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA11 && resultCode == RESULT_OK) {
            iv11.setImageURI(Uri8);
            lyt11.setVisibility(View.VISIBLE);
            select11.setVisibility(View.GONE);
            select12.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA12 && resultCode == RESULT_OK) {
            iv12.setImageURI(Uri12);
            lyt12.setVisibility(View.VISIBLE);
            select12.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_SHM && resultCode == RESULT_OK) {
            IVShm.setImageURI(UriSHM);
            TVSHM.setVisibility(View.GONE);
            IVShm.setVisibility(View.VISIBLE);
            LytSHM.setVisibility(View.VISIBLE);
            LytBtnShm.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_HGB && resultCode == RESULT_OK) {
            IVHgb.setImageURI(UriHGB);
            TVHGB.setVisibility(View.GONE);
            IVHgb.setVisibility(View.VISIBLE);
            LytHGB.setVisibility(View.VISIBLE);
            LytBtnHGB.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_HSHP && resultCode == RESULT_OK) {
            IVHshp.setImageURI(UriHSHP);
            TVHSHP.setVisibility(View.GONE);
            IVHshp.setVisibility(View.VISIBLE);
            LytHSHP.setVisibility(View.VISIBLE);
            LytBtnHSHP.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_PPJB && resultCode == RESULT_OK) {
            IVPpjb.setImageURI(UriPPJB);
            TVPPJB.setVisibility(View.GONE);
            IVPpjb.setVisibility(View.VISIBLE);
            LytPPJB.setVisibility(View.VISIBLE);
            LytBtnPPJB.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_STRA && resultCode == RESULT_OK) {
            IVStratatitle.setImageURI(UriSHM);
            TVSTRA.setVisibility(View.GONE);
            IVStratatitle.setVisibility(View.VISIBLE);
            LytStratatitle.setVisibility(View.VISIBLE);
            LytBtnStra.setVisibility(View.GONE);
        }  else if (requestCode == KODE_REQUEST_KAMERA_AJB && resultCode == RESULT_OK) {
            IVAJB.setImageURI(UriAJB);
            TVAJB.setVisibility(View.GONE);
            IVAJB.setVisibility(View.VISIBLE);
            LytAJB.setVisibility(View.VISIBLE);
            LytBtnAJB.setVisibility(View.GONE);
        }  else if (requestCode == KODE_REQUEST_KAMERA_PetokD && resultCode == RESULT_OK) {
            IVPetokD.setImageURI(UriPetokD);
            TVPetokD.setVisibility(View.GONE);
            IVPetokD.setVisibility(View.VISIBLE);
            LytPetokD.setVisibility(View.VISIBLE);
            LytBtnPetokD.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_PJP && resultCode == RESULT_OK) {
            IVPjp.setImageURI(UriPJP);
            LytPjp.setVisibility(View.VISIBLE);
            BtnPjp.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_PJP1 && resultCode == RESULT_OK) {
            IVPjp1.setImageURI(UriPJP1);
            LytPjp1.setVisibility(View.VISIBLE);
            BtnPjp1.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_Selfie && resultCode == RESULT_OK) {
            IVSelfie.setImageURI(UriSelfie);
//            LytSelfie.setVisibility(View.VISIBLE);
//            BtnSelfie.setVisibility(View.GONE);
            CBSelfie.setChecked(true);
        } else if (requestCode == PICK_PDF_SHM && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UriSHMPdf = data.getData();
            LytSHM.setVisibility(View.VISIBLE);
            LytBtnShm.setVisibility(View.GONE);
            TVSHM.setVisibility(View.VISIBLE);
            IVShm.setVisibility(View.GONE);
            String pdfFileName = getFileNameFromUri(UriSHMPdf);
            TVSHM.setText(pdfFileName);
        } else if (requestCode == PICK_PDF_HGB && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UriHGBPdf = data.getData();
            LytHGB.setVisibility(View.VISIBLE);
            LytBtnHGB.setVisibility(View.GONE);
            TVHGB.setVisibility(View.VISIBLE);
            IVHgb.setVisibility(View.GONE);
            String pdfFileName = getFileNameFromUri(UriHGBPdf);
            TVHGB.setText(pdfFileName);
        } else if (requestCode == PICK_PDF_PPJB && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UriPPJBPdf = data.getData();
            LytPPJB.setVisibility(View.VISIBLE);
            LytBtnPPJB.setVisibility(View.GONE);
            TVPPJB.setVisibility(View.VISIBLE);
            IVPpjb.setVisibility(View.GONE);
            String pdfFileName = getFileNameFromUri(UriPPJBPdf);
            TVPPJB.setText(pdfFileName);
        } else if (requestCode == PICK_PDF_HSHP && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UriHSHPPdf = data.getData();
            LytHSHP.setVisibility(View.VISIBLE);
            LytBtnHSHP.setVisibility(View.GONE);
            TVHSHP.setVisibility(View.VISIBLE);
            IVHshp.setVisibility(View.GONE);
            String pdfFileName = getFileNameFromUri(UriHSHPPdf);
            TVHSHP.setText(pdfFileName);
        } else if (requestCode == PICK_PDF_Stratatitle && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UriSTRAPdf = data.getData();
            LytStratatitle.setVisibility(View.VISIBLE);
            LytBtnStra.setVisibility(View.GONE);
            TVSTRA.setVisibility(View.VISIBLE);
            IVStratatitle.setVisibility(View.GONE);
            String pdfFileName = getFileNameFromUri(UriSTRAPdf);
            TVSTRA.setText(pdfFileName);
        } else if (requestCode == PICK_PDF_AJB && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UriAJBPdf = data.getData();
            LytAJB.setVisibility(View.VISIBLE);
            LytBtnAJB.setVisibility(View.GONE);
            TVAJB.setVisibility(View.VISIBLE);
            IVAJB.setVisibility(View.GONE);
            String pdfFileName = getFileNameFromUri(UriAJBPdf);
            TVAJB.setText(pdfFileName);
        } else if (requestCode == PICK_PDF_PetokD && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UriPetokDPdf = data.getData();
            LytPetokD.setVisibility(View.VISIBLE);
            LytBtnPetokD.setVisibility(View.GONE);
            TVPetokD.setVisibility(View.VISIBLE);
            IVPetokD.setVisibility(View.GONE);
            String pdfFileName = getFileNameFromUri(UriPetokDPdf);
            TVPetokD.setText(pdfFileName);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void clearBitmap1() {
        if (Uri1 != null) {
            Uri1 = null;
            lyt1.setVisibility(View.GONE);
            select1.setVisibility(View.VISIBLE);
            select1.setText("Tambah Gambar Tampak Depan");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt1.setVisibility(View.GONE);
                                    select1.setVisibility(View.VISIBLE);
                                    select1.setText("Tambah Gambar Tampak Depan");
                                    isDelete1 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap2() {
        if (Uri2 != null) {
            Uri2 = null;
            lyt2.setVisibility(View.GONE);
            select2.setVisibility(View.VISIBLE);
            select2.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_2,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt2.setVisibility(View.GONE);
                                    select2.setVisibility(View.VISIBLE);
                                    select2.setText("Tambah Gambar");
                                    isDelete2 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap3() {
        if (Uri3 != null) {
            Uri3 = null;
            lyt3.setVisibility(View.GONE);
            select3.setVisibility(View.VISIBLE);
            select3.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_3,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt3.setVisibility(View.GONE);
                                    select3.setVisibility(View.VISIBLE);
                                    select3.setText("Tambah Gambar");
                                    isDelete3 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap4() {
        if (Uri4 != null) {
            Uri4 = null;
            lyt4.setVisibility(View.GONE);
            select4.setVisibility(View.VISIBLE);
            select4.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_4,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt4.setVisibility(View.GONE);
                                    select4.setVisibility(View.VISIBLE);
                                    select4.setText("Tambah Gambar");
                                    isDelete4 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap5() {
        if (Uri5 != null) {
            Uri5 = null;
            lyt5.setVisibility(View.GONE);
            select5.setVisibility(View.VISIBLE);
            select5.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_5,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt5.setVisibility(View.GONE);
                                    select5.setVisibility(View.VISIBLE);
                                    select5.setText("Tambah Gambar");
                                    isDelete5 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap6() {
        if (Uri6 != null) {
            Uri6 = null;
            lyt6.setVisibility(View.GONE);
            select6.setVisibility(View.VISIBLE);
            select6.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_6,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt6.setVisibility(View.GONE);
                                    select6.setVisibility(View.VISIBLE);
                                    select6.setText("Tambah Gambar");
                                    isDelete6 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap7() {
        if (Uri7 != null) {
            Uri7 = null;
            lyt7.setVisibility(View.GONE);
            select7.setVisibility(View.VISIBLE);
            select7.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_7,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt7.setVisibility(View.GONE);
                                    select7.setVisibility(View.VISIBLE);
                                    select7.setText("Tambah Gambar");
                                    isDelete7 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap8() {
        if (Uri8 != null) {
            Uri8 = null;
            lyt8.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
            select.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_8,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt8.setVisibility(View.GONE);
                                    select.setVisibility(View.VISIBLE);
                                    select.setText("Tambah Gambar");
                                    isDelete8 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap9() {
        if (Uri9 != null) {
            Uri9 = null;
            lyt9.setVisibility(View.GONE);
            select9.setVisibility(View.VISIBLE);
            select9.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_9,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt9.setVisibility(View.GONE);
                                    select9.setVisibility(View.VISIBLE);
                                    select9.setText("Tambah Gambar");
                                    isDelete9 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no)
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap10() {
        if (Uri10 != null) {
            Uri10 = null;
            lyt10.setVisibility(View.GONE);
            select10.setVisibility(View.VISIBLE);
            select10.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_10,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt10.setVisibility(View.GONE);
                                    select10.setVisibility(View.VISIBLE);
                                    select10.setText("Tambah Gambar");
                                    isDelete10 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no)
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap11() {
        if (Uri11 != null) {
            Uri11 = null;
            lyt11.setVisibility(View.GONE);
            select11.setVisibility(View.VISIBLE);
            select11.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_11,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt11.setVisibility(View.GONE);
                                    select11.setVisibility(View.VISIBLE);
                                    select11.setText("Tambah Gambar");
                                    isDelete11 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no)
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmap12() {
        if (Uri12 != null) {
            Uri12 = null;
            lyt12.setVisibility(View.GONE);
            select12.setVisibility(View.VISIBLE);
            select12.setText("Tambah Gambar");
        } else {
            pDialog.setMessage("Sedang Menghapus");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_IMAGE_12,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.cancel();
                            try {
                                JSONObject res = new JSONObject(response);
                                String status = res.getString("Status");
                                if (status.equals("Sukses")) {
                                    lyt12.setVisibility(View.GONE);
                                    select12.setVisibility(View.VISIBLE);
                                    select12.setText("Tambah Gambar");
                                    isDelete12 = "0";
                                } else if (status.equals("Error")) {
                                    Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Gagal Menghapus");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    Glide.with(EditDataPralistingActivity.this)
                                            .load(R.mipmap.ic_no)
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Terdapat Masalah Jaringan");
                            ok.setVisibility(View.GONE);

                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            Glide.with(EditDataPralistingActivity.this)
                                    .load(R.mipmap.ic_eror_network_foreground)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("IdPraListing", idpralisting);
                    System.out.println(map);

                    return map;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
    private void clearBitmapSHM() {
        UriSHM = null;
        UriSHMPdf = null;
        LytSHM.setVisibility(View.GONE);
        LytBtnShm.setVisibility(View.VISIBLE);
    }
    private void clearBitmapHGB() {
        UriHGB = null;
        UriHGBPdf = null;
        LytHGB.setVisibility(View.GONE);
        LytBtnHGB.setVisibility(View.VISIBLE);
    }
    private void clearBitmapHSHP() {
        UriHSHP = null;
        UriHSHPPdf = null;
        LytHSHP.setVisibility(View.GONE);
        LytBtnHSHP.setVisibility(View.VISIBLE);
    }
    private void clearBitmapPPJB() {
        UriPPJB = null;
        UriPPJBPdf = null;
        LytPPJB.setVisibility(View.GONE);
        LytBtnPPJB.setVisibility(View.VISIBLE);
    }
    private void clearBitmapSTRA() {
        UriSTRA = null;
        UriSTRAPdf = null;
        LytStratatitle.setVisibility(View.GONE);
        LytBtnStra.setVisibility(View.VISIBLE);
    }
    private void clearBitmapAJB() {
        UriAJB = null;
        UriAJBPdf = null;
        LytAJB.setVisibility(View.GONE);
        LytBtnAJB.setVisibility(View.VISIBLE);
    }
    private void clearBitmapPetokD() {
        UriPetokD = null;
        UriPetokDPdf = null;
        LytPetokD.setVisibility(View.GONE);
        LytBtnPetokD.setVisibility(View.VISIBLE);
    }
    private void clearBitmapPJP() {
        if (UriPJP != null) {
            UriPJP = null;
            LytPjp.setVisibility(View.GONE);
            BtnPjp.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmapPJP1() {
        if (UriPJP1 != null) {
            UriPJP1 = null;
            LytPjp1.setVisibility(View.GONE);
            BtnPjp1.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmapSelfie() {
        if (UriSelfie != null) {
            UriSelfie = null;
//            LytSelfie.setVisibility(View.GONE);
//            BtnSelfie.setVisibility(View.VISIBLE);
            CBSelfie.setChecked(false);
        }
    }
    private void showProgressDialog() {
        pDialog.setMessage("Unggah Gambar");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void HideProgressDialog() {
        pDialog.dismiss();
        pDialog.cancel();
    }
    private void handleImage1Success() {
        if (Uri1 != null) {
            showProgressDialog();
            ImgListing1.putFile(Uri1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing1.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image1 = imageUrl;
                                        handleImage2Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        HideProgressDialog();
                                        handleImage1Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            HideProgressDialog();
                            handleImage1Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            showProgressDialog();
            image1 = isimage1;
            handleImage2Success();
        }
    }
    private void handleImage2Success() {
        if (Uri2 != null) {
            ImgListing2.putFile(Uri2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing2.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image2 = imageUrl;
                                        handleImage3Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage2Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage2Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image2 = isimage2;
            handleImage3Success();
        }
    }
    private void handleImage3Success() {
        if (Uri3 != null) {
            ImgListing3.putFile(Uri3)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing3.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image3 = imageUrl;
                                        handleImage4Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage3Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage3Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image3 = isimage3;
            handleImage4Success();
        }
    }
    private void handleImage4Success() {
        if (Uri4 != null) {
            ImgListing4.putFile(Uri4)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing4.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image4 = imageUrl;
                                        handleImage5Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage4Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage4Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image4 = isimage4;
            handleImage5Success();
        }
    }
    private void handleImage5Success() {
        if (Uri5 != null) {
            ImgListing5.putFile(Uri5)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing5.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image5 = imageUrl;
                                        handleImage6Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage5Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage5Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image5 = isimage5;
            handleImage6Success();
        }
    }
    private void handleImage6Success() {
        if (Uri6 != null) {
            ImgListing6.putFile(Uri6)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing6.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image6 = imageUrl;
                                        handleImage7Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage6Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage6Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image6 = isimage6;
            handleImage7Success();
        }
    }
    private void handleImage7Success() {
        if (Uri7 != null) {
            ImgListing7.putFile(Uri7)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing7.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image7 = imageUrl;
                                        handleImage8Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage7Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage7Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image7 = isimage7;
            handleImage8Success();
        }
    }
    private void handleImage8Success() {
        if (Uri8 != null) {
            ImgListing8.putFile(Uri8)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing8.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image8 = imageUrl;
                                        handleImage9Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage8Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage8Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image8 = isimage8;
            handleImage9Success();
        }
    }
    private void handleImage9Success() {
        if (Uri9 != null) {
            ImgListing9.putFile(Uri9)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing9.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image9 = imageUrl;
                                        handleImage10Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage9Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage9Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image9 = isimage9;
            handleImage10Success();
        }
    }
    private void handleImage10Success() {
        if (Uri10 != null) {
            ImgListing10.putFile(Uri10)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing10.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image10 = imageUrl;
                                        handleImage11Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage10Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage10Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image10 = isimage10;
            handleImage11Success();
        }
    }
    private void handleImage11Success() {
        if (Uri11 != null) {
            ImgListing11.putFile(Uri11)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing11.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image11 = imageUrl;
                                        handleImage12Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage11Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage11Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image11 = isimage11;
            handleImage12Success();
        }
    }
    private void handleImage12Success() {
        if (Uri12 != null) {
            ImgListing12.putFile(Uri12)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing12.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image12 = imageUrl;
                                        handleImageSHMSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage12Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage12Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image12 = isimage12;
            handleImageSHMSuccess();
        }
    }
    private void handleImageSHMSuccess() {
        if (UriSHM != null) {
            ImgSertifikatshm.putFile(UriSHM)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikatshm.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        SHM = imageUrl;
                                        handleImageHGBSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageSHMSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageSHMSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (UriSHMPdf != null) {
            ImgSertifikatshmpdf.putFile(UriSHMPdf)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikatshmpdf.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        SHM = imageUrl;
                                        handleImageHGBSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageSHMSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageSHMSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            SHM = isSHM;
            handleImageHGBSuccess();
        }
    }
    private void handleImageHGBSuccess() {
        if (UriHGB != null) {
            ImgSertifikathgb.putFile(UriHGB)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikathgb.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        HGB = imageUrl;
                                        handleImageHSHPSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageSHMSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageSHMSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (UriHGBPdf != null) {
            ImgSertifikathgbpdf.putFile(UriHGBPdf)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikathgbpdf.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        HGB = imageUrl;
                                        handleImageHSHPSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageSHMSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageSHMSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            HGB = isHGB;
            handleImageHSHPSuccess();
        }
    }
    private void handleImageHSHPSuccess() {
        if (UriHSHP != null) {
            ImgSertifikathshp.putFile(UriHSHP)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikathshp.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        HSHP = imageUrl;
                                        handleImagePPJBSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageHSHPSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageHSHPSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (UriHSHPPdf != null) {
            ImgSertifikathshppdf.putFile(UriHSHPPdf)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikathshppdf.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        HSHP = imageUrl;
                                        handleImagePPJBSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageHSHPSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageHSHPSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            HSHP = isHSHP;
            handleImagePPJBSuccess();
        }
    }
    private void handleImagePPJBSuccess() {
        if (UriPPJB != null) {
            ImgSertifikatppjb.putFile(UriPPJB)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikatppjb.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        PPJB = imageUrl;
                                        handleImageSTRASuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImagePPJBSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImagePPJBSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (UriPPJBPdf != null) {
            ImgSertifikatppjbpdf.putFile(UriPPJBPdf)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikatppjbpdf.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        PPJB = imageUrl;
                                        handleImageSTRASuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImagePPJBSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImagePPJBSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            PPJB = isPPJB;
            handleImageSTRASuccess();
        }
    }
    private void handleImageSTRASuccess() {
        if (UriSTRA != null) {
            ImgSertifikatstra.putFile(UriSTRA)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikatstra.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        STRA = imageUrl;
                                        handleImageAJBSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageSTRASuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageSTRASuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (UriSTRAPdf != null) {
            ImgSertifikatstrapdf.putFile(UriSTRAPdf)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikatstrapdf.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        STRA = imageUrl;
                                        handleImageAJBSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageSTRASuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageSTRASuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            STRA = isSTRA;
            handleImageAJBSuccess();
        }
    }
    private void handleImageAJBSuccess() {
        if (UriAJB != null) {
            ImgSertifikatajb.putFile(UriAJB)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikatajb.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        AJB = imageUrl;
                                        handleImagePetokDSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageAJBSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageAJBSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (UriAJBPdf != null) {
            ImgSertifikatajbpdf.putFile(UriAJBPdf)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikatajbpdf.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        AJB = imageUrl;
                                        handleImagePetokDSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageAJBSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageAJBSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            AJB = isAJB;
            handleImagePetokDSuccess();
        }
    }
    private void handleImagePetokDSuccess() {
        if (UriPetokD != null) {
            ImgSertifikatpetokd.putFile(UriPetokD)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikatpetokd.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        PetokD = imageUrl;
                                        handleImagePJP1Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImagePetokDSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImagePetokDSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (UriPetokDPdf != null) {
            ImgSertifikatpetokdpdf.putFile(UriPetokDPdf)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSertifikatpetokdpdf.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        PetokD = imageUrl;
                                        handleImagePJP1Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImagePetokDSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImagePetokDSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            PetokD = isPetokD;
            handleImagePJP1Success();
        }
    }
    private void handleImagePJP1Success() {
        if (UriPJP != null) {
            ImgPjp.putFile(UriPJP)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgPjp.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        PJPHal1 = imageUrl;
                                        handleImagePJP2Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImagePJP1Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImagePJP1Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            PJPHal1 = isPJP1;
            handleImagePJP2Success();
        }
    }
    private void handleImagePJP2Success() {
        if (UriPJP1 != null) {
            ImgPjp1.putFile(UriPJP1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgPjp1.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        PJPHal2 = imageUrl;
                                        handleImageSelfieSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImagePJP2Success();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImagePJP2Success();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            PJPHal2 = isPJP2;
            handleImageSelfieSuccess();
        }
    }
    private void handleImageSelfieSuccess() {
        if (UriSelfie != null) {
            ImageSelfie.putFile(UriSelfie)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageSelfie.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        ImgSelfie = imageUrl;
                                        HideProgressDialog();
                                        simpanData();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageSelfieSuccess();
                                        Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageSelfieSuccess();
                            Toast.makeText(EditDataPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            ImgSelfie = isSelfie;
            HideProgressDialog();
            simpanData();
        }
    }
    public boolean Validate() {
        if (Etnamaproperti.getText().toString().equals("")) {
            Etnamaproperti.setError("Harap Isi Nama Properti");
            Etnamaproperti.requestFocus();
            return false;
        }
        if (Etalamatproperti.getText().toString().equals("")) {
            Etalamatproperti.setError("Harap Isi Alamat Properti");
            Etalamatproperti.requestFocus();
            return false;
        }
        if (Etstatus.getText().toString().equals("")) {
            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Pilih Status Properti");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(EditDataPralistingActivity.this)
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        } else if (Etstatus.getText().toString().equals("Jual")) {
            if (Etharga.getText().toString().equals("")) {
                Etharga.setError("Harap Isi Harga Properti");
                Etharga.requestFocus();
                return false;
            }
        } else if (Etstatus.getText().toString().equals("Sewa")) {
            if (Ethargasewa.getText().toString().equals("")) {
                Ethargasewa.setError("Harap Isi Harga Properti");
                Ethargasewa.requestFocus();
                return false;
            }
        } else {
            if (Etharga.getText().toString().equals("")) {
                Etharga.setError("Harap Isi Harga Properti");
                Etharga.requestFocus();
                return false;
            }
            if (Ethargasewa.getText().toString().equals("")) {
                Ethargasewa.setError("Harap Isi Harga Properti");
                Ethargasewa.requestFocus();
                return false;
            }
        }
        if (Etbanner.getText().toString().equals("")) {
            Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Isi Pemasangan Banner");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(EditDataPralistingActivity.this)
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        } else if (Etbanner.getText().toString().equals("Ya")) {
            if (Etsize.getText().toString().equals("")) {
                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap Isi Ukuran Banner");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditDataPralistingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        return true;
    }
    private void simpanData() {
        pDialog.setMessage("Menyimpan Pembaruan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_PRALISTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
                                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Berhasil Menambahkan Listingan");
                                cobalagi.setVisibility(View.GONE);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DEVICE, null, new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {
                                                try {
                                                    ArrayList<String> tokens = new ArrayList<>();
                                                    for (int i = 0; i < response.length(); i++) {
                                                        JSONObject tokenObject = response.getJSONObject(i);
                                                        String token = tokenObject.getString("Token");
                                                        tokens.add(token);
                                                    }
                                                    new EditDataPralistingActivity.SendMessageTask().execute(tokens.toArray(new String[0]));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                            }
                                        });
                                        requestQueue.add(jsonArrayRequest);
                                        customDialog.dismiss();
                                        finish();
                                    }
                                });

                                Glide.with(EditDataPralistingActivity.this)
                                        .load(R.mipmap.ic_yes)
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            } else if (status.equals("Error")) {
                                Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Gagal Menambahkan Listingan");
                                ok.setVisibility(View.GONE);

                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                        finish();
                                    }
                                });

                                Glide.with(EditDataPralistingActivity.this)
                                        .load(R.mipmap.ic_no)
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.cancel();
                        Dialog customDialog = new Dialog(EditDataPralistingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Terdapat Masalah Jaringan");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(EditDataPralistingActivity.this)
                                .load(R.mipmap.ic_eror_network_foreground)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                String img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;
                if (latitudeStr == null) {
                    Lat = "0";
                } else {
                    Lat = latitudeStr;
                }
                if (longitudeStr == null) {
                    Lng = "0";
                } else {
                    Lng = longitudeStr;
                }
                if (HargaString == null){
                    HargaString = Etharga.getText().toString();
                }
                if (HargaSewaString == null){
                    HargaSewaString = Ethargasewa.getText().toString();
                }
                if (isDelete1 == null){
                    img1 = image1;
                } else {
                    img1 = "0";
                }
                if (isDelete2 == null){
                    img2 = image2;
                } else {
                    img2 = "0";
                }
                if (isDelete3 == null){
                    img3 = image3;
                } else {
                    img3 = "0";
                }
                if (isDelete4 == null){
                    img4 = image4;
                } else {
                    img4 = "0";
                }
                if (isDelete5 == null){
                    img5 = image5;
                } else {
                    img5 = "0";
                }
                if (isDelete6 == null){
                    img6 = image6;
                } else {
                    img6 = "0";
                }
                if (isDelete7 == null){
                    img7 = image7;
                } else {
                    img7 = "0";
                }
                if (isDelete8 == null){
                    img8 = image8;
                } else {
                    img8 = "0";
                }
                if (isDelete9 == null){
                    img9 = image9;
                } else {
                    img9 = "0";
                }
                if (isDelete10 == null){
                    img10 = image10;
                } else {
                    img10 = "0";
                }
                if (isDelete11 == null){
                    img11 = image11;
                } else {
                    img11 = "0";
                }
                if (isDelete12 == null){
                    img12 = image12;
                } else {
                    img12 = "0";
                }

                final String StringSHM = CBSHM.isChecked() ? "1" : "0";
                final String StringHGB = CBHGB.isChecked() ? "1" : "0";
                final String StringHSHP = CBHSHP.isChecked() ? "1" : "0";
                final String StringPPJB = CBPPJB.isChecked() ? "1" : "0";
                final String StringSTRA = CBSTRA.isChecked() ? "1" : "0";
                final String StringAJB = CBAJB.isChecked() ? "1" : "0";
                final String StringPetokD = CBPetokD.isChecked() ? "1" : "0";
                final String StringMarketable = CBMarketable.isChecked() ? "1" : "0";
                final String StringHarga = CBHarga.isChecked() ? "1" : "0";
                final String StringSelfie = CBSelfie.isChecked() ? "1" : "0";
                final String StringLokasi = CBLokasi.isChecked() ? "1" : "0";

                map.put("IdPraListing", idpralisting);
                map.put("NamaListing", Etnamaproperti.getText().toString());
                map.put("Alamat", Etalamatproperti.getText().toString());
                map.put("AlamatTemplate", Etalamatpropertitemplate.getText().toString());
                map.put("Wilayah", Etwilayahproperti.getText().toString());
                map.put("Wide", Etluas.getText().toString());
                map.put("Land", Etland.getText().toString());
                map.put("Dimensi", Etdimensi.getText().toString());
                map.put("Listrik", Etlistrik.getText().toString());
                map.put("Level", Etlantai.getText().toString());
                map.put("Bed", Etbed.getText().toString());
                map.put("Bath", Etbath.getText().toString());
                map.put("BedArt", Etbedart.getText().toString());
                map.put("BathArt", Etbathart.getText().toString());
                map.put("Garage", Etgarasi.getText().toString());
                map.put("Carpot", Etcarpot.getText().toString());
                map.put("Hadap", Ethadap.getText().toString());
                map.put("SHM", StringSHM);
                map.put("HGB", StringHGB);
                map.put("HSHP", StringHSHP);
                map.put("PPJB", StringPPJB);
                map.put("Stratatitle", StringSTRA);
                map.put("AJB", StringAJB);
                map.put("PetokD", StringPetokD);
                map.put("ImgSHM", SHM);
                map.put("ImgHGB", HGB);
                map.put("ImgHSHP", HSHP);
                map.put("ImgPPJB", PPJB);
                map.put("ImgStratatitle", STRA);
                map.put("ImgAJB", AJB);
                map.put("ImgPetokD", PetokD);
                map.put("ImgPjp", PJPHal1);
                map.put("ImgPjp1", PJPHal2);
                map.put("JenisProperti", Etjenisproperti.getText().toString());
                map.put("SumberAir", Etair.getText().toString());
                map.put("Kondisi", Etstatus.getText().toString());
                map.put("Deskripsi", Etketerangan.getText().toString());
                map.put("Prabot", Etperabot.getText().toString());
                map.put("KetPrabot", Etketperabot.getText().toString());
                map.put("Priority", priority);
                map.put("Banner", Etbanner.getText().toString());
                map.put("Size", Etsize.getText().toString());
                map.put("Harga", HargaString);
                map.put("HargaSewa", HargaSewaString);
                map.put("Img1", img1);
                map.put("Img2", img2);
                map.put("Img3", img3);
                map.put("Img4", img4);
                map.put("Img5", img5);
                map.put("Img6", img6);
                map.put("Img7", img7);
                map.put("Img8", img8);
                map.put("Img9", img9);
                map.put("Img10", img10);
                map.put("Img11", img11);
                map.put("Img12", img12);
                map.put("Video", idnull);
                map.put("LinkFacebook", idnull);
                map.put("LinkTiktok", idnull);
                map.put("LinkInstagram", idnull);
                map.put("LinkYoutube", idnull);
                map.put("Fee", EtFee.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void fetchDataDaerah() {
        daerahManager.fetchDataFromApi(this, new DaerahManager.ApiCallback() {
            @Override
            public void onSuccess(List<DaerahManager.DataItem> dataList) {
                showAlertDaerah(dataList);
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }
    private void showAlertDaerah(List<DaerahManager.DataItem> dataList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Daftar Kota");

        final String[] dataItems = new String[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            DaerahManager.DataItem item = dataList.get(i);
            dataItems[i] = item.getNamaDaerah();
        }

        builder.setItems(dataItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DaerahManager.DataItem selectedData = dataList.get(which);
                String iddaerah = selectedData.getIdDaerah();
                fetchDataWilayah(iddaerah);
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etwilayahproperti.setText("");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void fetchDataWilayah(String Id) {
        wilayahManager.fetchDataFromApi(this, Id, new WilayahManager.ApiCallback() {
            @Override
            public void onSuccess(List<WilayahManager.DataItem> dataList) {
                showAlertWilayah(dataList);
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }
    private void showAlertWilayah(List<WilayahManager.DataItem> dataList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Daftar Wilayah");

        final String[] dataItems = new String[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            WilayahManager.DataItem item = dataList.get(i);
            dataItems[i] = item.getNamaWilayah();
        }

        builder.setItems(dataItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WilayahManager.DataItem selectedData = dataList.get(which);
                Etwilayahproperti.setText(selectedData.getNamaWilayah());
//                handleSelectedWilayah(selectedData);
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Tambah Wilayah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showTambahWilayah();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                fetchDataDaerah();
            }
        });
        builder.show();
    }
    private void showTambahWilayah() {
        AlertDialog.Builder customBuilder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        customBuilder.setTitle("Wilayah Lainnya");

        LinearLayout containerLayout = new LinearLayout(this);
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        containerLayout.setPadding(50, 20, 50, 0);

        final EditText customNamaWilayah = new EditText(this);
        customNamaWilayah.setHint("Masukkan Nama Wilayah");
        customNamaWilayah.setTextColor(getResources().getColor(android.R.color.black));
        customNamaWilayah.setHintTextColor(getResources().getColor(android.R.color.black));

        containerLayout.addView(customNamaWilayah);

        customBuilder.setView(containerLayout);

        customBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String customWilayah = customNamaWilayah.getText().toString();
                Etwilayahproperti.setText(customWilayah);
            }
        });

        customBuilder.setNegativeButton("Batal", null);

        AlertDialog customDialog = customBuilder.create();
        customDialog.show();
    }
    public void ShowPjp(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ketersediaan PJP");

        final CharSequence[] JenisProperti = {"Ya", "Tidak"};
        final int[] SelectedJenisProperti = {0};

        builder.setSingleChoiceItems(JenisProperti, SelectedJenisProperti[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedJenisProperti[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EtPjp.setText(JenisProperti[SelectedJenisProperti[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void ShowJenisProperti(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Jenis Properti");

        final CharSequence[] JenisProperti = {"Rumah", "Ruko", "Tanah", "Gudang", "Ruang Usaha", "Villa", "Apartemen", "Pabrik", "Kantor", "Hotel", "Rukost"};
        final int[] SelectedJenisProperti = {0};

        builder.setSingleChoiceItems(JenisProperti, SelectedJenisProperti[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedJenisProperti[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etjenisproperti.setText(JenisProperti[SelectedJenisProperti[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void ShowSumberAir(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Sumber Air");

        final CharSequence[] SumberAir = {"PAM atau PDAM", "Sumur Pompa", "Sumur Bor", "Sumur Resapan", "Sumur Galian", "Artesis"};
        final int[] SelectedSumberAir = {0};

        builder.setSingleChoiceItems(SumberAir, SelectedSumberAir[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedSumberAir[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etair.setText(SumberAir[SelectedSumberAir[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void ShowPerabot(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ketersediaan Prabot");

        final CharSequence[] Perabot = {"Ada", "Tidak"};
        final int[] SelectedPerabot = {0};

        builder.setSingleChoiceItems(Perabot, SelectedPerabot[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedPerabot[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etperabot.setText(Perabot[SelectedPerabot[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void ShowBanner(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Pemasangan Banner");

        final CharSequence[] Banner = {"Ya", "Tidak"};
        final int[] SelectedBanner = {0};

        builder.setSingleChoiceItems(Banner, SelectedBanner[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedBanner[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etbanner.setText(Banner[SelectedBanner[0]]);
                if (Banner[SelectedBanner[0]].equals("Ya")) {
                    Etsize.setVisibility(View.VISIBLE);
                }
                /*if (Banner[SelectedBanner[0]] == "Ya"){
                    size.setVisibility(View.VISIBLE);
                }*/
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void ShowSize(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ukuran Banner");

        final CharSequence[] Banner = {"80 X 90", "100 X 125", "180 X 80", "Lainnya"};
        final int[] SelectedBanner = {0};

        builder.setSingleChoiceItems(Banner, SelectedBanner[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == Banner.length - 1) {
                    ShowCustomSize();
                } else {
                    Etsize.setText(Banner[which]);
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void ShowCustomSize() {
        final EditText editTextCustomType = new EditText(this);
        editTextCustomType.setHint("Masukkan Ukuran Banner");
        editTextCustomType.setTextColor(getResources().getColor(android.R.color.black));
        editTextCustomType.setHintTextColor(getResources().getColor(android.R.color.black));
        editTextCustomType.setBackgroundColor(getResources().getColor(android.R.color.white));
        editTextCustomType.setPadding(50, 20, 50, 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ukuran Banner")
                .setView(editTextCustomType)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String customType = editTextCustomType.getText().toString().trim();
                        if (!customType.isEmpty()) {
                            Etsize.setText(customType);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void ShowStatus(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Status Properti");

        final CharSequence[] Status = {"Jual", "Sewa", "Jual/Sewa"};
        final int[] SelectedStatus = {0};

        builder.setSingleChoiceItems(Status, SelectedStatus[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedStatus[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etstatus.setText(Status[SelectedStatus[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }
    private class SendMessageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String token : params) {
                sendNotificationToToken(token, "pesan");
            }
            return null;
        }
        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void sendNotificationToToken(String token, String notificationType) {
        String title = Preferences.getKeyNama(this);
        String message = "Melakukan Update PraListing";
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }
}