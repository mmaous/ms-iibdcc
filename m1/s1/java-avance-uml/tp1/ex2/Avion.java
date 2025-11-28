package ex2;


public class Avion extends Vehicule {
    private String compagnie;
    private double vitesseMax;

    public Avion(String nom, double prix, String compagnie, double vitesseMax) {
        super(nom, prix);
        this.compagnie = compagnie;
        this.vitesseMax = vitesseMax;
    }

    @Override
    public void emettreSon() {
        System.out.println("L'avion fait un bruit de moteur puissant!");
    }

    @Override
    public void afficherInformations() {
        super.afficherInformations();
        System.out.println("Détails Avion : Compagnie " + compagnie + ", Vitesse Max " + vitesseMax + " km/h");
    }
}
