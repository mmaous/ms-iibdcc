package ma.enset.digitalbanking.repositories;

import ma.enset.digitalbanking.dtos.MonthlyOperationDTO;
import ma.enset.digitalbanking.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
  List<AccountOperation> findByBankAccountId(String accountId);

  @Query("SELECT MONTH(o.operationDate), " +
      "SUM(CASE WHEN o.type = 'DEBIT' THEN o.amount ELSE 0.0 END), " +
      "SUM(CASE WHEN o.type = 'CREDIT' THEN o.amount ELSE 0.0 END) " +
      "FROM AccountOperation o GROUP BY MONTH(o.operationDate)")
  List<Object[]> aggregateMonthlyOperationsRaw();

  @Query("SELECT o.amount FROM AccountOperation o ORDER BY o.operationDate DESC LIMIT 10")
  List<Double> findRecentTransactionVolumes();

}