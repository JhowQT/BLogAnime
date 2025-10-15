package br.com.fiap.BlogAnime.controllers;

import br.com.fiap.BlogAnime.dto.*;
import br.com.fiap.BlogAnime.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/animes")
public class AnimeController {

    @Autowired
    private AnimeService service;

    // ✅ LISTAR TODOS OS ANIMES (AGORA COM PAGINAÇÃO E FILTRO)
    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam(required = false) String titulo,
            @PageableDefault(size = 10, sort = "titulo", direction = Direction.ASC) Pageable pageable) {
        try {
            Page<AnimeResponseDTO> pagina;

            // Se o título for informado, aplica o filtro
            if (titulo != null && !titulo.isEmpty()) {
                pagina = service.filtrarPorTitulo(titulo, pageable);
            } else {
                pagina = service.listarPaginado(pageable);
            }

            if (pagina.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("Nenhum anime encontrado.");
            }

            return ResponseEntity.ok(pagina); // 200 OK

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar animes: " + e.getMessage()); // 500
        }
    }

    // ✅ BUSCAR ANIME POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            AnimeResponseDTO anime = service.buscarPorId(id);
            return ResponseEntity.ok(anime); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Anime não encontrado com o ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar anime: " + e.getMessage()); // 500
        }
    }

    // ✅ CRIAR NOVO ANIME
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AnimeCreateDTO dto) {
        try {
            AnimeResponseDTO novoAnime = service.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAnime); // 201
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro nos dados enviados: " + e.getMessage()); // 400
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar anime: " + e.getMessage()); // 500
        }
    }

    // ✅ ATUALIZAR ANIME
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AnimeUpdateDTO dto) {
        try {
            AnimeResponseDTO atualizado = service.atualizar(id, dto);
            return ResponseEntity.ok(atualizado); // 200
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Anime não encontrado com o ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar anime: " + e.getMessage()); // 500
        }
    }

    // ✅ DELETAR ANIME
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Anime não encontrado com o ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar anime: " + e.getMessage()); // 500
        }
    }
}
