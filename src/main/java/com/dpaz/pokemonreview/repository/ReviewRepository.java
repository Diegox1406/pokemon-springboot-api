package com.dpaz.pokemonreview.repository;

import com.dpaz.pokemonreview.models.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    List<ReviewEntity> findByPokemonId(int id);

}
