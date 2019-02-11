package me.feelzor.faerunbattle.model;

import me.feelzor.faerunbattle.Couleur;
import me.feelzor.faerunbattle.personnages.Guerrier;

import java.util.List;

public class Plateau {
    private Carreau[] carreaux;

    public Plateau(int nbCarreaux) {
        if (nbCarreaux != 5 && nbCarreaux != 10 && nbCarreaux != 15) {
            throw new IllegalArgumentException("Le nombre de carreaux ne peut être que 5, 10 ou 15.");
        }
        this.carreaux = new Carreau[nbCarreaux];
        for (int i = 0; i < nbCarreaux; i++) {
            this.carreaux[i] = new Carreau();
        }
    }

    private Carreau[] getCarreaux() {
        return carreaux;
    }

    private Carreau getCarreauAt(int index) {
        if (index < 0 || index >= getNbCarreaux()) {
            throw new IllegalArgumentException("Le carreau à l'index " + index + " n'existe pas");
        }

        return getCarreaux()[index];
    }

    /**
     * @param numCarreau L'index du carreau
     * @return Les unités placées sur un carreau
     */
    public List<Guerrier> getUnitesCarreau(int numCarreau) {
        return getCarreauAt(numCarreau).getUnites();
    }

    /**
     * @param numCarreau L'index du carreau
     * @return La couleur du carreau
     */
    public Couleur getCouleurCarreau(int numCarreau) {
        return getCarreauAt(numCarreau).getCouleur();
    }

    /**
     * @return Le nombre de carreaux dans le jeu
     */
    public int getNbCarreaux() {
        return carreaux.length;
    }

    /**
     * Ajoute une unité depuis un château
     * @param unite L'unité à ajouter
     */
    public void addUnite(Guerrier unite) {
        if (unite.getCouleur() == Couleur.BLEU) {
            getCarreauAt(0).ajouterUnite(unite);
        } else { // Couleur rouge
            getCarreauAt(getNbCarreaux() - 1).ajouterUnite(unite);
        }
    }

    /**
     * Déplace les unités d'un carreau si possible
     */
    public void mouvementUnites() {
        while (!deplacementsTermines()) {
            deplacementParCouleur(Couleur.BLEU, 1);
            deplacementParCouleur(Couleur.ROUGE, -1);
        }
    }

    /**
     * @return true si les déplacements sont terminés
     */
    private boolean deplacementsTermines() {
        Couleur coul;
        boolean resultat = true;
        int i = 0;
        while (i < getNbCarreaux() && resultat) {
            if ((coul = getCarreauAt(i).getCouleur()) == Couleur.AUCUN || // S'il y a des combats sur la case, ou qu'elle est vide
                    coul == Couleur.BLEU && i == getNbCarreaux() - 1 || // Ou si les unités ont atteint la fin du plateau
                    coul == Couleur.ROUGE && i == 0) { i++; continue; }

            List<Guerrier> guerriers = getCarreauAt(i).getUnites();
            int j = 0;
            while (j < guerriers.size() && resultat) {
                if (guerriers.get(j).peutSeDeplacer()) {
                    resultat = false;
                }
                j++;
            }
            i++;
        }

        return resultat;
    }

    /**
     * Effectue les combats sur le plateau (sur un seul carreau à déterminer).
     */
    public void effectuerCombats() {
        int i = 0;
        Carreau carreau;
        while (i < getNbCarreaux() && ((carreau = getCarreauAt(i)).getCouleur() != Couleur.AUCUN || carreau.getNbUnites() == 0)) {
            i++;
        }

        if (i < getNbCarreaux()) {
            Carreau c = getCarreauAt(i);
            if (c.getNbUnites() > 0 && c.getCouleur() == Couleur.AUCUN) {
                c.engagerCombat();
                this.mouvementUnites();
            }
        }
    }

    /**
     * Effectue les déplacements de toutes les unités d'une couleur
     * @param direction 1 si la couleur est bleue, -1 si la couleur est rouge
     */
    private void deplacementParCouleur(Couleur coul, int direction) {
        int i = (direction == 1) ? 0 : getNbCarreaux() - 1;
        Carreau carreau;
        while (i >= 0 && i < getNbCarreaux() && ((carreau = getCarreauAt(i)).getCouleur() == coul || carreau.getCouleur() == Couleur.AUCUN)) {
            if (carreau.getCouleur() != Couleur.AUCUN && (direction == 1 && i != getNbCarreaux() - 1 || direction == -1 && i != 0)) { // Si on n'est pas à la dernière case
                List<Guerrier> deplacements = carreau.deplacerUnites();
                getCarreauAt(i+direction).ajouterUnites(deplacements);
            }
            i += direction;
        }
    }

    /**
     * Affiche le plateau
     */
    public void afficherPlateau() {
        for (int i = 0; i < this.getNbCarreaux(); i++) {
            System.out.println("------- Case " + (i+1) + " -------");
            for (Guerrier g : getCarreauAt(i).getUnites()) {
                System.out.println(g);
            }

            if (i < 10) { System.out.println("----------------------"); }
            else { System.out.println("-----------------------"); }
        }
    }
}
