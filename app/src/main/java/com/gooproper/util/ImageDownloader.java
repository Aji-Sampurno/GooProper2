package com.gooproper.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, String> {
    private Context context;

    public ImageDownloader(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        String imageUrl = urls[0];
        Bitmap bitmap = downloadImageFromURL(imageUrl);

        if (bitmap != null) {
            return saveImageToDownloadDirectory(bitmap);
        }

        return null;
    }

    private Bitmap downloadImageFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String saveImageToDownloadDirectory(Bitmap image) {
        File downloadDirectory = context.getDir("Download", Context.MODE_PRIVATE);
        String fileName = "downloaded_image.png";

        File imageFile = new File(downloadDirectory, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String imagePath) {
        if (imagePath != null) {
            showToast("Gambar berhasil diunduh dan disimpan di " + imagePath);
        } else {
            showToast("Gagal menyimpan gambar.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
