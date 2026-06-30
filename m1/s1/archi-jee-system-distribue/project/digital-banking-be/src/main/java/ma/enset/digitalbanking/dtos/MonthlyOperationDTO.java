package ma.enset.digitalbanking.dtos;

public record MonthlyOperationDTO(
    String month,
    Double debitSum,
    Double creditSum
) {}