package br.com.fiap.BlogAnime.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.BlogAnime.model.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    // 🔍 Permite filtrar por título com paginação
    Page<Anime> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

}
