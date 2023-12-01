package com.gooproper.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//public class ImageDownloaderMIUI extends AsyncTask<String, Void, File> {
//    private Context context;
//    private ImageView imageView;
//
//    public ImageDownloaderMIUI(Context context, ImageView imageView) {
//        this.context = context;
//        this.imageView = imageView;
//    }
//
//    @Override
//    protected File doInBackground(String... params) {
//        String imageUrl = params[0];
//
//        try {
//            // Buka koneksi ke URL
//            URL url = new URL(imageUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//
//            // Dapatkan InputStream dari koneksi
//            InputStream input = connection.getInputStream();
//
//            File internalDir = context.getFilesDir();
//            String filename = String.format("%d.jpg", System.currentTimeMillis());
//            File outFile = new File(internalDir, filename);
//
//            FileOutputStream output = new FileOutputStream(outFile);
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = input.read(buffer)) != -1) {
//                output.write(buffer, 0, bytesRead);
//            }
//            output.close();
//            input.close();
//
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(outFile)));
//
//            return outFile;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    protected void onPostExecute(File result) {
//        if (result != null) {
//            // Tampilkan gambar yang diunduh ke ImageView menggunakan Picasso
//            Picasso.get().load(result).into(imageView);
//
//            Toast.makeText(context, "Berhasil Download", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Gagal Download", Toast.LENGTH_SHORT).show();
//        }
//    }
//}

public class ImageDownloaderMIUI extends AsyncTask<String, Void, Uri> {
    private Context context;
    private ImageView imageView;

    public ImageDownloaderMIUI(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected Uri doInBackground(String... params) {
        String imageUrl = params[0];

        try {
            // Buka koneksi ke URL
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            // Dapatkan InputStream dari koneksi
            InputStream input = connection.getInputStream();

            // Simpan gambar ke direktori MediaStore
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.TITLE, String.format("%d.jpg", System.currentTimeMillis()));
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, String.format("%d.jpg", System.currentTimeMillis()));
            contentValues.put(MediaStore.Images.Media.DESCRIPTION, String.format("%d.jpg", System.currentTimeMillis()));
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

            // Menyimpan file di direktori Pictures/MY_APP_NAME
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "GooProper");

            ContentResolver resolver = context.getContentResolver();
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            if (imageUri != null) {
                try (OutputStream outputStream = resolver.openOutputStream(imageUri)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }

            // Tutup InputStream setelah selesai
            input.close();

            return imageUri;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Uri result) {
        if (result != null) {
            // Tampilkan gambar yang diunduh ke ImageView menggunakan Picasso
            Picasso.get().load(result).into(imageView);

            Toast.makeText(context, "Berhasil Download", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Gagal Download", Toast.LENGTH_SHORT).show();
        }
    }
}