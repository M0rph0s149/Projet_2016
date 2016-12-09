
import java.util.ArrayList;
import java.util.List;


/**
 * Projet algo 
 * 
 * Classe permettant de memoriser et traiter les coordonnees d'un parcours
 * 
 * @author Rifaut Alexis -- Verelst Thomas
 * 
 **/
public class Vol {
	private Date date; // date du parcours
	private String pilote; // nom et prenom du pilote
	private Coordonnees[] tableCoordonnees;

    /**
     * Construit le comportement d'un objet Vol qui est définit par une date, un pilote et un ensemble de coordonnées par lesquelles le vol est passé
     * @param date Date du vol
     * @param pilote Nom du pilote
     * @param tableCoordonnees Table de coordonnées par lesquelles le vol est passé
     */
    public Vol(Date date, String pilote, Coordonnees[] tableCoordonnees) {
		this.date = date;
		this.pilote = pilote;
		this.tableCoordonnees = tableCoordonnees;
	}

	/**
	 * Cette methode renvoie la duree du vol
	 * Une unite de temps correspond au temps ecoule entre 2 mesures de position du gps
	 * @return la duree
	 */
	public int duree() {
		return this.tableCoordonnees.length-1;
	}

    /**
     * Calcul la coordonnée enregistrée le plus éloignée du point de départ
     * @return La Coordonnees la plus éloignée du point de départ
     */
    public Coordonnees distanceMaximalePointDepart(){
		double distanceMaximale = 0;
		Coordonnees coordonneeMax = null;
		for(int i = 1; i < this.tableCoordonnees.length; i++){
			double distance = this.tableCoordonnees[i].distance(this.tableCoordonnees[0]);
			if(distance>distanceMaximale){
				distanceMaximale = distance;
				coordonneeMax = this.tableCoordonnees[i];
			}
		}
		return coordonneeMax;
	}
    
    /**
     * Calcul les 4 coordonnées cardinales les plus extremes
     * @return List contenant les 4 Coordonnees cardinales les plus extremes
     */
    public List extremeCoordonnees(){
        List<Coordonnees> reponse  = new ArrayList<Coordonnees>();
        reponse.add(0, extremeNord());
        reponse.add(1, extremeSud());
        reponse.add(2, extremeOuest());
        reponse.add(3, extremeEst());

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
        for (int i = 1; i < this.tableCoordonnees.length; i++) {
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
        for (int i = 1; i < this.tableCoordonnees.length; i++) {
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
        for (int i = 1; i < this.tableCoordonnees.length; i++) {
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
        for (int i = 1; i < this.tableCoordonnees.length; i++) {
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
        for (int i = 1; i < tableCoordonnees.length; i++) {
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
        for (int i = 1; i < this.tableCoordonnees.length; i++) {
            distance += this.tableCoordonnees[i - 1].distance(tableCoordonnees[i]);
        }
        return distance;
    }

    /**
     * Calcul la distance maximale du vol en passant par des points de contournements
     * 
     * @param nbContournement Nombre de points de contournements
     * @return L'addition totale de l'espace entre chaque points de contournements
     */
    public double distanceContournement(int nbContournement) {
        int ecart = tableCoordonnees.length / nbContournement + 1;
        int cpt = 0;
        double distance = 0;
        for (int i = 0; i < nbContournement; i++) {
            distance += tableCoordonnees[cpt].distance(tableCoordonnees[cpt + ecart]);
            cpt += ecart;
            if (cpt + ecart > tableCoordonnees.length) {
                distance += tableCoordonnees[cpt].distance(tableCoordonnees[tableCoordonnees.length - 1]);
                break;
            }
        }
        return distance;
    }

    /**
     * Affiche le Vol
     * 
     * @return Un String représentant l'objet
     */
    public String toString() {
        String texte = "Date: " + this.date + "\nPilote: " + this.pilote + "\nCoordonnees: \n";
        for (Coordonnees coordonnees : tableCoordonnees) {
            texte += coordonnees.toString() + "\n";
        }
        return texte;
    }
} // fin classe
