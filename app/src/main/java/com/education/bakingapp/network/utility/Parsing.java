package com.education.bakingapp.network.utility;

import android.util.Log;

import com.education.bakingapp.models.Recipe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Parsing {


    public static ArrayList<Recipe> parseRecipes(String json) {

        Log.e("parse", "enter");
        ArrayList<Recipe> trailers = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Recipe>>() {
            }.getType();
            trailers = gson.fromJson(json, type);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("parse_error", e.getMessage());
        }
        return trailers;
    }
}
