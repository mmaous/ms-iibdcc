package partie1.presentation;


import partie1.dao.DaoImpl;
import partie1.metier.MetierImpl;

public class PresentationStatique {
  public static void main(String[] args) {
    DaoImpl dao = new DaoImpl();
    MetierImpl metier = new MetierImpl();
    metier.setDao(dao);
    System.out.println(metier.calcul());
  }
}