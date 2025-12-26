import java.util.ArrayList;

public class Application {
    private static ArrayList<CompteBancaire> comptes = new ArrayList<>();

    public static CompteBancaire trouverCompte(String num) throws CompteInexistantException {
        for (CompteBancaire c : comptes) {
            if (c.getNumCompte().equals(num)) return c;
        }
        throw new CompteInexistantException("Compte " + num + " introuvable");
    }

    public static void transferer(String de, String a, double m)
            throws FondsInsuffisantsException, CompteInexistantException {
        CompteBancaire source = trouverCompte(de);
        CompteBancaire cible = trouverCompte(a);
        source.retirer(m);
        cible.deposer(m);
    }

    public static void main(String[] args) {
        try {
            comptes.add(new CompteCourant("CA34421", "Ali", 1000, 200));
            comptes.add(new CompteEpargne("EER2331", "Ilham", 5000));

            transferer("CA34421", "EER2331", 500);
            trouverCompte("CA34421").afficherSolde();

            // Test exception
            transferer("CA34421", "Inconnu", 100);
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}

/*
% java Application                                                                                                                            tp3-Exceptions/ex2 (main ⚡) maMac
Compte CA34421 | Solde: 500.0
Erreur: Compte Inconnu introuvable
*/
