package br.com.fiap.BlogAnime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "tbl_fw_anime")
public class Anime {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnime;

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
