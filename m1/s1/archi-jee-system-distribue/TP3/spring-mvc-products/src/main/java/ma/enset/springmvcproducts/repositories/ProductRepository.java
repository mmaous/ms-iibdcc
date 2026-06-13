package ma.enset.springmvcproducts.repositories;

import ma.enset.springmvcproducts.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByNameContains(String keyword); // doesnt ignore case by dfault
}