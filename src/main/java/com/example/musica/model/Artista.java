package com.example.musica.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoArtistas tipo;

    // cascede serve para salvar no banco de dados
    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Musicas> musicas = new ArrayList<>();

    public Artista(String nome, TipoArtistas tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public Artista() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoArtistas getTipo() {
        return tipo;
    }

    public void setTipo(TipoArtistas tipo) {
        this.tipo = tipo;
    }

    public List<Musicas> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musicas> musicas) {
        this.musicas = musicas;
    }

    @Override
    public String toString() {
        return
                ", Artista ='" + nome + '\'' +
                ", tipo =" + tipo +
                ", musicas =" + musicas;
    }
}
