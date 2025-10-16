package br.com.fiap.BlogAnime.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long idUsuario;

    @NotBlank(message = "{user.name.notblank}")
    private String nome;

    @NotBlank(message = "{user.email.notblank}")
    private String email;

    @NotBlank(message = "{user.senha.notblank}")
    private String senha;
}
