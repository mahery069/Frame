package mg.itu.frame;

/**
 * Fichier envoyé depuis un formulaire (input type="file"). Le framework crée
 * l'instance et la remplit à partir de la partie multipart correspondante.
 *
 * Exemple : "cin_recto.png" -> nom = "cin_recto", extension = "png",
 * binaire = contenu du fichier.
 *
 * Le stockage (dossier sur le serveur, BLOB en base...) reste à la charge de
 * l'application.
 */
public class MapFile {

    private String nom;
    private String extension;
    private byte[] binaire;

    public MapFile() {
    }

    public MapFile(String nom, String extension, byte[] binaire) {
        this.nom = nom;
        this.extension = extension;
        this.binaire = binaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getBinaire() {
        return binaire;
    }

    public void setBinaire(byte[] binaire) {
        this.binaire = binaire;
    }

    /**
     * Nom d'origine du fichier : nom + "." + extension.
     */
    public String getNomComplet() {
        return (extension == null || extension.isEmpty()) ? nom : nom + "." + extension;
    }

    /**
     * Taille du fichier en octets.
     */
    public int getTaille() {
        return binaire == null ? 0 : binaire.length;
    }
}
