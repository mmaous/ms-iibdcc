package partie2.presentation;

import partie1.metier.IMetier;
import partie2.framework.MySpringContext;

public class TestCustomFramework {
  public static void main(String[] args) throws Exception {
    MySpringContext ctx = new MySpringContext();

    // Test de la version XML
    System.out.println("--- Test Mini-Framework XML ---");
    ctx.loadXmlConfig("src/main/resources/custom-ioc.xml");
    IMetier metierXml = ctx.getBean(IMetier.class);
    System.out.println("Résultat XML: " + metierXml.calcul());

  }
}