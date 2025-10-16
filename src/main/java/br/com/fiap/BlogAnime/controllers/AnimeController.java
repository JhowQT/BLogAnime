package br.com.fiap.BlogAnime.controllers;

import br.com.fiap.BlogAnime.dto.AnimeDTO;
import br.com.fiap.BlogAnime.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animes")
public class AnimeController {

    @Autowired
    private AnimeService service;

    
    @GetMapping("/teste")
    public String ping() {
        return "AnimeController funcionando!";
    }

    @GetMapping
    public ResponseEntity<Page<AnimeDTO>> listar(
            @RequestParam(required = false) String titulo,
            @PageableDefault(size = 3, sort = "titulo", direction = Direction.ASC) Pageable pageable) {

        Page<AnimeDTO> pagina = (titulo != null && !titulo.isBlank())
                ? service.filtrarPorTitulo(titulo, pageable)
                : service.listarPaginado(pageable);

        return pagina.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AnimeDTO> criar(@RequestBody AnimeDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimeDTO> atualizar(@PathVariable Long id, @RequestBody AnimeDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
