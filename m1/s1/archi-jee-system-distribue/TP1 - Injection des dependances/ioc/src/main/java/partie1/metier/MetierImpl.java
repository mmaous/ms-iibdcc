package partie1.metier;

import partie1.dao.IDao;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class MetierImpl implements IMetier {

  @Autowired
  private IDao dao;

  public double calcul() {
    double nb = dao.getData();
    return 2 * nb;
  }

  public void setDao(IDao dao) {
    this.dao = dao;
  }
}