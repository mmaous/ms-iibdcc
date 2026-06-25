// 1. Définition de la classe de base (Personne)
class Personne {
  String nom;
  int age;

  // Constructeur
  Personne(this.nom, this.age);

  // Méthode
  void sePresenter() {
    print("Bonjour, je m'appelle $nom et j'ai $age ans.");
  }
}

// 2. Héritage : Le Salarie est une Personne
class Salarie extends Personne {
  double salaire; // Attribut spécifique

  // Constructeur faisant appel à la classe parente (super)
  Salarie(String nom, int age, this.salaire) : super(nom, age);

  // Redéfinition (Polymorphisme)
  @override
  void sePresenter() {
    print("Bonjour, Je suis un Salarié, je m'appelle $nom, j'ai $age ans et mon salaire est de $salaire €.");
  }

  // Méthode spécifique
  void augmentation(double montant) {
    salaire += montant;
    print("Nouveau salaire pour $nom : $salaire €.");
  }
}

// Main method to run Example 1
void main() {
  print("--- RUNNING EXEMPLE 1: PERSONNE & SALARIE ---");
  // Création d'un objet Personne
  Personne p1 = Personne("Alice", 30);
  p1.sePresenter();

  // Création d'un objet Salarie
  Salarie s1 = Salarie("Bob", 35, 5000);
  s1.sePresenter();
  s1.augmentation(300);
  print("");
}