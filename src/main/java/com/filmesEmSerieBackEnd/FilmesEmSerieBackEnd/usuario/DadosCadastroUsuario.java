package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(String nome,String senha, boolean valor) {
}
