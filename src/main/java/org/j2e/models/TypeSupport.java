package org.j2e.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class TypeSupport {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    private String type;

    @OneToMany(mappedBy = "typeSupport")
    private List<TitreMusical> titresMusicaux;

}

