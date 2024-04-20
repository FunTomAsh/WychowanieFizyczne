package com.example.wychowaniefizyczne;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
//import com.dudegym.dudefitnessworkout.R;

public class MainActivity extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button Btn1 = findViewById(R.id.button1);
        /*final Button Btn2 = findViewById(R.id.button2);
        final Button Btn3 = findViewById(R.id.button3);
        final Button Btn5 = findViewById(R.id.button5);
        final Button Btn6 = findViewById(R.id.button6);
        final Button Btn7 = findViewById(R.id.button7);
        final Button Btn9 = findViewById(R.id.button9);
        final Button Btn10 = findViewById(R.id.button10);
        final Button Btn11 = findViewById(R.id.button11);
        final Button Btn13 = findViewById(R.id.button13);
        final Button Btn14 = findViewById(R.id.button14);
        final Button Btn15 = findViewById(R.id.button15);
        final Button Btn17 = findViewById(R.id.button17);
        final Button Btn18 = findViewById(R.id.button18);
        final Button Btn19 = findViewById(R.id.button19);
        final Button Btn21 = findViewById(R.id.button21);
        final Button Btn22 = findViewById(R.id.button22);
        final Button Btn23 = findViewById(R.id.button23);
        final Button Btn25 = findViewById(R.id.button25);
        final Button Btn26 = findViewById(R.id.button26);
        final Button Btn27 = findViewById(R.id.button27);
        final Button Btn29 = findViewById(R.id.button29);
        final Button Btn30 = findViewById(R.id.button30);*/

        try {
            JSONObject jsonData = loadJSONFromAsset(getApplicationContext(), "fiz.json");

            if (jsonData != null) {
                JSONArray dailyExercises = jsonData.getJSONArray("dailyEx");

                for (int i = 0; i < dailyExercises.length(); i++) {
                    JSONObject dailyExercise = dailyExercises.getJSONObject(i);
                    int day = dailyExercise.getInt("day");
                    boolean finished = dailyExercise.getBoolean("finished");
                    System.out.println("dzień: " + day + " " + finished);
                    // Update button based on finished status
                    updateButton(day, finished);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    Intent intent = new Intent(MainActivity.this, LoadDay.class);
                    intent.putExtra("selectedDay", Btn1.getText());
                    startActivity(intent);
            }
        });
    }

    @SuppressLint("ResourceType")
    private void updateButton(int day, boolean finished) {
        String buttonId = "button" + day;
        int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
        Button button = findViewById(resId);

        if (button != null) {
            if (finished) {
                button.setBackgroundResource(R.drawable.button_completed);
                button.setTextColor(getResources().getColorStateList(R.drawable.text_completed));
            } else {
                button.setBackgroundResource(R.drawable.button_background);
                button.setTextColor(getResources().getColorStateList(R.drawable.text_background));
            }
        }
    }

    private JSONObject loadJSONFromAsset(Context context, String fileName) {
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
    }
}