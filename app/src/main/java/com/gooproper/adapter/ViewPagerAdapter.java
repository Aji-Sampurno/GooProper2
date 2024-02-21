package com.gooproper.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.gooproper.R;
import com.gooproper.ui.ImageViewActivity;
import com.gooproper.util.ImageDownloader;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> images;
    GestureDetector gestureDetector;
    private static final String CHANNEL_ID = "download_notification";
    private ExoPlayer currentExoPlayer;

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

    @OptIn(markerClass = UnstableApi.class) @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_viewpager, null);
        ImageView imageView = view.findViewById(R.id.ivlisting);
        PlayerView videoView = view.findViewById(R.id.playerView);

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
            videoView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(currentLink)
                    .into(imageView);
//            Picasso.get()
//                    .load(currentLink)
//                    .into(imageView);
            container.addView(view, 0);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImageViewActivity.class);
                    intent.putExtra("imageResources", images); // Mengirim seluruh daftar sumber gambar
                    context.startActivity(intent);
                }
            });
        } else {
            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            try {
                if (currentExoPlayer != null && currentExoPlayer.isPlaying()) {
                    currentExoPlayer.stop();
                }
                ExoPlayer exoPlayer = new ExoPlayer.Builder(context).build();
                videoView.setPlayer(exoPlayer);

                MediaItem mediaItem = MediaItem.fromUri(images.get(position));
                exoPlayer.setMediaItem(mediaItem);

                exoPlayer.prepare();
                exoPlayer.setPlayWhenReady(false);

                videoView.setShowNextButton(false);
                videoView.setShowPreviousButton(false);
                videoView.setShowFastForwardButton(false);
                videoView.setShowRewindButton(false);
                videoView.setShowShuffleButton(false);

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

    private boolean isImage(String mediaUrl) {
        return mediaUrl.endsWith(".jpg") || mediaUrl.endsWith(".jpeg") || mediaUrl.endsWith(".png");
    }

}
