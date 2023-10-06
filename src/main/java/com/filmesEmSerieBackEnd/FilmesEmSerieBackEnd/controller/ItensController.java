package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.controller;

import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.CommunicationData.ReturnData;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.CommunicationData.ReturnMessage;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.itens.*;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario.Usuario;
import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
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
    public ReturnData cadastrarItem(@RequestBody DadosValidaItem data){
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
        return itensRepository.findAll(page).map(DadosRetornoItem::new);
    }
    @GetMapping("/get-imagem/id={id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id ){
        Optional<Item> item = itensRepository.findById(id);
        if (item.isEmpty()) return null;
        if (item.get().getImagem() == null) return null;

        byte[] imagem = item.get().getImagem();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imagem, headers, HttpStatus.OK);

    }

    @PutMapping("tipo")
    public Page<DadosRetornoItem> buscarPorTipo(@RequestBody @Valid DadosValidaItem data, Pageable page){
        Optional<Usuario> user = usuarioRepository.findById(data.idUsuario());
        if (user.isEmpty()) return null;
        List<DadosRetornoItem> itensFiltrados= user.get().getItens().stream()
                .filter(item -> item.getTipos() == data.tipo()).toList().stream()
                .map(DadosRetornoItem::new).toList();
        return new PageImpl<>(itensFiltrados,page,itensFiltrados.size());
    }

    @GetMapping("/id={id}")
    public ReturnData bustaPorId(@PathVariable Integer id){
        Optional<Item> item = itensRepository.findById(id);
        if(item.isEmpty()) return new ReturnMessage("Item not find", false);
        return new DadosRetornoItem(item.get(), true);
    }

    @PutMapping
    @Transactional
    public ReturnData updateItem(@RequestBody @Valid DadosUpdateItem data){
        if (!itensRepository.existsById(data.id())) return new ReturnMessage("Iten not find", false);
        var item = itensRepository.getReferenceById(data.id());
        boolean valor = item.update(data);
        if (valor){
            return new DadosRetornoItem(item, valor);
        }
        return new ReturnMessage("no changes",false);
    }

    @PostMapping("/imagem/add-{idItem}")
    @Transactional
    public ReturnData addImage(@RequestPart MultipartFile image, @PathVariable Integer idItem){
        try {
            Item item = null;
            if (image == null)
                return new ReturnMessage("image not sent", false);
            if (idItem == null)
                return new ReturnMessage("id item not sent", false);
            String fileName = image.getOriginalFilename();
            if (fileName != null){
                if (fileName.toLowerCase().endsWith(".png")) {
                    item = itensRepository.getReferenceById(idItem);
                    item.setImagem(image.getBytes());
                    return new ReturnMessage("saved image", true);
                }
                return new ReturnMessage("wrong image type", false);
            }
            return new ReturnMessage("wrong file type", false);
        }catch (IOException e){
            return new ReturnMessage("image not saved",false);
        }

    }

    @DeleteMapping("id={id}")
    @Transactional
    public ReturnData deletarUsuario(@PathVariable Integer id){
        itensRepository.deleteById(id);
        return new ReturnMessage("deleted Item",true);
    }
}
