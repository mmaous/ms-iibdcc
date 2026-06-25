class Rectangle {
  int longueur;
  int largeur;

  Rectangle(this.longueur, this.largeur);

  // méthode qui calcule la surface du rectangle
  int surface() {
    return this.longueur * this.largeur;
  }

  // méthode qui calcule le périmètre du rectangle
  int perimetre() {
    return 2 * (this.longueur + this.largeur);
  }
}

// Main method to run Example 3
void main() {
  print("--- RUNNING EXEMPLE 3: RECTANGLE ---");
  var R = Rectangle(10, 5);
  print("La surface du rectangle R est : ${R.surface()}");
  print("Le périmètre du rectangle R est : ${R.perimetre()}");
  print("");
}