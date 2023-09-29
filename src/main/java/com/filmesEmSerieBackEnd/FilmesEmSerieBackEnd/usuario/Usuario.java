package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Usuario(DadosValidaUsuario dados){
        this.nome_usuario = dados.nome();
        this.senha = dados.senha();
    }
}
