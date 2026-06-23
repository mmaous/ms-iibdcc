package ma.enset.digitalbanking;

import ma.enset.digitalbanking.entities.AppUser;
import ma.enset.digitalbanking.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DigitalBankingBeApplication {

  public static void main(String[] args) {
    SpringApplication.run(DigitalBankingBeApplication.class, args);
  }

  @Bean
  CommandLineRunner startSecurity(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
    return args -> {
      if (appUserRepository.findByUsername("admin").isEmpty()) {
        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("1234"));
        admin.setRoles("ADMIN,USER");
        appUserRepository.save(admin);
      }
    };
  }
}
