package GestionSalaries;

public class Salarie {
    private int matricule;
    private String nom;
    private String prenom;
    private double salaire;

    public Salarie(int matricule, String nom, String prenom, double salaire) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.salaire = salaire;
    }

    // gettrs
    public double getSalaire() {
        return salaire;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return " - Mat: " + matricule + " | " + nom.toUpperCase() + " " + prenom + " | Salaire: " + salaire + " DH";
    }
}
