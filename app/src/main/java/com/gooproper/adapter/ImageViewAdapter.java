package com.gooproper.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;
import com.gooproper.R;
import com.gooproper.ui.ImageViewActivity;
import com.gooproper.util.ImageDownloader;
import com.gooproper.util.ImageDownloaderMIUI;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ImageViewAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> images;
    GestureDetector gestureDetector;
    BitmapDrawable bitmapDrawable;
    Bitmap bitmap;

    public ImageViewAdapter(Context context, ArrayList<String> images){
        this.context = context;
        this.images = images;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.image_viewpager, null);
        PhotoView imageView = view.findViewById(R.id.ivlisting);
        Picasso.get()
                .load(images.get(position))
                .into(imageView);
        container.addView(view, 0);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.CustomAlertDialogStyle);
                builder.setTitle("Konfirmasi Unduhan");
                builder.setMessage("Apakah Anda ingin mengunduh gambar ini?");
                builder.setPositiveButton("Ya", (dialog, which) -> {
                    String imageUrl = images.get(position);
                    if (isMIUI()) {
                        ImageDownloaderMIUI imageDownloaderMIUI = new ImageDownloaderMIUI(context, imageView);
                        imageDownloaderMIUI.execute(imageUrl);
                    } else {
                        ImageDownloaderMIUI imageDownloaderMIUI = new ImageDownloaderMIUI(context, imageView);
                        imageDownloaderMIUI.execute(imageUrl);
//                        ImageDownloader imageDownloader = new ImageDownloader(context, imageView);
//                        imageDownloader.execute(imageUrl);
                    }

//                    bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
//                    if (imageView.getDrawable() instanceof BitmapDrawable) {
//                        bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
//                        bitmap = bitmapDrawable.getBitmap();
//
//                        FileOutputStream fileOutputStream = null;
//
//                        File sdCard = Environment.getExternalStorageDirectory();
//                        File Directory = new File(sdCard.getAbsolutePath()+"/Download");
//                        Directory.mkdir();
//
//                        String filename = String.format("%d.jpg", System.currentTimeMillis());
//                        File outfile = new File(Directory,filename);
//
//                        Toast.makeText(context, "Berhasil Download", Toast.LENGTH_SHORT).show();
//                        try {
//                            fileOutputStream = new FileOutputStream(outfile);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
//                            fileOutputStream.flush();
//                            fileOutputStream.close();
//
//                            Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                            intent.setData(Uri.fromFile(outfile));
//                            context.sendBroadcast(intent);
//
//                        } catch (FileNotFoundException e) {
//                            throw new RuntimeException(e);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                    } else {
//                        // Handle the case where imageView.getDrawable() is not a BitmapDrawable
//                        // Log or display a message, or take appropriate action.
//                    }
                });
                builder.setNegativeButton("Batal", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.create().show();
                return true;
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (manufacturer.equalsIgnoreCase("Xiaomi")) {
            return checkIfModelIsMIUI(model);
        }

        return false;
    }

    private static boolean checkIfModelIsMIUI(String model) {
        String[] miuiModels = {"MI", "Redmi", "POCO"};

        for (String miuiModel : miuiModels) {
            if (model.contains(miuiModel)) {
                return true;
            }
        }

        return false;
    }
    private void Download(){

    }

    /*private void downloadImage(String imageUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Membuat URL dari alamat gambar
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();

                    // Membaca gambar dari input stream
                    InputStream input = connection.getInputStream();

                    // Menyimpan gambar di penyimpanan eksternal
                    String customFileName = "image_" + System.currentTimeMillis();
                    String fileName = customFileName + ".jpg";
                    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    File file = new File(storageDir, fileName);

                    if (!storageDir.exists()) {
                        storageDir.mkdirs();
                    }

                    FileOutputStream output = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                    output.close();
                    input.close();

                    showDownloadSuccessNotification();
                } catch (IOException e) {
                    e.printStackTrace();
                    showDownloadErrorNotification();
                }
            }
        }).start();
    }*/

    private void showDownloadSuccessNotification() {
        // Menampilkan pemberitahuan unduhan berhasil di UI utama (melalui Handler)
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Gambar berhasil diunduh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDownloadErrorNotification() {
        // Menampilkan pemberitahuan unduhan gagal di UI utama (melalui Handler)
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Gagal mengunduh gambar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
