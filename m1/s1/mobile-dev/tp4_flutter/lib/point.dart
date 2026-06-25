import 'dart:math' as math;

class Point {
  num x, y;

  // Un constructeur avec deux paramètres obligatoires
  Point(this.x, this.y);

  // Un constructeur nommé avec une liste d'initialisation
  Point.origin():
        x = 0,
        y = 0;

  void affiche() {
    print("[${x},${y}]");
  }

  num distance(Point p) {
    var dx = x - p.x;
    var dy = y - p.y;
    return math.sqrt(dx * dx + dy * dy);
  }

  // Surcharge d'opérateur
  Point operator +(Point p) => Point(x + p.x, y + p.y);
}

// Main method to run Example 2
void main() {
  print("--- RUNNING EXEMPLE 2: POINT GEOMETRIQUE ---");
  var p1 = Point(2, 5);
  print("p1.x = ${p1.x}");
  print("p1.y = ${p1.y}");
  p1.affiche();

  var p2 = Point.origin();
  p2.affiche();

  var distance = p1.distance(p2);
  print("Distance between p1 and p2: $distance");
  print("");
}