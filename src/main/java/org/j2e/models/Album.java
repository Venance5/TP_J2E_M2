package org.j2e.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Album {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    public List<TitreMusical> getTitresMusicaux() {
        return titresMusicaux;
    }

    public void setTitresMusicaux(List<TitreMusical> titresMusicaux) {
        this.titresMusicaux = titresMusicaux;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @ManyToOne
    @JoinColumn(name = "idArtiste")
    private Artiste artiste;

    @OneToMany(mappedBy = "album")
    private List<TitreMusical> titresMusicaux;

}