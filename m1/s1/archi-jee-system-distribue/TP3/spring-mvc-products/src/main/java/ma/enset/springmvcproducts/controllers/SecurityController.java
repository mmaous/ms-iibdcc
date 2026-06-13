package ma.enset.springmvcproducts.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
  @GetMapping("/notAuthorized")
  public String notAuthorized() {
    return "notAuthorized";
  }

  @GetMapping("/")
  public String home() {
    return "redirect:/user/index";
  }
}