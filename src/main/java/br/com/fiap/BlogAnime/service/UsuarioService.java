package br.com.fiap.BlogAnime.service;

import br.com.fiap.BlogAnime.dto.UsuarioDTO;
import br.com.fiap.BlogAnime.model.Usuario;
import br.com.fiap.BlogAnime.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Page<UsuarioDTO> listarPaginado(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toDTO);
    }

    public Page<UsuarioDTO> filtrarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(this::toDTO);
    }

    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
        return toDTO(usuario);
    }

    public UsuarioDTO criar(UsuarioDTO dto) {
        Usuario usuario = toEntity(dto);
        Usuario salvo = repository.save(usuario);
        return toDTO(salvo);
    }

    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));

        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null) usuario.setSenha(dto.getSenha());

        return toDTO(repository.save(usuario));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha()
        );
    }

    private Usuario toEntity(UsuarioDTO dto) {
        return new Usuario(
                dto.getIdUsuario(),
                dto.getNome(),
                dto.getEmail(),
                dto.getSenha()
        );
    }
}
