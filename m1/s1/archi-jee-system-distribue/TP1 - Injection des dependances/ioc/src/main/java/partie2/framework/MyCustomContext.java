package partie2.framework;

import partie2.annotations.MyComponent;
import partie2.annotations.MyInject;
import java.lang.reflect.*;
import java.util.*;

public class MyCustomContext {
  private Map<Class<?>, Object> beans = new HashMap<>();

  public MyCustomContext(Class<?>... componentClasses) throws Exception {
    for (Class<?> clazz : componentClasses) {
      if (clazz.isAnnotationPresent(MyComponent.class)) {
        beans.put(clazz, clazz.getDeclaredConstructor().newInstance());
      }
    }
    // dep inejection
    for (Object bean : beans.values()) {
      injectDependencies(bean);
    }
  }

  private void injectDependencies(Object bean) throws Exception {
    Class<?> clazz = bean.getClass();

    // field injection
    for (Field field : clazz.getDeclaredFields()) {
      if (field.isAnnotationPresent(MyInject.class)) {
        Object dependency = findBeanByType(field.getType());
        field.setAccessible(true);
        field.set(bean, dependency);
      }
    }

    // setter injection
    for (Method method : clazz.getDeclaredMethods()) {
      if (method.isAnnotationPresent(MyInject.class) && method.getName().startsWith("set")) {
        Object dependency = findBeanByType(method.getParameterTypes()[0]);
        method.invoke(bean, dependency);
      }
    }
  }

  private Object findBeanByType(Class<?> type) {
    return beans.values().stream()
        .filter(type::isInstance)
        .findFirst()
        .orElse(null);
  }

  public <T> T getBean(Class<T> type) {
    return type.cast(findBeanByType(type));
  }
}
