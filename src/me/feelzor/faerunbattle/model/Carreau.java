package me.feelzor.faerunbattle.model;

import me.feelzor.faerunbattle.Couleur;
import me.feelzor.faerunbattle.CoupDivin;
import me.feelzor.faerunbattle.personnages.Guerrier;

import java.util.ArrayList;
import java.util.List;

public class Carreau {
    private List<Guerrier> unites;

    public Carreau() {
        this.unites = new ArrayList<>();
    }

    /**
     * Récupère la couleur d'une case
     * @return ROUGE si toutes les unités sont rouges, BLEU si toutes les unités sont bleues et AUCUN s'il n'y a aucune unité ou les deux couleurs
     */
    public Couleur getCouleur() {
        Couleur coul = Couleur.AUCUN;
        int i = 0;
        while (i < getNbUnites() && (coul == getUniteAt(i).getCouleur() || coul == Couleur.AUCUN)) {
            coul = getUniteAt(i).getCouleur();
            i++;
        }

        return (i == getNbUnites()) ? coul : Couleur.AUCUN;
    }

    /**
     * @return la liste des unités sur la case
     */
    public List<Guerrier> getUnites() {
        return this.unites;
    }

    /**
     * @param index L'index de l'unité recherchée
     * @return L'unité à l'index index
     */
    private Guerrier getUniteAt(int index) {
        if (index < 0 || index >= getNbUnites()) {
            throw new IllegalArgumentException("L'index doit correspondre à une unité.");
        }

        return getUnites().get(index);
    }

    /**
     * @return Le nombre d'unités sur la case
     */
    public int getNbUnites() { return getUnites().size(); }

    /**
     * Ajoute une unité sur la case
     * @param unite L'unité à rajouter
     */
    public void ajouterUnite(Guerrier unite) {
        getUnites().add(unite);
    }

    /**
     * Ajoute une liste d'unités sur la case
     * @param unites La liste d'unités à rajouter
     */
    public void ajouterUnites(List<Guerrier> unites) {
        getUnites().addAll(unites);
    }

    /**
     * Supprime l'intégralité des unités de la case
     */
    public void supprimerUnites() {
        getUnites().clear();
    }

    /**
     * Retire une unité de la case
     * @param unite L'unité à retirer
     */
    public void supprimerUnite(Guerrier unite) {
        getUnites().remove(unite);
    }

    /**
     * Retire une unité de la case
     * @param index L'index de l'unité à retirer
     */
    public void supprimerUnite(int index) {
        getUnites().remove(index);
    }

    /**
     * Retire un ensemble d'unités de la case
     * @param guerriers La liste des unités à retirer
     */
    public void supprimerUnites(List<Guerrier> guerriers) {
        getUnites().removeAll(guerriers);
    }

    /**
     * Déplace des unités
     * @return La liste des unités retirées de la case, à déplacer
     */
    public List<Guerrier> deplacerUnites() {
        if (getCouleur() == Couleur.AUCUN) {
            throw new IllegalStateException("Impossible de déplacer les unités pendant un combat");
        }

        List<Guerrier> resultat = new ArrayList<>();
        for (Guerrier g : getUnites()) {
            if (g.peutSeDeplacer()) {
                resultat.add(g);
                g.deplacer();
            }
        }

        this.supprimerUnites(resultat);
        return resultat;
    }

    /**
     * Lance le combat sur la case
     */
    public void engagerCombat() {
        for (Guerrier g : this.getUnites()) {
            if (g.estVivant()) {
                try { g.tour(this.getUnites()); }
                catch (CoupDivin e) {
                    System.out.println("Coup divin ! Tout le monde est mort.");
                    this.supprimerUnites();
                    this.ajouterUnite(e.recupererAttaquant());
                }
            }
        }

        getUnites().removeIf(p -> (!p.estVivant()));
    }
}
