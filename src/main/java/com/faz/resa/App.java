package com.faz.resa;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UniteResaFaz");
        EntityManager em = emf.createEntityManager();

        try {
            // ================================================
            // PARTIE 1 : CRÉATION DES DONNÉES
            // ================================================
            em.getTransaction().begin();

            // --- Création d'un opérateur ---
            OperateurNodalFaz operateur = new OperateurNodalFaz();
            operateur.setEmailFaz("fatima.aitboutargante@faz.com");
            operateur.setNomFaz("Fatima Zahra Ait Boutargante");

            // --- Création d'un espace ---
            EspaceAcoustiqueFaz espace = new EspaceAcoustiqueFaz();
            espace.setReferenceFaz("ESPACE-A");
            espace.setCapaciteMaxFaz(20);
            espace.setEstEquipeFaz(true);

            // --- Création d'un artefact ---
            ArtefactTechniqueFaz projecteur = new ArtefactTechniqueFaz();
            projecteur.setNomArtefactFaz("Projecteur");

            espace.addArtefact(projecteur);

            // --- Création d'un créneau ---
            CreneauOccupationFaz creneau = new CreneauOccupationFaz();
            creneau.setDateOccupation(LocalDate.now());
            creneau.setDureeHeures(2);
            creneau.setEspaceFaz(espace);
            creneau.setOperateurFaz(operateur);

            operateur.addCreneau(creneau);

            // Persister
            em.persist(espace);
            em.persist(projecteur);
            em.persist(operateur);

            em.getTransaction().commit();
            System.out.println("✅ PARTIE 1 : Création réussie.");

            // ================================================
            // PARTIE 2 : TEST orphanRemoval
            // (retirer un créneau de la collection de l'opérateur)
            // ================================================
            em.getTransaction().begin();

            OperateurNodalFaz opTrouve = em.find(OperateurNodalFaz.class, operateur.getId());
            CreneauOccupationFaz creneauARetirer = opTrouve.getCreneauxFaz().iterator().next();
            opTrouve.removeCreneau(creneauARetirer);

            em.getTransaction().commit();
            System.out.println("✅ PARTIE 2 : Créneau retiré de la collection (orphanRemoval testé).");

            // Vérification : le créneau doit avoir été supprimé de la base
            em.getTransaction().begin();
            CreneauOccupationFaz creneauVerif = em.find(CreneauOccupationFaz.class, creneauARetirer.getId());
            if (creneauVerif == null) {
                System.out.println("✅ Vérification : le créneau a bien été supprimé de la base (orphanRemoval).");
            } else {
                System.out.println("❌ Le créneau existe toujours en base !");
            }
            em.getTransaction().commit();

            // ================================================
            // PARTIE 3 : TEST cascade REMOVE
            // (supprimer un opérateur supprime aussi ses créneaux)
            // ================================================
            // On crée d'abord un nouvel opérateur avec un créneau pour tester la cascade
            em.getTransaction().begin();

            OperateurNodalFaz op2 = new OperateurNodalFaz();
            op2.setEmailFaz("test.cascade@faz.com");
            op2.setNomFaz("Test Cascade");

            CreneauOccupationFaz creneau2 = new CreneauOccupationFaz();
            creneau2.setDateOccupation(LocalDate.now().plusDays(1));
            creneau2.setDureeHeures(3);
            creneau2.setEspaceFaz(espace);
            creneau2.setOperateurFaz(op2);

            op2.addCreneau(creneau2);

            em.persist(op2);

            em.getTransaction().commit();
            System.out.println("✅ PARTIE 3a : Nouvel opérateur créé avec un créneau.");

            // Vérification avant suppression
            Long idCreneau2 = creneau2.getId();
            Long idOp2 = op2.getId();

            em.getTransaction().begin();
            OperateurNodalFaz opASupprimer = em.find(OperateurNodalFaz.class, idOp2);
            em.remove(opASupprimer);
            em.getTransaction().commit();
            System.out.println("✅ PARTIE 3b : Opérateur supprimé (cascade REMOVE).");

            // Vérification : le créneau doit aussi avoir été supprimé
            em.getTransaction().begin();
            CreneauOccupationFaz creneauVerif2 = em.find(CreneauOccupationFaz.class, idCreneau2);
            if (creneauVerif2 == null) {
                System.out.println("✅ Vérification : le créneau a bien été supprimé en cascade avec l'opérateur.");
            } else {
                System.out.println("❌ Le créneau existe toujours en base !");
            }
            em.getTransaction().commit();

            // ================================================
            // PARTIE 4 : Vérifier que l'artefact (ManyToMany) N'A PAS été supprimé
            // ================================================
            em.getTransaction().begin();
            Long idProjecteur = projecteur.getId();
            ArtefactTechniqueFaz projecteurVerif = em.find(ArtefactTechniqueFaz.class, idProjecteur);
            if (projecteurVerif != null) {
                System.out.println("✅ Vérification : l'artefact 'Projecteur' existe toujours (pas de cascade REMOVE sur ManyToMany).");
            } else {
                System.out.println("❌ L'artefact a été supprimé par erreur !");
            }
            em.getTransaction().commit();

            System.out.println("\n🎉 TOUS LES TESTS DE SUPPRESSION SONT TERMINÉS.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("❌ Erreur : " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}