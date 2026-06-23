package ma.enset.digitalbanking.mappers;

import ma.enset.digitalbanking.dtos.AccountOperationDTO;
import ma.enset.digitalbanking.dtos.CurrentAccountDTO;
import ma.enset.digitalbanking.dtos.CustomerDTO;
import ma.enset.digitalbanking.dtos.SavingAccountDTO;
import ma.enset.digitalbanking.entities.AccountOperation;
import ma.enset.digitalbanking.entities.CurrentAccount;
import ma.enset.digitalbanking.entities.Customer;
import ma.enset.digitalbanking.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

  public CustomerDTO fromCustomer(Customer customer) {
    CustomerDTO customerDTO = new CustomerDTO();
    BeanUtils.copyProperties(customer, customerDTO);
    return customerDTO;
  }

  public Customer fromCustomerDTO(CustomerDTO customerDTO) {
    Customer customer = new Customer();
    BeanUtils.copyProperties(customerDTO, customer);
    return customer;
  }

  public SavingAccountDTO fromSavingBankAccount(SavingAccount savingAccount) {
    SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
    BeanUtils.copyProperties(savingAccount, savingAccountDTO);
    savingAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
    savingAccountDTO.setType("SAVE");
    return savingAccountDTO;
  }

  public CurrentAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
    CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
    BeanUtils.copyProperties(currentAccount, currentAccountDTO);
    currentAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
    currentAccountDTO.setType("CURR");
    return currentAccountDTO;
  }

  public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
    AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
    BeanUtils.copyProperties(accountOperation, accountOperationDTO);
    return accountOperationDTO;
  }
}