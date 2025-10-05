package com.dpaz.pokemonreview.services;

import com.dpaz.pokemonreview.dto.PokemonDto;
import com.dpaz.pokemonreview.dto.PokemonResponse;

public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponse getAllPokemon(int pageNo, int pageSize);
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);
    void deletePokemon(int id);

}
