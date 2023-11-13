package com.gooproper.pager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gooproper.R;
import com.gooproper.ui.ImageViewActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SertifikatPdfAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> pdfUrls;

    public SertifikatPdfAdapter(Context context, ArrayList<String> pdfUrls){
        this.context = context;
        this.pdfUrls = pdfUrls;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewpager_pdf, null);
        int indexPercent2 = pdfUrls.get(position).indexOf("%2");
        String title = pdfUrls.get(position).substring(indexPercent2 + 3);
        int indexQuestionMark = title.indexOf("?");
        if (indexQuestionMark != -1) {
            title = title.substring(0, indexQuestionMark);
        }
        ImageView gambar = view.findViewById(R.id.ivlisting);
        Button openPdfButton = view.findViewById(R.id.openPdfButton);
        TextView TVPdf = view.findViewById(R.id.titleTextView);
        if (title.endsWith(".jpg")) {
            TVPdf.setVisibility(View.GONE);
            openPdfButton.setVisibility(View.GONE);
            Picasso.get()
                    .load(pdfUrls.get(position))
                    .into(gambar);
            container.addView(view, 0);
            gambar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImageViewActivity.class);
                    intent.putExtra("imageResources", pdfUrls);
                    context.startActivity(intent);
                }
            });
            return view;
        } else {
            gambar.setVisibility(View.GONE);
            TVPdf.setText(title);
            openPdfButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPDF(pdfUrls.get(position));
                }
            });
            container.addView(view);
            return view;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return pdfUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    private void openPDF(String pdfUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
