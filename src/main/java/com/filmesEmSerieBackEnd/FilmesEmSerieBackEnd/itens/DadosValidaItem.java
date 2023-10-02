package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.itens;

public record DadosCadastroItem(String nomeItem, Tipos tipo, StatusItem status, Float nota, Integer idUsuario) {

    public boolean nullChecks(){
        if (this.nomeItem == null)return false;
        if (this.tipo == null)return false;
        if (this.status == null)return false;
        if (this.nota == null)return false;
        if (this.idUsuario == null)return false;

        return true;
    }
}
