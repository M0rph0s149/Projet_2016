
import java.util.*;

/**
 * Cette classe Date permet de construire une date et de la valider. Elle permet
 * aussi de lire la date system, de tester si une année est bissextile.
 * Remarque: il faudrait compléter cette classe en y ajoutant un nombre de jours
 * écoulés depuis une date de référence. Cela facilite les comparaisons, ...
 * 
 * @author Olivier Legrand 01/2003.
 */
public class Date implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// le 1er janvier 1980 est la date de référence, c'était un mardi
	private static final int JOUR_REF = 2; // 2 car mardi (1: lundi, 2:
											// mardi,...)
	private static final int ANNEE_MIN = 1980; // pour la validation
	private static final int ANNEE_MAX = 2050; // pour la validation
	private static int[] NB_JOURS_MOIS = { 0, 31, 28, 31, 30, 31, 30, 31, 31,
			30, 31, 30, 31 };

	private int jour;
	private int mois;
	private int annee;

	/**
	 * constructeur de la classe Date.
	 * 
	 * @param jour
	 *            entier représentant le jour.
	 * @param mois
	 *            entier représentant le mois.
	 * @param année
	 *            entier représentant l'année.
	 * @throws Exception
	 *             si la date est invalide.
	 */
	public Date(int jour, int mois, int année) throws Exception {
		if (Date.estValide(jour, mois, année)) {
			this.jour = jour;
			this.mois = mois;
			this.annee = année;
		} else {
			throw new Exception("Date incorrecte : " + jour + " / " + mois
					+ " / " + année);
		}
	}

	/**
	 * constructeur qui reçoit une chaîne de caractères contenant une date. Elle
	 * permet de valider et de construire une date. Si la date extraite de cette
	 * chaîne n'est pas valide, elle renvoie une exception.
	 * 
	 * @param stringDate
	 *            chaîne de caractères contenant la date à créer.
	 * @throws Exception
	 *             si la date est invalide.
	 */
	public Date(String stringDate) throws Exception {

		// format valide : jj/mm/aaaa
		String s = stringDate.trim();

		// jours
		int jour = 0;
		StringTokenizer st = new StringTokenizer(s, "/", true);
		try {
			if (!st.hasMoreTokens())
				throw new Exception("Date incorrecte. Veuillez vérifier les "
						+ " jours.");
			String sJours = (st.nextToken()).trim();
			jour = (new Integer(sJours)).intValue();
		} catch (Exception e) {
			throw new Exception("Date incorrecte. Veuillez vérifier les "
					+ " jours.");
		}

		// les mois
		int mois;
		try {
			if (!st.hasMoreTokens())
				throw new Exception("Date incorrecte. Veuillez vérifier le "
						+ " mois.");
			st.nextToken(); // lecture du délimiteur
			String sMois = (st.nextToken()).trim();
			mois = (new Integer(sMois)).intValue();
		} catch (Exception e) {
			throw new Exception("Date incorrecte. Veuillez vérifier le "
					+ " mois.");
		}

		// les années
		int année;
		try {
			String sAnnées = s.substring(s.length() - 4, s.length());
			année = (new Integer(sAnnées)).intValue();
		} catch (Exception e) {
			throw new Exception("Date incorrecte. Veuillez vérifier l' "
					+ " année.");
		}
		if (Date.estValide(jour, mois, année)) {
			this.jour = jour;
			this.mois = mois;
			this.annee = année;
		} else {
			throw new Exception("Date incorrecte : " + jour + " / " + mois
					+ " / " + année);
		}
	}

	/**
	 * constructeur de la classe Date.
	 * 
	 * @param numJour
	 *            entier représentant le numéro du jour dans l'année. Attention,
	 *            1er janvier => 0
	 * @param année
	 *            entier représentant l'année.
	 * @throws Exception
	 *             si la date est invalide.
	 */
	public Date(int numJour, int année) throws Exception {
		boolean annéeBis = Date.anneeBissextile(année);
		int nbMaxJours = annéeBis ? 366 : 365;
		if (numJour < 0 || numJour >= nbMaxJours) {
			throw new Exception("Impossible de créer une date."
					+ " numJour incorrecte : " + numJour);
		}
		int nbJours = numJour + 1;
		int mois;
		for (mois = 1; mois < 13; mois++) {
			int nbJoursMois;
			if (mois == 2 && annéeBis)
				nbJoursMois = 29;
			else
				nbJoursMois = NB_JOURS_MOIS[mois];
			if (nbJours <= nbJoursMois)
				break;
			nbJours -= nbJoursMois;
		}
		if (Date.estValide(nbJours, mois, année)) {
			this.jour = nbJours;
			this.mois = mois;
			this.annee = année;
		} else {
			throw new Exception("Date incorrecte : " + nbJours + " / " + mois
					+ " / " + année);
		}
	}

	/**
	 * méthode de lecture de la date système.
	 * 
	 * @return elle retourne la date système.
	 */
	public static Date lireDateSystem() throws Exception {
		long time = System.currentTimeMillis();
		GregorianCalendar date = new GregorianCalendar(Locale.FRANCE);
		date.setTime(new java.util.Date(time));
		int jour = date.get(Calendar.DAY_OF_MONTH);
		int mois = date.get(Calendar.MONTH) + 1;
		int année = date.get(Calendar.YEAR);
		return new Date(jour, mois, année);
	}

	/**
	 * méthode de comparaison de 2 dates.
	 * 
	 * @param date2
	 *            la date à comparer à la date de l'objet courant.
	 * @return entier <0 si la date précède la date2. =0 si les dates sont
	 *         identiques. >0 si la date est postérieure à la date2.
	 */
	public int compareTo(Date date2) {
		if (this.annee < date2.annee)
			return -1;
		if (this.annee > date2.annee)
			return 1;
		// même année
		if (this.mois < date2.mois)
			return -1;
		if (this.mois > date2.mois)
			return 1;
		// même mois
		if (this.jour < date2.jour)
			return -1;
		if (this.jour > date2.jour)
			return 1;
		// même jour
		return 0;
	}

	/**
	 * méthode de formatage de la date en string.
	 * 
	 * @return elle retourne une string contenant la date (format jj/mm/aa).
	 */
	public String toString() {
		String sJours, sMois;
		if ((this.jour / 10) == 0)
			sJours = new String("0" + this.jour);
		else
			sJours = "" + this.jour;
		if ((this.mois / 10) == 0)
			sMois = "0" + this.mois;
		else
			sMois = "" + this.mois;
		return "" + sJours + "/" + sMois + "/" + annee;
	}

	/*
	 * méthode qui teste si année bissextile.
	 * 
	 * @param année entier indicant l'année à tester.
	 */
	public static boolean anneeBissextile(int année) {
		if (((année % 100) == 0) && ((année % 400) != 0))
			return false;
		if ((année % 4) == 0)
			return true;
		return false;
	}

	/*
	 * méthode qui teste la validité d'une date.
	 * 
	 * @param jour entier représentant le jour.
	 * 
	 * @param mois entier représentant le mois.
	 * 
	 * @param année entier représentant l'année.
	 * 
	 * @return boolean signale si la date est valide ou pas.
	 */
	public static boolean estValide(int jour, int mois, int année) {
		if ((année < ANNEE_MIN) || (année > ANNEE_MAX))
			return false;

		// validation des jours et des mois.
		switch (mois) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if ((jour < 1) || (jour > 31))
				return false;
			else
				return true;
		case 4:
		case 6:
		case 9:
		case 11:
			if ((jour < 1) || (jour > 30))
				return false;
			else
				return true;
		case 2: {
			if ((jour < 1) || (jour > 29))
				return false;
			if (jour == 29 && !Date.anneeBissextile(année))
				return false;
			return true;
		}
		default:
			return false;
		} // fin switch
	}

	/**
	 * méthode qui renvoie le numéro du jour dans l'année (en commençant à 0).
	 * ex: 1 janvier => 0, 31 janvier => 30. Ce nombre indique aussi le nombre
	 * de jours écoulés depuis le début de l'année.
	 * 
	 * @return int nombre de jours écoulés depuis le début de l'année.
	 */
	public int numJourDsAnnee() {
		int mois;
		int nbJours = 0;
		for (mois = 1; mois < this.getMois(); mois++) {
			if (mois == 2 && Date.anneeBissextile(this.annee)) {
				nbJours += 29;
			} else {
				nbJours += NB_JOURS_MOIS[mois];
			}
		}
		return nbJours + this.jour - 1;
	}

	public int getJour() {
		return this.jour;
	}

	public int getMois() {
		return this.mois;
	}

	public int getAnnee() {
		return this.annee;
	}

	/**
	 * Méthode qui renvoie un entier indiquant le numéro du jour de la semaine.
	 * 1 : lundi .. 7 : dimanche.
	 * 
	 * @return entier indiquant le numéro du jour de la semaine.
	 */
	public int jourDeLaSemaine() {
		int année1;
		int nbJours = 0;
		for (année1 = Date.ANNEE_MIN; année1 < this.annee; année1++) {
			if (Date.anneeBissextile(année1)) {
				nbJours += 366;
			} else {
				nbJours += 365;
			}
		}
		int mois;
		for (mois = 1; mois < this.getMois(); mois++) {
			if (mois == 2 && Date.anneeBissextile(this.annee)) {
				nbJours += 29;
			} else {
				nbJours += NB_JOURS_MOIS[mois];
			}
		}
		nbJours += this.jour;
		return nbJours % 7 + JOUR_REF - 1;
	}

} // fin de la classe