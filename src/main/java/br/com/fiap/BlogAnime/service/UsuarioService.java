package br.com.fiap.BlogAnime.service;

import br.com.fiap.BlogAnime.dto.*;
import br.com.fiap.BlogAnime.model.Usuario;
import br.com.fiap.BlogAnime.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    // ✅ LISTAR TODOS (SEM PAGINAÇÃO)
    public List<UsuarioResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ✅ LISTAR TODOS (COM PAGINAÇÃO)
    public Page<UsuarioResponseDTO> listarPaginado(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponse);
    }

    // ✅ FILTRAR POR NOME (COM PAGINAÇÃO)
    public Page<UsuarioResponseDTO> filtrarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(this::toResponse);
    }

    // ✅ BUSCAR POR ID
    public UsuarioResponseDTO buscarPorId(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        return usuario.map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    // ✅ CRIAR NOVO USUÁRIO
    public UsuarioResponseDTO criar(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        Usuario salvo = repository.save(usuario);
        return toResponse(salvo);
    }

    // ✅ ATUALIZAR USUÁRIO
    public UsuarioResponseDTO atualizar(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));

        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null) usuario.setSenha(dto.getSenha());

        Usuario atualizado = repository.save(usuario);
        return toResponse(atualizado);
    }

    // ✅ DELETAR USUÁRIO
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    // 🧠 CONVERSOR ENTITY → DTO
    private UsuarioResponseDTO toResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
