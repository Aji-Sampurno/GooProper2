package com.gooproper.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, File> {
    private Context context;
    private ImageView imageView;

    public ImageDownloader(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected File doInBackground(String... params) {
        String imageUrl = params[0];

        try {
            // Buka koneksi ke URL
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            // Dapatkan InputStream dari koneksi
            InputStream input = connection.getInputStream();

            // Simpan gambar ke file di external storage
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/Download");
            directory.mkdir();

            String filename = String.format("%d.jpg", System.currentTimeMillis());
            File outFile = new File(directory, filename);

            FileOutputStream output = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            output.close();
            input.close();

            // Broadcast ke media scanner untuk memperbarui galeri
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(outFile)));

            return outFile;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (result != null) {
            // Tampilkan gambar yang diunduh ke ImageView menggunakan Picasso
            Picasso.get().load(result).into(imageView);

            Toast.makeText(context, "Berhasil Download", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Gagal Download", Toast.LENGTH_SHORT).show();
        }
    }
}
