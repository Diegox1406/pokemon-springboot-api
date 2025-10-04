package com.dpaz.pokemonreview.repository;

import com.dpaz.pokemonreview.models.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<PokemonEntity, Integer>{

}
