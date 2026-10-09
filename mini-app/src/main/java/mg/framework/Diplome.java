package mg.framework;

/**
 * Remplie automatiquement par le framework à partir des champs
 * name="diplome.nom", name="diplome.annee".
 */
public class Diplome {

    private String nom;
    private int annee;

    public Diplome() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }
}
