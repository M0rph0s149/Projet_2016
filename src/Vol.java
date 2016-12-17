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
     * Construit le comportement d'un objet Vol qui est d�finit par une date, un
     * pilote et un ensemble de coordonn�es par lesquelles le vol est pass�
     *
     * @param date Date du vol
     * @param pilote Nom du pilote
     * @param tableCoordonnees Table de coordonn�es par lesquelles le vol est
     * pass�
     * @throws IllegalArgumentException Si date est null
     * @throws IllegalArgumentException Si pilote est null
     * @throws IllegalArgumentException Si tableCoordonnees contient au moins un
     * �l�ment null
     */
    public Vol(Date date, String pilote, Coordonnees[] tableCoordonnees) {
        if (date == null) {
            throw new IllegalArgumentException("date est null, ne peut-�tre null !");
        }
        if (pilote == null) {
            throw new IllegalArgumentException("pilote est null, ne peut-�tre null !");
        }

        for (Coordonnees coordonnees : tableCoordonnees) {
            if(coordonnees == null){
                throw new IllegalArgumentException("tableCoordonnees contient au moins un �l�ment null !");
            }
        }
        this.date = date;
        this.pilote = pilote;
        this.tableCoordonnees = tableCoordonnees;
        NB_COORDONNEE = tableCoordonnees.length;
    }

    /**
     * Calcul la dur�e du vol, une unit� de temps correspond au
     * temps �coul� entre 2 mesures de position du gps
     *
     * @return La dur�e de voyage entre deux coordonn�es
     */
    public int duree() {
        return this.NB_COORDONNEE - 1;
    }

    /**
     * Calcule la coordonn�e enregistr�e le plus �loign�e du point de d�part.
     * En cas d'ex-aequos, c'est la premi�re coordonn�e qui sera renvoy�e
     *
     * @return La Coordonnees la plus �loign�e du point de d�part
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
     * Calcule les 4 coordonn�es cardinales les plus extr�mes.
     *
     * @return Un tableau contenant les 4 coordonn�es cardinales les plus extr�mes
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
     * Calcule les coordonn�es cardinales les plus au Nord et au Sud
     *
     * @return Un tableau contenant les coordonn�es les plus au Nord et au Sud
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
     * Calcule les coordonn�es cardinales les plus � l'Ouest et � l'Est
     *
     * @return Un tableau contenant les coordonn�es les plus � l'Ouest et �
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
     * Calcule la coordonn�e la plus proche d'une cible d�finie.
     * En cas d?ex-aequos, c?est la premi�re coordonn�e qui sera donn�e.
     *
     * @param cible Coordonnees voulu pour rapprochement
     * @return Coodonnees la plus proche de la coordonn�e cible
     * @throws IllegalArgumentException Si cible est null
     */
    public Coordonnees lieuPlusProcheCible(Coordonnees cible) {
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
     * Calcule la distance totale du vol
     *
     * @return La somme totale de l'espace entre chaque coordonn�es
     */
    public double distanceTotale() {
        double distance = 0;

        for (int i = 1; i < this.NB_COORDONNEE; i++) {
            distance += this.tableCoordonnees[i - 1].distance(tableCoordonnees[i]);
        }
        return distance;
    }

    /**
     * Calcule la distance maximale du vol en passant par le point de d�part,
     * X points de contournements et l'arriv�. Les points de contournements sont
     * d�termin� dans un but de distance maximale.
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
     * Calcule la distance maximale du vol en passant par le point de d�part,
     * aucun points de contournements et l'arriv�.
     *
     * @return La distance entre le point de d�part et le point final
     */
    private double contournement0() {
        return tableCoordonnees[0].distance(tableCoordonnees[NB_COORDONNEE - 1]);
    }

    /**
     * Calcule la distance maximale du vol en passant par le point de d�part,
     * 1 point de contournement et l'arriv�. Le point de contournement est
     * d�termin� dans un but de distance maximale.
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
     * Calcule la distance maximale du vol en passant par le point de d�part,
     * s points de contournements et l'arriv�. Les points de contournements sont
     * d�termin� dans un but de distance maximale.
     *
     * @return La somme totale de l'espace entre le point de de�art, les deux
     * points de contournements et le point d'arriv�
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
     * soit des segments qui se croisent ou deux coordonn�es se superposant tel
     * que le d�part et l'arriv�.
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
     * Cherche les coordonn�es atteintes lors du parcours parmi un tableau de
     * Coordonnees donn�es. L'ordre des cibles n'a pas d'importance ainsi que
     * leur affichage.
     *
     * @param cibles Tableau de Coordonnees sans doublon.
     * @return Le nombre de cibles atteintes parmi les cibles donn�es,une
     * cible atteinte plusieurs fois n'est compt�e qu'une fois.
     * @throws IllegalArgumentException Si cibles contient au moins un �l�ment
     * null
     */
    public Coordonnees[] ciblesAtteintes(Coordonnees[] cibles) {
        for (Coordonnees cible : cibles) {
            if(cible == null){
                throw new IllegalArgumentException("cibles contient au moins un �l�ment null !");
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
     * Calcul le nombre de coordonn�es atteintes lors d'un parcours impos�.
     * L'ordre d'atteinte des coordonn�es est importante. une coordonn�es peut
     * �tre compt�e plusieurs fois. Si l'ordre n'est pas respect�, le programme
     * retourne le nombre de cibles atteintes avant que le l'ordre ne f�t
     * perturb�.
     * 
     * @param parcoursImpose Tableau de Coordonnees, doublon autoris�s.
     * @return Le nombre de coordonn�es atteintes, une coordonn�es peut �tre
     * survol�e plusieurs fois.
     * @throws IllegalArgumentException Si parcoursImpose contient au moins un
     * �l�ment null
     */
    public int nbCibleAtteintesParcoursImpose(Coordonnees[] parcoursImpose) {
        for (Coordonnees cible : parcoursImpose) {
            if(cible == null){
                throw new IllegalArgumentException("parcoursImpose contient au moins un �l�ment null !");
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
     * Calcule la distance moyenne entre chaque coordonn�es du vol
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
