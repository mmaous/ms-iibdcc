package ma.enset.digitalbanking.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.digitalbanking.dtos.BankAccountDTO;
import ma.enset.digitalbanking.dtos.CustomerDTO;
import ma.enset.digitalbanking.services.IBankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {

  private IBankAccountService bankAccountService;

  @GetMapping("/customers")
  public List<CustomerDTO> customers() {
    return bankAccountService.listCustomers();
  }

  @PostMapping("/customers")
  public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
    return bankAccountService.saveCustomer(customerDTO);
  }

  @GetMapping("/customers/search")
  public List<CustomerDTO> searchCustomers(
      @RequestParam(name = "keyword", defaultValue = "") String keyword
  ) {
    return bankAccountService.searchCustomers("%" + keyword + "%");
  }

  @GetMapping("/customers/{customerId}/accounts")
  public List<BankAccountDTO> getAccountsByCustomer(@PathVariable Long customerId) {
    return bankAccountService.getBankAccountsByCustomerId(customerId);
  }
}
