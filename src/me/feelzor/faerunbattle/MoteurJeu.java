package me.feelzor.faerunbattle;

import me.feelzor.faerunbattle.model.Chateau;
import me.feelzor.faerunbattle.model.Plateau;
import me.feelzor.faerunbattle.personnages.Guerrier;
import me.feelzor.faerunbattle.personnages.TypePersonnage;

import java.util.List;
import java.util.Scanner;

public class MoteurJeu {
    public static void main(String[] args) {
        Scanner entree = new Scanner(System.in);
        Plateau plateau;

        while (true) {
            try {
                System.out.print("Entrez le nombre de cases du plateau (5 / 10 / 15) : ");
                int nombreCases = entree.nextInt();
                entree.nextLine();
                plateau = new Plateau(nombreCases);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Chateau bleu = new Chateau(Couleur.BLEU, plateau);
        Chateau rouge = new Chateau(Couleur.ROUGE, plateau);

        while (plateau.getCouleurCarreau(0) != Couleur.ROUGE
                && plateau.getCouleurCarreau(plateau.getNbCarreaux() - 1) != Couleur.BLEU) {
            nouveauTour(plateau, bleu, rouge);
            tourDeJeu(bleu, entree);
            tourDeJeu(rouge, entree);
            plateau.mouvementUnites();
            plateau.effectuerCombats();
        }

        if (plateau.getCouleurCarreau(0) == Couleur.ROUGE) {
            System.out.println("Victoire des rouges !");
        } else {
            System.out.println("Victoire des bleus !");
        }
    }

    /**
     * Démarre un nouveau tour
     * @param bleu Le château des bleus
     * @param rouge Le château des rouges
     */
    private static void nouveauTour(Plateau plateau, Chateau bleu, Chateau rouge) {
        System.out.println("-------- Nouveau Tour --------");

        plateau.afficherPlateau();
        for (int i = 0; i < plateau.getNbCarreaux(); i++) {
            List<Guerrier> unites = plateau.getUnitesCarreau(i);
            for (Guerrier g : unites) {
                g.reinitialiserDeplacements();
            }
        }

        bleu.debutTour();
        rouge.debutTour();
    }

    /**
     * Débute le tour d'un château
     */
    private static void tourDeJeu(Chateau joueur, Scanner entree) {
        System.out.println("Aux " + joueur.getCouleur() + "s de jouer !");
        System.out.println("Vous avez " + joueur.getRessources() + " ressources.");

        String action;
        do {
            System.out.println();
            System.out.println("Action (précédez l'action par le mot \"info\" pour obtenir des informations supplémentaires)");
            System.out.println("Nain (n), Elfe (e), Chef Nain (cn), Chef Elfe (ce), Paladin (p), Recruteur (r), Soigneur (s), Compétence (c), Vider (v), Quitter (q)");
            action = entree.nextLine();

            switch (action.toLowerCase()) {
                case "elfe": case "e":
                    joueur.ajouterEntrainement(TypePersonnage.ELFE);
                    System.out.println("Un Elfe a été ajouté à la liste d'attente.");
                    break;
                case "nain": case "n":
                    joueur.ajouterEntrainement(TypePersonnage.NAIN);
                    System.out.println("Un Nain a été ajouté à la liste d'attente.");
                    break;
                case "chef elfe": case "ce":
                    joueur.ajouterEntrainement(TypePersonnage.CHEF_ELFE);
                    System.out.println("Un Chef Elfe a été ajouté à la liste d'attente.");
                    break;
                case "chef nain": case "cn":
                    joueur.ajouterEntrainement(TypePersonnage.CHEF_NAIN);
                    System.out.println("Un Chef Nain a été ajouté à la liste d'attente.");
                    break;
                case "paladin": case "p":
                    joueur.ajouterEntrainement(TypePersonnage.PALADIN);
                    System.out.println("Un Paladin a été ajouté à la liste d'attente.");
                    break;
                case "recruteur": case "r":
                    joueur.ajouterEntrainement(TypePersonnage.RECRUTEUR);
                    System.out.println("Un Recruteur a été ajouté à la liste d'attente.");
                    break;
                case "soigneur": case "s":
                    joueur.ajouterEntrainement(TypePersonnage.SOIGNEUR);
                    System.out.println("Un Soigneur a été ajouté à la liste d'attente.");
                    break;
                case "compétence": case "competence": case "compétences": case "competences": case "c":
                    menuCompetence(joueur, entree);
                    break;
                case "vider": case "v":
                    joueur.annulerEntrainements();
                    System.out.println("La liste d'attente a été vidée.");
                    break;
            }

            if (action.toLowerCase().startsWith("info ")) { Commande.afficherInformations(action.substring(5)); }
        } while (!action.equalsIgnoreCase("quitter") && !action.equalsIgnoreCase("q"));

        joueur.entrainerUnites();
    }

    /**
     * Ouvre le menu d'utilisation de compétences
     */
    private static void menuCompetence(Chateau joueur,  Scanner entree) {
        boolean competenceValide;
        String action;
        do {
            competenceValide = true;
            System.out.println();
            System.out.println("Compétence à utiliser (précédez la compétence par \"info\" pour obtenir plus d'informations)");
            System.out.println("Marchandage (m), Cri Motivation (cm), Négociations (n), Entraînement Intensif (ei), Quitter (q)");
            action = entree.nextLine();

            try {
                switch (action.toLowerCase()) {
                    case "marchandage": case "m":
                        utiliserCompetence(joueur, "Marchandage", "Marchandage utilisé !");
                        break;
                    case "cri motivation": case "cm":
                        utiliserCompetence(joueur, "CriMotivation", "Cri de Motivation utilisé !");
                        break;
                    case "négociations": case "negociations": case "n":
                        utiliserCompetence(joueur, "Negociations", "Négociations activées !");
                        break;
                    case "entraînement intensif": case "entrainement intensif": case "ei":
                        utiliserCompetence(joueur, "Entrainement", "Entraînement intensif activé !");
                        break;
                    case "quitter": case "q":
                        break;
                    default:
                        competenceValide = false;
                }
            } catch (IllegalStateException e) {
                System.out.print(e.getMessage());
            }

            if (action.toLowerCase().startsWith("info ")) { Commande.afficherCompetence(action.substring(5), joueur); }
        } while (!competenceValide);
    }

    /**
     * Essaie d'untiliser une compétence
     * @param messageActivation Le message à afficher avant le rappel du nombre de ressources restantes, si la compétence s'est correctement activée
     */
    private static void utiliserCompetence(Chateau joueur, String nomCompetence, String messageActivation) {
        if (joueur.getCompetence(nomCompetence).activer()) {
            System.out.println(messageActivation + " Il vous reste " + joueur.getRessources() + " ressources.");
        } else { // Pas assez de ressources
            System.out.print("Pas assez de ressources pour utiliser la compétence !");
        }
    }
}
