package com.example.startproject2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    ArrayList<Movie> items = new ArrayList<Movie>();

    Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Movie item = items.get(position);
        try {
            holder.setItem(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Movie item){
        items.add(item);
    }

    public void setItems(ArrayList<Movie> items){
        this.items = items;
    }

    public Movie getItem(int position) {
        return items.get(position);
    }

    public void clearItems() {
        this.items.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2, textView3, textView4;
        ImageView imageView;
        RatingBar ratingBar;

        public View layout;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            imageView = itemView.findViewById(R.id.imageView);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            layout = itemView;
/*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    //itemView.getContext().startActivity(intent);

                    System.out.println("테스트 입니다.");
                }
            });
*/


        }

        public void setItem(final Movie item) throws IOException {

            textView.setText(item.title);
            textView2.setText("감독: " + item.director);
            textView3.setText("출연: " + item.actor);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(item.image);
                        URLConnection connection = url.openConnection();
                        connection.connect();

                        int size = connection.getContentLength();
                        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream(), size);
                        final Bitmap bitmap = BitmapFactory.decodeStream(bis);

                        MainActivity.handler.post(new Runnable(){

                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                        bis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            if(item.userRating != 0) {
                textView4.setText(item.userRating + " ");
                ratingBar.setVisibility(View.VISIBLE);
                ratingBar.setRating(item.userRating / 2);
            } else {
                textView4.setText("평점 없음");
                ratingBar.setVisibility(View.GONE);
            }

            final String url = item.link;
            //item.link
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    itemView.getContext().startActivity(intent);

                    System.out.println("테스트 입니다.");
                }
            });
        }
    }



}
