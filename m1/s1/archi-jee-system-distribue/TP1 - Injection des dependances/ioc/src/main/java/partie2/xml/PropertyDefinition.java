package partie2.xml;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyDefinition {
  @XmlAttribute public String name;
  @XmlAttribute public String ref;
}