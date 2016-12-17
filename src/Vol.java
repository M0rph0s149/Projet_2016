import java.util.ArrayList;

/**
 * Projet algo
 *
 * Classe permettant de memoriser et traiter les coordonnees d'un parcours
 *
 * @author Rifaut Alexis -- Verelst Thomas
 *
 */
public class Vol {

    private Date date;
    private String pilote;
    private Coordonnees[] tableCoordonnees;
    private final int NB_COORDONNEE;

    /**
     * Construit le comportement d'un objet Vol qui est définit par une date, un
     * pilote et un ensemble de coordonnées par lesquelles le vol est passé
     *
     * @param date Date du vol
     * @param pilote Nom du pilote
     * @param tableCoordonnees Table de coordonnées par lesquelles le vol est
     * passé
     * @throws IllegalArgumentException Si date est null
     * @throws IllegalArgumentException Si pilote est null
     * @throws IllegalArgumentException Si tableCoordonnees contient au moins un
     * élément null
     */
    public Vol(Date date, String pilote, Coordonnees[] tableCoordonnees) {
        if (date == null) {
            throw new IllegalArgumentException("date est null, ne peut-être null !");
        }
        if (pilote == null) {
            throw new IllegalArgumentException("pilote est null, ne peut-être null !");
        }

        for (Coordonnees coordonnees : tableCoordonnees) {
            if(coordonnees == null){
                throw new IllegalArgumentException("tableCoordonnees contient au moins un élément null !");
            }
        }
        this.date = date;
        this.pilote = pilote;
        this.tableCoordonnees = tableCoordonnees;
        NB_COORDONNEE = tableCoordonnees.length;
    }

    /**
     * Calcul la durée du vol, une unité de temps correspond au
     * temps écoulé entre 2 mesures de position du gps
     *
     * @return La durée de voyage entre deux coordonnées
     */
    public int duree() {
        return this.NB_COORDONNEE - 1;
    }

    /**
     * Calcule la coordonnée enregistrée le plus éloignée du point de départ.
     * En cas d'ex-aequos, c'est la premiére coordonnée qui sera renvoyée
     *
     * @return La Coordonnees la plus éloignée du point de départ
     */
    public Coordonnees lieuxLePLusEloigneDuPointDepart() {
        double distanceMaximale = 0;
        Coordonnees coordonneeMax = null;
        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            if (tableCoordonnees[i].distance(this.tableCoordonnees[0]) > distanceMaximale) {
                distanceMaximale = tableCoordonnees[i].distance(this.tableCoordonnees[0]);
                coordonneeMax = this.tableCoordonnees[i];
            }
        }
        return coordonneeMax;
    }

    /**
     * Calcule les 4 coordonnées cardinales les plus extrêmes.
     *
     * @return Un tableau contenant les 4 coordonnées cardinales les plus extrêmes
     */
    public Coordonnees[] coordonneesExtreme() {
        Coordonnees[] reponse = new Coordonnees[4];

        reponse[0] = extremeNordEtSud()[0];
        reponse[1] = extremeNordEtSud()[1];
        reponse[2] = extremeOuestEtEst()[0];
        reponse[3] = extremeOuestEtEst()[1];

        return reponse;
    }

    /**
     * Calcule les coordonnées cardinales les plus au Nord et au Sud
     *
     * @return Un tableau contenant les coordonnées les plus au Nord et au Sud
     */
    private Coordonnees[] extremeNordEtSud() {
        long latitudeMax = 0;
        long latitudeMin = 0;
        Coordonnees coordonneeMax = this.tableCoordonnees[0];
        Coordonnees coordonneeMin = this.tableCoordonnees[0];

        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            if (this.tableCoordonnees[i].getLatitude() < latitudeMin) {
                latitudeMin = this.tableCoordonnees[i].getLatitude();
                coordonneeMin = this.tableCoordonnees[i];
            }
            if (this.tableCoordonnees[i].getLatitude() > latitudeMax) {
                latitudeMax = this.tableCoordonnees[i].getLatitude();
                coordonneeMax = this.tableCoordonnees[i];
            }
        }
        Coordonnees[] extremesNordEtSud = {coordonneeMax, coordonneeMin};
        return extremesNordEtSud;
    }

    /**
     * Calcule les coordonnées cardinales les plus à l'Ouest et à l'Est
     *
     * @return Un tableau contenant les coordonnées les plus à l'Ouest et à
     * l'Est
     */
    private Coordonnees[] extremeOuestEtEst() {
        long longitudeMin = 0;
        long longitudeMax = 0;
        Coordonnees coordonneeMin = this.tableCoordonnees[0];
        Coordonnees coordonneeMax = this.tableCoordonnees[0];

        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            if (this.tableCoordonnees[i].getLongitude() < longitudeMin) {
                longitudeMin = this.tableCoordonnees[i].getLongitude();
                coordonneeMin = this.tableCoordonnees[i];
            }
            if (this.tableCoordonnees[i].getLongitude() > longitudeMax) {
                longitudeMax = this.tableCoordonnees[i].getLongitude();
                coordonneeMax = this.tableCoordonnees[i];
            }
        }
        Coordonnees[] extremesOuestEtEst = {coordonneeMin, coordonneeMax};
        return extremesOuestEtEst;
    }

    /**
     * Calcule la coordonnée la plus proche d'une cible définie.
     * En cas d?ex-aequos, c?est la première coordonnée qui sera donnée.
     *
     * @param cible Coordonnees voulu pour rapprochement
     * @return Coodonnees la plus proche de la coordonnée cible
     * @throws IllegalArgumentException Si cible est null
     */
    public Coordonnees lieuPlusProcheCible(Coordonnees cible) {
        if (cible == null) {
            throw new IllegalArgumentException("cible est null, ne peut -être null !");
        }

        double distanceMin = cible.distance(tableCoordonnees[0]);
        Coordonnees coordonneesMin = tableCoordonnees[0];

        for (int i = 1; i < NB_COORDONNEE; i++) {

            if (cible.distance(tableCoordonnees[i]) < distanceMin) {
                distanceMin = cible.distance(tableCoordonnees[i]);
                coordonneesMin = tableCoordonnees[i];
            }
        }
        return coordonneesMin;
    }

    /**
     * Calcule la distance totale du vol
     *
     * @return La somme totale de l'espace entre chaque coordonnées
     */
    public double distanceTotale() {
        double distance = 0;

        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            distance += this.tableCoordonnees[i - 1].distance(tableCoordonnees[i]);
        }
        return distance;
    }

    /**
     * Calcule la distance maximale du vol en passant par le point de départ,
     * X points de contournements et l'arrivé. Les points de contournements sont
     * déterminé dans un but de distance maximale.
     *
     * @param nbContournement Nombre de points de contournements (compris entre
     * 0 et 2 inclus)
     * 
     * @return La somme totale de l'espace entre chaque points de contournements
     * @throws IllegalArgumentException Si nbContournement n'est pas compri
     * entre 0 et 2 inclus
     */
    public double distanceContournement(int nbContournement) {
        if (nbContournement < 0 || nbContournement > 2) {
            throw new IllegalArgumentException("nbContournement n'est pas compris entre 0 et 2 inclus !");
        }
        double distance = 0;

        switch (nbContournement) {
            case 0:
                distance = contournement0();
                break;
            case 1:
                distance = contournement1();
                break;
            case 2:
                distance = contournement2();
                break;
        }
        return distance;
    }

    /**
     * Calcule la distance maximale du vol en passant par le point de départ,
     * aucun points de contournements et l'arrivé.
     *
     * @return La distance entre le point de départ et le point final
     */
    private double contournement0() {
        return tableCoordonnees[0].distance(tableCoordonnees[NB_COORDONNEE - 1]);
    }

    /**
     * Calcule la distance maximale du vol en passant par le point de départ,
     * 1 point de contournement et l'arrivé. Le point de contournement est
     * déterminé dans un but de distance maximale.
     *
     * @return l'addition totale de l'espace entre le point de depart, le point
     * de contournement et le point final.
     */
    private double contournement1() {
        double distance = 0;
        for (int i = 0; i < NB_COORDONNEE; i++) {
            if (distance < tableCoordonnees[0].distance(tableCoordonnees[i]) + tableCoordonnees[NB_COORDONNEE - 1].distance(tableCoordonnees[i])) {
                distance = tableCoordonnees[0].distance(tableCoordonnees[i]) + tableCoordonnees[NB_COORDONNEE - 1].distance(tableCoordonnees[i]);
            }
        }
        return distance;
    }

    /**
     * Calcule la distance maximale du vol en passant par le point de départ,
     * s points de contournements et l'arrivé. Les points de contournements sont
     * déterminé dans un but de distance maximale.
     *
     * @return La somme totale de l'espace entre le point de deéart, les deux
     * points de contournements et le point d'arrivé
     */
    private double contournement2() {
        double distance = 0;
        for (int i = 0; i < NB_COORDONNEE; i++) {
            for (int j = i + 1; j < NB_COORDONNEE; j++) {
                if (distance < tableCoordonnees[0].distance(tableCoordonnees[i]) + tableCoordonnees[i].distance(tableCoordonnees[j]) + tableCoordonnees[NB_COORDONNEE - 1].distance(tableCoordonnees[j])) {
                    distance = tableCoordonnees[0].distance(tableCoordonnees[i]) + tableCoordonnees[i].distance(tableCoordonnees[j]) + tableCoordonnees[NB_COORDONNEE - 1].distance(tableCoordonnees[j]);
                }
            }
        }
        return distance;
    }

    /**
     * Calcule le nombre de croisements lors du parcours. Les croisement sont
     * soit des segments qui se croisent ou deux coordonnées se superposant tel
     * que le départ et l'arrivé.
     *
     * @return Le nombre de croisements
     */
    public int nbCroisement() {
        int cpt = 0;

        for (int i = 0; i < NB_COORDONNEE; i++) {
            for (int j = i + 2; j < NB_COORDONNEE - 1; j++) {
                if (Coordonnees.segmentsCroises(tableCoordonnees[i], tableCoordonnees[i + 1], tableCoordonnees[j], tableCoordonnees[j + 1])) {
                    cpt++;
                    if (Coordonnees.segmentsCroises(tableCoordonnees[i], tableCoordonnees[i + 1], tableCoordonnees[j], tableCoordonnees[j])) {
                        cpt--;
                    }else if (Coordonnees.segmentsCroises(tableCoordonnees[i + 1], tableCoordonnees[i + 1], tableCoordonnees[j], tableCoordonnees[j + 1])) {
                        cpt--;
                    }
                }
            }
        }
        return cpt;
    }

    /**
     * Cherche les coordonnées atteintes lors du parcours parmi un tableau de
     * Coordonnees données. L'ordre des cibles n'a pas d'importance ainsi que
     * leur affichage.
     *
     * @param cibles Tableau de Coordonnees sans doublon.
     * @return Le nombre de cibles atteintes parmi les cibles données,une
     * cible atteinte plusieurs fois n'est comptée qu'une fois.
     * @throws IllegalArgumentException Si cibles contient au moins un élément
     * null
     */
    public Coordonnees[] ciblesAtteintes(Coordonnees[] cibles) {
        for (Coordonnees cible : cibles) {
            if(cible == null){
                throw new IllegalArgumentException("cibles contient au moins un élément null !");
            }
        }
        
        ArrayList<Coordonnees> atteint = new ArrayList();

        for (Coordonnees cible : cibles) {
            for (int i = 0; i < NB_COORDONNEE - 1; i++) {
                if (tableCoordonnees[i].equals(cible)) {
                    atteint.add(cible);
                    break;
                } else if (i <= NB_COORDONNEE - 1 && Coordonnees.segmentsCroises(tableCoordonnees[i], tableCoordonnees[i + 1], cible, cible)) {
                    atteint.add(cible);
                    break;
                }
            }
        }
        
        Coordonnees[] tabAtteinte = new Coordonnees[atteint.size()];
        
        for (int i = 0; i < atteint.size(); i++) {
            tabAtteinte[i] = atteint.get(i);
        }
        
        return tabAtteinte;
    }

    /**
     * Calcul le nombre de coordonnées atteintes lors d'un parcours imposé.
     * L'ordre d'atteinte des coordonnées est importante. une coordonnées peut
     * être comptée plusieurs fois. Si l'ordre n'est pas respecté, le programme
     * retourne le nombre de cibles atteintes avant que le l'ordre ne fût
     * perturbé.
     * 
     * @param parcoursImpose Tableau de Coordonnees, doublon autorisés.
     * @return Le nombre de coordonnées atteintes, une coordonnées peut être
     * survolée plusieurs fois.
     * @throws IllegalArgumentException Si parcoursImpose contient au moins un
     * élément null
     */
    public int nbCibleAtteintesParcoursImpose(Coordonnees[] parcoursImpose) {
        for (Coordonnees cible : parcoursImpose) {
            if(cible == null){
                throw new IllegalArgumentException("parcoursImpose contient au moins un élément null !");
            }
        }

        int cpt = 0;
        int cpt2 = 0;
        for (Coordonnees coordonnees : parcoursImpose) {
            for (int i = 0; i < NB_COORDONNEE - 1; i++) {
                if (tableCoordonnees[i].equals(coordonnees)) {
                    cpt++;
                    break;
                } else if (i <= NB_COORDONNEE - 1 && Coordonnees.segmentsCroises(tableCoordonnees[i], tableCoordonnees[i + 1], coordonnees, coordonnees)) {
                    cpt++;
                    break;
                }
            }
            cpt2++;
            if (cpt != cpt2) {
                return cpt;
            }
        }
        return cpt;
    }

    /**
     * Calcule la distance moyenne entre chaque coordonnées du vol
     *
     * @return La distance moyenne entre chaque coordonnées du vol
     */
    public double distanceMoyenne() {
        double moyenne = 0;
        int cpt = 0;
        
        for (int i = 0; i < tableCoordonnees.length - 1; i++) {
            moyenne += tableCoordonnees[i].distance(tableCoordonnees[i + 1]);
            cpt++;
        }
        
        return moyenne /= cpt;
    }

    /**
     * Affiche le Vol
     *
     * @return Un String représentant l'objet
     */
    @Override
    public String toString() {
        String texte = "Date: " + this.date + "\nPilote: " + this.pilote + "\nCoordonnees: \n";
        for (Coordonnees coordonnees : tableCoordonnees) {
            texte += coordonnees.toString() + "\n";
        }
        return texte;
    }
}
