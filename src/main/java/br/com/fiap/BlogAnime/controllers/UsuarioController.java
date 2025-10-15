package br.com.fiap.BlogAnime.controllers;

import br.com.fiap.BlogAnime.dto.*;
import br.com.fiap.BlogAnime.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // ✅ LISTAR TODOS (PAGINADO E COM FILTRO)
    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {

        try {
            Page<UsuarioResponseDTO> pagina;

            if (nome != null && !nome.isEmpty()) {
                pagina = service.filtrarPorNome(nome, pageable);
            } else {
                pagina = service.listarPaginado(pageable);
            }

            if (pagina.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum usuário encontrado.");
            }

            return ResponseEntity.ok(pagina); // 200 OK

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar usuários: " + e.getMessage()); // 500
        }
    }

    // ✅ BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            UsuarioResponseDTO usuario = service.buscarPorId(id);
            return ResponseEntity.ok(usuario); // 200
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado com o ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar usuário: " + e.getMessage()); // 500
        }
    }

    // ✅ CRIAR NOVO
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UsuarioCreateDTO dto) {
        try {
            UsuarioResponseDTO novoUsuario = service.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario); // 201
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro nos dados enviados: " + e.getMessage()); // 400
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar usuário: " + e.getMessage()); // 500
        }
    }

    // ✅ ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UsuarioUpdateDTO dto) {
        try {
            UsuarioResponseDTO atualizado = service.atualizar(id, dto);
            return ResponseEntity.ok(atualizado); // 200
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado com o ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar usuário: " + e.getMessage()); // 500
        }
    }

    // ✅ DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado com o ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar usuário: " + e.getMessage()); // 500
        }
    }
}
