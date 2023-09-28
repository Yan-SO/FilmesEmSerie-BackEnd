package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.controller;

import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.CommunicationData.ReturnMessage;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.CommunicationData.ReturnData;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;
    @PostMapping
    @Transactional
    public ReturnData cadastrar(@RequestBody DadosCadastroUsuario data){
        if (data.nome() == null || data.senha() == null) {
            return new ReturnMessage("is null", false);
        }

        if (!data.nome().isEmpty() && !data.senha().isEmpty()){

            List<Usuario> list= repository.findUsuariosByNomeUsuario(data.nome());
            if (list.isEmpty()){
                Usuario user = new Usuario(data);
                repository.save(user);
                return new DadosRetornoUsuario(user, true);
            }else {
                return new ReturnMessage("username "+ data.nome() + " already exists",false);
            }
        }else if (data.nome().isEmpty()){
            return new ReturnMessage( "name void",false);

        }else{
            return new ReturnMessage( "password void", false);
        }


    }
}
