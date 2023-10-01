package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario;

import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.itens.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome_usuario;
    private String senha;

    @OneToMany(mappedBy = "usuario")
    private List<Item> itens = new ArrayList<>();


    public Usuario(DadosValidaUsuario dados){
        this.nome_usuario = dados.nome();
        this.senha = dados.senha();
    }
}
