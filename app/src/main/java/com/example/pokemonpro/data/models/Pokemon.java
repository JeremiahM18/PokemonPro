package com.example.pokemonpro.data.models;

public class Pokemon {
    public int id;
    public String name;
    public String imageUrl;

    public Pokemon(int id, String name, String imageUrl)
    {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
