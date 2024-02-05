package com.gooproper.adapter;

import android.app.Activity;
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
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
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
import java.util.Collections;

public class ImageViewAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> images;
    GestureDetector gestureDetector;
    BitmapDrawable bitmapDrawable;
    Bitmap bitmap;
    private ExoPlayer currentExoPlayer;

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

    @OptIn(markerClass = UnstableApi.class) @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.image_viewpager, null);
        PhotoView imageView = view.findViewById(R.id.ivlisting);
        PlayerView playerView = view.findViewById(R.id.playerView);
        ExoPlayer exoPlayer = new ExoPlayer.Builder(context).build();

        String currentLink = images.get(position);

        int indexPercent2 = images.get(position).indexOf("%2");
        String title = images.get(position).substring(indexPercent2 + 3);
        int indexQuestionMark = title.indexOf("?");
        if (indexQuestionMark != -1) {
            title = title.substring(0, indexQuestionMark);
        }

        if (isImage(title)) {
            if (currentExoPlayer != null && currentExoPlayer.isPlaying()) {
                currentExoPlayer.stop();
            }

            playerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);

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
                        }

                    });
                    builder.setNegativeButton("Batal", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    builder.create().show();
                    return true;
                }
            });
        } else {
            playerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);

            try {
                MediaItem mediaItem = MediaItem.fromUri(images.get(position));

                if (currentExoPlayer != null && currentExoPlayer.isPlaying()) {
                    currentExoPlayer.stop();
                }

                playerView.setPlayer(exoPlayer);
                exoPlayer.setMediaItem(mediaItem);
                exoPlayer.prepare();
                exoPlayer.setPlayWhenReady(false);

                playerView.setShowNextButton(false);
                playerView.setShowPreviousButton(false);
                playerView.setShowFastForwardButton(false);
                playerView.setShowRewindButton(false);
                playerView.setShowShuffleButton(false);

                container.addView(view);
                currentExoPlayer = exoPlayer;
            } catch (Exception e) {
                Log.e("VideoViewError", "Error: " + e.getMessage());
            }
        }

        return view;
    }
    public ExoPlayer getCurrentExoPlayer() {
        return currentExoPlayer;
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
    private boolean isImage(String mediaUrl) {
        return mediaUrl.endsWith(".jpg") || mediaUrl.endsWith(".jpeg") || mediaUrl.endsWith(".png");
    }
}
