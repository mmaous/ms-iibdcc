package partie2.framework;

import jakarta.xml.bind.*;
import partie2.annotations.*;
import partie2.xml.*;
import java.io.File;
import java.lang.reflect.*;
import java.util.*;

public class MySpringContext {
  private Map<String, Object> instances = new HashMap<>();

  // Version XML : Utilise JAXB pour lire la configuration [cite: 268]
  public void loadXmlConfig(String path) throws Exception {
    JAXBContext context = JAXBContext.newInstance(BeansConfig.class);
    Unmarshaller unmarshaller = context.createUnmarshaller();
    BeansConfig config = (BeansConfig) unmarshaller.unmarshal(new File(path));

    for (BeanDefinition b : config.beans) {
      instances.put(b.id, Class.forName(b.className).getDeclaredConstructor().newInstance());
    }

    for (BeanDefinition b : config.beans) {
      Object obj = instances.get(b.id);
      if (b.properties != null) {
        for (PropertyDefinition p : b.properties) {
          String setterName = "set" + p.name.substring(0, 1).toUpperCase() + p.name.substring(1);
          Object dep = instances.get(p.ref);
          Method method = obj.getClass().getMethod(setterName, dep.getClass().getInterfaces()[0]);
          method.invoke(obj, dep); // Injection par Setter [cite: 259]
        }
      }
    }
  }

  // Version Annotations : Scanne les classes et injecte via @MyInject [cite: 296]
  public void scanAndInject(Class<?>... classes) throws Exception {
    for (Class<?> cl : classes) {
      if (cl.isAnnotationPresent(MyComponent.class)) {
        instances.put(cl.getSimpleName(), cl.getDeclaredConstructor().newInstance());
      }
    }

    for (Object obj : instances.values()) {
      for (Field f : obj.getClass().getDeclaredFields()) {
        if (f.isAnnotationPresent(MyInject.class)) {
          f.setAccessible(true);
          f.set(obj, findInstance(f.getType())); // Injection par Attribut (Field)
        }
      }
    }
  }

  private Object findInstance(Class<?> type) {
    return instances.values().stream().filter(type::isInstance).findFirst().orElse(null);
  }

  public <T> T getBean(Class<T> type) {
    return (T) findInstance(type);
  }
}