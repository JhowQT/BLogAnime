package br.com.fiap.BlogAnime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.BlogAnime.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
