package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.controller;

import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.CommunicationData.ReturnMessage;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.CommunicationData.ReturnData;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;
    @PostMapping
    @Transactional
    public ReturnData cadastrar(@RequestBody DadosValidaUsuario data){
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
    @GetMapping("/todos")
    public Page<ReturnData> listar(Pageable page){
        return repository.findAll(page).map(DadosRetornoUsuario::new);
    }
    @GetMapping("/login")
    public ReturnData valicarLogin(@RequestBody DadosValidaUsuario data){
        List<Usuario> retorno = null;
        if (data.nome() != null ) retorno = repository.findUsuariosByNomeUsuario(data.nome());
        else {return new ReturnMessage("null name", false);}
        if (retorno.isEmpty()){
            return new ReturnMessage("wrong name", false);
        }else if(data.senha() != null){
            if(retorno.get(0).getSenha().equals(data.senha())){
                return new DadosRetornoUsuario(retorno.get(0));
            }else {return new ReturnMessage("wrong password",false);}
        }else {return new ReturnMessage("null password",false);}
    }

    @DeleteMapping("/id={id}")
    @Transactional
    public ReturnData deletarUsuario(@PathVariable long id){
        repository.deleteById(id);
        return new ReturnMessage("deleted user",true);
    }
}
