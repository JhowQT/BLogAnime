package br.com.fiap.BlogAnime.controllers;

import br.com.fiap.BlogAnime.dto.UsuarioDTO;
import br.com.fiap.BlogAnime.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

     @GetMapping("/teste")
    public String ping() {
        return "UsuarioController funcionando!";
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {

        Page<UsuarioDTO> pagina = (nome != null && !nome.isBlank())
                ? service.filtrarPorNome(nome, pageable)
                : service.listarPaginado(pageable);

        return pagina.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
