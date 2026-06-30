package ma.enset.digitalbanking.services;

import ma.enset.digitalbanking.dtos.*;

import java.util.List;

public interface IBankAccountService {
  CustomerDTO saveCustomer(CustomerDTO customerDTO);

  List<CustomerDTO> searchCustomers(String keyword);

  CurrentAccountDTO saveCurrentBankAccount(
      double initialBalance,
      double overDraft,
      Long customerId
  );

  SavingAccountDTO saveSavingBankAccount(
      double initialBalance,
      double interestRate,
      Long customerId
  );

  List<CustomerDTO> listCustomers();

  BankAccountDTO getBankAccount(String accountId);

  void debit(String accountId, double amount, String description);

  void credit(String accountId, double amount, String description);

  void transfer(
      String accountIdSource,
      String accountIdDestination,
      double amount
  );

  List<BankAccountDTO> bankAccountList();

  List<AccountOperationDTO> accountHistory(String accountId);

  List<BankAccountDTO> getBankAccountsByCustomerId(Long customerId);
}
