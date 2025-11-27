package com.example.pokemonpro.ui.details;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pokemonpro.R;

public class DetailActivity extends AppCompatActivity {

    TextView tvName;
    TextView tvId;
    TextView tvHeight;
    TextView tvWeight;
    TextView tvBaseXP;
    TextView tvAbility;
    TextView tvMove;
    ImageView ivPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tvName);
        tvId = findViewById(R.id.tvId);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvBaseXP = findViewById(R.id.tvBaseXP);
        tvAbility = findViewById(R.id.tvAbility);
        tvMove = findViewById(R.id.tvMove);
        ivPokemon = findViewById(R.id.ivPokemon);

        // Receive data
        String name = getIntent().getStringExtra("name");
        int id = getIntent().getIntExtra("id", 0);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        int height = getIntent().getIntExtra("height", 0);
        int weight = getIntent().getIntExtra("weight", 0);
        int baseXP = getIntent().getIntExtra("baseXP", 0);
        String ability = getIntent().getStringExtra("ability");
        String move = getIntent().getStringExtra("move");

        // Fill UI
        tvName.setText(name);
        tvId.setText("ID: " + id);
        tvHeight.setText("Height: " + height);
        tvWeight.setText("Weight: " + weight);
        tvBaseXP.setText("Base XP: " + baseXP);
        tvAbility.setText("Ability: " + ability);
        tvMove.setText("Move: " + move);

        Glide.with(this).load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground).into(ivPokemon);

    }
}
