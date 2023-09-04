package com.gooproper.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.style.IconMarginSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.gooproper.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> images;

    public ViewPagerAdapter(Context context, ArrayList<String> images) {
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

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_viewpager, null);
        ImageView imageView = view.findViewById(R.id.ivlisting);
        Picasso.get()
                .load(images.get(position))
                .into(imageView);
        container.addView(view, 0);
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImage(images.get(position));
            }
        });*/
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // Panggil metode untuk memulai pengunduhan di sini
                    downloadImage(images.get(position));
                    return true; // Konsumsi acara sentuhan
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void downloadImage(String imageUrl) {
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
    }

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
