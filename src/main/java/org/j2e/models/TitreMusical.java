package org.j2e.models;

import jakarta.persistence.*;

@Entity
public class TitreMusical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public TypeSupport getTypeSupport() {
        return typeSupport;
    }

    public void setTypeSupport(TypeSupport typeSupport) {
        this.typeSupport = typeSupport;
    }

    @ManyToOne
    @JoinColumn(name = "idAlbum")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "idType")
    private TypeSupport typeSupport;
}