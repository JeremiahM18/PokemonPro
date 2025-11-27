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

    public boolean isValidInput(String input) {
        // Rule 1: Forbidden characters
        String forbidden = "%&*(@)!:;<>";

        for(char c : forbidden.toCharArray()) {
            if(input.contains(String.valueOf(c))) {
                return false;
            }
        }

        // Rule 2: If input is a number, check range
        try {
            int num = Integer.parseInt(input);
            if(num < 1 || num > 1010) {
                return false;
            }
        } catch (NumberFormatException e) {
            // name not a number
        }

        return true;
    }

    public Call<PokemonResponse> getPokemon(String nameOrId) {
        return service.getPokemon(nameOrId.toLowerCase().trim());
    }
}
