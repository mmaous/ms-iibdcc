import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        MetierProduitImpl metier = new MetierProduitImpl();
        Scanner scanner = new Scanner(System.in);
        int choix = 0;

        while (choix != 5) {
            System.out.println("\n--- MENU GESTION PRODUITS ---");
            System.out.println("1. Afficher la liste des produits");
            System.out.println("2. Rechercher un produit par son id");
            System.out.println("3. Ajouter un nouveau produit");
            System.out.println("4. Supprimer un produit par id");
            System.out.println("5. Quitter");
            System.out.print("Votre choix : ");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("Liste des produits :");
                    for (Produit p : metier.getAll()) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.print("Entrez l ID a rechercher : ");
                    long idRecherche = scanner.nextLong();
                    Produit pFound = metier.findById(idRecherche);
                    System.out.println(
                        pFound != null ? pFound : "Aucun produit trouve"
                    );
                    break;
                case 3:
                    System.out.print("ID : ");
                    long id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Marque : ");
                    String marque = scanner.nextLine();
                    System.out.print("Prix : ");
                    double prix = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Description : ");
                    String desc = scanner.nextLine();
                    System.out.print("Stock : ");
                    int stock = scanner.nextInt();

                    metier.add(new Produit(id, nom, marque, prix, desc, stock));
                    System.out.println("Produit ajoutee!");
                    break;
                case 4:
                    System.out.print("Entrez l ID à supprimer : ");
                    long idDel = scanner.nextLong();
                    metier.delete(idDel);
                    break;
                case 5:
                    System.out.println("Fin du programme.");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }
}
