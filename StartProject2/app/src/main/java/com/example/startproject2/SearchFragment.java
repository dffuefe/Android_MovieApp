package com.example.startproject2;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    static Handler handler = new Handler();
    MovieAdapter adapter;
    RecyclerView recyclerView;

    static Movie movie;
    static MovieList movieList;

    String clientId = "5WJeBbmKjYvuyQo4OLLm";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "iSoamyvv4z";//애플리케이션 클라이언트 시크릿값";

    String urlStr = "https://openapi.naver.com/v1/search/movie.json?query=";//서치뷰에서 값을 받아 이 쿼리에 전달해준다?
    static RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        adapter = new MovieAdapter();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        SearchView searchView = rootView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(final String query) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(urlStr + query);
                    }
                }).start();

                movie.title = query;
                adapter.addItem(movie);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        movie = new Movie();

        movie.title = "movie title";
        movie.director = "director";
        movie.actor = "person1, pesron2, person3";
        movie.userRating = 5;

        return rootView;
    }

    public void request(String urlStr){
        StringBuilder output = new StringBuilder();

        try{
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn != null){
                //conn.setConnectTimeout(10000);
                //conn.setDoInput(true);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("X-Naver-Client-Id", clientId);
                conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                int resCode = conn.getResponseCode();
                //BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                BufferedReader br;
                if(resCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();
                processResponse(response);

                System.out.println(response.toString());
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void processResponse(StringBuffer response) throws IOException{

           Gson gson = new Gson();
           MovieList movieList = gson.fromJson(response.toString(), MovieList.class);

           for (int i=0; i<movieList.items.size(); i++) {
               Movie movie = movieList.items.get(i);
               adapter.addItem(movie);
           }

           handler.post(new Runnable(){
               @Override
               public void run() {
                   adapter.notifyDataSetChanged();
               }
           });

    }



}
