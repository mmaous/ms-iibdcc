package ex2;

public class Program {

    public static void main(String[] args) {
            // Création des objets
            Voiture v = new Voiture("Clio 5", 18000, "Renault", 2023);
            Moto m = new Moto("MT-07", 7800, "Yamaha", 75);
            Avion a = new Avion("A320", 100000000, "Air France", 900);

            System.out.println("--- TEST VOITURE ---");
            v.emettreSon();
            v.afficherInformations();

            System.out.println("\n--- TEST MOTO ---");
            m.emettreSon();
            m.afficherInformations();

            System.out.println("\n--- TEST AVION ---");
            a.emettreSon();
            a.afficherInformations();
        }

}
