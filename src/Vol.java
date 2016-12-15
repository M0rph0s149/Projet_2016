
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
     * Construit le comportement d'un objet Vol qui est d�finit par une date, un
     * pilote et un ensemble de coordonn�es par lesquelles le vol est pass�
     *
     * @param date Date du vol
     * @param pilote Nom du pilote
     * @param tableCoordonnees Table de coordonn�es par lesquelles le vol est
     * pass�
     */
    public Vol(Date date, String pilote, Coordonnees[] tableCoordonnees) {
        if (date == null) {
            throw new IllegalArgumentException("date est null, ne peut-�tre null !");
        }
        if (pilote == null) {
            throw new IllegalArgumentException("pilote est null, ne peut-�tre null !");
        }
        if (tableCoordonnees == null) {
            throw new IllegalArgumentException("tableCoordonnees est null, ne peut-�tre null !");
        }
        this.date = date;
        this.pilote = pilote;
        this.tableCoordonnees = tableCoordonnees;
        NB_COORDONNEE = tableCoordonnees.length;
    }

    /**
     * Cette methode renvoie la duree du vol Une unite de temps correspond au
     * temps ecoule entre 2 mesures de position du gps
     *
     * @return la duree de voyage entre deux coordonn�es
     */
    public int duree() {
        return this.NB_COORDONNEE - 1;
    }

    /**
     * Calcul la coordonn�e enregistr�e le plus �loign�e du point de d�part
     *
     * @return La Coordonnees la plus �loign�e du point de d�part
     */
    public Coordonnees distanceMaximalePointDepart() {
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
     * Calcul les 4 coordonn?es cardinales les plus extremes
     *
     * @return List contenant les 4 Coordonnees cardinales les plus extremes
     */
    public Coordonnees[] extremeCoordonnees() {
        Coordonnees[] reponse = new Coordonnees[4];

        reponse[0] = extremeNordEtSud()[0];
        reponse[1] = extremeNordEtSud()[1];
        reponse[2] = extremeOuestEtEst()[0];
        reponse[3] = extremeOuestEtEst()[1];

        return reponse;
    }

    /**
     * Calcul les coordonn?es cardinales les plus au Nord et au Sud
     *
     * @return un tableau contenant les Coordonnees les plus au Nord et au Sud
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
     * Calcul les coordonn?es cardinales les plus � l'Ouest et � l'Est
     *
     * @return un tableau contenant les Coordonnees les plus � l'Ouest et �
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
     * Calcul la coordonn�e la plus proche d'une cible d�finie
     *
     * @param cible Coordonnees voulu pour rapprochement
     * @return Coodonnees la plus proche de la coordonn�e cible
     */
    public Coordonnees distancePlusProcheCible(Coordonnees cible) {
        if (cible == null) {
            throw new IllegalArgumentException("cible est null, ne peut -�tre null !");
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
     * Calcul la distance totale du vol
     *
     * @return L'addition totale de l'espace entre chaque Coordonnees
     */
    public double distanceTotale() {
        double distance = 0;

        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            distance += this.tableCoordonnees[i - 1].distance(tableCoordonnees[i]);
        }
        return distance;
    }

    /**
     * Calcul la distance maximale du vol en passant par des points de
     * contournements
     *
     * @param nbContournement Nombre de points de contournements (compris entre
     * 0 et 2 inclus)
     * @return L'addition totale de l'espace entre chaque points de
     * contournements
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
     * Calcul la distance entre le point de d?part et le point final a vol
     * d'oiseau
     *
     * @return La distance entre le point de d?part et le point final
     */
    private double contournement0() {
        return tableCoordonnees[0].distance(tableCoordonnees[NB_COORDONNEE - 1]);
    }

    /**
     * Calcul la distance maximale du vol en passant par un point de
     * contournement
     *
     * @return l'addition totale de l'espace entre le point de depart, le point
     * final et le point de contournement
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
     * Calcul la distance maximale du vol en passant par deux points de
     * contournements
     *
     * @return l'addition totale de l'espace entre le point de depart, le point
     * final et les deux points de contournement
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
     * Calcul le nombre de croisements lors du parcours
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
                    } else if (Coordonnees.segmentsCroises(tableCoordonnees[i + 1], tableCoordonnees[i + 1], tableCoordonnees[j], tableCoordonnees[j + 1])) {
                        cpt--;
                    }
                }
            }
        }
        return cpt;
    }

    /**
     * Calcul le nombre de cibles atteintes lors du parcours
     *
     * @param cibles Liste de cibles sans doublonTableau contenant les cibles �
     * atteindre
     * @return Le nombre de cibles atteintes parmi lespoints de 'cibles',une
     * cible atteinte plusieurs fois n'est compt�e qu'une fois
     */
    public int nbCiblesAtteintes(Coordonnees[] cibles) {
        if (cibles == null) {
            throw new IllegalArgumentException("cibles est nul, ne peu-�tre null !");
        }

        int cpt = 0;
        for (Coordonnees cible : cibles) {
            for (int i = 0; i < NB_COORDONNEE; i++) {
                if (tableCoordonnees[i].equals(cible)) {
                    cpt++;
                    break;
                } else if (i <= NB_COORDONNEE - 1 && Coordonnees.segmentsCroises(tableCoordonnees[i], tableCoordonnees[i + 1], cible, cible)) {
                    cpt++;
                    break;
                }
            }
        }
        return cpt;
    }

    /**
     * 
     * 
     * @param ParcoursImpose
     * @return
     */
    public int nbCibleAtteintesLorsParcours(Coordonnees[] ParcoursImpose) {
        if (ParcoursImpose == null) {
            throw new IllegalArgumentException("ParcoursImpose est nul, ne peu-�tre null !");
        }

        int cpt = 0;
        int cpt2 = 0;
        for (Coordonnees coordonnees : ParcoursImpose) {
            for (int i = 0; i < NB_COORDONNEE; i++) {
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
     * Calcul la distance moyenne entre chaque coordonn�es du vol
     *
     * @return La distance moyenne entre chaque coordonn�es du vol
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
     * @return Un String repr�sentant l'objet
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
