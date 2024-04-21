package com.example.wychowaniefizyczne;

import android.content.Context;
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

import java.io.IOException;
import java.io.InputStream;

public class SingleExerciseActivity extends AppCompatActivity {

    private TextView exNum;
    private ImageView exImage;
    private TextView exerciseNameTextView;
    private TextView exerciseDescTextView;
    private String jsonData;

    private int[] exNr = new int[5];
    private String[] exNames = new String[5];
    private String[] exDescriptions = new String[5];
    private String[] exImg = new String[5];
    int curEx = 1;
    int resourceId;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_exercise);

        final Button nextBut = findViewById(R.id.button_next_exerc);
        final String getSelectedDay = getIntent().getStringExtra("selectedDay");


        exNum = findViewById(R.id.exerNr);
        exImage = findViewById(R.id.image_exercise);
        exerciseNameTextView = findViewById(R.id.text_exercise_name);
        exerciseDescTextView = findViewById(R.id.text_exercise_description);

        int curDay = getDayFromButtonText(getSelectedDay);
        System.out.println("DDDDDDDDDDDDDDDDzien: " + curDay);

        JSONObject jsonObject = JsonReader.loadJSONFromAsset(getApplicationContext(), "fiz.json");
        jsonData = String.valueOf(jsonObject);

        updateTextView(curDay);

        nextBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curEx < 5){
                    exNum.setText(exNr[curEx]+"/5");
                    resourceId = getResources().getIdentifier(exImg[curEx], "drawable", getPackageName());
                    exImage.setImageResource(resourceId);
                    exerciseNameTextView.setText(exNames[curEx]);
                    exerciseDescTextView.setText(exDescriptions[curEx]);
                    if(curEx==4){
                        nextBut.setText("Koniec");
                    }
                    curEx++;
                }
                else{
                    //TU TRZEBA DOPISAĆ ZMIANE STATUSU W JSONIE
                    Intent intent = new Intent(SingleExerciseActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
 /*               Intent intent = new Intent(SingleExerciseActivity.this, MainActivity.class);
                intent.putExtra("selectedDay", getSelectedDayName);
                startActivity(intent);
                //finish();*/
            }
        });

    }

    private void updateTextView(int day) {

        try {
            JSONObject jsonData = loadJSONFromAsset(getApplicationContext(), "fiz.json");

            if (jsonData != null) {
                JSONArray dailyExercises = jsonData.getJSONArray("dailyEx");

                for (int i = 0; i < dailyExercises.length(); i++) {
                    JSONObject dailyExercise = dailyExercises.getJSONObject(i);
                    if (dailyExercise.getInt("day") == day){
                        JSONArray exercises = dailyExercise.getJSONArray("exercises");
                        for (int j = 0; j <  exercises.length(); j++){
                            JSONObject exercise = exercises.getJSONObject(j).getJSONObject("exercise");
                             exNr[j] = exercise.getInt("exNr");
                             exImg[j] = exercise.getString("imageName");
                             exNames[j] = exercise.getString("exName");
                             exDescriptions[j] = exercise.getString("exDesc");
                            System.out.println(exNr[j] + ". Wstawiono nazwe : " + exNames[j] + " oraz opis " +  exDescriptions[j]);
                        }

                    }
                }
                exNum.setText(exNr[0]+"/5");
                resourceId = getResources().getIdentifier(exImg[0], "drawable", getPackageName());
                exImage.setImageResource(resourceId);
                exerciseNameTextView.setText(exNames[0]);
                exerciseDescTextView.setText(exDescriptions[0]);
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

    private JSONObject loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            //System.out.println("udallo sie otworzyc assets");
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
    }
}
