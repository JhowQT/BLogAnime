package br.com.fiap.BlogAnime.service;

import br.com.fiap.BlogAnime.dto.AnimeDTO;
import br.com.fiap.BlogAnime.model.Anime;
import br.com.fiap.BlogAnime.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository repository;

    public Page<AnimeDTO> listarPaginado(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toDTO);
    }

    public Page<AnimeDTO> filtrarPorTitulo(String titulo, Pageable pageable) {
        return repository.findByTituloContainingIgnoreCase(titulo, pageable)
                .map(this::toDTO);
    }

    public AnimeDTO buscarPorId(Long id) {
        Anime anime = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anime não encontrado com o ID: " + id));
        return toDTO(anime);
    }

    public AnimeDTO criar(AnimeDTO dto) {
        Anime anime = toEntity(dto);
        Anime salvo = repository.save(anime);
        return toDTO(salvo);
    }

    public AnimeDTO atualizar(Long id, AnimeDTO dto) {
        Anime anime = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anime não encontrado com o ID: " + id));

        if (dto.getTitulo() != null) anime.setTitulo(dto.getTitulo());
        if (dto.getDescricao() != null) anime.setDescricao(dto.getDescricao());
        if (dto.getAutor() != null) anime.setAutor(dto.getAutor());
        if (dto.getAnoLancamento() != null) anime.setAnoLancamento(dto.getAnoLancamento());
        if (dto.getGenero() != null) anime.setGenero(dto.getGenero());

        return toDTO(repository.save(anime));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Anime não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private AnimeDTO toDTO(Anime anime) {
        return new AnimeDTO(
                anime.getIdAnime(),
                anime.getTitulo(),
                anime.getDescricao(),
                anime.getAutor(),
                anime.getAnoLancamento(),
                anime.getGenero()
        );
    }

    private Anime toEntity(AnimeDTO dto) {
        return new Anime(
                dto.getIdAnime(),
                dto.getTitulo(),
                dto.getDescricao(),
                dto.getAutor(),
                dto.getAnoLancamento(),
                dto.getGenero()
        );
    }
}
