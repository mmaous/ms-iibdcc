package partie1.dao;
import org.springframework.stereotype.Component;

@Component("d")
public class DaoImpl implements IDao {
  public double getData() {
    return 5;
  }
}