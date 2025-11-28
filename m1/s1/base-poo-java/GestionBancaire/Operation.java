package GestionBancaire;

import java.util.Date;

public class Operation {
    private Date date;
    private double montant;
    private String type; // "VERS" ou "RETR"

    public Operation(Date date, double montant, String type) {
        this.date = date;
        this.montant = montant;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public double getMontant() {
        return montant;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        // Affiche la date, le type et le montant
        return " -> " + date.toString() + " | " + type + " : " + montant + " DH";
    }
}
