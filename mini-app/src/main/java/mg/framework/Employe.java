package mg.framework;

import mg.itu.frame.MapFile;

/**
 * Remplie automatiquement par le framework à partir des champs
 * name="employe.nom", name="employe.prenom", name="employe.age".
 * SPRINT 8 : rectoCin et versoCin sont remplis à partir des champs
 * input type="file" name="rectoCin" / name="versoCin".
 */
public class Employe {

    private String nom;
    private String prenom;
    private int age;
    private MapFile rectoCin;
    private MapFile versoCin;

    public Employe() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MapFile getRectoCin() {
        return rectoCin;
    }

    public void setRectoCin(MapFile rectoCin) {
        this.rectoCin = rectoCin;
    }

    public MapFile getVersoCin() {
        return versoCin;
    }

    public void setVersoCin(MapFile versoCin) {
        this.versoCin = versoCin;
    }
}
