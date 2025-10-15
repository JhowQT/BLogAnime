package br.com.fiap.BlogAnime.service;

import br.com.fiap.BlogAnime.dto.*;
import br.com.fiap.BlogAnime.model.Anime;
import br.com.fiap.BlogAnime.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository repository;

    // ✅ LISTAR TODOS OS ANIMES
    public List<AnimeResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ✅ BUSCAR ANIME POR ID
    public AnimeResponseDTO buscarPorId(Long id) {
        Optional<Anime> anime = repository.findById(id);
        return anime.map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Anime não encontrado com o ID: " + id));
    }

    // ✅ CRIAR NOVO ANIME
    public AnimeResponseDTO criar(AnimeCreateDTO dto) {
        Anime anime = new Anime();
        anime.setTitulo(dto.getTitulo());
        anime.setDescricao(dto.getDescricao());
        anime.setAutor(dto.getAutor());
        anime.setAnoLancamento(dto.getAnoLancamento());
        anime.setGenero(dto.getGenero());

        Anime salvo = repository.save(anime);
        return toResponse(salvo);
    }

    // ✅ ATUALIZAR (EDITAR) ANIME
    public AnimeResponseDTO atualizar(Long id, AnimeUpdateDTO dto) {
        Anime anime = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anime não encontrado com o ID: " + id));

        if (dto.getTitulo() != null) anime.setTitulo(dto.getTitulo());
        if (dto.getDescricao() != null) anime.setDescricao(dto.getDescricao());
        if (dto.getAutor() != null) anime.setAutor(dto.getAutor());
        if (dto.getAnoLancamento() != null) anime.setAnoLancamento(dto.getAnoLancamento());
        if (dto.getGenero() != null) anime.setGenero(dto.getGenero());

        Anime atualizado = repository.save(anime);
        return toResponse(atualizado);
    }

    // ✅ DELETAR ANIME
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Anime não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    // 🧠 MÉTODO DE CONVERSÃO: ENTITY → RESPONSE DTO
    private AnimeResponseDTO toResponse(Anime anime) {
        return new AnimeResponseDTO(
                anime.getIdAnime(),
                anime.getTitulo(),
                anime.getDescricao(),
                anime.getAutor(),
                anime.getAnoLancamento(),
                anime.getGenero()
        );
    }
}
