package br.com.fiap.BlogAnime.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateDTO {

    private String nome;

    @Email(message = "{user.email.invalid}")
    private String email;

    private String senha;
}
