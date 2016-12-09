/**
 * Dossier algo Programme principal a completer
 *
 * @author Rifaut Alexis -- Verelst Thomas
 *
 */
public class TraitementVol {

    private static final String REPERTOIRE = "data/";
    private static Vol vol;

    public static java.util.Scanner scanner = new java.util.Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\nTraitement d'un vol\n*******************");
        vol = chargerVol();
        int choix;
        do {
            choix = lireChoix();
            switch (choix) {
                case 1:
                    statistique1();
                    break;
                case 2:
                    statistique2();
                    break;
                case 3:
                    statistique3();
                    break;
                case 4:
                    statistique4();
                    break;
                case 5:
                    statistique5();
                    break;
                case 6:
                    statistique6();
                    break;
                case 7:
                    statistique7();
                    break;
                case 8:
                    statistique8();
                    break;
                case 9:
                    statistique9();
                    break;
                case 10:
                    statistique10();
                    break;
            }
        } while (choix != 11);
        System.out.println("Au revoir!\n");
    }

    private static int lireChoix() {
        System.out.println("\nMenu");
        System.out.println("----");
        System.out.println("   1 --> duree du vol");
        System.out.println("   2 --> lieu le plus eloigne du point de depart");
        System.out.println("   3 --> lieux extremes");
        System.out.println("   4 --> lieu le plus proche d'une cible");
        System.out.println("   5 --> distance parcourue");
        System.out.println("   6 --> distance maximale");
        System.out.println("   7 --> nombre de croisement");
        System.out.println("   8 --> cibles atteintes");
        System.out.println("   9 --> nombre de cibles atteintes lors d'un parcours impose");
        System.out.println("   10 --> A MODIFIER PLUS TARD");
        System.out.println("   11 --> fin");
        System.out.print("\nTon choix : ");
        int choix = Utilitaires.lireUnEntierComprisEntre(1, 11);
        return choix;
    }

    public static Coordonnees lireCoordonnees() {									//METHODE NON TESTEE
        System.out.println("Quelle longitude?\nLongitude:");
        long longitude = scanner.nextLong();

        System.out.println("Quelle latitude?\nLatitude:");
        long latitude = scanner.nextLong();

        return new Coordonnees(latitude, longitude);
    }

    public static void afficherParcours(Coordonnees[] parcours) {					//METHODE NON TESTEE
        for (Coordonnees coordonnees : parcours) {
            System.out.println(coordonnees.toString());
        }
    }

    public static Coordonnees[] creerParcours() {									//METHODE NON TESTEE  (Afficher coordonnees du tableau)
        System.out.println("Combien de coordonnees?\nNombre de coordonnees:");
        int nombreCoordonnees = Utilitaires.lireUnEntierStrictementPositif();
        int compteurCoordonnees = 0;

        Coordonnees[] parcours = new Coordonnees[nombreCoordonnees];

        System.out.println("Voulez-vous inscrire une coordonnee?");
        char reponse = Utilitaires.lireOouN();
        while (compteurCoordonnees < parcours.length && reponse == 'O') {
            parcours[compteurCoordonnees] = lireCoordonnees();
            compteurCoordonnees++;

            System.out.println("Voulez-vous continuer?");
            reponse = Utilitaires.lireOouN();
        }

        return parcours;
    }

    public static void statistique1() {
        System.out.println("\nTon vol a dure " + vol.duree() + " unites temps.");
    }

    public static void statistique2() {
        System.out.println("\nLes coordonnees du lieu le plus eloigne de ton point de depart sont :\n" + vol.distanceMaximalePointDepart().toString());
    }

    public static void statistique3() {
        System.out.println("\nLes coordonnees du lieu le plus au nord sont :\n" + vol.extremeCoordonnees().get(0).toString());
        System.out.println("\nLes coordonnees du lieu le plus au sud sont :\n" + vol.extremeCoordonnees().get(1).toString());
        System.out.println("\nLes coordonnees du lieu le plus a l'ouest sont :\n" + vol.extremeCoordonnees().get(2).toString());
        System.out.println("\nLes coordonnees du lieu le plus a l'est sont :\n" + vol.extremeCoordonnees().get(3).toString());
    }

    public static void statistique4() {
        System.out.println("\nVeuillez rentrer les coordonnees d'une cible a atteindre lors de votre parcours.");
        System.out.println("Le point le plus proche d'une cible est aux coordonnees " + vol.distancePlusProcheCible(lireCoordonnees()).toString());
    }

    public static void statistique5() {
        System.out.println("\nLa distance totale parcourue est de " + vol.distanceTotale() + " unites distance.");
    }

    public static void statistique6() {
        System.out.println("\nCombien de points de contournement?");
        int nbContournement = Utilitaires.lireUnEntierComprisEntre(1, 2);
        System.out.println("\nLa distance totale parcourue avec contournements est de " + vol.distanceContournement(nbContournement));
    }

    public static void statistique7() {
        // A COMPLETER
    }

    public static void statistique8() {
        // A COMPLETER
    }

    public static void statistique9() {
        // A COMPLETER
    }

    public static void statistique10() {
        // A COMPLETER
    }

    /**
     * Construit un vol sur base des donnees du fichier dont le nom est encode
     * au clavier
     *
     * @return parcours cree sur base des donnees du fichier
     */
    private static Vol chargerVol() {
        System.out.print("Introduis le nom du fichier : ");
        String nomFichier = scanner.next();
        Coordonnees[] tableCoordonnees = new Coordonnees[4];
        Fichier fichier = new Fichier(REPERTOIRE + nomFichier);
        int nombreCoordonnees = 0;
        Date date = null;
        String pilote = null;
        try {
            // ouverture fichier
            fichier.ouvrirEnLecture();

            // lecture Date, Pilote
            date = (Date) fichier.lireObjet();
            pilote = (String) fichier.lireObjet();

            // lecture des Coordonnees
            while (true) { // on quitte cette repetitive lorsque EOF rencontree
                Coordonnees coordonnee = (Coordonnees) fichier.lireObjet();
                if (nombreCoordonnees == tableCoordonnees.length) {
                    Coordonnees[] temp = new Coordonnees[tableCoordonnees.length * 2];
                    for (int i = 0; i < tableCoordonnees.length; i++) {
                        temp[i] = tableCoordonnees[i];
                    }
                    tableCoordonnees = temp;
                }
                tableCoordonnees[nombreCoordonnees] = coordonnee;
                nombreCoordonnees++;
            }
        } catch (java.io.EOFException ex) { // fin du fichier rencontree
            Coordonnees[] tableCoordonnees2 = new Coordonnees[nombreCoordonnees];
            for (int i = 0; i < nombreCoordonnees; i++) {
                tableCoordonnees2[i] = tableCoordonnees[i];
            }
            return new Vol(date, pilote, tableCoordonnees2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally { // quoi qu'il arrive, il doit essayer de fermer le fichier.
            try {
                fichier.fermer();
            } catch (java.io.IOException ex) { // si erreur lors de la fermeture
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

} // fin pgm
