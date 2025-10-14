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

import br.com.fiap.BlogAnime.model.Anime;
import br.com.fiap.BlogAnime.repository.AnimeRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j

public class AnimeController {


    @Autowired
    private AnimeRepository animeRepository;
    
    @GetMapping("/animes")
    public List<Anime> ListAnimes(){
        return animeRepository.findAll();
    };
    
    @PostMapping("/animes")
    @ResponseStatus(HttpStatus.CREATED)
    public Anime createAnime(@RequestBody Anime anime){
        log.info("Você crio uma obra prima: {} " +  anime);
        return animeRepository.save(anime);
    }

    @GetMapping("{id}")
    public Anime getByAnime(@PathVariable Long id){
        log.info("Buscando anime por Id" + id);
        return getAnimeById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Anime getByID(@PathVariable Long id){
        log.info("Buscando anime por Id" + id);
        return getAnimeById(id);
    }



    @PutMapping("{id}")   
    public Anime updateAnime(@RequestBody Anime animeUpdate, @PathVariable Long id){
        log.info("Atualizando o anime {} com id {}", animeUpdate, id);
        getAnimeById(id);
        animeUpdate.setIdAnime(id);
        return animeRepository.save(animeUpdate);

}

    private Anime getAnimeById(Long id) {
    return animeRepository
                .findById(id)
                .orElseThrow(   
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Tarefa não encontrada com o id " + id)
                );
}

}
