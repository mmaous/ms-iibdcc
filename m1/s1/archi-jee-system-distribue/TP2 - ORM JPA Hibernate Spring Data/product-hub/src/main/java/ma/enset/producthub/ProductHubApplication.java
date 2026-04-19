package ma.enset.producthub;

import ma.enset.producthub.entity.Product;
import ma.enset.producthub.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ProductHubApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductHubApplication.class, args);
  }


  @Bean
  CommandLineRunner start(ProductRepository productRepository) {
    return args -> {
      //    - Ajouter des produits
      productRepository.save(new Product(null, "Computer", 4300, 3));
      productRepository.save(new Product(null, "Printer", 1200, 4));
      productRepository.save(new Product(null, "Smartphone", 3200, 32));

      //    - Consulter tous les produits
      List<Product> products = productRepository.findAll();
      products.forEach(p -> System.out.println(p.getName()));

      //    - Consulter un produit
      Product p = productRepository.findById(1L).orElse(null);
      System.out.println("Produit 1 : " + (p != null ? p.getName() : "Inconnu"));
      //    - Chercher des produits
      productRepository.findByNameContains("C").forEach(prod -> System.out.println(prod.getName()));

      //    - Mettre à jour un produit
      if (p != null) {
        p.setPrice(4500);
        productRepository.save(p);
      }

      //    - supprimer un produit
      productRepository.deleteById(2L);
    };
  }

}
