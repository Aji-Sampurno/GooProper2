package com.gooproper.ui.tambah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.util.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TambahListingPrimaryActivity extends AppCompatActivity {

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
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1 = 11;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES1 = 12;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2 = 13;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES2 = 14;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3 = 15;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES3 = 16;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4 = 17;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES4 = 18;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5 = 19;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES5 = 20;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6 = 21;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES6 = 22;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7 = 23;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES7 = 24;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8 = 25;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES8 = 26;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9 = 27;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES9 = 28;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10 = 29;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES10 = 30;
    private static final int MAPS_ACTIVITY_REQUEST_CODE = 31;
    Uri Uri1, Uri2, Uri3, Uri4, Uri5, Uri6, Uri7, Uri8, Uri9, Uri10;
    LinearLayout Lyt1, Lyt2, Lyt3, Lyt4, Lyt5, Lyt6, Lyt7, Lyt8, Lyt9, Lyt10;
    Button BtnBatal, BtnSubmit, BtnSelect1, BtnSelect2, BtnSelect3, BtnSelect4, BtnSelect5, BtnSelect6, BtnSelect7, BtnSelect8, BtnSelect9, BtnSelect10;
    ImageView IV1, IV2, IV3, IV4, IV5, IV6, IV7, IV8, IV9, IV10, IVDelete1, IVDelete2, IVDelete3, IVDelete4, IVDelete5, IVDelete6, IVDelete7, IVDelete8, IVDelete9, IVDelete10;
    TextInputEditText ETJudul, ETHarga, ETLokasi, ETDeskripsi, ETKontak1, ETKontak2;
    String latitudeStr, longitudeStr, addressStr, Lat, Lng, token;
    String Image1, Image2, Image3, Image4, Image5, Image6, Image7, Image8, Image9, Image10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_listing_primary);

        pDialog = new ProgressDialog(TambahListingPrimaryActivity.this);

        Lyt1 = findViewById(R.id.LytGambar1);
        Lyt2 = findViewById(R.id.LytGambar2);
        Lyt3 = findViewById(R.id.LytGambar3);
        Lyt4 = findViewById(R.id.LytGambar4);
        Lyt5 = findViewById(R.id.LytGambar5);
        Lyt6 = findViewById(R.id.LytGambar6);
        Lyt7 = findViewById(R.id.LytGambar7);
        Lyt8 = findViewById(R.id.LytGambar8);
        Lyt9 = findViewById(R.id.LytGambar9);
        Lyt10 = findViewById(R.id.LytGambar10);

        BtnBatal = findViewById(R.id.BtnBatal);
        BtnSubmit = findViewById(R.id.BtnSubmit);
        BtnSelect1 = findViewById(R.id.BtnTambahGambar1);
        BtnSelect2 = findViewById(R.id.BtnTambahGambar2);
        BtnSelect3 = findViewById(R.id.BtnTambahGambar3);
        BtnSelect4 = findViewById(R.id.BtnTambahGambar4);
        BtnSelect5 = findViewById(R.id.BtnTambahGambar5);
        BtnSelect6 = findViewById(R.id.BtnTambahGambar6);
        BtnSelect7 = findViewById(R.id.BtnTambahGambar7);
        BtnSelect8 = findViewById(R.id.BtnTambahGambar8);
        BtnSelect9 = findViewById(R.id.BtnTambahGambar9);
        BtnSelect10 = findViewById(R.id.BtnTambahGambar10);

        IV1 = findViewById(R.id.IV1);
        IV2 = findViewById(R.id.IV2);
        IV3 = findViewById(R.id.IV3);
        IV4 = findViewById(R.id.IV4);
        IV5 = findViewById(R.id.IV5);
        IV6 = findViewById(R.id.IV6);
        IV7 = findViewById(R.id.IV7);
        IV8 = findViewById(R.id.IV8);
        IV9 = findViewById(R.id.IV9);
        IV10 = findViewById(R.id.IV10);
        IVDelete1 = findViewById(R.id.IVDelete1);
        IVDelete2 = findViewById(R.id.IVDelete2);
        IVDelete3 = findViewById(R.id.IVDelete3);
        IVDelete4 = findViewById(R.id.IVDelete4);
        IVDelete5 = findViewById(R.id.IVDelete5);
        IVDelete6 = findViewById(R.id.IVDelete6);
        IVDelete7 = findViewById(R.id.IVDelete7);
        IVDelete8 = findViewById(R.id.IVDelete8);
        IVDelete9 = findViewById(R.id.IVDelete9);
        IVDelete10 = findViewById(R.id.IVDelete10);

        ETJudul = findViewById(R.id.ETJudulListingPrimary);
        ETLokasi = findViewById(R.id.ETLokasiListingPrimary);
        ETHarga = findViewById(R.id.ETHargaListingPrimary);
        ETDeskripsi = findViewById(R.id.ETDeskripsiListingPrimary);
        ETKontak1 = findViewById(R.id.ETKontak1ListingPrimary);
        ETKontak2 = findViewById(R.id.ETKontak2ListingPrimary);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String filePrimary1 = "Listing1_" + timeStamp + ".jpg";
        String filePrimary2 = "Listing2_" + timeStamp + ".jpg";
        String filePrimary3 = "Listing3_" + timeStamp + ".jpg";
        String filePrimary4 = "Listing4_" + timeStamp + ".jpg";
        String filePrimary5 = "Listing5_" + timeStamp + ".jpg";
        String filePrimary6 = "Listing6_" + timeStamp + ".jpg";
        String filePrimary7 = "Listing7_" + timeStamp + ".jpg";
        String filePrimary8 = "Listing8_" + timeStamp + ".jpg";
        String filePrimary9 = "Listing9_" + timeStamp + ".jpg";
        String filePrimary10 = "Listing10_" + timeStamp + ".jpg";

        BtnSelect1.setOnClickListener(view -> showSelect1());
        BtnSelect2.setOnClickListener(view -> showSelect2());
        BtnSelect3.setOnClickListener(view -> showSelect3());
        BtnSelect4.setOnClickListener(view -> showSelect4());
        BtnSelect5.setOnClickListener(view -> showSelect5());
        BtnSelect6.setOnClickListener(view -> showSelect6());
        BtnSelect7.setOnClickListener(view -> showSelect7());
        BtnSelect8.setOnClickListener(view -> showSelect8());
        BtnSelect9.setOnClickListener(view -> showSelect9());
        BtnSelect10.setOnClickListener(view -> showSelect10());
        IVDelete1.setOnClickListener(view -> clearUri1());
        IVDelete2.setOnClickListener(view -> clearUri2());
        IVDelete3.setOnClickListener(view -> clearUri3());
        IVDelete4.setOnClickListener(view -> clearUri4());
        IVDelete5.setOnClickListener(view -> clearUri5());
        IVDelete6.setOnClickListener(view -> clearUri6());
        IVDelete7.setOnClickListener(view -> clearUri7());
        IVDelete8.setOnClickListener(view -> clearUri8());
        IVDelete9.setOnClickListener(view -> clearUri9());
        IVDelete10.setOnClickListener(view -> clearUri10());
        BtnBatal.setOnClickListener(view -> finish());
//        BtnSubmit.setOnClickListener(view -> simpanData());
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.setMessage("Menyimpan Data");
                pDialog.setCancelable(false);
                pDialog.show();

                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference ImgListing1 = storageRef.child("primary/" + filePrimary1);
                StorageReference ImgListing2 = storageRef.child("primary/" + filePrimary2);
                StorageReference ImgListing3 = storageRef.child("primary/" + filePrimary3);
                StorageReference ImgListing4 = storageRef.child("primary/" + filePrimary4);
                StorageReference ImgListing5 = storageRef.child("primary/" + filePrimary5);
                StorageReference ImgListing6 = storageRef.child("primary/" + filePrimary6);
                StorageReference ImgListing7 = storageRef.child("primary/" + filePrimary7);
                StorageReference ImgListing8 = storageRef.child("primary/" + filePrimary8);
                StorageReference ImgListing9 = storageRef.child("primary/" + filePrimary9);
                StorageReference ImgListing10 = storageRef.child("primary/" + filePrimary10);

                List<StorageTask<UploadTask.TaskSnapshot>> uploadTasks = new ArrayList<>();

                if (Uri1 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task1 = ImgListing1.putFile(Uri1)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgListing1.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image1 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task1);
                } else {
                    Image1 = "0";
                }
                if (Uri2 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task2 = ImgListing2.putFile(Uri2)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgListing2.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image2 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task2);
                } else {
                    Image2 = "0";
                }
                if (Uri3 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task3 = ImgListing3.putFile(Uri3)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgListing3.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image3 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task3);
                } else {
                    Image3 = "0";
                }
                if (Uri4 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task4 = ImgListing4.putFile(Uri4)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgListing4.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image4 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task4);
                } else {
                    Image4 = "0";
                }
                if (Uri5 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task5 = ImgListing5.putFile(Uri5)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgListing5.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image5 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task5);
                } else {
                    Image5 = "0";
                }
                if (Uri6 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task6 = ImgListing6.putFile(Uri6)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgListing6.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image6 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task6);
                } else {
                    Image6 = "0";
                }
                if (Uri7 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task7 = ImgListing7.putFile(Uri7)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgListing7.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image7 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task7);
                } else {
                    Image7 = "0";
                }
                if (Uri8 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task8 = ImgListing8.putFile(Uri8)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgListing8.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image8 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task8);
                } else {
                    Image8 = "0";
                }
                if (Uri9 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task9 = ImgListing9.putFile(Uri9)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgListing9.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image9 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task9);
                } else {
                    Image9 = "0";
                }
                if (Uri10 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task10 = ImgListing10.putFile(Uri10)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgListing10.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image10 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task10);
                } else {
                    Image10 = "0";
                }

                Tasks.whenAllSuccess(uploadTasks)
                        .addOnSuccessListener(results -> {
                            pDialog.cancel();
                            simpanData();
                        })
                        .addOnFailureListener(exception -> {
                            Dialog customDialog = new Dialog(TambahListingPrimaryActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_eror_input);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                            tv.setText("Gagal Saat Unggah Gambar");

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                            Glide.with(TambahListingPrimaryActivity.this)
                                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifImageView);

                            customDialog.show();
                        });
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
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
        }  else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            }
        }  else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
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
        }  else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            }
        }  else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
            }
        } else if (requestCode == CODE_GALLERY_REQUEST1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST2);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST3);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST4);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST5);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST6) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST6);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST7) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST7);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST8);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
            } else {

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
            }
        }

        if (requestCode == CODE_GALLERY_REQUEST1 && resultCode == RESULT_OK && data != null) {
            Uri1 = data.getData();
            IV1.setImageURI(Uri1);
            Lyt1.setVisibility(View.VISIBLE);
            BtnSelect1.setVisibility(View.GONE);
            BtnSelect2.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST2 && resultCode == RESULT_OK && data != null) {
            Uri2 = data.getData();
            IV2.setImageURI(Uri2);
            Lyt2.setVisibility(View.VISIBLE);
            BtnSelect2.setVisibility(View.GONE);
            BtnSelect3.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST3 && resultCode == RESULT_OK && data != null) {
            Uri3 = data.getData();
            IV3.setImageURI(Uri3);
            Lyt3.setVisibility(View.VISIBLE);
            BtnSelect3.setVisibility(View.GONE);
            BtnSelect4.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST4 && resultCode == RESULT_OK && data != null) {
            Uri4 = data.getData();
            IV4.setImageURI(Uri4);
            Lyt4.setVisibility(View.VISIBLE);
            BtnSelect4.setVisibility(View.GONE);
            BtnSelect5.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST5 && resultCode == RESULT_OK && data != null) {
            Uri5 = data.getData();
            IV5.setImageURI(Uri5);
            Lyt5.setVisibility(View.VISIBLE);
            BtnSelect5.setVisibility(View.GONE);
            BtnSelect6.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST6 && resultCode == RESULT_OK && data != null) {
            Uri6 = data.getData();
            IV6.setImageURI(Uri6);
            Lyt6.setVisibility(View.VISIBLE);
            BtnSelect6.setVisibility(View.GONE);
            BtnSelect7.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST7 && resultCode == RESULT_OK && data != null) {
            Uri7 = data.getData();
            IV7.setImageURI(Uri7);
            Lyt7.setVisibility(View.VISIBLE);
            BtnSelect7.setVisibility(View.GONE);
            BtnSelect8.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST8 && resultCode == RESULT_OK && data != null) {
            Uri8 = data.getData();
            IV8.setImageURI(Uri8);
            Lyt8.setVisibility(View.VISIBLE);
            BtnSelect8.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST9 && resultCode == RESULT_OK && data != null) {
            Uri9 = data.getData();
            IV9.setImageURI(Uri9);
            Lyt9.setVisibility(View.VISIBLE);
            BtnSelect9.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST10 && resultCode == RESULT_OK && data != null) {
            Uri10 = data.getData();
            IV10.setImageURI(Uri10);
            Lyt10.setVisibility(View.VISIBLE);
            BtnSelect10.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showSelect1() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES1);
        }
    }
    private void showSelect2() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES2);
        }
    }
    private void showSelect3() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES3);
        }
    }
    private void showSelect4() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES4);
        }
    }
    private void showSelect5() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES5);
        }
    }
    private void showSelect6() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES6);
        }
    }
    private void showSelect7() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES7);
        }
    }
    private void showSelect8() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES8);
        }
    }
    private void showSelect9() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES9);
        }
    }
    private void showSelect10() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES10);
        }
    }
    private void clearUri1() {
        if (Uri1 != null) {
            Uri1 = null;
            Lyt1.setVisibility(View.GONE);
            BtnSelect1.setVisibility(View.VISIBLE);
        }
    }
    private void clearUri2() {
        if (Uri2 != null) {
            Uri2 = null;
            Lyt2.setVisibility(View.GONE);
            BtnSelect2.setVisibility(View.VISIBLE);
        }
    }
    private void clearUri3() {
        if (Uri3 != null) {
            Uri3 = null;
            Lyt3.setVisibility(View.GONE);
            BtnSelect3.setVisibility(View.VISIBLE);
        }
    }
    private void clearUri4() {
        if (Uri4 != null) {
            Uri4 = null;
            Lyt4.setVisibility(View.GONE);
            BtnSelect4.setVisibility(View.VISIBLE);
        }
    }
    private void clearUri5() {
        if (Uri5 != null) {
            Uri5 = null;
            Lyt5.setVisibility(View.GONE);
            BtnSelect5.setVisibility(View.VISIBLE);
        }
    }
    private void clearUri6() {
        if (Uri6 != null) {
            Uri6 = null;
            Lyt6.setVisibility(View.GONE);
            BtnSelect6.setVisibility(View.VISIBLE);
        }
    }
    private void clearUri7() {
        if (Uri7 != null) {
            Uri7 = null;
            Lyt7.setVisibility(View.GONE);
            BtnSelect7.setVisibility(View.VISIBLE);
        }
    }
    private void clearUri8() {
        if (Uri8 != null) {
            Uri8 = null;
            Lyt8.setVisibility(View.GONE);
            BtnSelect8.setVisibility(View.VISIBLE);
        }
    }
    private void clearUri9() {
        if (Uri9 != null) {
            Uri9 = null;
            Lyt9.setVisibility(View.GONE);
            BtnSelect9.setVisibility(View.VISIBLE);
        }
    }
    private void clearUri10() {
        if (Uri10 != null) {
            Uri10 = null;
            Lyt10.setVisibility(View.GONE);
            BtnSelect10.setVisibility(View.VISIBLE);
        }
    }
    private void simpanData() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_PRIMARY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            String idprimary = res.getString("Id");
                            if (status.equals("Sukses")) {
                                Dialog customDialog = new Dialog(TambahListingPrimaryActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Lanjukan Tambah Tipe");
                                cobalagi.setVisibility(View.GONE);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent update = new Intent(TambahListingPrimaryActivity.this, TambahTipeListingPrimaryActivity.class);
                                        update.putExtra("IdPrimary",idprimary);
                                        startActivity(update);
                                        finish();
                                    }
                                });

                                Glide.with(TambahListingPrimaryActivity.this)
                                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            } else if (status.equals("Error")) {
                                Dialog customDialog = new Dialog(TambahListingPrimaryActivity.this);
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
                                    }
                                });

                                Glide.with(TambahListingPrimaryActivity.this)
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
                        Dialog customDialog = new Dialog(TambahListingPrimaryActivity.this);
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

                        Glide.with(TambahListingPrimaryActivity.this)
                                .load(R.mipmap.ic_eror_network_foreground)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                String Lokasi = "0";

                map.put("JudulListingPrimary", ETJudul.getText().toString());
                map.put("HargaListingPrimary", ETHarga.getText().toString());
                map.put("DeskripsiListingPrimary", ETDeskripsi.getText().toString());
                map.put("AlamatListingPrimary", ETLokasi.getText().toString());
                map.put("LatitudeListingPrimary", Lokasi);
                map.put("LongitudeListingPrimary", Lokasi);
                map.put("LocationListingPrimary", ETLokasi.getText().toString());
                map.put("KontakPerson1", ETKontak1.getText().toString());
                map.put("KontakPerson2", ETKontak2.getText().toString());
                map.put("Img1", Image1);
                map.put("Img2", Image2);
                map.put("Img3", Image3);
                map.put("Img4", Image4);
                map.put("Img5", Image5);
                map.put("Img6", Image6);
                map.put("Img7", Image7);
                map.put("Img8", Image8);
                map.put("Img9", Image9);
                map.put("Img10", Image10);
                System.out.println(map);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}