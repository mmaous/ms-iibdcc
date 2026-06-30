package ma.enset.digitalbanking.dtos;

import java.util.List;
import java.util.Map;

public record DashboardStatsDTO(
    List<MonthlyOperationDTO> monthlyOperations,
    Map<String, Long> accountTypeDistribution,
    List<Double> recentTransactionVolume,
    List<CustomerBalanceDTO> balancePerCustomer
) {}

