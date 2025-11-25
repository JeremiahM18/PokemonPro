package com.example.pokemonpro.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pokemonpro.R;
import com.example.pokemonpro.data.models.PokemonResponse;
import com.example.pokemonpro.data.repository.PokemonRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    PokemonRepository repo = new PokemonRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        testApi();
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