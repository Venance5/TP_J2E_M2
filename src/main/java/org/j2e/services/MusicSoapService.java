package org.j2e.services;

import jakarta.jws.*;
import jakarta.jws.WebService;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.j2e.models.Album;
import org.j2e.models.TitreMusical;
import org.j2e.models.TypeSupport;

import java.util.ArrayList;
import java.util.List;


@WebService
public class MusicSoapService {
    @PersistenceContext
    private EntityManager em;

    @WebMethod
    public List<TitreMusical> filtrerTitres(
            @WebParam(name = "titre") String titre,
            @WebParam(name = "album") String album,
            @WebParam(name = "typeSupport") String typeSupport) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TitreMusical> query = cb.createQuery(TitreMusical.class);
        Root<TitreMusical> titreRoot = query.from(TitreMusical.class);

        List<Predicate> predicates = new ArrayList<>();

        if (titre != null && !titre.isEmpty()) {
            predicates.add(cb.like(cb.lower(titreRoot.get("titre")), "%" + titre.toLowerCase() + "%"));
        }

        if (album != null && !album.isEmpty()) {
            Join<TitreMusical, Album> albumJoin = titreRoot.join("album");
            predicates.add(cb.like(cb.lower(albumJoin.get("nom")), "%" + album.toLowerCase() + "%"));
        }

        if (typeSupport != null && !typeSupport.isEmpty()) {
            Join<TitreMusical, TypeSupport> typeSupportJoin = titreRoot.join("typeSupport");
            predicates.add(cb.equal(cb.lower(typeSupportJoin.get("type")), typeSupport.toLowerCase()));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getResultList();
    }
}
