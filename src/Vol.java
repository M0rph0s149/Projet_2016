/**
 * Projet algo
 *
 * Classe permettant de memoriser et traiter les coordonnees d'un parcours
 *
 * @author Rifaut Alexis -- Verelst Thomas
 *
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
     */
    public Vol(Date date, String pilote, Coordonnees[] tableCoordonnees) {
        this.date = date;
        this.pilote = pilote;
        this.tableCoordonnees = tableCoordonnees;
        NB_COORDONNEE = tableCoordonnees.length;
    }

    /**
     * Cette methode renvoie la duree du vol Une unite de temps correspond au
     * temps ecoule entre 2 mesures de position du gps
     *
     * @return la duree
     */
    public int duree() {
        return this.NB_COORDONNEE - 1;
    }

    /**
     * Calcul la coordonnée enregistrée le plus éloignée du point de départ
     *
     * @return La Coordonnees la plus éloignée du point de départ
     */
    public Coordonnees distanceMaximalePointDepart() {
        double distanceMaximale = 0;
        Coordonnees coordonneeMax = null;
        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            double distance = this.tableCoordonnees[i].distance(this.tableCoordonnees[0]);
            if (distance > distanceMaximale) {
                distanceMaximale = distance;
                coordonneeMax = this.tableCoordonnees[i];
            }
        }
        return coordonneeMax;
    }

    /**
     * Calcul les 4 coordonnées cardinales les plus extremes
     *
     * @return List contenant les 4 Coordonnees cardinales les plus extremes
     */
    public Coordonnees[] extremeCoordonnees() {
        Coordonnees[] reponse = new Coordonnees[4];
        reponse[0] = extremeNord();
        reponse[1] = extremeSud();
        reponse[2] = extremeOuest();
        reponse[3] = extremeEst();

        return reponse;
    }

    /**
     * Calcul la coordonnée cardinale la plus au Nord
     *
     * @return La Coordonnees la plus au Nord
     */
    private Coordonnees extremeNord() {
        long latitudeMax = 0;
        Coordonnees coordonneeMax = this.tableCoordonnees[0];
        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            if (this.tableCoordonnees[i].getLatitude() > latitudeMax) {
                latitudeMax = this.tableCoordonnees[i].getLatitude();
                coordonneeMax = this.tableCoordonnees[i];
            }
        }
        return coordonneeMax;
    }

    /**
     * Calcul la coordonnée cardinale la plus au Sud
     *
     * @return La Coordonnees la plus au Sd
     */
    private Coordonnees extremeSud() {
        long latitudeMin = 0;
        Coordonnees coordonneeMin = this.tableCoordonnees[0];
        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            if (this.tableCoordonnees[i].getLatitude() < latitudeMin) {
                latitudeMin = this.tableCoordonnees[i].getLatitude();
                coordonneeMin = this.tableCoordonnees[i];
            }
        }
        return coordonneeMin;
    }

    /**
     * Calcul la coordonnée cardinale la plus à l'Ouest
     *
     * @return La Coordonnees la plus à l'Ouest
     */
    private Coordonnees extremeOuest() {
        long longitudeMin = 0;
        Coordonnees coordonneeMin = this.tableCoordonnees[0];
        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            if (this.tableCoordonnees[i].getLongitude() < longitudeMin) {
                longitudeMin = this.tableCoordonnees[i].getLongitude();
                coordonneeMin = this.tableCoordonnees[i];
            }
        }
        return coordonneeMin;
    }

    /**
     * Calcul la coordonnée cardinale la plus à l'Est
     *
     * @return La Coordonnees la plus à l'Est
     */
    private Coordonnees extremeEst() {
        long longitudeMax = 0;
        Coordonnees coordonneeMax = this.tableCoordonnees[0];
        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            if (this.tableCoordonnees[i].getLongitude() > longitudeMax) {
                longitudeMax = this.tableCoordonnees[i].getLongitude();
                coordonneeMax = this.tableCoordonnees[i];
            }
        }
        return coordonneeMax;
    }

    /**
     * Calcul la coordonnée la plus proche d'une cible définie
     *
     * @param cible Coordonnees voulu pour rapprochement
     * @return Coodonnees la plus proche de la coordonnée cible
     */
    public Coordonnees distancePlusProcheCible(Coordonnees cible) {
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
     * @param nbContournement Nombre de points de contournements
     * @return L'addition totale de l'espace entre chaque points de
     * contournements
     */
    public double distanceContournement(int nbContournement) {
        int ecart = NB_COORDONNEE / nbContournement + 1;
        int cpt = 0;
        double distance = 0;
        for (int i = 0; i < nbContournement; i++) {
            if (cpt + ecart > NB_COORDONNEE) {
                distance += tableCoordonnees[cpt].distance(tableCoordonnees[NB_COORDONNEE - 1]);
                break;
            }
            distance += tableCoordonnees[cpt].distance(tableCoordonnees[cpt + ecart]);
            cpt += ecart;
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
            for (int j = 3; j < NB_COORDONNEE; j++) {
                if (j + 1 != NB_COORDONNEE && i + 1 != NB_COORDONNEE && Coordonnees.segmentsCroises(tableCoordonnees[i], tableCoordonnees[i + 1], tableCoordonnees[j], tableCoordonnees[j + 1])) {
                    cpt ++;
                }
            }
        }
////        if (tableCoordonnees[0].equals(tableCoordonnees[NB_COORDONNEE - 1])) {
////            cpt++;
////        }
//        for (int i = 0; i < NB_COORDONNEE; i++) {
//            for (int j = i + 1; j < NB_COORDONNEE; j++) {
//                if (tableCoordonnees[j].equals(tableCoordonnees[i])) {
//                    cpt++;
//                }
////                if (j + 1 != NB_COORDONNEE && i + 1 != NB_COORDONNEE && Coordonnees.segmentsCroises(tableCoordonnees[j], tableCoordonnees[j + 1], tableCoordonnees[i], tableCoordonnees[i + 1])) {
////                    cpt++;
////                }
//            }
//        }


//        for (int i = 1; i < NB_COORDONNEE; i++) {
//            for(int j = 0; j < i; j++) {
//                if(tableCoordonnees[i].equals(tableCoordonnees[j]))
//                    cpt++;
//                if(j > 0 && tableCoordonnees[i] != tableCoordonnees[j] && tableCoordonnees[i] != tableCoordonnees[j-1] && tableCoordonnees[i-1] != tableCoordonnees[j] && tableCoordonnees[i-1] != tableCoordonnees[j-1])
//                if(j > 0 && j + 1 < i && Coordonnees.segmentsCroises(tableCoordonnees[i], tableCoordonnees[i-1], tableCoordonnees[j], tableCoordonnees[j-1]))
//                    cpt++;
//            }
//        }
//        if(tableCoordonnees[0].equals(tableCoordonnees[NB_COORDONNEE-1])){
//            cpt ++;
//        }
        return cpt;
    }

    /**
     * Calcul le nombre de cibles atteintes lors du parcours
     *
     * @param cibles Tableau contenant les cibles à atteindre
     * @return Le nombre de cibles atteintes
     */
    public int nbCiblesAtteintes(Coordonnees[] cibles) {
        int cpt = 0;
        for (Coordonnees cible : cibles) {
            for (Coordonnees tableCoordonnee : tableCoordonnees) {
                if (tableCoordonnee.equals(cible)) {
                    cpt++;
                    break;
                }
            }
        }
        return cpt;
    }

    public int nbCibleAtteintesLorsParcours(Coordonnees[] ParcoursImpose) {
        int cpt = 0;
        int cpt2 = 0;
        for (Coordonnees coordonnees : ParcoursImpose) {
            for (Coordonnees tableCoordonnee : tableCoordonnees) {
                if(tableCoordonnee.equals(coordonnees)) {
                    cpt++;
                    break;
                }
            }
            cpt2++;
            if(cpt != cpt2){
                return cpt;
            }
        }
        return cpt;
    }

    /**
     * Calcul la distance moyenne entre chaque coordonnées du vol
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
