package com.example.pokemonpro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonpro.R;
import com.example.pokemonpro.data.models.Pokemon;
import com.example.pokemonpro.data.models.PokemonResponse;
import com.example.pokemonpro.data.repository.PokemonRepository;
import com.example.pokemonpro.ui.details.DetailActivity;
import com.example.pokemonpro.ui.home.PokemonAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    PokemonRepository repo = new PokemonRepository();
    EditText etSearch;
    Button btnAdd;
    RecyclerView rv;
    List<Pokemon> watchlist = new ArrayList<>();
    PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.etSearch);
        btnAdd = findViewById(R.id.btnAdd);
        rv = findViewById(R.id.rvWatchlist);

        adapter = new PokemonAdapter(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnPokemonClickListener(pokemon -> {
            // Fetch full detail information
            repo.getPokemon(String.valueOf(pokemon.id)).enqueue(new Callback<PokemonResponse>() {
                @Override
                public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        PokemonResponse p = response.body();

                        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                        intent.putExtra("name", p.name);
                        intent.putExtra("id", p.id);
                        intent.putExtra("imageUrl", p.sprites.frontDefault);
                        intent.putExtra("height", p.height);
                        intent.putExtra("weight", p.weight);
                        intent.putExtra("baseXP", p.baseXP);
                        intent.putExtra("ability", p.abilities.get(0).ability.name);
                        intent.putExtra("move", p.moves.get(0).move.name);

                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<PokemonResponse> call, Throwable throwable) {
                    Toast.makeText(HomeActivity.this, "Failed to load details", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // temp onClick
        btnAdd.setOnClickListener(v -> {
            String input = etSearch.getText().toString();

            if(input.isEmpty()) {
                Toast.makeText(this, "Enter a Pokemon name or Id", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!repo.isValidInput(input)){
                Toast.makeText(this, "Invalid characters in input", Toast.LENGTH_SHORT).show();
                return;
            }
            fetchAndAddPokemon(input);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        testApi();
    }

    private void fetchAndAddPokemon(String input) {
        repo.getPokemon(input).enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if(response.isSuccessful() && response.body() != null) {

                    PokemonResponse p = response.body();

                    Pokemon pokemon = new Pokemon(p.id, p.name, p.sprites.frontDefault);

                    watchlist.add(pokemon);
                    adapter.updateData(watchlist);

                    Toast.makeText(HomeActivity.this, p.name + " added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "Pokemon not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable throwable) {
                Toast.makeText(HomeActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void testApi() {
        repo.getPokemon("pikachu").enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    String msg = "Pokemon: " + response.body().name;
                    Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    String msg = "API Error: " + response.code();
                    Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable throwable) {
                Toast.makeText(HomeActivity.this, "Network Failure: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}