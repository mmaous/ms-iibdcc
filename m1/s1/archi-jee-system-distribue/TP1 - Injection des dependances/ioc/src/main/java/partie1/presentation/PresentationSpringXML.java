package partie1.presentation;

import partie1.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresentationSpringXML {

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("spring-ioc.xml");
    IMetier metier = (IMetier) context.getBean("metier");
    System.out.println(metier.calcul());
  }
}
