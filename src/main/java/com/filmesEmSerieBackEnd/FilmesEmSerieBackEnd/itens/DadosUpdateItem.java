package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.itens;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosUpdateItem(@NotNull Integer id, String nomeItem, Float nota, StatusItem status, Tipos tipos) {
}
