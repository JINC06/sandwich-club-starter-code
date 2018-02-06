package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;

        try {

            String mainName;
            List<String> alsoKnownAs;
            String placeOfOrigin;
            String description;
            String image;
            List<String> ingredients;

            JSONObject jsonObjectSandwichDetail = new JSONObject(json);

            JSONObject tagName = jsonObjectSandwichDetail.getJSONObject("name");
            mainName = tagName.getString("mainName");

            JSONArray alsoKnowAsTag = tagName.getJSONArray("alsoKnownAs");
            alsoKnownAs = new ArrayList<>();
            for (int index = 0; index < alsoKnowAsTag.length(); index++) {
                alsoKnownAs.add(alsoKnowAsTag.get(index).toString());
            }

            placeOfOrigin = jsonObjectSandwichDetail.getString("placeOfOrigin");
            description = jsonObjectSandwichDetail.getString("description");
            image = jsonObjectSandwichDetail.getString("image");

            JSONArray ingredientsTag = jsonObjectSandwichDetail.getJSONArray("ingredients");
            ingredients = new ArrayList<>();
            for (int index = 0; index < ingredientsTag.length(); index++) {
                ingredients.add(ingredientsTag.get(index).toString());
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
