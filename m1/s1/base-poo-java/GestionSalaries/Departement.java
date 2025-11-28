package GestionSalaries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Departement {
    private int id;
    private String nom;
    private List<Salarie> salaries;

    public Departement(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.salaries = new ArrayList<>();
    }

    // ajouter un salarié au département
    public void ajouterSalarie(Salarie s) {
        salaries.add(s);
    }

    // calculer la masse salariale
    public double getMasseSalariale() {
        double total = 0;
        for (Salarie s : salaries) {
            total += s.getSalaire();
        }
        return total;
    }

    // affiche detail Nom, Liste triée ... etc
    public void afficherDetails() {
        System.out.println("\n=== DÉPARTEMENT : " + nom + " (ID: " + id + ") ===");
        Collections.sort(salaries, new Comparator<Salarie>() {
            @Override
            public int compare(Salarie s1, Salarie s2) {
                return Double.compare(s1.getSalaire(), s2.getSalaire());
            }
        });

        if (salaries.isEmpty()) {
            System.out.println("Aucun salarié dans ce département.");
        } else {
            for (Salarie s : salaries) {
                System.out.println(s.toString());
            }
        }

        System.out.println("--------------------------------");
        System.out.println("MASSE SALARIALE TOTALE : " + getMasseSalariale() + " DH");
    }
}
