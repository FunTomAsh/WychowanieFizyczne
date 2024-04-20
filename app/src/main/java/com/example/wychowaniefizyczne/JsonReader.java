package com.example.wychowaniefizyczne;

import android.content.Context;

import com.google.gson.Gson;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    public static JSONObject loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            // Open the JSON file
            InputStream inputStream = context.getAssets().open(fileName);

            // Read the JSON data into a string
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);

            // Parse the JSON string into a JSONObject
            return new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}