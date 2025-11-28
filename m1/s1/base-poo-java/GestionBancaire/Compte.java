package GestionBancaire;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compte {
    private String numero;
    private Date dateCreation;
    private Client client;
    private List<Operation> operations;

    public Compte(String numero, Client client) {
        this.numero = numero;
        this.client = client;
        this.dateCreation = new Date(); // Date actuelle
        this.operations = new ArrayList<>(); // Initialisation de la liste vide
    }

    public void ajouterOperation(Operation op) {
        operations.add(op);
    }

    // Méthode demandée : Recalcule le solde à partir de la liste
    public double getSolde() {
        double soldeCalcul = 0.0;
        for (Operation op : operations) {
            if (op.getType().equals("VERS")) {
                soldeCalcul += op.getMontant();
            } else if (op.getType().equals("RETR")) {
                soldeCalcul -= op.getMontant();
            }
        }
        return soldeCalcul;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void afficherDetails() {
        System.out.println("\n=== DÉTAILS DU COMPTE ===");
        System.out.println("Numéro : " + numero);
        System.out.println("Date Création : " + dateCreation);
        System.out.println("Propriétaire : " + client.toString());
        System.out.println("--- Opérations ---");

        for (Operation op : operations) {
            System.out.println(op.toString());
        }

        System.out.println("------------------");
        System.out.println("SOLDE FINAL : " + getSolde() + " DH");
    }
}
