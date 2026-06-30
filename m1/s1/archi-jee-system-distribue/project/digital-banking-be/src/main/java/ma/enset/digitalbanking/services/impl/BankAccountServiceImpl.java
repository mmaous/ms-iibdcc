package ma.enset.digitalbanking.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.digitalbanking.dtos.*;
import ma.enset.digitalbanking.entities.*;
import ma.enset.digitalbanking.enums.OperationType;
import ma.enset.digitalbanking.exceptions.BalanceNotSufficientException;
import ma.enset.digitalbanking.exceptions.BankAccountNotFoundException;
import ma.enset.digitalbanking.exceptions.CustomerNotFoundException;
import ma.enset.digitalbanking.mappers.BankAccountMapperImpl;
import ma.enset.digitalbanking.repositories.AccountOperationRepository;
import ma.enset.digitalbanking.repositories.BankAccountRepository;
import ma.enset.digitalbanking.repositories.CustomerRepository;
import ma.enset.digitalbanking.services.IBankAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements IBankAccountService {

  private CustomerRepository customerRepository;
  private BankAccountRepository bankAccountRepository;
  private AccountOperationRepository accountOperationRepository;
  private BankAccountMapperImpl dtoMapper;

  @Override
  public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
    log.info("Saving new Customer");
    Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
    Customer savedCustomer = customerRepository.save(customer);
    return dtoMapper.fromCustomer(savedCustomer);
  }

  @Override
  public CurrentAccountDTO saveCurrentBankAccount(
      double initialBalance,
      double overDraft,
      Long customerId
  ) {
    Customer customer = customerRepository
        .findById(customerId)
        .orElseThrow(() ->
            new CustomerNotFoundException("Customer not found")
        );
    CurrentAccount currentAccount = new CurrentAccount();
    currentAccount.setId(UUID.randomUUID().toString());
    currentAccount.setCreatedAt(new Date());
    currentAccount.setBalance(initialBalance);
    currentAccount.setCustomer(customer);
    currentAccount.setOverDraft(overDraft);
    CurrentAccount savedBankAccount = bankAccountRepository.save(
        currentAccount
    );
    return dtoMapper.fromCurrentBankAccount(savedBankAccount);
  }

  @Override
  public SavingAccountDTO saveSavingBankAccount(
      double initialBalance,
      double interestRate,
      Long customerId
  ) {
    Customer customer = customerRepository
        .findById(customerId)
        .orElseThrow(() ->
            new CustomerNotFoundException("Customer not found")
        );
    SavingAccount savingAccount = new SavingAccount();
    savingAccount.setId(UUID.randomUUID().toString());
    savingAccount.setCreatedAt(new Date());
    savingAccount.setBalance(initialBalance);
    savingAccount.setCustomer(customer);
    savingAccount.setInterestRate(interestRate);
    SavingAccount savedBankAccount = bankAccountRepository.save(
        savingAccount
    );
    return dtoMapper.fromSavingBankAccount(savedBankAccount);
  }

  @Override
  public List<CustomerDTO> searchCustomers(String keyword) {
    List<Customer> customers = customerRepository.searchCustomer(keyword);
    return customers
        .stream()
        .map(cust -> dtoMapper.fromCustomer(cust))
        .collect(Collectors.toList());
  }

  @Override
  public List<CustomerDTO> listCustomers() {
    List<Customer> customers = customerRepository.findAll();
    return customers
        .stream()
        .map(customer -> dtoMapper.fromCustomer(customer))
        .collect(Collectors.toList());
  }

  @Override
  public BankAccountDTO getBankAccount(String accountId) {
    BankAccount bankAccount = bankAccountRepository
        .findById(accountId)
        .orElseThrow(() ->
            new BankAccountNotFoundException("BankAccount not found")
        );

    if (bankAccount instanceof SavingAccount) {
      SavingAccount savingAccount = (SavingAccount) bankAccount;
      return dtoMapper.fromSavingBankAccount(savingAccount);
    } else {
      CurrentAccount currentAccount = (CurrentAccount) bankAccount;
      return dtoMapper.fromCurrentBankAccount(currentAccount);
    }
  }

  @Override
  public void debit(String accountId, double amount, String description) {
    BankAccount bankAccount = bankAccountRepository
        .findById(accountId)
        .orElseThrow(() ->
            new BankAccountNotFoundException("BankAccount not found")
        );

    
    if (bankAccount.getBalance() < amount) {
      throw new BalanceNotSufficientException("Insufficient Balance");
    }

    AccountOperation accountOperation = new AccountOperation();
    accountOperation.setType(OperationType.DEBIT);
    accountOperation.setAmount(amount);
    accountOperation.setDescription(description);
    accountOperation.setOperationDate(new Date());
    accountOperation.setBankAccount(bankAccount);
    accountOperationRepository.save(accountOperation);

    bankAccount.setBalance(bankAccount.getBalance() - amount);
    bankAccountRepository.save(bankAccount);
  }

  @Override
  public void credit(String accountId, double amount, String description) {
    BankAccount bankAccount = bankAccountRepository
        .findById(accountId)
        .orElseThrow(() ->
            new BankAccountNotFoundException("BankAccount not found")
        );

    AccountOperation accountOperation = new AccountOperation();
    accountOperation.setType(OperationType.CREDIT);
    accountOperation.setAmount(amount);
    accountOperation.setDescription(description);
    accountOperation.setOperationDate(new Date());
    accountOperation.setBankAccount(bankAccount);
    accountOperationRepository.save(accountOperation);

    bankAccount.setBalance(bankAccount.getBalance() + amount);
    bankAccountRepository.save(bankAccount);
  }

  @Override
  public void transfer(
      String accountIdSource,
      String accountIdDestination,
      double amount
  ) {
    
    debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
    credit(
        accountIdDestination,
        amount,
        "Transfer from " + accountIdSource
    );
  }

  @Override
  public List<BankAccountDTO> bankAccountList() {
    List<BankAccount> bankAccounts = bankAccountRepository.findAll();
    return bankAccounts
        .stream()
        .map(bankAccount -> {
          if (bankAccount instanceof SavingAccount) {
            return dtoMapper.fromSavingBankAccount(
                (SavingAccount) bankAccount
            );
          } else {
            return dtoMapper.fromCurrentBankAccount(
                (CurrentAccount) bankAccount
            );
          }
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<AccountOperationDTO> accountHistory(String accountId) {
    List<AccountOperation> accountOperations =
        accountOperationRepository.findByBankAccountId(accountId);
    return accountOperations
        .stream()
        .map(op -> dtoMapper.fromAccountOperation(op))
        .collect(Collectors.toList());
  }

  @Override
  public List<BankAccountDTO> getBankAccountsByCustomerId(Long customerId) {
    List<BankAccount> bankAccounts = bankAccountRepository.findByCustomerId(customerId);
    return bankAccounts.stream().map(bankAccount -> {
      if (bankAccount instanceof SavingAccount) {
        return dtoMapper.fromSavingBankAccount((SavingAccount) bankAccount);
      } else {
        return dtoMapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
      }
    }).collect(Collectors.toList());
  }

}
