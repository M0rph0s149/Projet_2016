
/**
 * Projet algo 
 * 
 * Classe permettant de memoriser et traiter les coordonnees d'un parcours
 * 
 * @author ... <----- !!!!!!! renseignez vos noms ici !!!!!
 * 
 **/
public class Vol {
	private Date date; // date du parcours
	private String pilote; // nom et prenom du pilote
	private Coordonnees[] tableCoordonnees;


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
	
	public Coordonnees extremeNord(){
		long latitudeMax = 0;
		Coordonnees coordonneeMax = this.tableCoordonnees[0];
		for(int i = 1; i < this.tableCoordonnees.length; i++){
			if(this.tableCoordonnees[i].getLatitude()>latitudeMax){
				latitudeMax = this.tableCoordonnees[i].getLatitude();
				coordonneeMax = this.tableCoordonnees[i];
			}
		}
		return coordonneeMax;
	}
	
	public Coordonnees extremeSud(){
		long latitudeMin = 0;
		Coordonnees coordonneeMin = this.tableCoordonnees[0];
		for(int i = 1; i < this.tableCoordonnees.length; i++){
			if(this.tableCoordonnees[i].getLatitude()<latitudeMin){
				latitudeMin = this.tableCoordonnees[i].getLatitude();
				coordonneeMin = this.tableCoordonnees[i];
			}
		}
		return coordonneeMin;
	}
	
	public Coordonnees extremeOuest(){
		long longitudeMin = 0;
		Coordonnees coordonneeMin = this.tableCoordonnees[0];
		for(int i = 1; i < this.tableCoordonnees.length; i++){
			if(this.tableCoordonnees[i].getLongitude()<longitudeMin){
				longitudeMin = this.tableCoordonnees[i].getLongitude();
				coordonneeMin = this.tableCoordonnees[i];
			}
		}
		return coordonneeMin;
	}
	
	public Coordonnees extremeEst(){
		long longitudeMax = 0;
		Coordonnees coordonneeMax = this.tableCoordonnees[0];
		for(int i = 1; i < this.tableCoordonnees.length; i++){
			if(this.tableCoordonnees[i].getLongitude()>longitudeMax){
				longitudeMax = this.tableCoordonnees[i].getLongitude();
				coordonneeMax = this.tableCoordonnees[i];
			}
		}
		return coordonneeMax;
	}
	
	public double distanceTotale(){
		double distance = 0;
		for(int i = 1; i < this.tableCoordonnees.length; i++){
			distance += this.tableCoordonnees[i-1].distance(tableCoordonnees[i]);
		}
		return distance;
	}
	
	public Coordonnees distancePlusProcheCible(Coordonnees cible){
		double distanceMin = cible.distance(tableCoordonnees[0]);
		Coordonnees coordonneesMin = tableCoordonnees[0];
		for(int i = 1; i < tableCoordonnees.length; i++){
			if(cible.distance(tableCoordonnees[i]) < distanceMin){
				distanceMin = cible.distance(tableCoordonnees[i]);
				coordonneesMin = tableCoordonnees[i];
			}
		}
		return coordonneesMin;
	}
	
	public double distanceContournement(int nbContournement){
		int ecart = tableCoordonnees.length / nbContournement + 1;
		int cpt = 0;
		double distance = 0;
		for(int i = 0; i < nbContournement; i++){
			distance += tableCoordonnees[cpt].distance(tableCoordonnees[cpt + ecart]);
			cpt += ecart;
			if(cpt + ecart > tableCoordonnees.length){
				distance += tableCoordonnees[cpt].distance(tableCoordonnees[tableCoordonnees.length-1]);
				break;
			}
		}
		return distance;
	}
	
	public String toString(){
		String texte = "Date: " + this.date + "\nPilote: " + this.pilote + "\nCoordonnees: \n";
		for (Coordonnees coordonnees : tableCoordonnees) {
			texte += coordonnees.toString() + "\n";
		}
		return texte;
	}
} // fin classe
