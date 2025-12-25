import java.util.ArrayList;
import java.util.List;

public class MetierProduitImpl implements IMetier<Produit> {

    private List<Produit> produits = new ArrayList<>();

    @Override
    public void add(Produit o) {
        produits.add(o);
    }

    @Override
    public List<Produit> getAll() {
        return produits;
    }

    @Override
    public Produit findById(long id) {
        for (Produit p : produits) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    @Override
    public void delete(long id) {
        Produit p = findById(id);
        if (p != null) {
            produits.remove(p);
            System.out.println("Produit supprimee avec succes!!!");
        } else {
            System.out.println("Produit introuvable!!!");
        }
    }
}
