package com.faz.resa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "operateur_nodal_faz")
public class OperateurNodalFaz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operateur_faz")
    private Long id;

    @Column(name = "email_operateur_faz", nullable = false, unique = true)
    private String emailFaz;

    @Column(name = "nom_operateur_faz", nullable = false)
    private String nomFaz;

    @OneToMany(mappedBy = "operateurFaz", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CreneauOccupationFaz> creneauxFaz = new HashSet<>();

    // Constructeurs
    public OperateurNodalFaz() {}

    public OperateurNodalFaz(String emailFaz, String nomFaz) {
        this.emailFaz = emailFaz;
        this.nomFaz = nomFaz;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmailFaz() { return emailFaz; }
    public void setEmailFaz(String emailFaz) { this.emailFaz = emailFaz; }

    public String getNomFaz() { return nomFaz; }
    public void setNomFaz(String nomFaz) { this.nomFaz = nomFaz; }

    public Set<CreneauOccupationFaz> getCreneauxFaz() { return creneauxFaz; }
    public void setCreneauxFaz(Set<CreneauOccupationFaz> creneauxFaz) { this.creneauxFaz = creneauxFaz; }

    // Méthode utilitaire pour ajouter un créneau (double mise à jour)
    public void addCreneau(CreneauOccupationFaz creneau) {
        creneauxFaz.add(creneau);
        creneau.setOperateurFaz(this);
    }

    // Méthode utilitaire pour retirer un créneau
    public void removeCreneau(CreneauOccupationFaz creneau) {
        creneauxFaz.remove(creneau);
        creneau.setOperateurFaz(null);
    }

    @Override
    public String toString() {
        return "OperateurNodalFaz [id=" + id + ", email=" + emailFaz + ", nom=" + nomFaz + "]";
    }
}