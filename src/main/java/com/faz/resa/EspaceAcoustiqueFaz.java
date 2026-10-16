package com.faz.resa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "espace_acoustique_faz")
public class EspaceAcoustiqueFaz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_espace_faz")
    private Long id;

    @Column(name = "reference_espace_faz", nullable = false, unique = true)
    private String referenceFaz;

    @Column(name = "capacite_max_faz", nullable = false)
    private int capaciteMaxFaz;

    @Column(name = "est_equipe_faz", nullable = false)
    private boolean estEquipeFaz;

    // Relation OneToMany vers CreneauOccupationFaz
    @OneToMany(mappedBy = "espaceFaz", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CreneauOccupationFaz> creneauxFaz = new HashSet<>();

    // Relation ManyToMany vers ArtefactTechniqueFaz
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "espace_artefact_faz",
            joinColumns = @JoinColumn(name = "espace_faz_id"),
            inverseJoinColumns = @JoinColumn(name = "artefact_faz_id")
    )
    private Set<ArtefactTechniqueFaz> artefactsFaz = new HashSet<>();

    // Constructeurs
    public EspaceAcoustiqueFaz() {}

    public EspaceAcoustiqueFaz(String referenceFaz, int capaciteMaxFaz, boolean estEquipeFaz) {
        this.referenceFaz = referenceFaz;
        this.capaciteMaxFaz = capaciteMaxFaz;
        this.estEquipeFaz = estEquipeFaz;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReferenceFaz() { return referenceFaz; }
    public void setReferenceFaz(String referenceFaz) { this.referenceFaz = referenceFaz; }

    public int getCapaciteMaxFaz() { return capaciteMaxFaz; }
    public void setCapaciteMaxFaz(int capaciteMaxFaz) { this.capaciteMaxFaz = capaciteMaxFaz; }

    public boolean isEstEquipeFaz() { return estEquipeFaz; }
    public void setEstEquipeFaz(boolean estEquipeFaz) { this.estEquipeFaz = estEquipeFaz; }

    public Set<CreneauOccupationFaz> getCreneauxFaz() { return creneauxFaz; }
    public void setCreneauxFaz(Set<CreneauOccupationFaz> creneauxFaz) { this.creneauxFaz = creneauxFaz; }

    public Set<ArtefactTechniqueFaz> getArtefactsFaz() { return artefactsFaz; }
    public void setArtefactsFaz(Set<ArtefactTechniqueFaz> artefactsFaz) { this.artefactsFaz = artefactsFaz; }

    // Méthode utilitaire pour ajouter un créneau (double mise à jour)
    public void addCreneau(CreneauOccupationFaz creneau) {
        creneauxFaz.add(creneau);
        creneau.setEspaceFaz(this);
    }

    // Méthode utilitaire pour retirer un créneau
    public void removeCreneau(CreneauOccupationFaz creneau) {
        creneauxFaz.remove(creneau);
        creneau.setEspaceFaz(null);
    }

    // Méthode utilitaire pour ajouter un artefact (double mise à jour)
    public void addArtefact(ArtefactTechniqueFaz artefact) {
        artefactsFaz.add(artefact);
        artefact.getEspacesFaz().add(this);
    }

    // Méthode utilitaire pour retirer un artefact
    public void removeArtefact(ArtefactTechniqueFaz artefact) {
        artefactsFaz.remove(artefact);
        artefact.getEspacesFaz().remove(this);
    }

    @Override
    public String toString() {
        return "EspaceAcoustiqueFaz [id=" + id + ", reference=" + referenceFaz + ", capaciteMax=" + capaciteMaxFaz + "]";
    }
}