package com.dpaz.pokemonreview.services.impl;

import com.dpaz.pokemonreview.dto.PokemonDto;
import com.dpaz.pokemonreview.exceptions.PokemonNotFoundException;
import com.dpaz.pokemonreview.models.PokemonEntity;
import com.dpaz.pokemonreview.repository.PokemonRepository;
import com.dpaz.pokemonreview.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    private PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        PokemonEntity newPokemon = pokemonRepository.save(pokemon);

        PokemonDto pokemonResponse = new PokemonDto();
        pokemonResponse.setId(newPokemon.getId());
        pokemonResponse.setName(newPokemon.getName());
        pokemonResponse.setType(newPokemon.getType());

        return pokemonResponse;
    }

    @Override
    public List<PokemonDto> getAllPokemon() {
        List<PokemonEntity> pokemon = pokemonRepository.findAll();
        return pokemon.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }

    @Override
    public PokemonDto getPokemonById(int id) {
        PokemonEntity pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon not found with id: " + id));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
        PokemonEntity pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be updated with id: " + id));
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        PokemonEntity updatedPokemon = pokemonRepository.save(pokemon);
        return mapToDto(updatedPokemon);
    }

    @Override
    public void deletePokemon(int id) {
        PokemonEntity pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be deleted with id: " + id));
        pokemonRepository.delete(pokemon);
    }

    private PokemonDto mapToDto(PokemonEntity pokemon) {
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }

    private PokemonEntity mapToEntity(PokemonDto pokemonDto) {
        PokemonEntity pokemonEntity = new PokemonEntity();
        pokemonEntity.setName(pokemonDto.getName());
        pokemonEntity.setType(pokemonDto.getType());
        return pokemonEntity;
    }
}
