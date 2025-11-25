package com.example.pokemonpro.data.repository;

import android.util.Patterns;

import com.example.pokemonpro.data.models.PokemonResponse;
import com.example.pokemonpro.data.network.ApiClient;
import com.example.pokemonpro.data.network.PokemonService;

import retrofit2.Call;

public class PokemonRepository {

    private final PokemonService service;

    public PokemonRepository() {
        service = ApiClient.getClient().create(PokemonService.class);
    }

    public boolean isValidInput(String query) {
        // Forbidden characters
        return !query.matches(".*[%&*(@)!;:<>].*");
    }

    public Call<PokemonResponse> getPokemon(String nameOrId) {
        return service.getPokemon(nameOrId.toLowerCase().trim());
    }
}
