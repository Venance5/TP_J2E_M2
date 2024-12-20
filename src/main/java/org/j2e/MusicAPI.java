package org.j2e;
import static spark.Spark.*;
import com.google.gson.Gson;
import jakarta.persistence.*;
import org.j2e.models.Album;
import org.j2e.models.Artiste;
import org.j2e.models.TitreMusical;
import org.j2e.models.TypeSupport;

import java.util.List;

public class MusicAPI {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("musicPU");
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        port(8000);

        get("/", (req, res) -> "Bienvenue sur votre API Spark Java!");

        post("/titres", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            TitreMusical titre = gson.fromJson(req.body(), TitreMusical.class);
            em.getTransaction().begin();
            em.persist(titre);
            em.getTransaction().commit();
            em.close();
            res.status(201);
            return gson.toJson(titre);
        });

        get("/titres", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            List<TitreMusical> titres = em.createQuery("SELECT t FROM TitreMusical t", TitreMusical.class).getResultList();
            em.close();
            return gson.toJson(titres);
        });

        get("/titres/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            TitreMusical titre = em.find(TitreMusical.class, Long.parseLong(req.params(":id")));
            em.close();
            if (titre != null) {
                return gson.toJson(titre);
            } else {
                res.status(404);
                return "Titre non trouvé";
            }
        });

        put("/titres/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            TitreMusical updatedTitre = gson.fromJson(req.body(), TitreMusical.class);
            TitreMusical titre = em.find(TitreMusical.class, Long.parseLong(req.params(":id")));
            if (titre != null) {
                em.getTransaction().begin();
                titre.setTitre(updatedTitre.getTitre());
                titre.setAlbum(updatedTitre.getAlbum());
                titre.setTypeSupport(updatedTitre.getTypeSupport());
                em.getTransaction().commit();
                em.close();
                return gson.toJson(titre);
            } else {
                res.status(404);
                return "Titre non trouvé";
            }
        });

        delete("/titres/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            TitreMusical titre = em.find(TitreMusical.class, Long.parseLong(req.params(":id")));
            if (titre != null) {
                em.getTransaction().begin();
                em.remove(titre);
                em.getTransaction().commit();
                em.close();
                res.status(204);
                return "";
            } else {
                res.status(404);
                return "Titre non trouvé";
            }
        });

        // Endpoints pour Artiste
        post("/artistes", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            Artiste artiste = gson.fromJson(req.body(), Artiste.class);
            em.getTransaction().begin();
            em.persist(artiste);
            em.getTransaction().commit();
            em.close();
            res.status(201);
            return gson.toJson(artiste);
        });

        get("/artistes", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            List<Artiste> artistes = em.createQuery("SELECT a FROM Artiste a", Artiste.class).getResultList();
            em.close();
            return gson.toJson(artistes);
        });

        get("/artistes/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            Artiste artiste = em.find(Artiste.class, Long.parseLong(req.params(":id")));
            em.close();
            return artiste != null ? gson.toJson(artiste) : "Artiste non trouvé";
        });

        put("/artistes/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            Artiste updatedArtiste = gson.fromJson(req.body(), Artiste.class);
            Artiste artiste = em.find(Artiste.class, Long.parseLong(req.params(":id")));
            if (artiste != null) {
                em.getTransaction().begin();
                artiste.setNom(updatedArtiste.getNom());
                em.getTransaction().commit();
            }
            em.close();
            return artiste != null ? gson.toJson(artiste) : "Artiste non trouvé";
        });

        delete("/artistes/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            Artiste artiste = em.find(Artiste.class, Long.parseLong(req.params(":id")));
            if (artiste != null) {
                em.getTransaction().begin();
                em.remove(artiste);
                em.getTransaction().commit();
                res.status(204);
            } else {
                res.status(404);
            }
            em.close();
            return "";
        });

        // Endpoints pour Album
        post("/albums", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            Album album = gson.fromJson(req.body(), Album.class);
            em.getTransaction().begin();
            em.persist(album);
            em.getTransaction().commit();
            em.close();
            res.status(201);
            return gson.toJson(album);
        });

        get("/albums", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            List<Album> albums = em.createQuery("SELECT a FROM Album a", Album.class).getResultList();
            em.close();
            return gson.toJson(albums);
        });

        get("/albums/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            Album album = em.find(Album.class, Long.parseLong(req.params(":id")));
            em.close();
            return album != null ? gson.toJson(album) : "Album non trouvé";
        });

        put("/albums/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            Album updatedAlbum = gson.fromJson(req.body(), Album.class);
            Album album = em.find(Album.class, Long.parseLong(req.params(":id")));
            if (album != null) {
                em.getTransaction().begin();
                album.setNom(updatedAlbum.getNom());
                album.setArtiste(updatedAlbum.getArtiste());
                em.getTransaction().commit();
            }
            em.close();
            return album != null ? gson.toJson(album) : "Album non trouvé";
        });

        delete("/albums/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            Album album = em.find(Album.class, Long.parseLong(req.params(":id")));
            if (album != null) {
                em.getTransaction().begin();
                em.remove(album);
                em.getTransaction().commit();
                res.status(204);
            } else {
                res.status(404);
            }
            em.close();
            return "";
        });

        // Endpoints pour TypeSupport
        post("/typeSupports", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            TypeSupport typeSupport = gson.fromJson(req.body(), TypeSupport.class);
            em.getTransaction().begin();
            em.persist(typeSupport);
            em.getTransaction().commit();
            em.close();
            res.status(201);
            return gson.toJson(typeSupport);
        });

        get("/typeSupports", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            List<TypeSupport> typeSupports = em.createQuery("SELECT t FROM TypeSupport t", TypeSupport.class).getResultList();
            em.close();
            return gson.toJson(typeSupports);
        });

        get("/typeSupports/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            TypeSupport typeSupport = em.find(TypeSupport.class, Long.parseLong(req.params(":id")));
            em.close();
            return typeSupport != null ? gson.toJson(typeSupport) : "TypeSupport non trouvé";
        });

        put("/typeSupports/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            TypeSupport updatedTypeSupport = gson.fromJson(req.body(), TypeSupport.class);
            TypeSupport typeSupport = em.find(TypeSupport.class, Long.parseLong(req.params(":id")));
            if (typeSupport != null) {
                em.getTransaction().begin();
                typeSupport.setType(updatedTypeSupport.getType());
                em.getTransaction().commit();
            }
            em.close();
            return typeSupport != null ? gson.toJson(typeSupport) : "TypeSupport non trouvé";
        });

        delete("/typeSupports/:id", (req, res) -> {
            EntityManager em = emf.createEntityManager();
            TypeSupport typeSupport = em.find(TypeSupport.class, Long.parseLong(req.params(":id")));
            if (typeSupport != null) {
                em.getTransaction().begin();
                em.remove(typeSupport);
                em.getTransaction().commit();
                res.status(204);
            } else {
                res.status(404);
            }
            em.close();
            return "";
        });
    }
}

