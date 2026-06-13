package ma.enset.springmvcproducts.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    return new InMemoryUserDetailsManager(
        User.withUsername("user").password(passwordEncoder().encode("1234")).roles("USER").build(),
        User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("USER", "ADMIN").build()
    );
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.formLogin(form -> form.permitAll());
    httpSecurity.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/webjars/**").permitAll();
      auth.requestMatchers("/user/**").hasRole("USER");
      auth.requestMatchers("/admin/**").hasRole("ADMIN");
      auth.anyRequest().authenticated();
    });
    httpSecurity.exceptionHandling(eh -> eh.accessDeniedPage("/notAuthorized"));
    return httpSecurity.build();
  }
}