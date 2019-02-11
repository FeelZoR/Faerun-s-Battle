package me.feelzor.faerunbattle.model;

import me.feelzor.faerunbattle.Couleur;
import me.feelzor.faerunbattle.personnages.TypePersonnage;
import me.feelzor.faerunbattle.competences.*;
import me.feelzor.faerunbattle.personnages.*;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class Chateau {
    private HashMap<String, Competence> competences;
    private Queue<Guerrier> attente;
    private Couleur couleur;
    private Plateau plateau;

    private int ressources;
    private int ressourcesParTour;

    public Chateau(Couleur coul, Plateau plateau) {
        if (coul == Couleur.AUCUN) {
            throw new IllegalArgumentException("Un château ne peut pas avoir pour couleur AUCUN.");
        }

        this.attente = new ArrayDeque<>();
        this.competences = new HashMap<>();
        setCouleur(coul);
        setPlateau(plateau);
        setRessources(3);
        setTauxRessources(1);

        ajouterCompetence("marchandage", new Marchandage(this));
        ajouterCompetence("crimotivation", new CriMotivation(this, getPlateau()));
        ajouterCompetence("negociations", new Negociations(this));
        ajouterCompetence("entrainement", new EntrainementIntensif(this));
    }

    /**
     * Récupère la couleur d'un château
     * @return ROUGE ou BLEU
     */
    public Couleur getCouleur() {
        return couleur;
    }

    private void setCouleur(Couleur couleur) {
        if (couleur == Couleur.AUCUN) {
            throw new IllegalArgumentException("Le château ne peut pas être de couleur " + couleur);
        }

        this.couleur = couleur;
    }

    /**
     * @return le nombre de ressources d'un château
     */
    public int getRessources() {
        return this.ressources;
    }

    private void setRessources(int nbRessources) {
        this.ressources = Math.max(nbRessources, 0);
    }

    /**
     * @return Le nombre de ressources gagnées à chaque début de tour
     */
    public int getTauxRessources() {
        return this.ressourcesParTour;
    }

    private void setTauxRessources(int tauxRessources) {
        this.ressourcesParTour = Math.max(tauxRessources, 0);
    }

    private Plateau getPlateau() {
        return plateau;
    }

    private void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    private Queue<Guerrier> getAttente() {
        return attente;
    }

    private HashMap<String, Competence> getCompetences() {
        return competences;
    }

    private void ajouterCompetence(String nom, Competence c) {
        competences.put(nom, c);
    }

    /**
     * @param nom Le nom de la compétence recherchée
     * @return La compétence trouvée
     */
    public Competence getCompetence(String nom) {
        if (!this.competences.containsKey(nom.toLowerCase())) {
            throw new IllegalArgumentException("Compétence non existante.");
        }

        return this.competences.get(nom.toLowerCase());
    }

    /**
     * Consomme un certain nombre de ressources
     * @param nbRessources Le nombre de ressources à consommer, doit être inférieur au nombre de ressources du château
     */
    public void consommerRessources(int nbRessources) {
        if (nbRessources > getRessources()) {
            throw new IllegalArgumentException("Pas assez de nbRessources.");
        } else if (nbRessources < 0) {
            throw new IllegalArgumentException("Impossible de consommer un nombre négatif de nbRessources.");
        }

        setRessources(getRessources() - nbRessources);
    }

    /**
     * Augmente le nombre de ressources gagnées à chaque tour
     */
    public void augmenterTauxRessources() {
        setTauxRessources(getTauxRessources() + 1);
    }

    /**
     * Augmente le nombre de ressources du château grâce au taux en vigueur
     */
    public void augmenterRessources() {
        setRessources(getRessources() + getTauxRessources());
    }

    /**
     * Ajoute un personnage à la liste des entraînements en attente
     */
    public void ajouterEntrainement(TypePersonnage type) {
        int bonusForce = 0;
        if (this.getCompetence("entrainement").estActif()) {
            bonusForce = ((EntrainementIntensif) this.getCompetence("entrainement")).getBoost();
        }

        switch (type) {
            case ELFE:
                ajouterUnite(new Elfe(getCouleur(), bonusForce));
                break;
            case NAIN:
                ajouterUnite(new Nain(getCouleur(), bonusForce));
                break;
            case CHEF_ELFE:
                ajouterUnite(new ChefElfe(getCouleur(), bonusForce));
                break;
            case CHEF_NAIN:
                ajouterUnite(new ChefNain(getCouleur(), bonusForce));
                break;
            case PALADIN:
                ajouterUnite(new Paladin(getCouleur(), bonusForce));
                break;
            case RECRUTEUR:
                ajouterUnite(new Recruteur(getCouleur()));
                break;
            case SOIGNEUR:
                ajouterUnite(new Soigneur(getCouleur()));
                break;
        }
    }

    private void ajouterUnite(Guerrier guerrier) {
        getAttente().add(guerrier);
    }

    /**
     * Annule tous les entraînements prévus
     */
    public void annulerEntrainements() {
        getAttente().clear();
    }

    /**
     * Entraîne autant d'unités que possible, selon la liste d'attente
     */
    public void entrainerUnites() {
        Queue<Guerrier> attente = getAttente();
        while (attente.peek() != null && attente.peek().getCout() <= getRessources()) {
            Guerrier g = attente.remove();
            g.deplacer(); // on dit à l'unité qu'elle s'est déplacée durant le tour
            getPlateau().addUnite(g);
            int cout = (this.getCompetence("negociations").estActif()) ? Math.max(g.getCout() - 1, 1) : g.getCout();
            consommerRessources(cout);
        }
    }

    /**
     * Débute un nouveau tour
     */
    public void debutTour() {
        this.augmenterRessources();

        for (Competence competence : getCompetences().values()) {
            competence.debutTour();
        }
    }
}
