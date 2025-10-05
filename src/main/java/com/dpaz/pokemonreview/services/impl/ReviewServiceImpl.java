package com.dpaz.pokemonreview.services.impl;

import com.dpaz.pokemonreview.dto.ReviewDto;
import com.dpaz.pokemonreview.exceptions.PokemonNotFoundException;
import com.dpaz.pokemonreview.exceptions.ReviewNotFoundException;
import com.dpaz.pokemonreview.models.PokemonEntity;
import com.dpaz.pokemonreview.models.ReviewEntity;
import com.dpaz.pokemonreview.repository.PokemonRepository;
import com.dpaz.pokemonreview.repository.ReviewRepository;
import com.dpaz.pokemonreview.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private PokemonRepository pokemonRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        ReviewEntity review = mapToEntity(reviewDto);
        PokemonEntity pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review not found"));
        review.setPokemon(pokemon);

        ReviewEntity newReview = reviewRepository.save(review);
        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int id) {
        List<ReviewEntity> reviews = reviewRepository.findByPokemonId(id);
        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        PokemonEntity pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review not found"));

        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associated review not found"));
        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to the specified pokemon");
        }
        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
        PokemonEntity pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review not found"));
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associated review not found"));
        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to the specified pokemon");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        ReviewEntity updatedReview = reviewRepository.save(review);

        return mapToDto(updatedReview);

    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {
        PokemonEntity pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review not found"));
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associated review not found"));
        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to the specified pokemon");
        }

        reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(ReviewEntity review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private ReviewEntity mapToEntity(ReviewDto reviewDto) {
        ReviewEntity review = new ReviewEntity();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }


}
