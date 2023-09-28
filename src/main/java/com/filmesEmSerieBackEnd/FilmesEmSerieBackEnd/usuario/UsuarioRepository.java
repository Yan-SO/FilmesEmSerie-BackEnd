package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.nome_usuario = :nomeUsuario")
    List<Usuario> findUsuariosByNomeUsuario(@Param("nomeUsuario") String nomeUsuario);
}

