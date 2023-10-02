package com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.itens;

import com.filmesEmSerieBackEnd.FilmesEmSerieBackEnd.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "Itens")
@Entity(name = "Item")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeItem;
    private Float nota;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_item")
    private StatusItem status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private Tipos tipos;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Item(DadosValidaItem data, Usuario user){
        this.nomeItem = data.nomeItem();
        this.nota = data.nota();
        this.status = data.status();
        this.tipos = data.tipo();
        this.usuario = user;
    }

    public boolean update(DadosUpdateItem date){
        boolean atualizado = false;
        if (date.nomeItem()!=null) {
            this.nomeItem = date.nomeItem();
            atualizado = true;
        }
        if (date.nota()!=null) {
            this.nota = date.nota();
            atualizado = true;
        }
        if (date.status()!=null) {
            this.status = date.status();
            atualizado = true;
        }
        if (date.tipos()!=null) {
            this.tipos = date.tipos();
            atualizado = true;
        }
        return atualizado;
    }

}
