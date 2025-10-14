package br.com.fiap.BlogAnime.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.BlogAnime.model.Usuario;
import br.com.fiap.BlogAnime.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class UsuarioController {

    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public List<Usuario> listUsers(){
        return usuarioRepository.findAll();
    }

    @PostMapping("usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario createUser(@RequestBody Usuario usuario){
        log.info("Criando um usuario" + usuario);
        return usuarioRepository.save(usuario);
    }

    @GetMapping("{id}")
    public Usuario getById(@PathVariable Long id){
        log.info("Buscando categoria por ID" + id);
        return getUsuarioById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyUser(@PathVariable Long id){
        log.info("Deletando categoria com id {}" + id);
        usuarioRepository.delete(getUsuarioById(id));
    }

    @PutMapping("{id}")
    public Usuario updateUser(@RequestBody Usuario usuarioUpdate, @PathVariable Long id){
        log.info("Atualizando a categoria {} com id {}", usuarioUpdate, id);
        getUsuarioById(id);
        usuarioUpdate.setIdUsuario(id);
        return usuarioRepository.save(usuarioUpdate);
    }

    private Usuario getUsuarioById(Long id){
        return usuarioRepository
                    .findById(id)
                    .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada com id" + id)
                    );
    }

}
