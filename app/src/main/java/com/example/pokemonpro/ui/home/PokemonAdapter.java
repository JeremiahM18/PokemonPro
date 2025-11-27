package com.example.pokemonpro.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokemonpro.R;
import com.example.pokemonpro.data.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemon> pokemons = new ArrayList<>();
    private Context context;

    // Click listener interface
    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon);
    }

    private OnPokemonClickListener listener;

    public void setOnPokemonClickListener(OnPokemonClickListener listener) {
        this.listener = listener;
    }

    public PokemonAdapter(Context context)
    {

        this.context = context;
    }

    public void updateData(List<Pokemon> newList) {
        this.pokemons = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon =  pokemons.get(position);
        holder.tvItem.setText(pokemon.id + " - " + pokemon.name);

        // Load sprite image using Glide
        Glide.with(context).load(pokemon.imageUrl).placeholder(R.drawable.ic_launcher_foreground).into(holder.ivSprite);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        private ImageView ivSprite;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvPokemonItem);
            ivSprite = itemView.findViewById(R.id.ivSprite);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onPokemonClick(pokemons.get(getAdapterPosition()));
                }
            });
        }
    }
}
