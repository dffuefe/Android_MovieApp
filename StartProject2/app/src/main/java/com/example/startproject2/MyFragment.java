package com.example.startproject2;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    EditText editText, editText2, editText3, editText4;
    RadioButton radioButton1, radioButton2;
    RadioGroup radioGroup;


    static DatePickerDialog.OnDateSetListener setListener;

    public MyFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my, container, false);

        final EditText editText = rootView.findViewById(R.id.editText);
        final EditText editText2 = rootView.findViewById(R.id.editText2);
        final EditText editText3 = rootView.findViewById(R.id.editText3);
        final EditText editText4 = rootView.findViewById(R.id.editText4);
        final RadioButton radioButton = rootView.findViewById(R.id.radioButton);
        final RadioButton radioButton2 = rootView.findViewById(R.id.radioButton2);
        ImageView imageView4 = rootView.findViewById(R.id.imageView4);

        SharedPreferences pref = getContext().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        String name=pref.getString("name_save", "");
        String data=pref.getString("date_save", "");
        String mail=pref.getString("mail_save", "");
        String pw=pref.getString("pw_save", "");
        String first = pref.getString("first", "");
        String second = pref.getString("second", "");
        editText.setText(name);
        editText2.setText(data);
        editText3.setText(mail);
        editText4.setText(pw);
        //
        if(first == "1") radioButton.setChecked(true);
        else if(second == "1") radioButton2.setChecked(true);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("name_save", editText.getText().toString());
                editor.putString("date_save", editText2.getText().toString());
                editor.putString("mail_save", editText3.getText().toString());
                editor.putString("pw_save", editText4.getText().toString());
                editor.putString("first_save", radioButton.getText().toString());
                editor.putString("second_save", radioButton2.getText().toString());

                editor.commit();
            }
        });

/*
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/

        return rootView;
    }



}
