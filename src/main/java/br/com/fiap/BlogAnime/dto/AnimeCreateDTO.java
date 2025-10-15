package br.com.fiap.BlogAnime.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeCreateDTO {

    @NotBlank(message = "{anime.titulo.notblank}")
    private String titulo;

    @NotBlank(message = "{anime.descricao.notblank}")
    private String descricao;

    @NotBlank(message = "{anime.autor.notblank}")
    private String autor;

    private Integer anoLancamento;

    @NotBlank(message = "{anime.genero.notblank}")
    private String genero;
}
