package GestionBancaire;

public class Client {
    private String cin;
    private String nom;
    private String prenom;
    private String telephone;

    public Client(String cin, String nom, String prenom, String telephone) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    // Getters
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }

    @Override
    public String toString() {
        return "Client [CIN: " + cin + ", Nom: " + nom + ", Prénom: " + prenom + ", Tél: " + telephone + "]";
    }
}
