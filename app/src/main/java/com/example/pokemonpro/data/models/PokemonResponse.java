package com.example.pokemonpro.data.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonResponse {

    public int id;
    public String name;

    @SerializedName("height")
    public int height;

    @SerializedName("weight")
    public int weight;

    @SerializedName("base_experience")
    public int baseXP;

    @SerializedName("abilities")
    public List<AbilitySlot> abilities;

    @SerializedName("moves")
    public List<MoveSlot> moves;

    @SerializedName("sprites")
    public Sprites sprites;

    public static class AbilitySlot {
        public Ability ability;
    }

    public static class Ability {
        public String name;
    }

    public static class MoveSlot {
        public Move move;
    }

    public static class Move {
        public String name;
    }

    public static class Sprites {
        @SerializedName("front_default")
        public String frontDefault;
    }
}
