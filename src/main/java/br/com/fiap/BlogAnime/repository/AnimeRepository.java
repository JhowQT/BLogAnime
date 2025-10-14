package br.com.fiap.BlogAnime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.BlogAnime.model.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    
}
