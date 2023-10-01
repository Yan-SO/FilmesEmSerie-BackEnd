package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.itens;

import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.CommunicationData.ReturnData;

public record DadosRetornoItem(Integer id, String nomeItem, float nota, StatusItem status, Tipos tipos,Integer idUser, boolean valor) implements ReturnData {
    public DadosRetornoItem(Item item, boolean valor){
        this(item.getId(), item.getNomeItem(), item.getNota(), item.getStatus(),item.getTipos(),item.getUsuario().getId(), valor);
    }

    public DadosRetornoItem(Item item) {
        this(item.getId(), item.getNomeItem(), item.getNota(), item.getStatus(),item.getTipos(), item.getUsuario().getId(), true);
    }
}
