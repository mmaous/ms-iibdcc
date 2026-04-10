package partie1.presentation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import partie1.metier.IMetier;

public class PresentationSpringAnnotations {
  public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext("partie1.dao", "partie1.metier");
    IMetier metier = ctx.getBean(IMetier.class);
    System.out.println(metier.calcul());
  }
}