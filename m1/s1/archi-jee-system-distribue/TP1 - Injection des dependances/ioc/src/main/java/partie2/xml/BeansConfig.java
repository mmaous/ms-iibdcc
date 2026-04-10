package partie2.xml;
import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "beans")
@XmlAccessorType(XmlAccessType.FIELD)
public class BeansConfig {
  @XmlElement(name = "bean") public List<BeanDefinition> beans;
}