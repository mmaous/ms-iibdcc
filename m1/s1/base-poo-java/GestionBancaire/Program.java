package GestionBancaire;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Program {
    public static void main(String[] args) {
        // 1. Créer un client
        Client c1 = new Client("AB123456", "Tazi", "Karim", "0661122334");

        // 2. Créer un compte bancaire associé à ce client
        Compte compte = new Compte("FR76 1234 5678", c1);

        // 3. Ajouter plusieurs opérations (Astuce: on modifie un peu le temps pour voir l'ordre)
        // Note: new Date() prend le temps actuel. Pour simuler des dates différentes, on peut utiliser des délais ou des timestamps.
        // Ici, j'ajoute les opérations dans le désordre pour tester le tri plus tard.

        long tempsActuel = System.currentTimeMillis();

        // Versement il y a 2 heures
        compte.ajouterOperation(new Operation(new Date(tempsActuel - 7200000), 5000, "VERS"));

        // Retrait il y a 1 heure
        compte.ajouterOperation(new Operation(new Date(tempsActuel - 3600000), 200, "RETR"));

        // Versement maintenant
        compte.ajouterOperation(new Operation(new Date(tempsActuel), 1500, "VERS"));

        System.out.println("--- État avant le tri (Ordre d'insertion) ---");
        compte.afficherDetails();

        // 4. Trier les opérations par date croissante
        // On récupère la liste et on utilise Collections.sort avec un Comparateur
        Collections.sort(compte.getOperations(), new Comparator<Operation>() {
            @Override
            public int compare(Operation o1, Operation o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        // 5. Affichage final après tri
        System.out.println("\n--- État après le tri par date ---");
        compte.afficherDetails();
    }
}
