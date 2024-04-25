package com.example.wychowaniefizyczne;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
                findViewById(R.id.text_zad5),
                findViewById(R.id.text_zad6)
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

    // Parsowanie JSONa i ekstrakcja danych w zależności od dnia
    private void updateTextViews(int day) {

        try {
            //JSONObject jsonData = loadJSONFromAsset(getApplicationContext(), "fiz.json");
            JSONObject jsonData = loadJSONFromFile("fiz.json");
            if (jsonData != null) {
                JSONArray dailyExercises = jsonData.getJSONArray("dailyEx");

                for (int i = 0; i < dailyExercises.length(); i++) {
                    JSONObject dailyExercise = dailyExercises.getJSONObject(i);
                    if (dailyExercise.getInt("day") == day){
                        for (int j = 0; j <  exerciseTextViews.length; j++){
                            JSONObject exercise = dailyExercises.getJSONObject(i)
                                    .getJSONArray("exercises").getJSONObject(j)
                                    .getJSONObject("exercise");
                            String exerciseName = exercise.getString("exName");
                            exerciseTextViews[j].setText(exerciseName);
                            System.out.println("Wstawiono nazwe : " + j);
                        }

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Konwercja tekstu przycisku "Dzień n"
    private int getDayFromButtonText(String buttonText) {
        // Np, jeżeli "Dzień 1", zwraca 1
        String[] parts = buttonText.split(" ");
        //System.out.println("NRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: " + parts[1]);
        return Integer.parseInt(parts[1]);
    }

    /*private JSONObject loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            System.out.println("udallo sie otworzyc assets");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            return new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    private JSONObject loadJSONFromFile(String fileName) {
        try {
            File file = new File(getFilesDir(), fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String json = new String(data, "UTF-8");
            return new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
