package me.feelzor.faerunbattle.personnages;

import me.feelzor.faerunbattle.Couleur;
import me.feelzor.faerunbattle.CoupDivin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Guerrier {
    private int pointsDeVie;
    private Couleur coul;
    private int nbMouvements;
    private int mouvementsTour;
    private int provocation;
    private int force;

    public Guerrier() {
        this(Couleur.AUCUN);
    }

    public Guerrier(Couleur coul) {
        this.setPointsDeVie(100);
        reinitialiserDeplacements();
        setProvocation(1);
        setForce(10);

        if (coul != Couleur.AUCUN) { this.setCouleur(coul); }
    }

    public Guerrier(Couleur coul, int bonusForce) {
        this(coul);
        if (bonusForce < 0) {
            throw new IllegalArgumentException("Les bonus ne peuvent être négatifs.");
        }

        setForce(getForce() + bonusForce);
    }

    /**
     * @return Les PV de l'unité
     */
    public int getPointsDeVie() {
        return pointsDeVie;
    }

    /**
     * @return La couleur de l'unité (ROUGE ou BLEU)
     */
    public Couleur getCouleur() {
        return coul;
    }

    /**
     * @return Le taux de provocation du personnage
     */
    public int getProvocation() {
        return provocation;
    }
    /**
     * @return La force de l'unité
     */
    public int getForce() {
        return force;
    }

    private int getNbMouvements() {
        return nbMouvements;
    }

    private int getMouvementsTour() {
        return mouvementsTour;
    }

    /**
     * @return Le coût d'entraînement de l'unité
     */
    public int getCout() {
        return 1;
    }

    /**
     * Met à jour le nombre de points de vie de l'unité
     * @param pointsDeVie Le nouveau nombre de points de vie, au minimum 0
     */
    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = Math.max(pointsDeVie, 0);
    }

    /**
     * Change la couleur de l'unité
     * @param coul La nouvelle couleur (soit ROUGE soit BLEU)
     */
    public void setCouleur(Couleur coul) {
        if (coul == Couleur.AUCUN) throw new IllegalArgumentException("La couleur d'une unité ne peut pas être AUCUN");

        this.coul = coul;
    }

    private void setNbMouvements(int nbMouvements) {
        this.nbMouvements = Math.max(nbMouvements, 0);
    }

    private void setMouvementsTour(int mouvementsTour) {
        this.mouvementsTour = Math.max(mouvementsTour, 0);
    }

    private void setProvocation(int provocation) {
        this.provocation = Math.max(provocation, 1);
    }

    private void setForce(int force) {
        this.force = Math.min(force, 0);
    }

    /**
     * Augmente le nombre de déplacements autorisés au cours d'un tour
     */
    public void augmenterNbDeplacements() {
        this.mouvementsTour++;
    }

    /**
     * Augmente le taux de provocation du personnage
     */
    public void augmenterProvocation(int montant) {
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant ne peut pas être négatif.");
        }

        setProvocation(getProvocation() + montant);
    }

    /**
     * @return true si l'unité est toujours vivante
     */
    public boolean estVivant() {
        return this.getPointsDeVie() > 0;
    }

    /**
     * @return true s'il reste des déplacements possibles
     */
    public boolean peutSeDeplacer() {
        return getNbMouvements() != getMouvementsTour();
    }

    /**
     * Indique à l'unité qu'elle a effectué un nouveau déplacement
     */
    public void deplacer() {
        if (!peutSeDeplacer()) {
            throw new IllegalStateException("Tous les déplacements ont déjà été faits pour ce tour.");
        }

        setNbMouvements(getNbMouvements() + 1);
    }

    /**
     * Réinitialise les déplacements (au début d'un tour)
     */
    public void reinitialiserDeplacements() {
        setMouvementsTour(1);
        setNbMouvements(0);
    }

    /**
     * @param guerriers La liste des guerriers sur la case
     * @return La moyenne des provocations de l'équipe du personnage
     */
    protected int moyenneProvocation(List<Guerrier> guerriers) {
        int nbAllies = 0;
        int totalProvoc = 0;
        for (Guerrier g : guerriers) {
            if (g.getCouleur() != this.getCouleur()) { continue; }
            nbAllies++;
            totalProvoc += g.getProvocation();
        }

        return Math.round((float) totalProvoc / nbAllies);
    }

    /**
     * Subis des dégâts
     * @param degats Le montant des dégâts à subir
     */
    protected void subirDegats(int degats) {
        this.setPointsDeVie(this.getPointsDeVie() - (degats));
    }

    /**
     * Attaque une autre unité
     * @param guerrier Le guerrier à attaquer
     */
    public void attaquer(Guerrier guerrier) {
        if (!this.estVivant()) { throw new IllegalStateException("Impossible d'attaquer en étant mort !"); }

        Random rand = new Random();
        int degats = 0;
        for (int i = 0; i < this.getForce(); i++) {
            degats += rand.nextInt(3) + 1; // génère un nombre entre 1 et 3
        }

        if (this.getForce() * 3 * 0.95 <= degats && degats != 0) { throw new CoupDivin(this); }
        guerrier.subirDegats(degats);
        this.augmenterProvocation(degats);
        System.out.println(this + " attaque " + guerrier);
    }

    /**
     * Effectue un tour de combat
     * @param guerriers Liste des guerriers
     */
    public void tour(List<Guerrier> guerriers) {
        Couleur couleurEnnemie = (this.getCouleur() == Couleur.BLEU) ? Couleur.ROUGE : Couleur.BLEU;
        Guerrier cible = premierVivant(guerriers, couleurEnnemie);

        if (cible != null) {
            this.attaquer(cible);
        }
    }

    /**
     * Récupère la première unité vivante dans une liste
     * @param guerriers La liste des unités
     * @param couleur La couleur recherchée
     * @return La première unité vivante, null s'il n'y en a pas
     */
    protected Guerrier premierVivant(List<Guerrier> guerriers, Couleur couleur) {
        List<Guerrier> cibles = new ArrayList<>();
        for (Guerrier g : guerriers) {
            if (g.getCouleur() == couleur && g.estVivant()) {
                cibles.add(g);
            }
        }

        if (cibles.size() == 0) return null;
        cibles.sort((g1, g2) -> (g2.getProvocation() - g1.getProvocation()));

        int currentProb = 0;
        int maxProb = 0;
        for (Guerrier g : cibles) {
            maxProb += g.getProvocation();
        }

        int i = -1;
        do {
            i++;
            currentProb += cibles.get(i).getProvocation();
        } while (i < cibles.size() && Math.random() * maxProb > currentProb);

        return cibles.get(i);
    }

    /**
     * @return Le nom du personnage en fonction de sa classe
     */
    private String getName() {
        String name = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            if (i != 0 && Character.isUpperCase(name.charAt(i))) { builder.append(' '); }
            builder.append(name.charAt(i));
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return this.getName() + ' ' + this.getCouleur() + " [" + this.getPointsDeVie() + "]";
    }
}
