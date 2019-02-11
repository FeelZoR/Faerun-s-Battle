package me.feelzor.faerunbattle;

import me.feelzor.faerunbattle.model.Chateau;
import me.feelzor.faerunbattle.personnages.*;

public class Commande {
    /**
     * Affiche des informations sur une unité
     */
    public static void afficherInformations(String requete) {
        switch (requete.toLowerCase()) {
            case "nain": case "n":
                afficherPersonnage("Nain");
                break;
            case "elfe": case "e":
                afficherPersonnage("Elfe");
                break;
            case "chef nain": case "cn":
                afficherPersonnage("Chef Nain");
                break;
            case "chef elfe": case "ce":
                afficherPersonnage("Chef Elfe");
                break;
            case "paladin": case "p":
                afficherPersonnage("Paladin", "\nIl va provoquer ses adversaires si son niveau de provocation est inférieur à la moyenne de son équipe." +
                        "\nIl a également plus de défense et de points de vie qu'un guerrier simple.");
                break;
            case "recruteur": case "r":
                afficherPersonnage("Recruteur", "\nIl a 15% de chances de changer la couleur de sa cible en la vôtre au tour suivant.");
                break;
            case "soigneur": case "s":
                afficherPersonnage("Soigneur", "\nIl a 40% de chances de soigner un allié de 20 pv ou tous ses alliés de 10 pv.");
                break;
            case "compétence": case "competence": case "compétences": case "competences": case "c":
                ecrireInfos("Compétences", "Ouvre le menu d'utilisation de compétence.", "Variable");
                break;
            case "vider": case "v":
                ecrireInfos("Vider", "Vide la liste d'attente des personnages.", "Aucun");
                break;
        }
    }

    private static void afficherPersonnage(String nom) {
        afficherPersonnage(nom, "");
    }

    private static void afficherPersonnage(String nom, String desc) {
        try {
            Guerrier g = (Guerrier) Class.forName("me.feelzor.faerunbattle.personnages." + nom.replaceAll("\\s", "")).newInstance();
            desc = "Ajoute un " + nom + " à la liste d'attente de personnages." + desc;
            ecrireInfos(nom, desc, Integer.toString(g.getCout()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche des informations sur une compétence
     */
    public static void afficherCompetence(String requete, Chateau joueur) {
        switch (requete.toLowerCase()) {
            case "marchandage": case "m":
                ecrireInfos("Marchandage", "Augmente les revenus par tour de 1.",
                        String.valueOf(joueur.getCompetence("Marchandage").getCout()));
                break;
            case "cri motivation": case "cm":
                ecrireInfos("Cri Motivation", "Motive les troupes, leur permettant d'avancer de deux cases durant ce tour.",
                        String.valueOf(joueur.getCompetence("CriMotivation").getCout()));
                break;
            case "négociations": case "negociations": case "n":
                ecrireInfos("Négociations", "Négocie les coûts de formation et les réduit de 1 pendant 3 tours (min 1).",
                        String.valueOf(joueur.getCompetence("negociations").getCout()));
                break;
            case "entraînement intensif": case "entrainement intensif": case "ei":
                ecrireInfos("Entraînement intensif", "Augmente la force de base des troupes formées de 2 pendant 2 tours.\n" +
                        "Chaque achat supplémentaire de cette compétence augmentera ce bonus de 1, à coût réduit.",
                        String.valueOf(joueur.getCompetence("entrainement").getCout()));
                break;
        }
    }

    /**
     * Affiche des informations
     * @param titre Le titre de l'information
     * @param description La description de l'information
     * @param cout Le coût d'utilisation
     */
    private static void ecrireInfos(String titre, String description, String cout) {
        System.out.println("----- Informations | " + titre + " -----");
        System.out.println("Coût : " + cout);
        System.out.println(description);
    }
}