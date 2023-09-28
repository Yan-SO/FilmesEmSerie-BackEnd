package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario;

import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.CommunicationData.ReturnData;

public record DadosRetornoUsuario(Integer id, String nome, boolean valor) implements ReturnData {
    public DadosRetornoUsuario(Usuario user, boolean valor){
        this(user.getId(),user.getNome_usuario(), valor);
    }

}
