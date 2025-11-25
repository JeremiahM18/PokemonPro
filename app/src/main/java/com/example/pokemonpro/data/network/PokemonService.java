package com.example.pokemonpro.data.network;

import com.example.pokemonpro.data.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonService {

    @GET("pokemon/{nameOrId}")
    Call<PokemonResponse> getPokemon(@Path("nameOrId") String nameOrId);
}
