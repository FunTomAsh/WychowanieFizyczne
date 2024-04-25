package com.example.wychowaniefizyczne;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoadFreeDay extends AppCompatActivity {
    private TextView dayText;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_day);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final String getSelectedDayName = getIntent().getStringExtra("selectedDay");

        dayText = findViewById(R.id.text_day);

        dayText.setText(getSelectedDayName);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoadFreeDay.this, MainActivity.class));
                finish();
            }
        });
    }

    private int getDayFromButtonText(String buttonText) {
        // Np, jeżeli "Dzień 1", zwraca 1
        String[] parts = buttonText.split(" ");
        //System.out.println("NRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: " + parts[1]);
        return Integer.parseInt(parts[1]);
    }
}
