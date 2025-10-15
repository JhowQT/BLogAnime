package br.com.fiap.BlogAnime.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.BlogAnime.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // 🔍 Filtro por nome (insensitive) com paginação
    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
