package br.com.fiap.BlogAnime.controllers;

import br.com.fiap.BlogAnime.dto.*;
import br.com.fiap.BlogAnime.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // ✅ LISTAR TODOS OS USUÁRIOS
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            List<UsuarioResponseDTO> usuarios = service.listarTodos();
            if (usuarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum usuário encontrado.");
            }
            return ResponseEntity.ok(usuarios); // 200 OK
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
            return ResponseEntity.ok(usuario); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado com o ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar usuário: " + e.getMessage()); // 500
        }
    }

    // ✅ CRIAR NOVO USUÁRIO
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

    // ✅ ATUALIZAR USUÁRIO
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UsuarioUpdateDTO dto) {
        try {
            UsuarioResponseDTO atualizado = service.atualizar(id, dto);
            return ResponseEntity.ok(atualizado); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado com o ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar usuário: " + e.getMessage()); // 500
        }
    }

    // ✅ DELETAR USUÁRIO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado com o ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar usuário: " + e.getMessage()); // 500
        }
    }
}
