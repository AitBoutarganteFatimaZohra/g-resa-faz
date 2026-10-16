package com.faz.resa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "artefact_technique_faz")
public class ArtefactTechniqueFaz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artefact_faz")
    private Long id;

    @Column(name = "nom_artefact_faz", nullable = false, unique = true)
    private String nomArtefactFaz;

    @ManyToMany(mappedBy = "artefactsFaz")
    private Set<EspaceAcoustiqueFaz> espacesFaz = new HashSet<>();

    // Constructeurs
    public ArtefactTechniqueFaz() {}

    public ArtefactTechniqueFaz(String nomArtefactFaz) {
        this.nomArtefactFaz = nomArtefactFaz;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomArtefactFaz() { return nomArtefactFaz; }
    public void setNomArtefactFaz(String nomArtefactFaz) { this.nomArtefactFaz = nomArtefactFaz; }

    public Set<EspaceAcoustiqueFaz> getEspacesFaz() { return espacesFaz; }
    public void setEspacesFaz(Set<EspaceAcoustiqueFaz> espacesFaz) { this.espacesFaz = espacesFaz; }

    // Méthode utilitaire pour ajouter un espace (double mise à jour)
    public void addEspace(EspaceAcoustiqueFaz espace) {
        espacesFaz.add(espace);
        espace.getArtefactsFaz().add(this);
    }

    // Méthode utilitaire pour retirer un espace
    public void removeEspace(EspaceAcoustiqueFaz espace) {
        espacesFaz.remove(espace);
        espace.getArtefactsFaz().remove(this);
    }

    @Override
    public String toString() {
        return "ArtefactTechniqueFaz [id=" + id + ", nom=" + nomArtefactFaz + "]";
    }
}