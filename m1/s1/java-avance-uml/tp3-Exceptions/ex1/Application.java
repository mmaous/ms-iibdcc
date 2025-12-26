public class Application {
    public static void main(String[] args) {
        try {
            EntierNaturel n = new EntierNaturel(2);
            n.decrementer(); // 1
            n.decrementer(); // 0
            n.decrementer(); // Lance l'exception
        } catch (NombreNegatifException e) {
            System.out.println("Erreur : " + e.getMessage());
            System.out.println("Valeur ayant cause l erreur: " + e.getValeurErronee());
        }
    }
}

/* output
% javac *.java                                                                                                                                tp3-Exceptions/ex1 (main ⚡) maMac
% java Application                                                                                                                            tp3-Exceptions/ex1 (main ⚡) maMac
Erreur : Valeur negative non autorisee: -1
Valeur ayant cause l erreur: -1
*/
