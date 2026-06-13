package ma.enset.springmvcproducts.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.springmvcproducts.entities.Product;
import ma.enset.springmvcproducts.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
  private ProductRepository productRepository;

  @GetMapping(path = "/user/index")
  public String products(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
    List<Product> products = productRepository.findByNameContains(keyword);
    model.addAttribute("listProducts", products);
    model.addAttribute("keyword", keyword);
    return "products";
  }

  @DeleteMapping("/admin/delete")
  public String delete(@RequestParam(name = "id") Long id) {
    productRepository.deleteById(id);
    return "redirect:/user/index";
  }

  @GetMapping("/admin/newProduct")
  public String newProduct(Model model) {
    model.addAttribute("product", new Product());
    return "newProduct";
  }

  @PostMapping(path = "/admin/save")
  public String save(Model model, @Valid Product product, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) return "newProduct";
    productRepository.save(product);
    return "redirect:/user/index";
  }

  @GetMapping("/admin/editProduct")
  public String editProduct(Model model, @RequestParam(name = "id") Long id) {
    Product product = productRepository.findById(id).orElse(null);
    if (product == null) throw new RuntimeException("Product not found");
    model.addAttribute("product", product);
    return "newProduct"; // On réutilise le même template car le paramètre id caché permet l'update JPA
  }
}