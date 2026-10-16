package com.faz.resa;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "creneau_occupation_faz")
public class CreneauOccupationFaz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_creneau_faz")
    private Long id;

    @Column(name = "date_occupation_faz", nullable = false)
    private LocalDate dateOccupation;

    @Column(name = "duree_heures_faz", nullable = false)
    private int dureeHeures;

    // Relation ManyToOne vers OperateurNodalFaz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operateur_faz_id", nullable = false)
    private OperateurNodalFaz operateurFaz;

    // Relation ManyToOne vers EspaceAcoustiqueFaz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "espace_faz_id", nullable = false)
    private EspaceAcoustiqueFaz espaceFaz;

    // Constructeurs
    public CreneauOccupationFaz() {}

    public CreneauOccupationFaz(LocalDate dateOccupation, int dureeHeures) {
        this.dateOccupation = dateOccupation;
        this.dureeHeures = dureeHeures;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDateOccupation() { return dateOccupation; }
    public void setDateOccupation(LocalDate dateOccupation) { this.dateOccupation = dateOccupation; }

    public int getDureeHeures() { return dureeHeures; }
    public void setDureeHeures(int dureeHeures) { this.dureeHeures = dureeHeures; }

    public OperateurNodalFaz getOperateurFaz() { return operateurFaz; }
    public void setOperateurFaz(OperateurNodalFaz operateurFaz) { this.operateurFaz = operateurFaz; }

    public EspaceAcoustiqueFaz getEspaceFaz() { return espaceFaz; }
    public void setEspaceFaz(EspaceAcoustiqueFaz espaceFaz) { this.espaceFaz = espaceFaz; }

    @Override
    public String toString() {
        return "CreneauOccupationFaz [id=" + id + ", date=" + dateOccupation + ", duree=" + dureeHeures + "h]";
    }
}