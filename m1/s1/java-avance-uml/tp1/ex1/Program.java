package ex1;

public class Program {
    public static void main(String[] args) {
        // 1. Instancier un adhérent
        Adherent unAdherent = new Adherent("Ahmed", "Karim", "k.ahmed@email.com", "0612345678", 22, 101);

        // 2. Instancier un auteur
        Auteur unAuteur = new Auteur("Sanae", "Ali", "a.sanae@email.com", "0000000000", 46, 550);

        // 3. Instancier un livre écrit par cet auteur
        Livre unLivre = new Livre(978207, "L'Étranger", unAuteur);

        // 4. Affichage
        System.out.println("--- INFOS ADHÉRENT ---");
        System.out.println(unAdherent.toString());

        System.out.println("\n--- INFOS LIVRE ---");
        System.out.println(unLivre.toString());
    }
}
