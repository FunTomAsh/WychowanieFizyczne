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
public class LoadDay extends AppCompatActivity{
    private TextView dayText;
    private TextView[] exerciseTextViews;
    private String jsonData;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final Button startB = findViewById(R.id.startButton);
        final ImageView backBtn = findViewById(R.id.backBtn);
        final String getSelectedDayName = getIntent().getStringExtra("selectedDay");

        dayText = findViewById(R.id.text_day);
        exerciseTextViews = new TextView[]{
                findViewById(R.id.text_zad1),
                findViewById(R.id.text_zad2),
                findViewById(R.id.text_zad3),
                findViewById(R.id.text_zad4),
                findViewById(R.id.text_zad5)
        };
        dayText.setText(getSelectedDayName);



        JSONObject jsonObject = JsonReader.loadJSONFromAsset(getApplicationContext(), "fiz.json");
        jsonData = String.valueOf(jsonObject);

        int day = getDayFromButtonText(getSelectedDayName);
        updateTextViews(day);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoadDay.this, MainActivity.class));
                finish();
            }
        });

        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoadDay.this, SingleExerciseActivity.class);
                intent.putExtra("selectedDay", getSelectedDayName);
                startActivity(intent);
                //finish();
            }
        });


    }

    // Parse JSON data and extract exercise names for the selected day
    private void updateTextViews(int day) {
        try {
            JSONArray dailyExercises = new JSONArray(jsonData);

            for (int i = 0; i < exerciseTextViews.length; i++) {
                JSONObject exercise = dailyExercises.getJSONObject(day)
                        .getJSONArray("exercises").getJSONObject(i)
                        .getJSONObject("exercise");
                String exerciseName = exercise.getString("exName");
                exerciseTextViews[i].setText(exerciseName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Convert button text to corresponding day number
    private int getDayFromButtonText(String buttonText) {
        // Extract the day number from the button text
        // For example, if buttonText is "Dzień 1", return 1
        String[] parts = buttonText.split(" ");
        //System.out.println("NRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: " + parts[1]);
        return Integer.parseInt(parts[1]);
    }
}
