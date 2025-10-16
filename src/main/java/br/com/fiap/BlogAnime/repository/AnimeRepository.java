package br.com.fiap.BlogAnime.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.BlogAnime.model.Anime;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    Page<Anime> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

}
