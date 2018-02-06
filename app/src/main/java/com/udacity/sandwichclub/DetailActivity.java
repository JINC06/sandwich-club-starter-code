package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI( sandwich );
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView detailAlsoKnownAsLabel = findViewById(R.id.also_known_title_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);

        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView ingredientsTvLabel = findViewById(R.id.ingredients_title_tv);

        TextView originTv = findViewById(R.id.origin_tv);
        TextView originTvLabel = findViewById(R.id.origin_title_tv);

        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView descriptionTvLabel = findViewById(R.id.description_title_tv);


        if(!sandwich.getAlsoKnownAs().isEmpty()) {
            for (String nameAlsoKnowAs : sandwich.getAlsoKnownAs()) {
                alsoKnownTv.append(nameAlsoKnowAs + "\n");
            }
        }else{
            detailAlsoKnownAsLabel.setVisibility(View.GONE);
            alsoKnownTv.setVisibility(View.GONE);
        }

        if(!sandwich.getIngredients().isEmpty()) {
            for (String ingredient : sandwich.getIngredients()) {
                ingredientsTv.append(ingredient + "\n");
            }
        }else{
            ingredientsTv.setVisibility(View.GONE);
            ingredientsTvLabel.setVisibility(View.GONE);
        }

        if( sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.append( sandwich.getPlaceOfOrigin() + "\n" );
        }else{
            originTv.setVisibility(View.GONE);
            originTvLabel.setVisibility(View.GONE);
        }

        if(sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            descriptionTv.append(sandwich.getDescription() + "\n");
        }else{
            descriptionTv.setVisibility(View.GONE);
            descriptionTvLabel.setVisibility(View.GONE);
        }
    }
}
