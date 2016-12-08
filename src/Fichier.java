
import java.io.*;

/**
 * Classe permettant d'écrire et lire dans un fichier. Elle permet d'écrire les
 * différents types primitifs (byte, short, ...) + des objets ( String, tableau
 * ou tout autre objet qui implémente l'interface java.io.Serializable). Les
 * méthodes de cette classe peuvent lancer des exceptions. (fin de fichier,
 * erreur de lecture ou écriture, etc.) Pour créer un fichier : il faut appeler
 * le constructeur en lui passant en paramètre une String spécifiant le chemin
 * d'accès et le nom du fichier. Ensuite, il faut l'ouvrir en écriture. Enfin,
 * on peut y écrire les données. Lors de l'écriture dans un fichier existant, le
 * contenu est écrasé et remplacé par la(es) donnée(s) écrite(s). Pour lire un
 * fichier existant, il faut créer un objet de la classe Fichier, en lui
 * renseignant le chemin d'accès et le nom du fichier. Ensuite, il faut l'ouvrir
 * en lecture. Enfin, il peut être lu, enregistrement par enregistrement. Si la
 * fin du fichier est rencontrée, une exception de type "java.io.EOFException"
 * est lancée. Le fichier doit être fermé en utilisant la méthode "fermer()".
 * 
 * @author O.Legrand
 * @version 1.0
 */
public class Fichier {

	private ObjectOutputStream out; // objet servant à l'écriture dans le stream
	private ObjectInputStream in; // objet servant à la lecture dans le stream
	private String nomFichier; // contient le nom complet du fichier. (
								// path+nom)

	/**
	 * constructeur de la classe Fichier.
	 * 
	 * @param nomFichier
	 *            String spécifiant le chemin d'accès et le nom du fichier.
	 *            Exemple : "C:/java/dossier/donnees.dat" ('/' obligatoire et
	 *            non '\')
	 */
	public Fichier(String nomFichier) {
		this.nomFichier = nomFichier;
		in = null;
		out = null;
	}

	/**
	 * ouverture du fichier en écriture
	 * 
	 * @throws java.io.IOException
	 *             si erreur lors de l'ouverture en écriture.
	 */
	public void ouvrirEnEcriture() throws java.io.IOException {
		if (out != null || in != null) {
			throw new java.io.IOException("fichier déjà ouvert");
		}
		out = new ObjectOutputStream(new FileOutputStream(nomFichier));
	}

	/**
	 * ouverture du fichier en lecture
	 * 
	 * @throws java.io.IOException
	 *             si erreur lors de l'ouverture en lecture.
	 */
	public void ouvrirEnLecture() throws java.io.IOException {
		if (out != null || in != null) {
			throw new java.io.IOException("fichier déjà ouvert");
		}
		in = new ObjectInputStream(new FileInputStream(nomFichier));
	}

	/**
	 * écriture d'un réel de type float dans le fichier.
	 * 
	 * @param réel
	 *            le nombre réel à écrire dans le fichier.
	 * @throws java.io.IOException
	 *             si erreur lors de l'écriture.
	 */
	public void ecrireFloat(float réel) throws java.io.IOException {
		out.writeFloat(réel);
	}

	/**
	 * lecture d'un réel de type float dans le fichier.
	 * 
	 * @return le nombre réel lu dans le fichier.
	 * @throws java.io.EOFException
	 *             si fin de fichier rencontrée.
	 * @throws java.io.IOException
	 *             si erreur lors de la lecture.
	 */
	public float lireFloat() throws java.io.EOFException, java.io.IOException {
		return in.readFloat();
	}

	/**
	 * écriture d'un réel de type double dans le fichier.
	 * 
	 * @param réel
	 *            le nombre réel à écrire dans le fichier.
	 * @throws java.io.IOException
	 *             si erreur lors de l'écriture.
	 */
	public void ecrireDouble(double réel) throws java.io.IOException {
		out.writeDouble(réel);
	}

	/**
	 * lecture d'un réel de type double dans le fichier.
	 * 
	 * @return le nombre réel lu dans le fichier.
	 * @throws java.io.EOFException
	 *             si fin de fichier rencontrée.
	 * @throws java.io.IOException
	 *             si erreur lors de la lecture.
	 */
	public double lireDouble() throws java.io.EOFException, java.io.IOException {
		return in.readDouble();
	}

	/**
	 * écriture d'un entier de type byte dans le fichier.
	 * 
	 * @param entier
	 *            le nombre entier à écrire dans le fichier.
	 * @throws java.io.IOException
	 *             si erreur lors de l'écriture.
	 */
	public void ecrireByte(byte entier) throws java.io.IOException {
		out.writeByte(entier);
	}

	/**
	 * lecture d'un entier de type byte dans le fichier.
	 * 
	 * @return le nombre entier lu dans le fichier.
	 * @throws java.io.EOFException
	 *             si fin de fichier rencontrée.
	 * @throws java.io.IOException
	 *             si erreur lors de la lecture.
	 */
	public byte lireByte() throws java.io.EOFException, java.io.IOException {
		return in.readByte();
	}

	/**
	 * écriture d'un entier de type short dans le fichier.
	 * 
	 * @param entier
	 *            le nombre entier à écrire dans le fichier.
	 * @throws java.io.IOException
	 *             si erreur lors de l'écriture.
	 */
	public void ecrireShort(short entier) throws java.io.IOException {
		out.writeShort(entier);
	}

	/**
	 * lecture d'un entier de type short dans le fichier.
	 * 
	 * @return le nombre entier lu dans le fichier.
	 * @throws java.io.EOFException
	 *             si fin de fichier rencontrée.
	 * @throws java.io.IOException
	 *             si erreur lors de la lecture.
	 */
	public short lireShort() throws java.io.EOFException, java.io.IOException {
		return in.readShort();
	}

	/**
	 * écriture d'un entier de type int dans le fichier.
	 * 
	 * @param entier
	 *            le nombre entier à écrire dans le fichier.
	 * @throws java.io.IOException
	 *             si erreur lors de l'écriture.
	 */
	public void ecrireInt(int entier) throws java.io.IOException {
		out.writeInt(entier);
	}

	/**
	 * lecture d'un entier de type int dans le fichier.
	 * 
	 * @return le nombre entier lu dans le fichier.
	 * @throws java.io.EOFException
	 *             si fin de fichier rencontrée.
	 * @throws java.io.IOException
	 *             si erreur lors de la lecture.
	 */
	public int lireInt() throws java.io.EOFException, java.io.IOException {
		return in.readInt();
	}

	/**
	 * écriture d'un entier de type long dans le fichier.
	 * 
	 * @param entier
	 *            le nombre entier à écrire dans le fichier.
	 * @throws java.io.IOException
	 *             si erreur lors de l'écriture.
	 */
	public void ecrireLong(long entier) throws java.io.IOException {
		out.writeLong(entier);
	}

	/**
	 * lecture d'un entier de type long dans le fichier.
	 * 
	 * @return le nombre entier lu dans le fichier.
	 * @throws java.io.EOFException
	 *             si fin de fichier rencontrée.
	 * @throws java.io.IOException
	 *             si erreur lors de la lecture.
	 */
	public long lireLong() throws java.io.EOFException, java.io.IOException {
		return in.readLong();
	}

	/**
	 * écriture d'un booléen dans le fichier.
	 * 
	 * @param booléen
	 *            le booléen à écrire dans le fichier.
	 * @throws java.io.IOException
	 *             si erreur lors de l'écriture.
	 */
	public void ecrireBoolean(boolean booléen) throws java.io.IOException {
		out.writeBoolean(booléen);
	}

	/**
	 * lecture d'un booléen dans le fichier.
	 * 
	 * @return le booléen lu dans le fichier.
	 * @throws java.io.EOFException
	 *             si fin de fichier rencontrée.
	 * @throws java.io.IOException
	 *             si erreur lors de la lecture.
	 */
	public boolean lireBoolean() throws java.io.EOFException,
			java.io.IOException {
		return in.readBoolean();
	}

	/**
	 * écriture d'un objet dans le fichier. Rem.: la classe de cet objet doit
	 * implémenter l'interface java.io.Serializable (cette interface ne possède
	 * aucun champ, ni aucune méthode)
	 * 
	 * @param objet
	 *            l'objet à écrire dans le fichier.
	 * @throws java.io.IOException
	 *             si erreur lors de l'écriture.
	 */
	public void ecrireObjet(Object objet) throws java.io.IOException {
		out.writeObject(objet);
	}

	/**
	 * lecture d'un objet dans le fichier. Exemple : Etudiant étudiant =
	 * (Etudiant) fichier.lireObjet();
	 * 
	 * @return l'objet lu dans le fichier.
	 * @throws java.io.EOFException
	 *             si fin de fichier rencontrée.
	 * @throws java.io.IOException
	 *             si erreur lors de la lecture.
	 * @throws Exception
	 *             si autre erreur.
	 */
	public Object lireObjet() throws java.io.EOFException, java.io.IOException,
			Exception {
		return in.readObject();
	}

	/**
	 * écriture d'un caractère dans le fichier.
	 * 
	 * @param caractère
	 *            le caractère à écrire dans le fichier.
	 * @throws java.io.IOException
	 *             si erreur lors de l'écriture.
	 */
	public void ecrireChar(char caractère) throws java.io.IOException {
		out.writeChar(caractère);
	}

	/**
	 * lecture d'un caractère dans le fichier.
	 * 
	 * @return le caractère lu dans le fichier.
	 * @throws java.io.EOFException
	 *             si fin de fichier rencontrée.
	 * @throws java.io.IOException
	 *             si erreur lors de la lecture.
	 */
	public char lireChar() throws java.io.EOFException, java.io.IOException {
		return in.readChar();
	}

	/**
	 * fermeture du fichier.
	 * 
	 * @throws java.io.IOException
	 *             si erreur lors de la fermeture.
	 */
	public void fermer() throws java.io.IOException {
		if (out != null)
			fermerOut();
		if (in != null)
			fermerIn();
	}

	/**
	 * suppression du fichier.
	 * 
	 * @throws java.io.IOException
	 *             si erreur lors de la suppression.
	 */
	public void supprimer() throws java.io.IOException {
		fermer();
		File file = null;
		try {
			file = new File(nomFichier);
			file.delete();
		} catch (NullPointerException ex) {
			/*
			 * afin que toutes les méthodes renvoient une exception du même type
			 * : java.io.IOException.
			 */
			throw new java.io.IOException(ex.getMessage());
		}
	}

	// fermeture de l'input stream.
	private void fermerIn() throws java.io.IOException {
		in.close();
		in = null;
	}

	// fermeture de l'output stream
	private void fermerOut() throws java.io.IOException {
		out.flush();
		out.close();
		out = null;
	}

} // fin de la classe