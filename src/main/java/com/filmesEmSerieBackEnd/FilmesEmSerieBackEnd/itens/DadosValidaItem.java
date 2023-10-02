package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.itens;

import jakarta.validation.constraints.NotNull;

public record DadosValidaItem(String nomeItem, @NotNull Tipos tipo, StatusItem status, Float nota, @NotNull Integer idUsuario) {

    public boolean nullChecks(){
        if (this.nomeItem == null)return false;
        if (this.tipo == null)return false;
        if (this.status == null)return false;
        if (this.nota == null)return false;
        if (this.idUsuario == null)return false;

        return true;
    }
}
