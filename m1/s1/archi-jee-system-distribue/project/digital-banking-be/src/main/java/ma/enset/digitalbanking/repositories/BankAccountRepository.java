package ma.enset.digitalbanking.repositories;

import ma.enset.digitalbanking.dtos.CustomerBalanceDTO;
import ma.enset.digitalbanking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
  List<BankAccount> findByCustomerId(Long customerId);

  @Query("SELECT new ma.enset.digitalbanking.dtos.CustomerBalanceDTO(b.customer.name, SUM(b.balance)) " +
      "FROM BankAccount b GROUP BY b.customer.name ORDER BY SUM(b.balance) DESC LIMIT 5")
  List<CustomerBalanceDTO> findTop5CustomersByBalance();

  @Query("SELECT TYPE(b), COUNT(b) FROM BankAccount b GROUP BY TYPE(b)")
  List<Object[]> countAccountTypeDistribution();
}