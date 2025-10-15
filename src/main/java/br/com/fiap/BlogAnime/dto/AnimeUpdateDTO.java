package br.com.fiap.BlogAnime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeUpdateDTO {

    private String titulo;
    private String descricao;
    private String autor;
    private Integer anoLancamento;
    private String genero;
}
