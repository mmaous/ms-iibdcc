package ma.enset.springmvcproducts;

import ma.enset.springmvcproducts.entities.Product;
import ma.enset.springmvcproducts.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringMvcProductsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringMvcProductsApplication.class, args);
  }

  @Bean
  CommandLineRunner start(ProductRepository productRepository) {
    return args -> {
      productRepository.save(Product.builder().name("Computer").price(4300).quantity(3).build());
      productRepository.save(Product.builder().name("Printer").price(1200).quantity(4).build());
      productRepository.save(Product.builder().name("Smartphone").price(3200).quantity(32).build());

      productRepository.findAll().forEach(p -> {
        System.out.println(p.getName());
      });
    };
  }

}
