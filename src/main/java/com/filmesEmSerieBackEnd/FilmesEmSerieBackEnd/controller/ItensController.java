package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.controller;

import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.CommunicationData.ReturnData;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.CommunicationData.ReturnMessage;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.itens.*;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario.Usuario;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItensController {

    @Autowired
    private ItensRepository itensRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ReturnData cadastrarItem(@RequestBody DadosCadastroItem data){
        if (!data.nullChecks()) return new ReturnMessage("null iten",false);

        Optional<Usuario> retorno= usuarioRepository.findById(data.idUsuario());

        if (retorno.isEmpty())return new ReturnMessage("User not find", false);
        if (data.nota() < 0 || data.nota() > 10)
            return new ReturnMessage("invalid note", false);

        Item item = new Item(data, retorno.get());
        itensRepository.save(item);
        return new DadosRetornoItem(item, true);
    }
    @GetMapping("/todos")
    public Page<ReturnData> listarTodos(Pageable page){
        var a = itensRepository.findAll();
        return itensRepository.findAll(page).map(DadosRetornoItem::new);
    }

    @GetMapping("/id={id}")
    public ReturnData bustaPorId(@PathVariable Integer id){
        Optional<Item> item = itensRepository.findById(id);
        if(item.isEmpty())
            return new ReturnMessage("Item not find", false);

        return new DadosRetornoItem(item.get(), true);
    }

    @PutMapping
    @Transactional
    public ReturnData updateItem(@RequestBody @Valid DadosUpdateItem data){
        var item = itensRepository.getReferenceById(data.id());
        boolean valor = item.update(data);
        if (valor){
            return new DadosRetornoItem(item, valor);
        }
        return new ReturnMessage("no changes",false);
    }

    @DeleteMapping("id={id}")
    @Transactional
    public ReturnData deletarUsuario(@PathVariable Integer id){
        itensRepository.deleteById(id);
        return new ReturnMessage("deleted Item",true);
    }
}
