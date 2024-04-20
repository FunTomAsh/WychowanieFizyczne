package com.example.wychowaniefizyczne;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SingleExerciseActivity extends AppCompatActivity {

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_exercise);

        final Button nextBut = findViewById(R.id.button_next_exerc);
        final String getSelectedDay = getIntent().getStringExtra("selectedDay");

        int curDay = getDayFromButtonText(getSelectedDay);
        System.out.println("DDDDDDDDDDDDDDDDzien: " + curDay);


    }

    private int getDayFromButtonText(String buttonText) {
        // Extract the day number from the button text
        // For example, if buttonText is "Dzień 1", return 1
        String[] parts = buttonText.split(" ");
        //System.out.println("NRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: " + parts[1]);
        return Integer.parseInt(parts[1]);
    }
}
