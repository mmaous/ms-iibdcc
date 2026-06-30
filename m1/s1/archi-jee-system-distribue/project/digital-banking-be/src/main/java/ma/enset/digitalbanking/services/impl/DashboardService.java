package ma.enset.digitalbanking.services.impl;


import ma.enset.digitalbanking.dtos.DashboardStatsDTO;
import ma.enset.digitalbanking.dtos.MonthlyOperationDTO;
import ma.enset.digitalbanking.repositories.AccountOperationRepository;
import ma.enset.digitalbanking.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private final BankAccountRepository accountRepo;
    private final AccountOperationRepository operationRepo;

    public DashboardService(BankAccountRepository accountRepo, AccountOperationRepository operationRepo) {
        this.accountRepo = accountRepo;
        this.operationRepo = operationRepo;
    }

    public DashboardStatsDTO getDashboardStats() {
        // 1. Account Types
        Map<String, Long> accountDistribution = accountRepo.countAccountTypeDistribution().stream()
            .collect(Collectors.toMap(
                row -> {
                    Class<?> accountClass = (Class<?>) row[0];
                    String className = accountClass.getSimpleName();
                    if (className.equals("CurrentAccount")) return "Current Account";
                    if (className.equals("SavingAccount")) return "Saving Account";
                    return className;
                },
                row -> (Long) row[1]
            ));

        // 2. Monthly Operations (Postgres-safe mapping)
        List<MonthlyOperationDTO> monthlyOps = operationRepo.aggregateMonthlyOperationsRaw().stream()
            .map(row -> {
                // Extract the month number (1-12) and convert to a word like "JANUARY"
                int monthNum = ((Number) row[0]).intValue();
                String monthName = Month.of(monthNum).name();

                // Safely cast the sums
                Double debitSum = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
                Double creditSum = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;

                return new MonthlyOperationDTO(monthName, debitSum, creditSum);
            })
            .collect(Collectors.toList());

        return new DashboardStatsDTO(
            monthlyOps,
            accountDistribution,
            operationRepo.findRecentTransactionVolumes(),
            accountRepo.findTop5CustomersByBalance()
        );

    }
}