package GestionSalaries;

public class Program {
    public static void main(String[] args) {
        // 1. Créer deux départements
        Departement deptIT = new Departement(1, "Informatique & Tech");
        Departement deptRH = new Departement(2, "Ressources Humaines");

        // 2. Créer plusieurs salariés
        Salarie s1 = new Salarie(101, "Alami", "Ahmed", 8500);
        Salarie s2 = new Salarie(102, "Bennani", "Sara", 12000); // Salaire élevé
        Salarie s3 = new Salarie(103, "Chraibi", "Karim", 7000); // Salaire plus bas

        Salarie s4 = new Salarie(201, "Daoudi", "Mouna", 9000);
        Salarie s5 = new Salarie(202, "El Fassi", "Youssef", 8800);

        // 3. Affecter les salariés aux départements
        // Département IT
        deptIT.ajouterSalarie(s1); // 8500
        deptIT.ajouterSalarie(s2); // 12000
        deptIT.ajouterSalarie(s3); // 7000

        // Département RH
        deptRH.ajouterSalarie(s4);
        deptRH.ajouterSalarie(s5);

        // 4. Afficher les détails (Test du tri et du calcul total)
        // Pour IT, on s'attend à voir l'ordre : Karim (7000), Ahmed (8500), Sara (12000)
        deptIT.afficherDetails();

        deptRH.afficherDetails();
    }
}
