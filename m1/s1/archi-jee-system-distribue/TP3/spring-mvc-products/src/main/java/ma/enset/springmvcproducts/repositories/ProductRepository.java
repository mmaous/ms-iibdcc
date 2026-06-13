package ma.enset.springmvcproducts.repositories;

import ma.enset.springmvcproducts.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}